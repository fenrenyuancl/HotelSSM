<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="copyright"
	content="All Rights Reserved, Copyright (C) 2019, cl." />
<title>后台管理主页</title>
<link rel="stylesheet" type="text/css"
	href="../resources/admin/easyui/easyui/1.3.4/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/admin/easyui/css/wu.css" />
<link rel="stylesheet" type="text/css"
	href="../resources/admin/easyui/css/icon.css" />
<script type="text/javascript"
	src="../resources/admin/easyui/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="../resources/admin/easyui/easyui/1.3.4/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../resources/admin/easyui/easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<!-- 以下解决easyui载入时短暂混乱 -->
<script>
function closes(){
	$("#Loading").fadeOut("normal",function(){
		$(this).remove();
	});
}
var pc;
$.parser.onComplete = function(){
	if(pc) clearTimeout(pc);
	pc = setTimeout(closes, 1000);
}
/* 以上解决easyui载入时短暂混乱  */
</script>
</head>
<body class="easyui-layout">
	<!-- 以下解决easyui载入时短暂混乱 -->
	<div id='Loading'
		style="position: absolute; z-index: 1000; top: 0px; left: 0px; width: 100%; height: 100%; 
			background: #DDDDDB ; 
			text-align: center; padding-top: 20%;">
		<h1>
			<font color="#15428B">加载中···</font>
		</h1>
	</div>
	<!-- 以上解决easyui载入时短暂混乱 -->
	
	<!-- begin of header -->
	<div class="wu-header"
		data-options="region:'north',border:false,split:true">
		<div class="wu-header-left">
			<h1>【疯人愿】后台管理系统</h1>
		</div>
		<div class="wu-header-right">
			<p>
				<strong class="easyui-tooltip" >${role.name}:${user.username}</strong>，欢迎您！
			</p>
			<p>
				<a href="../home/index" target="_blank">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>|<a
					href="logout">安全退出</a>
			</p>
		</div>
	</div>
	<!-- end of header -->
	<!-- begin of sidebar -->
	<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'"> 
    	<div class="easyui-accordion" data-options="border:false,fit:true"> 
        	<c:forEach items="${topMenuList }" var="topMenu">
        	<div title="${topMenu.name }" data-options="iconCls:'${topMenu.icon }'" style="padding:5px;">  	
    			<ul class="easyui-tree wu-side-tree">
                	<c:forEach items="${secondMenuList }" var="secondMenu">
                		<c:if test="${secondMenu.parentId == topMenu.id }">
                			<li iconCls="${secondMenu.icon }"><a href="javascript:void(0)" data-icon="${secondMenu.icon }" data-link="${secondMenu.url }?_mid=${secondMenu.id }" iframe="1">${secondMenu.name }</a></li>
                    	</c:if>
                    </c:forEach>
                </ul>
            </div>
            </c:forEach>
        </div>
    </div>
	<!-- end of sidebar -->
	<!-- begin of main -->
	<div class="wu-main" data-options="region:'center'">
		<div id="wu-tabs" class="easyui-tabs"
			data-options="border:false,fit:true">
			<div title="首页"
				data-options="href:'welcome',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
		</div>
	</div>
	<!-- end of main -->
	<!-- begin of footer -->
	<div class="wu-footer"
		data-options="region:'south',border:true,split:true">&copy; 2019
		CL All Rights Reserved</div>
	<!-- end of footer -->
	<script type="text/javascript">
		$(function() {
			$('.wu-side-tree a').bind("click", function() {
				var title = $(this).text();
				var url = $(this).attr('data-link');
				var iconCls = $(this).attr('data-icon');
				var iframe = $(this).attr('iframe') == 1 ? true : false;
				addTab(title, url, iconCls, iframe);
			});
		})

		/**
		 * Name 载入树形菜单 
		 */
		$('#wu-side-tree').tree({
			url : 'temp/menu.php',
			cache : false,
			onClick : function(node) {
				var url = node.attributes['url'];
				if (url == null || url == "") {
					return false;
				} else {
					addTab(node.text, url, '', node.attributes['iframe']);
				}
			}
		});

		/**
		 * Name 选项卡初始化
		 */
		$('#wu-tabs').tabs({
			tools : [ {
				iconCls : 'icon-reload',
				border : false,
				handler : function() {
					$('#wu-datagrid').datagrid('reload');
				}
			} ]
		});

		/**
		 * Name 添加菜单选项
		 * Param title 名称
		 * Param href 链接
		 * Param iconCls 图标样式
		 * Param iframe 链接跳转方式（true为iframe，false为href）
		 */
		function addTab(title, href, iconCls, iframe) {
			var tabPanel = $('#wu-tabs');
			if (!tabPanel.tabs('exists', title)) {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'
						+ href + '" style="width:100%;height:100%;"></iframe>';
				if (iframe) {
					tabPanel.tabs('add', {
						title : title,
						content : content,
						iconCls : iconCls,
						fit : true,
						cls : 'pd3',
						closable : true
					});
				} else {
					tabPanel.tabs('add', {
						title : title,
						href : href,
						iconCls : iconCls,
						fit : true,
						cls : 'pd3',
						closable : true
					});
				}
			} else {
				tabPanel.tabs('select', title);
			}
		}
		/**
		 * Name 移除菜单选项
		 */
		function removeTab() {
			var tabPanel = $('#wu-tabs');
			var tab = tabPanel.tabs('getSelected');
			if (tab) {
				var index = tabPanel.tabs('getTabIndex', tab);
				tabPanel.tabs('close', index);
			}
		}
	</script>
</body>
</html>
