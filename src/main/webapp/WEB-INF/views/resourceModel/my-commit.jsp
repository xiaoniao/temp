<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% 
	if (session.getAttribute("username") == null)
		response.sendRedirect("/");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>我的提交 - 城侍金鹰</title>
		<link rel="stylesheet" href="/lib/resourceModel/css/reset.css" />
		<link rel="stylesheet" href="/lib/resourceModel/css/main.css" />
	</head>
	<body>
		<!-- fixed header start-->
		<div class="fixed-header search-fixed-header">
			<div class="common">
				<div class="left">
					<span><a class="logo" href="/rest/um/resource/resource_list">城侍金鹰</a></span>
					<div class="city fixed-city">
						<span name="localCity"><i></i>杭州</span>
						<ul class="city-list">
							
						</ul>
					</div>
				</div>
				<form class="search-bar fixed-search-bar" action="javascript:;">
					<input type="text" placeholder="搜索你要的资源" />
					<button></button>
				</form>
				<div class="right">
					<span class="my-resource-fixed"><a href="/rest/um/resource/my_commit"><i></i>我的资源</a></span>
					<span class="user-name">${username} 先生<a href="javascript:logout()"> [退出登录]</a></span>
				</div>
			</div>
		</div>
		<!-- fixed header end-->
			<!--main start-->
		<div class="main">
			<div class="common my-commit">
				<div class="left my-commit-left">
					<div>用户名</div>
					<div class="modify-pass">
						<i class="my-icon"></i>
						<span>修改密码</span>
					</div>
					<a href="/rest/um/resource/resource_push"><button class="right commit-resource"><i></i>提交新资源</button></a>
				</div>
				<div class="my-commit-right">
					<ul class="my-commit-content-top">
						<li class="elected">
							<a href="javascript:;">
								<i class="my-icon"></i>
								<span>所有资源（<span id="allCommit">0</span>）</span>
							</a>
						</li>
						<li value=3>
							<a href="javascript:;">
								<i class="my-icon"></i>
								<span>已采纳（<span id="commit2">0</span>）</span>
							</a>
						</li>
						<li value=2>
							<a href="javascript:;">
								<i class="my-icon"></i>
								<span>已驳回（<span id="commit1">0</span>）</span>
							</a>
						</li>
						<li value=1>
							<a href="javascript:;">
								<i class="my-icon"></i>
								<span>待审核（<span id="commit0">0</span>）</span>
							</a>
						</li>
					</ul>
					<ul class="my-commit-content-bottom">
						<div id="all">数据加载中...</div>
						<div id="checked">数据加载中...</div>
						<div id="denied">数据加载中...</div>
						<div id="wait">数据加载中...</div>
					</ul>
				</div>
			</div>
		</div>
		<!--main end-->
		<!--footer start-->
		<div class="footer">
			<div class="footer-top">
				<div class="common">
					<div class="footer-logo logo"></div>
					<ul class="footer-list">
						<li><a href="http://cityjinying.com/about">关于我们</a></li>
						<li><a href="http://cityjinying.com/service">服务说明</a></li>
						<li><a href="http://cityjinying.com/news">新闻活动</a></li>
						<li><a href="http://cityjinying.com/jobs">工作机会</a></li>
					</ul>
					<div class="company-info">
						<div>
							<span class="info-left">地址:</span>
							<span class="info-right">浙江省杭州市西湖区教工路18号 欧美中心A座 琨瑶商务</span>
						</div>
						<div>
							<span class="info-left">邮箱:</span>
							<span class="info-right">jinying@kunyaoshangwu.com</span>
						</div>
						<div>
							<span class="info-left">电话:</span>
							<span class="info-right">0571-89739916</span>
						</div>
					</div>
					<div class="erweicode right">
						<img src="/lib/resourceModel/img/erweicode.jpg"  />
						<div>扫描二维码 获得更多资讯</div>
					</div>
				</div>
				
			</div>
			<div class="footer-bottom">
				<div class="common">
					<span>© 2016 杭州琨瑶商务服务有限公司 All rights reserved.</span>
					<span>浙ICP备 16044173号-4</span>
					<span>公安备案号 33010602007534</span>
				</div>
			</div>
		</div>
		<!--footer end-->
		
		<!--alert-->
		<div class="alert">
			<div class="modify-pass-alert">
				<div>修改密码</div>
				<form class="modify-form" action="javascript:;">
					<div class="modify-input">
						<span>原密码</span>
						<input type="password" id="old" placeholder="请输入密码"  />
					</div>
					<div class="modify-input">
						<span>新密码</span>
						<input type="password" id="new1" placeholder="请输入密码"  />
					</div>
					<div class="modify-input">
						<span>确认新密码</span>
						<input type="password" id="new2" placeholder="请输入密码"  />
					</div>
					<button class="cancel">取消</button>
					<button class="sure">确认修改</button>
				</form>
			</div>
		</div>
		
		<script type="text/javascript" src="/lib/resourceModel/js/jquery-1.8.3.min.js" ></script>
		<script type="text/javascript" src="/lib/resourceModel/js/main.js" ></script>
   		<script type="text/javascript" src="/javascript/sha256.js"></script>
		
		<script>
			$(function(){
				searchMethod();
				modifyPass();
				fixedCityTab();
				getCityList();
				getMyCommits();

				/** update password */
				$('.sure').click(function() {
					let old = $('#old').val(),
					new1 = $('#new1').val(),
					new2 = $('#new2').val();
					if (old == null || old == '' || new1 == null || new1 == '' || new2 == null || new2 == '') {
						return;
					}
					if (new1 != new2) {
						alert('两次输入新密码不一致');
						return;
					}
					$.ajax({
						url : '/rest/user/updPwd',
						type : 'POST',
						data : {
							'oldPassword' : sha256_digest(old),
							'newPassword' : sha256_digest(new1)
						},
						dataType : 'JSON',
						error : function(data) {
							console.log('error');
							console.log(data);
							alert('密码修改失败');
						},
						success : function(data) {
							if (data.statusCode == 0) {
								alert('密码修改成功，请重新登录');
								location.href = location.href.substr(0, location.href.indexOf('/')) + '/rest/page/tologin';
							} else if (data.statusCode == '30016') {
								alert('原密码错误');
							} else {
								alert('密码修改失败');
							}
						}
					});
				});

				/** 切换城市 */
				$('.city-list').on('click', 'li', function() {
					city = cityList[this.value];
					localStorage.setItem('city', JSON.stringify(city));
					$('[name="localCity"]').html('<i></i>' + city.name);
					$('.city-list').hide();
					getCityList();
				});

				$('.my-commit-content-top').on('click', 'li', function() {
					$(this).addClass('elected').siblings().removeClass('elected');
					let status = $(this).val() ? ($(this).val() - 1) : null;
					if (status == 0)
						$('#wait').show().siblings().hide();
					else if (status == 1)
						$('#denied').show().siblings().hide();
					else if (status == 2)
						$('#checked').show().siblings().hide();
					else
						$('#all').show().siblings().hide();
				});
			});
			let $all = $('#all'),
			$checked = $('#checked'),
			$denied = $('#denied'),
			$wait = $('#wait');
			$checked.hide();
			$denied.hide();
			$wait.hide();
			function getMyCommits(status) {
				$.ajax({
					url : '/rest/um/resourceSuggest/myList',
					dataType : 'JSON',
					error : function(data) {
						console.log('error');
						console.log(data);
					},
					success : function(data) {
						if (data.statusCode == 0) {
							let sources = data.data;
							allNum = 0, num0 = 0, num1 = 0, num2 = 0;
							let innerAll = '',
							innerChecked = '',
							innerDenied = '',
							innerWait = '';
							if (sources != null) {
								for (let source of sources) {
									allNum ++;
									let statusText = '',
									images;
									if (source.images != null && source.images != 'empty')
									images = source.images.split(',');
									if (source.checkStatus == 0) { 
										num0 ++;
										statusText = '待审核';
										innerWait += '<li>'+
											'<h1 class="my-commit-title"><a>'+ source.title +'</a><span>'+ source.createDate +'</span><span class="right">'+ statusText +'</span></h1>'+
											'<p>'+ source.content.replace(/<\/?[^>]*>/g,'').substr(0,100) + '...</p>';
										if (images != null && images.length > 0) {
											innerWait += '<div class="img-container">';
											for (let img of images) {
												innerWait += '<img src="'+ img +'" />';
											}
											innerWait += '</div>';
										}
										innerWait += '</li>';
									} else if (source.checkStatus == 1) {
										num1 ++;
										statusText = '已驳回';
										innerDenied += '<li>'+
											'<h1 class="my-commit-title"><a>'+ source.title +'</a><span>'+ source.createDate +'</span><span class="right">'+ statusText +'</span></h1>'+
											'<p>'+ source.content.replace(/<\/?[^>]*>/g,'').substr(0,100) + '...</p>';
										if (images != null && images.length > 0) {
											innerDenied += '<div class="img-container">';
											for (let img of images) {
												innerDenied += '<img src="'+ img +'" />';
											}
											innerDenied += '</div>';
										}
										innerDenied += '</li>';
									} else if (source.checkStatus == 2) { 
										num2 ++;
										statusText = '已采纳';
										innerChecked += '<li>'+
											'<h1 class="my-commit-title"><a>'+ source.title +'</a><span>'+ source.createDate +'</span><span class="right">'+ statusText +'</span></h1>'+
											'<p>'+ source.content.replace(/<\/?[^>]*>/g,'').substr(0,100) + '...</p>';
										if (images != null && images.length > 0) {
											innerChecked += '<div class="img-container">';
											for (let img of images) {
												innerChecked += '<img src="'+ img +'" />';
											}
											innerChecked += '</div>';
										}
										innerChecked += '</li>';
									}
									innerAll += '<li>'+
										'<h1 class="my-commit-title"><a>'+ source.title +'</a><span>'+ source.createDate +'</span><span class="right">'+ statusText +'</span></h1>'+
										'<p>'+ source.content.replace(/<\/?[^>]*>/g,'').substr(0,100) + '...</p>';
									if (images != null && images.length > 0) {
										innerAll += '<div class="img-container">';
										for (let img of images) {
											innerAll += '<img src="'+ img +'" />';
										}
										innerAll += '</div>';
									}
									innerAll += '</li>';
								}
							}
							$all.html(innerAll == '' ? '暂无数据' : innerAll);
							$checked.html(innerChecked == '' ? '暂无数据' : innerChecked);
							$denied.html(innerDenied == '' ? '暂无数据' : innerDenied);
							$wait.html(innerWait == '' ? '暂无数据' : innerWait);
							$('#allCommit').html(allNum);
							$('#commit0').html(num0);
							$('#commit1').html(num1);
							$('#commit2').html(num2);
						} else {
							console.log(data);
						}
					}
				});
			}

		</script>
	</body>
</html>
