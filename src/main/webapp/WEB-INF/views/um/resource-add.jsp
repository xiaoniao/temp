<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

		<!-- 上传图片 -->
		<link type="text/css" rel="stylesheet" href="/lib/jquery-ui/jquery-ui.min.css"/>
		<link type="text/css" rel="stylesheet" href="/lib/jquery.ui.plupload/css/jquery.ui.plupload.css"/>		
		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<!--/meta 作为公共模版分离出去-->

		<title>资源添加</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container">
		<form action="/rest/um/resource/add" method="post" class="form form-horizontal" id="form-resource-add">
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">标题：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<input type="text" class="input-text" name="title" autocomplete="off" placeholder="标题">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">城市：</label>
				<div class="col-xs-8 col-sm-8">
					<span class="select-box inline">
						<select name="provinceId" id="provinceId" class="select input-text help-inline">
							<option value="">-- 省 --</option>
							<c:forEach items="${provinceList}" var="province" varStatus="s">
								<option value="${province.id}">${province.name}</option>
							</c:forEach>
						</select>
					</span>
					<span class="select-box inline">
						<select name="cityId" id="cityId" class="select input-text help-inline">
							<option value="">-- 市 --</option>
						</select>
					</span>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">分类：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<span class="select-box">
					<select name="typeId" id="typeId" class="select input-text help-inline">
						<option value="">-- 分类 --</option>
						<c:forEach items="${resourceTypes}" var="resourceType" varStatus="s">
							<option value="${resourceType.id}">${resourceType.name}</option>
						</c:forEach>
					</select>
					</span>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">内容：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<textarea id="text-editor" class="example" rows="" cols="" style="width:100%;height:500px;">${string}</textarea>
					<input type="hidden" id="content" name="content">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-2 col-sm-2">图片上传：</label>
				<div class="formControls col-xs-8 col-sm-8">
					<div id="uploader">  
						<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
					</div>
					<input type="hidden" id="images" name="images">
					<!--<a href="javascript:deletePictrue(${cross.id});" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>			
					<ul class="cl portfolio-area">
						<c:forEach items="${imageList}" var="image">
							<li class="item">
								<div class="portfoliobox">
									<input class="checkbox" name="image" type="checkbox" value="${image}">
									<div class="picbox">
										<img src="${image}">
									</div>
								</div>
							</li>
						</c:forEach> 
					</ul>-->
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<input class="btn btn-primary radius" type="submit" href="javascript:save();" value="提交">
				</div>
			</div>
		</form>
	</article>
	
	<!-- 编辑器 -->
	<script type="text/javascript" src="/lib/kindeditor-4.1.7/kindeditor-min.js"></script> 
	<script type="text/javascript" src="/lib/kindeditor-4.1.7/lang/zh_CN.js"></script> 

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="/lib/icheck/jquery.icheck.min.js"></script>

	<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer /作为公共模版分离出去-->
	
	<!-- 表单验证 -->
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>

	<!-- 上传图片 -->
	<script type="text/javascript" src="/lib/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/plupload.full.min.js"></script>  
	<script type="text/javascript" src="/lib/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/i18n/zh_CN.js"></script>

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
	
		KindEditor.ready(function(K) {
			window.editor = K.create('#text-editor', {
                // uploadJson : '/rest/common/uploadImage',
                // fileManagerJson : '/rest/common/uploadImage2',
                // allowFileManager : true,
				// allowUpload : true,
				uploadJson : '/rest/common/uploadImage',
				fileManagerJson : '../jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
		});
		
		var picture = "";
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
						layer.alert('图片上传失败！', {icon: 6});
					}
				},
				UploadComplete : function(uploader,files) {
					$("#images").val(picture);
				},
				QueueChanged : function(uploader){
					var count=uploader.files.length; 
					if(count > 20){
						layer.alert('最多只能上传二十张图片,请重新上传', {icon: 6});
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
					url : "/rest/um/brand/cityList",
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

		var formId = "form-resource-add";
		
		var formRules = {
			title: {
                required : true
            },
			cityId: {
                required : true
            },
			typeId: {
                required : true
            },
			content: {
                required : true
            },
		}
		
		var message = {
			title: {
                required: "标题不能为空"
            },
			cityId: {
                required: "城市不能为空"
            },
			typeId: {
                required: "分类不能为空"
            },
			content: {
                required: "内容不能为空"
            },
		}
		
		var func1 = function() {
			var descHtml = editor.html(); 
			var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
			descHtml = descHtml.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
			console.log(descHtml)
			$('#content').val(descHtml);
			console.log($('#content').val());
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
			} else {
				layer.alert('未知错误', {icon: 6});
			}
		}
		
		FormObj.init(formId, formRules, message, func1, func2);
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>