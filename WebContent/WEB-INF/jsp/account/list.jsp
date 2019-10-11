<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../common/header.jsp"%>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
        <%@include file="../common/menus.jsp"%>	
        </div>
        <div class="wu-toolbar-search">
            <label>登录名:</label><input id="search-username" class="wu-text" style="width:100px">
           	<label>真实姓名:</label><input id="search-name" class="wu-text" style="width:80px">
           	<label>身份证号:</label><input id="search-idCard" class="wu-text" style="width:80px">
           	<label>手机号码:</label><input id="search-phoneNum" class="wu-text" style="width:80px">
            <label>客户状态:</label>
				<select id="search-status" class="easyui-combobox" panelHeight="auto" style="width:120px">
            	<option value="-1">全部</option>
            	<option value="0">可用</option>
            	<option value="1">冻结</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加窗口 -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:460px; padding:10px;">
	<form id="add-form" method="post">
        <table>
          <tr>
              <td width="80" align="right">客户登录名:</td>
              <td><input type="text" id="add-username" name="username" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写客户登录名'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">密码:</td>
              <td><input type="text" id="add-password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写登陆密码'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">姓名:</td>
              <td><input type="text" id="add-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写客户姓名'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">身份证:</td>
              <td><input type="text" id="add-idCard" name="idCard" class="wu-text easyui-validatebox" /></td>
          </tr>
          <tr>
              <td width="60" align="right">手机:</td>
              <td><input type="text" id="add-phoneNum" name="phoneNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写手机号码'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">地址:</td>
              <td><input type="text" id="add-address" name="address" class="wu-text easyui-validatebox" /></td>
          </tr>
          <tr>
              <td width="60" align="right">状态:</td>
              <td>
             	 <select id="add-status" name="status" class="easyui-combobox" panelHeight="auto" style="width:268px">
            		<option value="-1">全部</option>
	            	<option value="0">可用</option>
	            	<option value="1">冻结</option>
            	 </select>
              </td>
          </tr>
        </table>
    </form>
</div>
<!-- 修改窗口 -->
<div id="edit-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
	<form id="edit-form" method="post">
       <input type="hidden" name="id" id="edit-id">
       <table>
          <tr>
              <td width="80" align="right">客户登录名:</td>
              <td><input type="text" id="edit-username" name="username" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写客户登录名'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">密码:</td>
              <td><input type="text" id="edit-password" name="password" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写登陆密码'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">姓名:</td>
              <td><input type="text" id="edit-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写客户姓名'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">身份证:</td>
              <td><input type="text" id="edit-idCard" name="idCard" class="wu-text easyui-validatebox" /></td>
          </tr>
          <tr>
              <td width="60" align="right">手机:</td>
              <td><input type="text" id="edit-phoneNum" name="phoneNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写手机号码'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">地址:</td>
              <td><input type="text" id="edit-address" name="address" class="wu-text easyui-validatebox" /></td>
          </tr>
          <tr>
              <td width="60" align="right">状态:</td>
              <td>
             	 <select id="edit-status" name="status" class="easyui-combobox" panelHeight="auto" style="width:268px">
            		<option value="-1">全部</option>
	            	<option value="0">可用</option>
	            	<option value="1">冻结</option>
            	 </select>
              </td>
          </tr>
        </table>
    </form>
</div>

<!-- End of easyui-dialog -->
<script type="text/javascript">
	
