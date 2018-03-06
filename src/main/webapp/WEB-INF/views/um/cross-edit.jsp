<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
%>
	
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<LINK rel="Bookmark" href="/favicon.ico">
		<LINK rel="Shortcut Icon" href="/favicon.ico" />
		
		<!--[if lt IE 9]>
		<script type="text/javascript" src="lib/html5.js"></script>
		<script type="text/javascript" src="lib/respond.min.js"></script>
		<script type="text/javascript" src="lib/PIE_IE678.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" type="text/css" href="/static/h-ui/css/H-ui.min.css" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/H-ui.admin.css" />
		<link rel="stylesheet" type="text/css" href="/lib/Hui-iconfont/1.0.7/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="/lib/icheck/icheck.css" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/skin/default/skin.css" id="skin" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/style.css" />
		<link type="text/css" rel="stylesheet" href="/lib/jquery-ui/jquery-ui.min.css"/>
		<link type="text/css" rel="stylesheet" href="/lib/jquery.ui.plupload/css/jquery.ui.plupload.css"/>		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<!--/meta 作为公共模版分离出去-->

		<title>金鹰编辑</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container">
		<form action="/rest/um/crossInfo/edit" method="post" enctype="multipart/form-data" class="form form-horizontal" id="edit-cross-form">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>展示图（750*600）:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="row cl">
						<div style="width:750px; margin-left: 13px;">  
							<div id="uploader">  
								<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
							</div>
						</div> 
					</div>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>金鹰图片:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="row cl">
						<a href="javascript:deletePictrue(${cross.id});" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>			
						<ul class="cl portfolio-area">
							<c:forEach items="${bannersList}" var="banners">
								<li class="item">
									<div class="portfoliobox">
										<input class="checkbox" name="banners" type="checkbox" value="${banners}">
										<div class="picbox">
											<img src="${banners}">
										</div>
									</div>
								</li>
							</c:forEach> 
						</ul>
					</div>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>工作照（150*150）：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<img src="${cross.pic}" width="300">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">重新上传：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="btn-upload form-group">
						<input class="input-text upload-url radius" type="text" readonly name="filePath" placeholder="请选择工作照">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
						<input type="file" multiple name="imageFile" class="input-file">
					</span>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>服务城市：</label>
				<input type="hidden" value="${cross.id}" name="id" />
				<input type="hidden" value="${cross.userId}" name="userId" id="userId"/>
				<input type="hidden" name="banners" id="banners"/>
				<div class="formControls col-xs-8 col-sm-9">						
					<select name="provinceId" class="select input-text" id="provinceId" style="width: 136px;">
						<option value="0">-- 省 --</option>
						<c:forEach items="${provinceList}" var="province">
							<option value="${province.id}" <c:if test='${province.id == newProvinceList.id}'> selected='selected' </c:if> >${province.name}</option> 
						</c:forEach>
					</select>
					<select name="cityId" class="select input-text" id="cityId" style="width: 160px;">
						<option value="0">-- 市 --</option>
						<c:forEach items="${cityList}" var="city">
							<option value="${city.id}" <c:if test='${city.id == newCityList.id}'> selected='selected' </c:if>>${city.name}</option>
						</c:forEach>	
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性别：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input name="sex" type="radio" value="1" <c:if test='${cross.sex == 1}'>checked="checked"</c:if> class="screening-radio">男
					<input name="sex" type="radio" value="2" <c:if test='${cross.sex == 2}'>checked="checked"</c:if> class="screening-radio">女
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="name" name="name" type="text" placeholder="控制在25个字、50个字节以内" value="${cross.name}" class="input-text" style="width: 300px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">登录手机号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					${cross.mobile}
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>登录密码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<button id="czButton" class="btn">重置密码</button>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联系手机号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="contactPhone" name="contactPhone" type="text" value="${cross.contactPhone}" class="input-text" style="width: 300px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>工作名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="workName" name="workName" type="text" value="${cross.workName}" class="input-text" style="width: 300px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>身高：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="height" name="height" type="text"  value="${cross.height}" class="input-text" style="width: 300px;">cm
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>毕业院校：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="school" name="school" type="text"  value="${cross.school}" class="input-text" style="width: 300px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>学历：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<select name="education" class="select input-text" id="education" style="width: 160px;">
						<option value="大专" <c:if test='${cross.education == "大专"}'>selected='selected' </c:if>>大专</option>
						<option value="本科" <c:if test='${cross.education == "本科"}'>selected='selected' </c:if>>本科</option>
						<option value="硕士" <c:if test='${cross.education == "硕士"}'>selected='selected' </c:if>>硕士</option>
						<option value="博士" <c:if test='${cross.education == "博士"}'>selected='selected' </c:if>>博士</option>
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>籍贯：</label>
				<div class="formControls col-xs-8 col-sm-9">						
					<select name="nativePlaceProvinceId" class="select input-text" id="nativePlaceProvinceId" style="width: 136px;">
						<option value="0">-- 省 --</option>
						<c:forEach items="${provinceList}" var="nativePlaceProvince">
							<option value="${nativePlaceProvince.id}" <c:if test='${nativePlaceProvince.id == newNativePlaceProvinceList.id}'> selected='selected' </c:if>>
								${nativePlaceProvince.name}
							</option> 
						</c:forEach>
					</select>
					<select name="nativePlaceCityId" class="select input-text" id="nativePlaceCityId" style="width: 160px;">
						<option value="0">-- 市 --</option>
						<c:forEach items="${nativePlaceCityList}" var="nativePlaceCity">
							<option value="${nativePlaceCity.id}" <c:if test='${nativePlaceCity.id == newNativePlaceCityList.id}'> selected='selected' </c:if>>
								${nativePlaceCity.name}
							</option>
						</c:forEach>	
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>星座：</label>
				<div class="formControls col-xs-8 col-sm-9">						
					<select name="constellation" class="select input-text" id="constellation" style="width: 160px;">
						<option value="白羊座" <c:if test='${cross.constellation == "白羊座"}'>selected='selected' </c:if>>白羊座</option>
						<option value="金牛座" <c:if test='${cross.constellation == "金牛座"}'>selected='selected' </c:if>>金牛座</option>
						<option value="双子座" <c:if test='${cross.constellation == "双子座"}'>selected='selected' </c:if>>双子座</option>
						<option value="巨蟹座" <c:if test='${cross.constellation == "巨蟹座"}'>selected='selected' </c:if>>巨蟹座</option>
						<option value="狮子座" <c:if test='${cross.constellation == "狮子座"}'>selected='selected' </c:if>>狮子座</option>
						<option value="处女座" <c:if test='${cross.constellation == "处女座"}'>selected='selected' </c:if>>处女座</option>
						<option value="天秤座" <c:if test='${cross.constellation == "天秤座"}'>selected='selected' </c:if>>天秤座</option>
						<option value="天蝎座" <c:if test='${cross.constellation == "天蝎座"}'>selected='selected' </c:if>>天蝎座</option>
						<option value="射手座" <c:if test='${cross.constellation == "射手座"}'>selected='selected' </c:if>>射手座</option>
						<option value="摩羯座" <c:if test='${cross.constellation == "摩羯座"}'>selected='selected' </c:if>>摩羯座</option>
						<option value="水瓶座" <c:if test='${cross.constellation == "水瓶座"}'>selected='selected' </c:if>>水瓶座</option>
						<option value="双鱼座" <c:if test='${cross.constellation == "双鱼座"}'>selected='selected' </c:if>>双鱼座</option>
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>属相：</label>
				<div class="formControls col-xs-8 col-sm-9">						
					<select name="yearBirth" class="select input-text" id="yearBirth" style="width: 160px;">
						<option value="鼠" <c:if test='${cross.yearBirth == "鼠"}'>selected='selected' </c:if>>鼠</option>
						<option value="牛" <c:if test='${cross.yearBirth == "牛"}'>selected='selected' </c:if>>牛</option>
						<option value="虎" <c:if test='${cross.yearBirth == "虎"}'>selected='selected' </c:if>>虎</option>
						<option value="兔" <c:if test='${cross.yearBirth == "兔"}'>selected='selected' </c:if>>兔</option>
						<option value="龙" <c:if test='${cross.yearBirth == "龙"}'>selected='selected' </c:if>>龙</option>
						<option value="蛇" <c:if test='${cross.yearBirth == "蛇"}'>selected='selected' </c:if>>蛇</option>
						<option value="马" <c:if test='${cross.yearBirth == "马"}'>selected='selected' </c:if>>马</option>
						<option value="羊" <c:if test='${cross.yearBirth == "羊"}'>selected='selected' </c:if>>羊</option>
						<option value="猴" <c:if test='${cross.yearBirth == "猴"}'>selected='selected' </c:if>>猴</option>
						<option value="鸡" <c:if test='${cross.yearBirth == "鸡"}'>selected='selected' </c:if>>鸡</option>
						<option value="狗" <c:if test='${cross.yearBirth == "狗"}'>selected='selected' </c:if>>狗</option>
						<option value="猪" <c:if test='${cross.yearBirth == "猪"}'>selected='selected' </c:if>>猪</option>
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>工龄：</label>
				<div class="formControls col-xs-8 col-sm-9">						
					<input type="text" name="workAge" value="${cross.workAge}" id="workAge" class="input-text" style="width: 300px;">（月）
				</div>
			</div>
			<div class="row cl skin-minimal">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>金鹰状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="radio-box">
						<input type="radio" id="radio-1" name="status" value="1" <c:if test="${cross.status == 1}">checked</c:if>>
						<label for="radio-1">在职</label>
					</div>
					<div class="radio-box">
						<input type="radio" id="radio-2" name="status" value="0" <c:if test="${cross.status == 0}">checked</c:if>>
						<label for="radio-2">离职</label>
					</div>
				</div>
  			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>特长：</label>
				<div class="formControls col-xs-8 col-sm-9"> 
					<textarea name="specialty" class="input-text valid "style="width:400px;height:100px;">${cross.specialty}</textarea>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<button type="submit" class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
					<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
	</article>
	
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="/lib/icheck/jquery.icheck.min.js"></script>

	<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer /作为公共模版分离出去-->
	
	<!-- 表单验证 -->
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>

	<script type="text/javascript" src="/lib/kindeditor-4.1.7/kindeditor-min.js"></script> 
    <script type="text/javascript" src="/lib/kindeditor-4.1.7/lang/zh_CN.js"></script> 
	
	<script type="text/javascript" src="/lib/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/plupload.full.min.js"></script>  
	<script type="text/javascript" src="/lib/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
	<script type="text/javascript" src="/javascript/sha256.js"></script>	
	
	<!-- 国际化中文支持 -->  
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/i18n/zh_CN.js"></script>  

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
		var picture = "";
         
		/**
		 * 编辑器初始化
		 */
		KindEditor.ready(function(K) {
			window.editor = K.create('#text-editor');
		});
		
		
		function deletePictrue(crossId) {
			var banners = "";
			$("input[name='banners']:checked").each(function(){
				banners +=  $(this).val() + ";";
			});
			
			$.ajax({
				type : "post",
				dataType : "JSON",
				url : "/rest/um/crossInfo/deleteBanners/?crossId=" + crossId + "&" + "banners=" + banners,
				success : function(result) {
					 if (result.statusCode == 0) {
						layer.confirm('删除成功', {
							btn: ['确认'] //按钮
						}, function() {
							layer.closeAll('dialog');
							location.replace(location.href);
						});
					} else {
						layer.alert('删除失败');
					} 
				}
			})
		}
		
		
		/**
		 * 单选、复选样式加载
		 */
		$(function() {
			
			$("#czButton").click(function(){
				var new_pass = sha256_digest("cityjy123456");
				var userId = $("#userId").val();
				
				$.ajax({
					type : "post",
					dataType : "JSON",
					url : "/rest/um/crossInfo/updatePassword/?userId=" + userId + "&passWord=" + new_pass,
					success : function(result) {
						 if (result.statusCode == 0) {
							layer.confirm('重置成功', {
								btn: ['确认'] //按钮
							}, function() {
								layer.closeAll('dialog');
								location.replace(location.href);
							});
						} else {
							layer.alert('重置失败');
						} 
					}
				})
				
			});
			
			
			$('.skin-minimal input').iCheck({
				checkboxClass: 'icheckbox-blue',
				radioClass: 'iradio-blue',
				increaseArea: '20%'
			});
						
			$("#uploader").plupload({
				// General settings           
				runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
				url : "/rest/um/crossInfo/upload",    
				//unique_names: true,  
				chunk_size : '1mb',           
				//rename : true,  
				dragdrop: true,  
				filters : {               
					// Maximum file size               
					max_file_size : '10mb',  
					// Specify what files to browse for  
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
						if(info.status == 200){
							picture += info.response + ";";
						}else{
							layer.alert('图片上传失败！', {icon: 6});
						}
					},
					UploadComplete : function(uploader,files) {
						$("#banners").val(picture);
					},
					QueueChanged : function(uploader){
						var count=uploader.files.length; 
						if(count > 4){
							layer.alert('最多只能上传四张图片,请重新上传', {icon: 6});
							uploader.splice();
						}
					}
				}			  
			});  		
			
			
			$('#provinceId').change(function() {
				if($(this).val() > 0) {
					var cityName = $("#provinceId option:selected").text();
							
					$("#cityId option:gt(0)").remove();

					var params = {};
					params.provinceId = $(this).val();

					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/crossInfo/cityList",
						data : params,
						success : function(result) {
							for (var i = 0; i < result.data.length; i++) {
								$("<option value='" + result.data[i].id + "'>" + result.data[i].name + "</option>").appendTo("#cityId");
							}		
						}
					})
				}
				$("#cityId option:gt(0)").remove();
			});
			
			$('#nativePlaceProvinceId').change(function() {
				if($(this).val() > 0) {
					var cityName = $("#nativePlaceProvinceId option:selected").text();
							
					$("#nativePlaceCityId option:gt(0)").remove();

					var params = {};
					params.nativePlaceProvinceId = $(this).val();

					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/crossInfo/nativePlaceCityList",
						data : params,
						success : function(result) {
							for (var i = 0; i < result.data.length; i++) {
								$("<option value='" + result.data[i].id + "'>" + result.data[i].name + "</option>").appendTo("#nativePlaceCityId");
							}		
						}
					})
				}
				$("#nativePlaceCityId option:gt(0)").remove();
			});
		});
	   
		var formId = "edit-cross-form";
		
		var formRules = {
			
         	provinceId: {
                min : 1
            },
			cityId: {
                min : 1
            },
			name: {
                required : true
            },
			mobile: {
                required : true,
				isTel : true
            },
			contactPhone: {
                required : true,
				isTel : true
            },
			workName: {
                required : true
            },
			height: {
                required : true,
				isFloat : true
            },
			school: {
                required : true
            },
			education: {
				required : true
            },
			nativePlaceProvinceId: {
				min : 1
            },
			nativePlaceCityId: {
				min : 1
            },
			constellation: {
				required : true
            },
			yearBirth: {
				required : true
            },
			workAge: {
                required : true
            },
			specialty: {
                required : true,
				minlength:10
            }
		}
		
		var message = {
				
			provinceId: {
                min : '省份不能为空'
            },
			cityId: {
                min : '城市不能为空'
            },
			name: {
                required : '姓名不能为空'
            },
			mobile: {
                required : '登录手机号码不能为空',
				isTel : '请正确输入手机号码'
            },
			contactPhone: {
                required : '联系手机号码不能为空',
				isTel : '请正确输入手机号码'
            },
			workName: {
                required : '工作名不能为空'
            },
			height: {
                required : '身高不能为空',
				isFloat : '请填入数字'
            },
			school: {
                required : '毕业院校不能为空'
            },
			education: {
				required : '学历不能为空'
            },
			nativePlaceProvinceId: {
				min : '省不能为空'
            },
			nativePlaceCityId: {
				min : '市不能为空'
            },
			constellation: {
				required : '星座不能为空'
            },
			yearBirth: {
				required : '属相不能为空'
            },
			workAge: {
                required : '工龄不能为空'
            },
			specialty: {
                required : '特长不能为空',
				minlength: '特长不能小于十个字' 
            }
		}
		
		
		var func1 = function() { 
			
		}
		
		var func2 = function(data) {
			if (data.statusCode == 0) {
				
				layer.confirm('操作成功', {
					btn: ['确认'] //按钮
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
				
			} else if (data.statusCode == 40001) {
				layer.alert('服务器参数验证出错，请确认填写内容重新提交', {icon: 6});
			} else if (data.statusCode == 40002) {
				layer.alert('服务器运行异常', {icon: 6});
			} else if (data.statusCode == 40003) {
				layer.alert('图片上传失败', {icon: 6});
			} else if (data.statusCode == 40018) {
				layer.alert('该金鹰正在服务，请确认服务完成后离职;该金鹰正在服务，请确认服务完成后离职', {icon: 6});
			} else {
				layer.alert('未知错误', {icon: 6});
			}
		}
		
		FormObj.init(formId, formRules, message, func1, func2);
		
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>