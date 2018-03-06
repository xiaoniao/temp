<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	
	<link href="${basePath}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/static/h-ui.admin/css/H-ui.login.ebk.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/static/h-ui.admin/css/style.css" rel="stylesheet" type="text/css" />
	<link href="${basePath}/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
	
	<title>金鹰商务服务管理系统 - 登录</title>
</head>

<body>
	<div class="header"></div>
	<div class="loginWraper">
		<div class="loginWidth">
			<div class="login-width">
				<div class="loginBox">
			    	<form id="login-form" class="form form-horizontal" action="${basePath}/rest/user/login" method="post">
			    		<div class="login-form-title">
							商家登录
						</div>
						
						<!-- 错误提示 -->
						<div class="login-form-tips" id="login-tips">
							<i class="Hui-iconfont tips">&#xe6e0;</i><label id="login-tips-text">请输入账户名登录密码</label>
							<input type="hidden" name="loginTips" value="<c:out value="${loginTips}"></c:out>" id='loginTips'>
						</div>
						
						<!-- 用户名 -->
						<div class="login-form-row">
							<div class="login-form-input">
								<div class="icon"><i class="Hui-iconfont">&#xe60d;</i></div>
								<input name="username" id="username" type="text"  value="${username}" placeholder="请输入账户名" class="logininput">
							</div>
						</div>
						
						<!-- 密码 -->
						<div class="login-form-row">
							<div class="login-form-input">
								<div class="icon"><i class="Hui-iconfont">&#xe605;</i></div>
								<input id="password" name="password" type="password" placeholder="请输入密码" class="logininput">
							</div>
						</div>
						
						<!-- 验证码 -->
						<div class="login-form-row">
							<div class="login-form-input">
								<div class="icon"><i class="Hui-iconfont">&#xe63f;</i></div>
								<input id="captcha" name="captcha" type="text" placeholder="请输入验证码" class="logininput codeinput">
								<a id="change-kaptcha" href="javascript:;">
									<img src="${basePath}/rest/user/kaptcha?formKey=<c:out value="${formKey}"></c:out>" id="kaptcha-image" width="83px" height="42px" style="margin-left: 10px;"/>
								</a>
								<input type="hidden" name="formKey" value="<c:out value="${formKey}"></c:out>" id='formKey'>
							</div>
							<div style="clear: both;"></div>
						</div>
						
						<!-- 登录 -->
						<div class="login-form-row" style="margin: 52px 0;">
							<input type="submit" class="button loginTo" value="登录">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div class="footer">Copyright 城侍金鹰</div>
	<script type="text/javascript" src="${basePath}/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="${basePath}/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript" src="${basePath}/javascript/sha256.js"></script>
	<script type="text/javascript">
		$(function() {
			var top = ($(window).height() - $(".loginBox").height() - 50)/2;   
			$(".loginBox").css( { 'margin-top' : top });
			$(window).resize(function(){
				top = ($(window).height() - $(".loginBox").height() - 50)/2;  
				$(".loginBox").css( { 'margin-top' : top });
			});
			
			/**
			 * 错误信息的显示
			 */
			$("#login-tips").hide();
			var loginTips = $("#loginTips").val();
			if (loginTips != null && loginTips != "") {
				$("#login-tips-text").text(loginTips);
				$("#login-tips").show();
			}
			
			/**
			 * 用户名、密码、验证码 失去/获得 焦点样式变化
			 */
			$("#username, #password, #captcha").focus(function() {
				$(this).parent().css('border','1px solid #5a98de');
			}).blur(function() {
				$(this).parent().css('border','1px solid #ccc');
			});;
			
			/**
			 * 点击切换验证码
			 */
			$("#change-kaptcha").click(function() {
				var formKey = $("#formKey").val();
				$("#kaptcha-image").attr("src", "${basePath}/rest/user/kaptcha?formKey=" + formKey);
			});
			
			/**
			 * 登录表单验证
			 */
			$("#login-form").submit(function() {
				var errorText = "";
				
				var username = $("#username").val();
				var password = $("#password").val();
				var captcha  = $("#captcha").val();
				
				//console.log('username:' + username);
				//console.log('password:' + password);
				//console.log('captcha:' + captcha);
				
				if (username == null || username == '') {
					errorText = "请输入账户登录名";
				} else if (password == null || password == '') {
					errorText = "请输入账户登录密码";
				} else if (captcha == null || captcha == '') {
					errorText = "请输入验证码";
				}
				
				//console.log('errorText:' + errorText);

				if (errorText != "" && errorText.length > 0) {
					$("#login-tips-text").text(errorText);
					$("#login-tips").show();
					return false;
				}
				
				//console.log('c');

				$("#login-tips-text").text("");
				$("#login-tips").hide();
				var new_pass = sha256_digest(password);
				$("#password").val(new_pass);
				
				return true;
			});
		})
	</script>
</body>
</html>