/**
* 打开添加窗口
*/
function openAdd(){
	//$('#add-form').form('clear');
	$('#add-dialog').dialog({
		closed: false,
		modal:true,
        title: "添加客户类型信息",
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler:function add(){
        		var validate = $("#add-form").form("validate");
        		if(!validate){
        			$.messager.alert("消息提醒","请检查你输入的数据!","warning");
        			return;
        		}
        		var data = $("#add-form").serialize();
        		$.ajax({
        			url:'add',
        			dataType:'json',
        			type:'post',
        			data:data,
        			success:function(data){
        				if(data.type == 'success'){
        					$.messager.alert('信息提示','添加成功！','info');
        					$('#add-dialog').dialog('close');
        					$('#add-username').val('');
        					$('#add-password').val('');
        					$('#add-name').val('');
        					$('#add-idCard').val('');
        					$('#add-phoneNum').val('');
        					$('#add-address').val('');
        					$('#data-datagrid').datagrid('reload');
        				}else{
        					$.messager.alert('信息提示',data.msg,'warning');
        				}
        			}
        		});
        	}
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#add-dialog').dialog('close');                    
            }
        }],
        onBeforeOpen:function(){
        	//$("#add-form input").val('');
        }
    });
}
/**
* 打开修改窗口
*/
function openEdit(){
	//$('#add-form').form('clear');
	var item = $('#data-datagrid').datagrid('getSelected');
	if(item == null || item.length == 0){
		$.messager.alert('信息提示','请选择要编辑的数据！','info');
		return;
	}
	$('#edit-dialog').dialog({
		closed: false,
		modal:true,
        title: "修改客户类型信息",
        buttons: [{
            text: '确定',
            iconCls: 'icon-ok',
            handler:function edit(){
        		var validate = $("#edit-form").form("validate");
        		if(!validate){
        			$.messager.alert("消息提醒","请检查你输入的数据!","warning");
        			return;
        		}
        		var data = $("#edit-form").serialize();
        		$.ajax({
        			url:'edit',
        			dataType:'json',
        			type:'post',
        			data:data,
        			success:function(data){
        				if(data.type == 'success'){
        					$.messager.alert('信息提示','修改成功！','info');
        					$('#edit-dialog').dialog('close');
        					$('#data-datagrid').datagrid('reload');
        				}else{
        					$.messager.alert('信息提示',data.msg,'warning');
        				}
        			}
        		});
        	}
        }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: function () {
                $('#edit-dialog').dialog('close');                    
            }
        }],
        onBeforeOpen:function(){
        	$('#edit-id').val(item.id);
        	$('#edit-username').val(item.username);
        	$('#edit-password').val(item.password);
        	$('#edit-name').val(item.name);
        	$('#edit-idCard').val(item.idCard);
        	$('#edit-phoneNum').val(item.phoneNum);
        	$('#edit-address').val(item.address);
        	$('#edit-status').combobox('setValue',item.status);
        	$('#edit-remark').val(item.remark);
        }
    });
}
	
	/**
	* 删除记录
	*/
	function remove(){
		$.messager.confirm('信息提示','确定要删除该记录？', function(result){
			if(result){
				var item = $('#data-datagrid').datagrid('getSelected');
				if(item == null || item.length == 0){
					$.messager.alert('信息提示','请选择要删除的数据！','info');
					return;
				}
				$.ajax({
					url:'delete',
					dataType:'json',
					type:'post',
					data:{id:item.id},
					success:function(data){
						if(data.type == 'success'){
							$.messager.alert('信息提示','删除成功！','info');
							$('#data-datagrid').datagrid('reload');
						}else{
							$.messager.alert('信息提示',data.msg,'warning');
						}
					}
				});
			}	
		});
	}
	
	
	//搜索按钮监听
	$("#search-btn").click(function(){
		var option ={username:$("#search-username").val()};
		option.name = $("#search-name").val();
		option.idCard = $("#search-idCard").val();
		option.phoneNum = $("#search-phoneNum").val();
		var status = $("#search-status").combobox('getValue');
		if(status != -1){
			option.status = status;
		}
		$('#data-datagrid').datagrid('reload',option);
	});
	
	/** 
	* 载入数据
	*/
	$('#data-datagrid').datagrid({
		url:'list',
		rownumbers:true,
		singleSelect:true,//true为单选，false多选
		pageSize:20, 		//每页显示条数          
		pagination:true,	
		multiSort:true,
		fitColumns:true,
		idField:'id',
	    treeField:'name',
		fit:true,
		columns:[[
			{ field:'chk',checkbox:true},
			{ field:'username',title:'登录名',width:100,sortable:true},
			{ field:'password',title:'密码',width:100,sortable:true},
			{ field:'name',title:'真实姓名',width:100,sortable:true},
			{ field:'idCard',title:'身份证',width:100,sortable:true},
			{ field:'phoneNum',title:'手机',width:100,sortable:true},
			{ field:'address',title:'地址',width:100,sortable:true},
			{ field:'status',title:'客户状态',width:100,formatter:function(value,row,index){
				switch(value){
					case 0:{
						return '可用';
					}
					case 1:{
						return '冻结';
					}
				}
			}},
		]]
	});
</script>
<%@include file="../common/footer.jsp"%>