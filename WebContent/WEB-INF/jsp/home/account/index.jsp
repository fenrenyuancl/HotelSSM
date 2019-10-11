<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head lang="en">
  <meta charset="UTF-8">
  <title>用户中心</title>
  <link rel="stylesheet" href="../../resources/home/css/index.css"/>
  <link rel="stylesheet" href="../../resources/home/css/myOrder.css"/>
</head>
<body>
    <!--头部-->
    <div id="c_header"></div>
    <!--主体-->
    <div id="contain">
        <!--tab选项卡-->
      <ul class="tabs">
       
        <li><a href="../index"><p style="font-size:180%">首页</p></a></li>
        <li><a href="book_order">预定房间</a></li>
        <li><a href="#order">我的订单</a></li>
        <li><a href="#info">我的资料</a></li>
        <li><a href="#pwd">修改密码</a></li>

      </ul>

      <div class="content">
        
        <div class="order" style="display: block;">
          <table>
            <thead>
            <tr>
              <!--<th colspan="4">订单编号：</th>-->
              <!--<th colspan="2" >订单时间:</th>-->
              <th>房型图片</th>
              <th>房型</th>
              <th>入住人</th>
              <th>手机号</th>
              <th>身份证</th>
              <th>入住时间</th>
              <th>离店时间</th>
              <th>状态</th>
              <th>备注</th>
            </tr>
            </thead>
            <tbody>
               <c:forEach items="${bookOrderList }" var="bookOrder">
	               <tr>
	               	  <c:forEach items="${roomTypeList }" var="roomType">
	               	  <c:if test="${roomType.id == bookOrder.roomTypeId }">
							<td><img src="${roomType.photo }" width="100px"></td>
							<td>${roomType.name }</td>
						</c:if>
					  </c:forEach>
						<td>${bookOrder.name} </td>
						<td>${bookOrder.phoneNum} </td>
						<td>${bookOrder.idCard} </td>
						<td>${bookOrder.arriveDate} </td>
						<td>${bookOrder.leaveDate} </td>
						<td>
							<c:if test="${bookOrder.status == 0 }">
				          		<font color="red">预定中</font>
				          	</c:if>
				          	<c:if test="${bookOrder.status == 1 }">
				          		已入住
				          	</c:if>
				          	<c:if test="${bookOrder.status == 2 }">
				          		已结算离店
				          	</c:if>
						</td>
						<td>${bookOrder.remark} </td>
	               </tr>
               </c:forEach>
            </tbody>
          </table>
          
        </div>
        <div class="info" >
          <form id="update-info-form">
          <table style="border:0px;cellspacing:0px;">
            <tbody>
               <tr>
					<td style="border:0px;">用户名：</td><td style="float:left;width:400px;max-width: 420px;border:0px;"><input class="form-control" type="text" value="${account.username}" name="username" /></td>
               </tr>
			   <tr style="border:0px;">
					<td style="border:0px;">真实姓名：</td><td style="float:left;width:400px;max-width: 820px;border:0px;"><input class="form-control" type="text" value="${account.name}" name="name" /></td>
               </tr>
			   <tr>
					<td style="border:0px;">身份证号：</td><td style="float:left;width:400px;max-width: 820px;border:0px;"><input class="form-control" type="text" value="${account.idCard}" name="idCard" /></td>
               </tr>
			   <tr>
					<td style="border:0px;">手机号码：</td><td style="float:left;width:400px;max-width: 820px;border:0px;"><input class="form-control" type="text" value="${account.phoneNum}" name="phoneNum" /></td>
               </tr>
			   <tr>
					<td style="border:0px;">联系地址：</td><td style="float:left;width:400px;max-width: 820px;border:0px;"><input class="form-control" type="text" value="${account.address}" name="address" /></td>
               </tr>
			   <tr>
					<td style="border:0px;"><button type="button" id="update-info-btn" class="btn btn-success" style="width:100px;">提交</button></td><td style="float:left;width:400px;max-width: 820px;border:0px;"></td>
               </tr>
            </tbody>
          </table>
         </form> 
        </div>
		<div class="pwd" >
		<form id="update-pwd-form">
          <table style="border:0px;cellspacing:0px;">
            <tbody>
               <tr>
					<td style="border:0px;">原密码：</td><td style="float:left;width:400px;max-width: 820px;border:0px;">
					<input class="form-control" type="password" id="oldpassword" />
					</td>
               </tr>
			   <tr style="border:0px;">
					<td style="border:0px;">新密码：</td><td style="float:left;width:400px;max-width: 820px;border:0px;"><input class="form-control" type="password" id="newpassword" /></td>
               </tr>
			   <tr>
					<td style="border:0px;">重复密码：</td><td style="float:left;width:400px;max-width: 820px;border:0px;"><input class="form-control" type="password" id="renewpassword"  /></td>
               </tr>
			   
			   <tr>
					<td style="border:0px;"></td><td style="float:left;margin-top:15px;width:400px;max-width: 820px;border:0px;"><button type="button" id="update-pwd-btn" class="btn btn-success" style="width:100px;">提交</button></td>
               </tr>
            </tbody>
          </table>
          </form>
        </div>
      </div>

    </div>
    <!--底部-->
    <div id="c_footer"></div>
    <script src="../../resources/home/js/jquery-1.11.3.js"></script>
 <script>
	$(".tabs").on("click","li a",function(){
    $(this).addClass("active").parents().siblings().children(".active").removeClass("active");
    var href=$(this).attr("href");
    href=href.slice(1);

    var $div=$("div.content>div."+href);
     $div.show().siblings().hide();
     //修改个人资料
     $("#update-info-btn").click(function(){
    	 $.ajax({
    		 url:"update_info",
    		 type:'post',
    		 dataType:'json',
    		 data:$("#update-info-form").serialize(),//序列化
    		 success:function(data){
    			 alert(data.msg);
    		 }
    	 });
     });
     //修改密码
     $("#update-pwd-btn").click(function(){
    	 var oldpassword = $("#oldpassword").val();
    	 var newpassword = $("#newpassword").val();
    	 var renewpassword = $("#renewpassword").val();
    	 if(oldpassword == ''){
    			alert('请填写原密码！');
        		return;
     	 }
    	 if(newpassword == ''){
    			alert('请填写新密码！');
        		return;
     	 }
    	 if(newpassword != renewpassword){
    			alert('两次密码不一致！');
        		return;
     	 }
    	 $.ajax({
    		 url:"update_pwd",
    		 type:'post',
    		 dataType:'json',
    		 data:{oldpassword:oldpassword,newpassword:newpassword},
    		 success:function(data){
    			 alert(data.msg);
    		 }
    	 });
     });
});
	 $(function(){
	    	$("#look-btn").mousedown(function(){
	    		$("#oldpassword").attr("type", "text");
	    	});
	    	$("#look-btn").mouseup(function(){
	    		$("#oldpassword").attr("type", "password");
	    	});
	    });	
	
</script>
    
</body>
</html>