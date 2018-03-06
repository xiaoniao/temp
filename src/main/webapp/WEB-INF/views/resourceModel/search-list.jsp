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
		<title>搜索列表 - 城侍金鹰</title>
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
					<input type="text" placeholder="搜索你要的资源"/>
					<button></button>
				</form>
				<div class="right">
					<span class="my-resource-fixed"><a href="/rest/um/resource/my_commit"><i></i>我的资源</a></span>
					<span class="user-name">${username} 先生<a href="javascript:logout()"> [退出登录]</a></span>
				</div>
			</div>
		</div>
		<!-- fixed header end-->
		
		<form class="search-bar search-list-bar" action="javascript:;">
			<input type="text" placeholder="搜索你要的资源" />
			<button></button>
		</form>
		
		<!-- main start-->
		<div class="main">
			<div class="common">
				<ul class="search-list-top">
					<!--<li id="allResource" class="elected">
						<a href="">
							<span>全部</span>
							<span>123</span>
						</a>
					</li>-->
				</ul>
				<ul class="resource-list">
					<!--<li>
						<div class="left">
							<a href=""><img src="/lib/resourceModel/img/hotel_01.jpg" /></a>
						</div>
						<div class="right">
							<h1>住宿 - <a href="">某某酒店 旅馆北京市中心区旅馆</a></h1>
							<p class="resource-content">请输入内容请输入内容请输入内容容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输...</p>
							<div>最近更新时间：2016/12/1  22:10</div>
						</div>
					</li>
					<li>
						<div class="left">
							<a href=""><img src="/lib/resourceModel/img/hotel_01.jpg" /></a>
						</div>
						<div class="right">
							<h1>住宿 - <a href="">某某酒店 北京市中心区</a></h1>
							<p class="resource-content">请输入内容旅馆请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输...</p>
							<div>最近更新时间：2016/12/1  22:10</div>
						</div>
					</li>
					<li>
						<div class="left">
							<a href=""><img src="/lib/resourceModel/img/hotel_01.jpg" /></a>
						</div>
						<div class="right">
							<h1>住宿 - <a href="">某某酒店 北京市中心区</a></h1>
							<p class="resource-content">请输入内容旅馆请输入内容请输入旅馆内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入旅馆旅馆内容请输入内容请输入内容请输入内容请输入内容容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输入内容请输...</p>
							<div>最近更新时间：2016/12/1  22:10</div>
						</div>
					</li>-->
				</ul>
				<ul class="page-btn">
					<!--<li class="now"><a>1</a></li>
					<li><a>2</a></li>
					<li><a>3</a></li>
					<li><a>4</a></li>
					<li>...</li>
					<li><a>10</a></li>
					<li><a>20</a></li>
					<li><a>▶</a></li>
					<li>跳转到：
						<input type="text" />
						<a>GO</a>
					</li>-->
				</ul>
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
			var currentPage = 1, pageSize = 20, totalPage = 0, typeId, title;

			title = window.localStorage.getItem('searchTitle');
			$('.search-list-bar').find('input').val(title);
			window.localStorage.removeItem('searchTitle');

			$(function(){
				getTypes();
				fixedCityTab();
				searchMethod();
				getCityList();
				getResouceList();
				repstr($('.fixed-search-bar input').val(),'.resource-list h1');
				repstr($('.fixed-search-bar input').val(),'.resource-list p');

				$('button').on('click', function() {
					title = $(this).prev().val();
					getResouceList();
				});

				/** 切换城市 */
				$('.city-list').on('click', 'li', function() {
					city = cityList[this.value];
					localStorage.setItem('city', JSON.stringify(city));
					$('[name="localCity"]').html('<i></i>' + city.name);
					getResouceList();
					getTypes();
					$('.city-list').hide();
					getCityList();
				});

				/** 切换类型 */
				var $headerBottom = $('.search-list-top');
				$headerBottom.on('click', 'li', function() {
					$(this).addClass('elected').siblings().removeClass('elected');
					if ( $(this).val() < 1) return;
					typeId = $(this).val();
					getResouceList();
				});
				$headerBottom.on('click', '#allResource', function() {
					typeId = null;
					getResouceList();
				});

			});

			/** 资源类型列表 */
			function getTypes() {
				$.ajax({
					url : '/rest/um/resourceType/list',
					type : 'POST',
					data : {
						'isShowNumber' : 1,
						'pageSize' : pageSize,
						'cityId' : city.id,
						'title' : (title == null || title.replace(/\s+/g,"") == '') ? null : title
					},
					dataType : 'JSON',
					error : function(data) {
						console.log('error');
						console.log(data);
					},
					success : function(data) {
						if (data.statusCode == 0) {
							let types = data.data;
							let $typeList = $('.search-list-top');
							$typeList.empty();
							let totalNum = 0;
							let inner = '';
							if (types != null) {
								for (let type of types) {
									inner += '<li value="' + type.id + '">'+
										'<a href="javascript:;">'+
											'<span>' + type.name + '</span>'+
											'<span>' + type.number + '</span>'+
										'</a>'+
									'</li>';
									totalNum += type.number;
								}
								inner = '<li id="allResource" class="elected">'+
									'<a href="javascript:;">'+
										'<span>全部</span>'+
										'<span>' + totalNum + '</span>'+
									'</a>'+
								'</li>' + inner;
							}
							$typeList.html(inner);
						} else {
							console.log(data);
						}
					}
				});
			}

			function getResouceList() {
				if (title == null || title.replace(/\s+/g,"") == '') return;
				$.ajax({
					url : '/rest/um/resource/list',
					type : 'POST',
					data : {'currentPage' : currentPage, 'pageSize' : pageSize, 'cityId' : city.id, 'typeId' : typeId, 'title' : title},
					dataType : 'JSON',
					error : function(data) {
						console.log('error');
						console.log(data);
					},
					success : function(data) {
						if (data.statusCode == 0) {
							let resources = data.data;
							$('.resource-list').empty();
							let inner = '';;
							if (resources != null) {
								for (let resource of resources) {
									let image;
									if (resource.images != null && resource.images != 'empty')
										image = resource.images.split(',')[0];
									inner += '<li>'+
											'<div class="left">'+
												'<a href="/rest/um/resource/resource_detail?id=' + resource.id + '"><img src="' + (image ? image : "/lib/resourceModel/img/hotel_01.jpg") + '" /></a>'+
											'</div>'+
											'<div class="right">'+
												'<h1>住宿 - <a href="/rest/um/resource/resource_detail?id=' + resource.id + '">'+ resource.title +'</a></h1>'+
												'<p class="resource-content">'+ resource.content.replace(/<\/?[^>]*>/g,'').substr(0,200) + '...</p>'+
												'<div>最近更新时间：' + resource.updateDate + '</div>'+
											'</div>'+
										'</li>';
								}
							}
							$('.resource-list').html(inner);
						} else {
							console.log(data);
						}
					}
				});
				$.ajax({
					url : '/rest/um/resource/lscount',
					type : 'POST',
					data : {'pageSize' : pageSize, 'cityId' : city.id, 'typeId' : typeId, 'title' : title},
					dataType : 'JSON',
					error : function(data) {
						console.log('error');
						console.log(data);
					},
					success : function(data) {
						if (data.statusCode == 0) {
							let pageInfo = data.data;
							totalPage = pageInfo.totalPage;
							$('.page-btn').empty();
							let inner = '';
							if (currentPage > 1) {
								inner += '<li value="'+ (currentPage - 1) +'"><a href="javascript:;">◀</a></li>';
							}
							if (totalPage > 1) {
								for (let i=0; i<totalPage; i++) {
									if (currentPage == i+1) {
										inner += '<li value="' + (i+1) + '" class="now"><a href="javascript:;">' + (i+1) + '</a></li>';
									} else {
										inner += '<li value="' + (i+1) + '"><a href="javascript:;">' + (i+1) + '</a></li>';
									}
								}
								if (currentPage < totalPage) {
									inner += '<li value="'+ (currentPage + 1) +'"><a href="javascript:;">▶</a></li>';
								}
								inner += '<li title="jump">跳转到：<input type="text" id="pageNum"/> <a id="jump-btn">GO</a></li>';
							}
							$('.page-btn').html(inner);
						} else {
							totalPage = 0;
							currentPage = 1;
						}
					}
				});
			}
		</script>
	</body>
</html>
