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
            <label>房型名称:</label><input id="search-name" class="wu-text" style="width:100px">
            <label>房间状态:</label>
				<select id="search-status" class="easyui-combobox" panelHeight="auto" style="width:120px">
            	<option value="-1">全部</option>
            	<option value="0">房型已满</option>
            	<option value="1">可预订可入住</option>
            </select>
            <a href="#" id="search-btn" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- 添加窗口 -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:420px; padding:10px;">
	<form id="add-form" method="post">
        <table>
        	<tr>
                <td width="60" align="right">图片预览:</td>
                <td valign="middle">
                	<img id="preview-photo" style="float:left;" src="/SummerTest4/resources/admin/easyui/images/user_photo.jpg" width="100px">
                	<a style="float:left;margin-top:40px;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()" plain="true">上传图片</a>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">房间图片:</td>
                <td><input type="text" id="add-photo" name="photo" value="/SummerTest4/resources/admin/easyui/images/user_photo.jpg" readonly="readonly" class="wu-text " /></td>
            </tr>
          <tr>
              <td width="60" align="right">房型名称:</td>
              <td><input type="text" id="add-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型名称'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">价格:</td>
              <td><input type="text" id="add-price" name="price" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型价格'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">可住人数:</td>
              <td><input type="text" id="add-liveNum" name="liveNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型可住人数'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">床位数:</td>
              <td><input type="text" id="add-bedNum" name="bedNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型床位数'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">房间数:</td>
              <td><input type="text" id="add-roomNum" name="roomNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间数'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">状态:</td>
              <td>
             	 <select id="add-status" name="status" class="easyui-combobox" panelHeight="auto" style="width:268px">
            		<option value="0">房型已满</option>
            		<option value="1">可预订可入住</option>
            	 </select>
              </td>
          </tr>
          <tr>
              <td align="right">备注:</td>
              <td><textarea id="add-remark" name="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
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
                <td width="60" align="right">图片预览:</td>
                <td valign="middle">
                	<img id="edit-preview-photo" style="float:left;" src="/SummerTest5/resources/admin/easyui/images/user_photo.jpg" width="100px">
                	<a style="float:left;margin-top:40px;" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-upload" onclick="uploadPhoto()" plain="true">上传图片</a>
                </td>
            </tr>
            <tr>
                <td width="60" align="right">房间图片:</td>
                <td><input type="text" id="edit-photo" name="photo" value="/SummerTest5/WebContent/resources/admin/easyui/images/bigbed.jpg" readonly="readonly" class="wu-text " /></td>
            </tr>
          <tr>
              <td width="60" align="right">房型名称:</td>
              <td><input type="text" id="edit-name" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型名称'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">价格:</td>
              <td><input type="text" id="edit-price" name="price" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型价格'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">可住人数:</td>
              <td><input type="text" id="edit-liveNum" name="liveNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型可住人数'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">床位数:</td>
              <td><input type="text" id="edit-bedNum" name="bedNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间类型床位数'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">房间数:</td>
              <td><input type="text" id="edit-roomNum" name="roomNum" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写房间数'" /></td>
          </tr>
          <tr>
              <td width="60" align="right">状态:</td>
              <td>
             	 <select id="edit-status" name="status" class="easyui-combobox" panelHeight="auto" style="width:268px">
            		<option value="0">房型已满</option>
            		<option value="1">可预订可入住</option>
            	 </select>
              </td>
          </tr>
          <tr>
              <td align="right">备注:</td>
              <td><textarea id="edit-remark" name="remark" rows="6" class="wu-textarea" style="width:260px"></textarea></td>
          </tr>
        </table>
    </form>
</div>
<div id="process-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-upload',title:'正在上传图片'" style="width:450px; padding:10px;">
<div id="p" class="easyui-progressbar" style="width:400px;" data-options="text:'正在上传中...'"></div>
</div>
<input type="file" id="photo-file" style="display:none;" onchange="upload()">
<!-- End of easyui-dialog -->
<script type="text/javascript">
//上传图片
function start(){
	var value = $('#p').progressbar('getValue');
	if (value < 100){
		value += Math.floor(Math.random() * 10);
		$('#p').progressbar('setValue', value);
	}else{
		$('#p').progressbar('setValue',0)
	}
};
function upload(){
	if($("#photo-file").val() == '')return;
	var formData = new FormData();
	formData.append('photo',document.getElementById('photo-file').files[0]);
	$("#process-dialog").dialog('open');
	var interval = setInterval(start,200);
	$.ajax({
		url:'../user/upload_photo',
		type:'post',
		data:formData,
		contentType:false,
		processData:false,
		success:function(data){
			clearInterval(interval);
			$("#process-dialog").dialog('close');
			if(data.type == 'success'){
				$("#preview-photo").attr('src',data.filepath);
				$("#add-photo").val(data.filepath);
				$("#edit-preview-photo").attr('src',data.filepath);
				$("#edit-photo").val(data.filepath);
			}else{
				$.messager.alert("消息提醒",data.msg,"warning");
			}
		},
		error:function(data){
			clearInterval(interval);
			$("#process-dialog").dialog('close');
			$.messager.alert("消息提醒","上传失败!","warning");
		}
	});
}

function uploadPhoto(){
$("#photo-file").click();
}


/**
* 打开添加窗口
*/
function openAdd(){
	//$('#add-form').form('clear');
	$('#add-dialog').dialog({
		closed: false,
		modal:true,
        title: "添加房间类型信息",
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
        					$('#add-name').val('');
        					$('#add-remark').val('');
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
        title: "修改房间类型信息",
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
        	$('#edit-name').val(item.name);
        	$('#edit-price').val(item.price);
        	$('#edit-liveNum').val(item.liveNum);
        	$('#edit-bedNum').val(item.bedNum);
        	$('#edit-roomNum').val(item.roomNum);
        	$('#edit-status').combobox('setValue',item.status);
        	$('#edit-remark').val(item.remark);
        	$('#edit-photo').val(item.photo);
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
		var option ={name:$("#search-name").val()};
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
			{ field:'photo',title:'房间照片',width:100,align:'center',formatter:function(value,row,index){
				var img = '<img src="'+value+'" width="50px" />';
				return img;
			}},
			{ field:'name',title:'房型名称',width:100,sortable:true},
			{ field:'price',title:'房型价格',width:100,sortable:true},
			{ field:'liveNum',title:'可住人数',width:80,sortable:true},
			{ field:'bedNum',title:'床位数',width:80,sortable:true},
			{ field:'roomNum',title:'房间数',width:80,sortable:true},
			{ field:'avilableNum',title:'可住或可预订房间数',width:100,sortable:true},
			{ field:'bookNum',title:'已预订数',width:100,sortable:true},
			{ field:'livedNum',title:'已入住数',width:100,sortable:true},
			{ field:'status',title:'房间状态',width:100,formatter:function(value,row,index){
				switch(value){
					case 0:{
						return '房型已满';
					}
					case 1:{
						return '可预订可入住';
					}
				}
			}},
			{ field:'remark',title:'备注',width:180,sortable:true},
		]]
	});
</script>
<%@include file="../common/footer.jsp"%>