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
		<link rel="stylesheet" href="/lib/resourceModel/css/reset.css" />
		<link rel="stylesheet" href="/lib/resourceModel/css/main.css" />

		<!-- 上传图片 -->
		<link type="text/css" rel="stylesheet" href="/lib/jquery-ui/jquery-ui.min.css"/>
		<link type="text/css" rel="stylesheet" href="/lib/jquery.ui.plupload/css/jquery.ui.plupload.css"/>

		<title>资源详情 - 城侍金鹰</title>
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
			<div class="common resource-detail-content">
				<div class="resource-detail-content-left">
					<div class="article">
						<h1>
							<span id="title">无内容</span>
							<span class="right">更新时间：<span id="uptDate"></span></span>
						</h1>
						<div class="article-content">
							<div class="show-slider-container">
								<div class="left-arrow"></div>
								<ul class="show-slider">
									
								</ul>
								<div class="right-arrow"></div>
							</div>
							<div id="content">
							无内容
							</div>
						</div>
					</div>
					
					<div class="upload-resource">
						<div class="want-upload">我要更新</div>
						<form class="upload-form" action="javascript:;">
							<div id="uploader">  
								<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
							</div>
							<input type="hidden" id="images" name="images">
							<div class="upload-form-top">
							<!--<div>
								<div class="img-upload">
									<input type="file"  />
									<div>上传图片</div>
								</div>
								<span class="right">最多传十张</span>
							</div>-->
							<textarea placeholder="输入文字..." id="uptContent" name="content"></textarea>
							</div>
							<button class="upload-commit" id="updateBtn">提交资源</button>
						</form>
					</div>
					
					<div class="accept">
						<div class="accept-top">全部已采纳更新（<span class="yellow" id="resourceCounts">0</span>）</div>
						<ul class="accept-list">
						
						</ul>
					</div>
				</div>
				<div class="right">
					<a href="/rest/um/resource/resource_push"><button class="right commit-resource"><i></i>提交新资源</button></a>
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
		<script type="text/javascript" src="/lib/resourceModel/js/jquery-1.8.3.min.js" ></script>
		<script type="text/javascript" src="/lib/resourceModel/js/main.js" ></script>
		<!--上传图片-->
		<script type="text/javascript" src="/lib/jquery-ui/jquery-ui.min.js"></script>
		<script type="text/javascript" src="/lib/plupload-2.1.9/js/plupload.full.min.js"></script>  
		<script type="text/javascript" src="/lib/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
		<script type="text/javascript" src="/lib/plupload-2.1.9/js/i18n/zh_CN.js"></script>

		<script>
			$(function(){
				searchMethod();
				showSlider();
				fixedCityTab();
				getCityList();

				var resourceInfo = {};
				let resourceId = location.search.split('=')[1];
				loadResourceDetail();

				function loadResourceDetail() {
					if (resourceId == null || resourceId.replace(/<\/?[^>]*>/g,'') == '' || resourceId == 'null') {
						$('.upload-resource').hide();
					}
					$.ajax({
						url : '/rest/um/resource/info?id='+resourceId,
						dataType : 'JSON',
						error : function(data) {
							console.log('error');
							console.log(data);
						},
						success : function(data) {
							if (data.statusCode == 0) {
								resourceInfo = data.data;
								$('#title').html(resourceInfo.title);
								$('#uptDate').html(resourceInfo.updateDate);
								$('#content').html(resourceInfo.content);
								let images;
								if (resourceInfo.images != null && resourceInfo.images != 'empty') {
									$('.show-slider-container').show();
									images = resourceInfo.images.split(',');
									let $showSlider = $('.show-slider'),
									inner = '';
									for (let img of images) {
										inner += '<li><img src="' + img + '" /></li>'
									}
									$showSlider.html(inner);
								} else {
									$('.show-slider-container').hide();
								}
								getResouceList();
							} else {
								console.log(data);
							}
						}
					});
				}

				function getResouceList() {
					$.ajax({
						url : '/rest/um/resourceSuggest/myList',
						data : {'currentPage' : 1, 'pageSize' : 5, 'cityId' : city.id, 'resourceId' : resourceInfo.id , 'checkStatus' : 2},
						dataType : 'JSON',
						error : function(data) {
							console.log('error');
							console.log(data);
						},
						success : function(data) {
							if (data.statusCode == 0) {
								let resources = data.data;
								$('#resourceCounts').html(resources.length);
								$('.accept-list').empty();
								let inner = '';
								if (resources != null) {
									for (let resource of resources) {
										inner += '<li>'+
											'<div class="accept-list-top">'+
												'<span class="yellow">' + resource.submitUserName + '</span>'+
												'<span>' + resource.createDate + '</span>'+
											'</div>'+
											'<p>' + resource.content.replace(/<\/?[^>]*>/g,'').substr(0,200) + '</p>';
										if (resource.images != null && resource.images != 'empty') {
											inner += '<div class="img-container">';
											for (let img of resource.images.split(',')) {
												inner += '<img src="' + img + '" />';
											}
											inner += '</div>';
										}
										inner += '</li>';
									}
								}
								$('.accept-list').html(inner);
							} else {
								console.log(data);
							}
						}
					});
				}

				/** 切换城市 */
				$('.city-list').on('click', 'li', function() {
					city = cityList[this.value];
					localStorage.setItem('city', JSON.stringify(city));
					$('[name="localCity"]').html('<i></i>' + city.name);
					$('.city-list').hide();
					getCityList();
				});

				var picture = "";
				/** 上传图片 */
				$("#uploader").plupload({
					// General settings           
					runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
					url : "/rest/um/crossInfo/upload?directory=resource",
					//unique_names: true,  
					chunk_size : '1mb',
					//rename : true,
					dragdrop: true,
					filters : {           
						// Maximum file size
						max_file_size : '10mb',
						// Specify what files to browse forms
						mime_types: [  
							{title : "Image files", extensions : "jpg,gif,png"},
						],
						prevent_duplicates : true //不允许选取重复文件							
					},
					// Resize images on clientside if we can
					resize: {
							width : 200,
							height : 200,
							quality : 90,
							crop: true   
						// crop to exact dimensions           
					},
					views: {
						list: true,
						thumbs: true, // Show thumbs
						active: 'thumbs'
					},
					// Flash settings           
					flash_swf_url : '/lib/plupload-2.1.9/js/Moxie.swf',                 
					// Silverlight settings           
					silverlight_xap_url : '/lib/plupload-2.1.9/js/Moxie.xap' ,     
					// 参数  
					//multipart_params: {'user': 'Rocky', 'time': '2012-06-12'},
					init : {
						FileUploaded : function(up, file, info) {
							if(info.status == 200) {
								if (picture.length != 0) picture += ',';
								picture += info.response;
							}else{
								alert('图片上传失败！');
							}
						},
						UploadComplete : function(uploader,files) {
							$("#images").val(picture);
						},
						QueueChanged : function(uploader){
							var count=uploader.files.length; 
							if(count > 20){
								alert('最多只能上传二十张图片,请重新上传');
								uploader.splice();
							}
						}
					}
				});

				$('#updateBtn').click(function() {
					$.ajax({
						url : '/rest/um/resourceSuggest/update',
						type : 'POST',
						data :{
							resourceId : resourceInfo.id,
							images : $('#images').val(),
							content : $('#uptContent').val(),
							cityId : city.id,
							title : resourceInfo.title
						},
						dataType : 'JSON',
						error : function(data) {
							console.log('error');
							console.log(data);
						},
						success : function(data) {
							if (data.statusCode == 0) {
								alert('更新提交成功！等待审核中');
								$('.upload-form')[0].reset();
								history.go(-1);
							} else {
								alert('提交失败');
							}
						}
					});
				});
			})
		</script>
	</body>
</html>
