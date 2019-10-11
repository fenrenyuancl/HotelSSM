<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
 <head>
  <meta charset="UTF-8">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <title>疯人愿|酒店登录界面</title>
  <link href="../resources/home/css/index.css" type="text/css" rel="Stylesheet" />
  <link href="../resources/home/css/login.css" type="text/css" rel="Stylesheet" />
 </head>
 <body>

       <header>
	          <div>
	          <font size="6" color="gray">疯人愿|酒店登录界面</font>
				<span>会员登录</span>
			  </div>

	   </header>
       <section>
	        <div class="left">
			    <img src="../resources/home/images\index.jpg" alt="">
			</div>
			<div class="login">
			     
				
				 

				 <div id="normal">
						 <ul id="nor_log">
							<li class="username" style="margin-top:25px;">
							   <input id="username" name="username"type="text" placeholder="请输入用户名">
							   <span class="icon"></span>
							</li>
							<li class="pwd" style="margin-top:25px;">
							   <input id="password" name="upwd" type="password" placeholder="密码">
							   <span class="icon"></span>
							</li>
						 </ul>
					   <div class="codes" style="margin-top:25px;">
							 <input id="cpacha" type="text" style="width:135px;" class="blur"  placeholder="请输入验证码"/>
							 <img id="cpacha-img" title="点击切换验证码" style="cuesor:pointer;" src="../system/get_cpacha?vl=4&w=135&h=33&type=loginCpacha" width="135px" heigth="33px" onclick="changeCpacha()">
						 </div>
						 
				 </div>
				
				 <div class="log" id="bt_login" style="margin-top:25px;">登 录</div>
			     
			</div>
			<div class="reg">
			   <a href="register">立即注册 &gt;&gt;</a>
			</div>
	   </section>
	  <script src="../resources/home/js/jquery-1.11.3.js"></script>
	  <script type="text/javascript">
		//点击切换验证码
		function changeCpacha() {
			$("#cpacha-img").attr("src",'../system/get_cpacha?vl=4&w=110&h=30&type=loginCpacha&t='+new Date().getTime());
		}
		//登录按钮事件
		$(document).ready(function(){
			$("#bt_login").click(function(){
				var username =$("#username").val();
				var password =$("#password").val();
				var cpacha =$("#cpacha").val();
				if(username == '' || password == '' || cpacha == ''){
					alert('请填写全部信息再登录！');
					return;
				}
				$.ajax({
					url:'login',
					type:'post',
					dataType:'json',
					data:{username:username,password:password,cpacha:cpacha},
					success:function(data){
						if(data.type == 'success'){
							//alert('登录成功！');
							window.location.href = 'index';
						}else{
							alert(data.msg);
							changeCpacha();
						}
					}
				});
			})
		});
	  </script>
<%@include file="../common/footer.jsp"%>