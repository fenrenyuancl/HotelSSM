<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>疯人愿|酒店注册界面</title>
  <link href="../resources/home/css/login.css" type="text/css" rel="Stylesheet" />
  <link href="../resources/home/css/regsiter.css" type="text/css" rel="Stylesheet" />
  <link href="../resources/home/css/index.css" type="text/css" rel="Stylesheet" />
  <style>
    #ad>ul {
       margin:0;
    }
  </style>
</head>
<body>
  <!--头部-->

  <header>
    <div>
     <font size="6" color="gray">疯人愿|酒店注册界面</font> <span>注册</span>
    </div>

  </header>
  <!--中间部分-->
  <div id="reg">
         <!---温馨提示-->
		 <div class="msg">
			 <div class="panel">
    <form id="user_info">
      <div class="form-group">
        <label for="uname">用户名：</label>
        <input required minlength="3" maxlength="9" type="text" placeholder="*请输入用户名" autofocus name="username" id="uname"/>
        <span class="msg-default">用户名长度在3到9位之间</span>
      </div>
      <div class="form-group">
        <label for="upwd">密码：</label>
        <input type="password" minlength="6" maxlength="12" placeholder="*请输入密码" name="password" id="upwd"/>
        <span class="msg-default hidden">密码长度在6到12位之间</span>
      </div>
      <div class="form-group">
        <label for="upwd2">确认密码：</label>
        <input type="password" placeholder="*再次输入密码" id="upwd2"/>
        <span class="msg-default hidden">密码长度在6到12位之间</span>
      </div>
      <div class="form-group">
        <label for="rname">姓名：</label>
        <input  type="text" placeholder="请输入姓名" name="rname" maxlength="11" id="rname"/>
        <span class="msg-default hidden"></span>
      </div>
      
	  <div class="form-group">
        <label for="idCard">身份证：</label>
        <input  type="text" placeholder="请输入身份证号" name="idCard" maxlength="18" id="idCard"/>
        <span class="msg-default hidden"></span>
      </div>
      <div class="form-group">
        <label for="uphone">手机：</label>
        <input required type="tel" placeholder="*请输入手机号码" name="phoneNum" maxlength="11" id="uphone"/>
        <span class="msg-default hidden">请输入合法的手机号码</span>
      </div>
      <div class="form-group">
        <label for="uphone">地址：</label>
        <input  type="text" placeholder="请输入地址" name="addr" id="addr"/>
        <span class="msg-default hidden"></span>
      </div>
      <div>
        <div class="form-group">
          <label><h6>带*为必填项</h6></label>
          <input type="button" value="提交注册信息" id="btn_reg" />
        </div>
      </div>

    </form>
  </div>
			 <div id="ad">
			    <!--<p>注册会员后，你可以:</p>-->
				<!--<ul>-->
				    <!--<li><b>1</b>查询，计划您的订单</li>-->
					<!--<li><b>2</b>预订美食，客房</li>-->
					<!--<li><b>3</b>享受超低优惠折扣</li>-->
				<!--</ul>-->
               <div class="login">
                   已有账号，去 <a href="login.html">登陆</a>
               </div>
				<ul id="trigger">
				  <li><img src="../resources/home/images/new1.png" alt=""></li>
				</ul>
				
			 </div>
		 </div>
  </div>
  
   <script src="../resources/home/js/jquery-1.11.3.js"></script>
<script>
  /*1.对用户名进行验证*/
  var login=0;
  uname.onblur = function(){
    var val=this.value;
    if(this.validity.valueMissing){
      this.nextElementSibling.innerHTML = '用户名不能为空';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('用户名不能为空');
    }else if(this.validity.tooShort){
      this.nextElementSibling.innerHTML = '用户名不能少于3位';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('用户名不能少于3位');
    }else {
      this.nextElementSibling.innerHTML = '用户名格式正确';
      this.nextElementSibling.className = 'msg-success';
      login=1;
      this.setCustomValidity('');
    }

  }


  //2.对密码进行验证
  upwd.onfocus = function(){
    this.nextElementSibling.innerHTML = '密码至少为6位数字或者字符';
    this.nextElementSibling.className = 'msg-default';
    login=0;
  }
  upwd.onblur = function(){
    if(upwd.value == '' || upwd.value.length < 6){
      this.nextElementSibling.innerHTML = '密码至少为6位数字或者字符';
	  this.nextElementSibling.className = 'msg-default';
      login=0;
    }else{
		this.nextElementSibling.innerHTML = '输入正确';
		this.nextElementSibling.className = 'msg-success';
		login=1;
		this.setCustomValidity('');
	}
      
  }
  
  //3.对手机号进行校验
  uphone.onblur = function(){
	var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if(this.validity.valueMissing){
      this.nextElementSibling.innerHTML = '电话号码不能为空';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('电话号码不能为空');
    }else if(this.validity.typeMismatch){
      this.nextElementSibling.innerHTML = '电话号码格式不正确';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('电话号码格式不正确');
    }else if(!myreg.test(uphone.value)){
		this.nextElementSibling.innerHTML = '电话号码格式不正确';
      this.nextElementSibling.className = 'msg-error';
      login=0;
      this.setCustomValidity('电话号码格式不正确');
	}
	else {
      this.nextElementSibling.innerHTML = '格式正确';
      this.nextElementSibling.className = 'msg-success';
      login=1;
      this.setCustomValidity('');
    }
  }
  uphone.onfocus = function(){
    this.nextElementSibling.innerHTML = '请输入合法的手机号码';
    this.nextElementSibling.className = 'msg-default';
    login=0;
  }
  //确认密码
  upwd2.onblur=function() {
    if (upwd2.value != upwd.value) {
      this.nextElementSibling.innerHTML = '两次密码输入不一致';
      login=0;
      this.nextElementSibling.className = 'msg-error';
    } else if (upwd2.value == upwd.value) {
      this.nextElementSibling.innerHTML = '输入正确';
      this.nextElementSibling.className = 'msg-success';
      login=1;
    }
  }
  $('#btn_reg').click(function(){
    //表单序列化，获得所有的用户输入
	  if (upwd2.value != upwd.value){
	    	return;
	    }
    var data = $('#user_info').serialize();

    //异步提交请求数据
    $.ajax({
      type: 'POST',
      dataType:'json',
      url: 'register',
      data: data,
      success: function(result){
        //console.log(result);
        if(result.type=='success'){
          alert('注册成功！');
          window.location.href = 'login';
//          location.href='login.html';
        }else {
          alert(result.msg);
        }
      }
    });
  })
  
/*功能点2：轮播*/
var pic = {
  intr: function () {
    var i = 1;
    var str1 = $("#trigger img").attr("src");
    var str = str1.toString();
    var timer = setInterval(function () {
      i++;
      if (i > 3) {
        i = 1;
      }
      str = str.replace(/[1-3]/, i);
      $("#trigger img").attr("src", str);
    }, 2000);
  }
}
pic.intr();
</script>	
<%@include file="../common/footer.jsp"%>