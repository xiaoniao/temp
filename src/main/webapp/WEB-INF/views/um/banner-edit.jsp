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
		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<!--/meta 作为公共模版分离出去-->

		<title>品牌编辑</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container">
		<form action="/rest/um/banner/edit" method="post" enctype="multipart/form-data" class="form form-horizontal" id="edit-banner-form">
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">标题：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="hidden" value="${banner.id}" name="id" />
					<input type="text" class="input-text" id="title" name="title" style="width:300px" value="${banner.title}">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">图片（750*300）：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<img src="${banner.image}" width="300">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-3">重新上传：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="btn-upload form-group">
						<input class="input-text upload-url radius" type="text" readonly name="filePath" placeholder="图片尺寸750*300px">
						<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
						<input type="file" multiple name="imageFile" class="input-file">
					</span>
				</div>
			</div>
			<div class="row cl skin-minimal">
				<label class="form-label col-xs-4 col-sm-3">状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div class="radio-box">
						<input type="radio" id="radio-1" name="status" value="1" <c:if test="${banner.status == 1}">checked</c:if>>
						<label for="radio-1">显示</label>
					</div>
					<div class="radio-box">
						<input type="radio" id="radio-2" name="status" value="0" <c:if test="${banner.status == 0}">checked</c:if>>
						<label for="radio-2">禁用</label>
					</div>
				</div>
  			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<button type="submit" class="btn btn-success radius" id="confirm-edit-btn">
						<i class="icon-ok"></i> 确定
					</button>
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
	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
		/**
		 * 单选、复选样式加载
		 */
		$(function() {
			$('.skin-minimal input').iCheck({
				checkboxClass: 'icheckbox-blue',
				radioClass: 'iradio-blue',
				increaseArea: '20%'
			});
		});
		
		var formId = "edit-banner-form";
		
		var formRules = {
        	title: {
                required : true
            },
            imageFile: {
            	required: false
            }
		}
		
		var message = {
			title: {
                required: "标题不能为空"
            },
            imageFile: {
            	required: "图片不能为空"
			}
		}
		
		var func1 = function() { }
		
		var func2 = function(data) {
			if (data.statusCode == 0) {
				
				layer.confirm('操作成功', {
					btn: ['确认'] //按钮
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
				
			} else if (data.statusCode == 501) {
				layer.alert('服务器参数验证出错，请确认填写内容重新提交', {icon: 6});
			} else if (data.statusCode == 502) {
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