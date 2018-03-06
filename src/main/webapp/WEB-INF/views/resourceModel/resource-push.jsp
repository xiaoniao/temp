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
		<title>发布资源 - 城侍金鹰</title>
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
			<div class="common resource-push">
				<h1>提交新资源</h1>
				<form class="resource-push-form" action="javascript:;">
					<div class="input-group">
						<span>分类</span>
						<select id="type" name="typeId">
						</select>
					</div>
					<div class="input-group">
						<span>标题</span>
						<input type="text" name="title" placeholder="请输入内容"  />
					</div>
					<div class="input-group content-input-group">
						<div class="content-input">
							<span>内容</span>
							<textarea name="content" placeholder="请输入内容"></textarea>
						</div>
						<button>提交审核</button>
					</div>
				</form>
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
		<script type="text/javascript" src="/lib/resourceModel/js/jquery-1.8.3.min.js" ></script>
		<script type="text/javascript" src="/lib/resourceModel/js/main.js" ></script>
		<script>
			$(function(){
				getTypes();
				searchMethod();
				fixedCityTab();
				getCityList();

				/** 切换城市 */
				$('.city-list').on('click', 'li', function() {
					city = cityList[this.value];
					localStorage.setItem('city', JSON.stringify(city));
					$('[name="localCity"]').html('<i></i>' + city.name);
					$('.city-list').hide();
					getCityList();
				});

				/** 资源类型 */
				function getTypes() {
					$.ajax({
						url : '/rest/um/resourceType/list',
						type : 'get',
						dataType : 'JSON',
						error : function(data) {
							console.log('error');
							console.log(data);
						},
						success : function(data) {
							if (data.statusCode == 0) {
								let types = data.data;
								let $typeList = $('#type');
								for (let type of types) {
									$typeList.append('<option value="' + type.id + '">' + type.name + '</option>');
								}
							} else {
								console.log(data);
							}
						}
					});
				}

				/** 提交 */
				let _form = $('.resource-push-form');
				_form.on('click', 'button', function() {
					let datas = _form.serialize().split('&');
					for (let data of datas) {
						if (data.split('=')[1] == '') {
							alert('请填写' + data.split('=')[0]);
							return;
						}
					}
					$.ajax({
						url : '/rest/um/resourceSuggest/add',
						type : 'POST',
						dataType : 'JSON',
						data : _form.serialize() + '&cityId=' + city.id,
						error : function(data) {
							console.log('error');
							console.log(data);
						},
						success : function(data) {
							if (data.statusCode == 0) {
								alert('提交成功！');
								_form[0].reset();
								history.go(-1);
							} else {
								console.log(data);
								alert('提交失败');
							}
						}
					});
				});
			});
		</script>
	</body>
</html>
