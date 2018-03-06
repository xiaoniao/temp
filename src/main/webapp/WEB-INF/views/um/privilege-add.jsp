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
		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<!--/meta 作为公共模版分离出去-->

		<title>广告添加</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container">
		<form action="/rest/um/privilege/add" method="post" enctype="multipart/form-data" class="form form-horizontal" id="add-banner-form">

			<div class="row cl skin-minimal">
				<label class="form-label col-xs-4 col-sm-2">角色：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<div>
						<div class="radio-box">
							<input type="radio" id="radio-1" name="type" value="normal_manage" checked>
							<label for="radio-1">普通管理员</label>
						</div>
					</div>
					<div>
						<div class="radio-box">
							<input type="radio" id="radio-2" name="type" value="res_manage_all" >
							<label for="radio-2">全国资源管理人</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="radio-3" name="type" value="res_manage_city" >
							<label for="radio-3">城市资源管理人</label>
						</div>
					</div>
					<div>
						<div class="radio-box">
							<input type="radio" id="radio-4" name="type" value="res_dev_full" >
							<label for="radio-4">全职资源开发人</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="radio-5" name="type" value="res_dev_practice" >
							<label for="radio-5">实习资源开发人</label>
						</div>
					</div>
				</div>
  			</div>

			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">用户名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text" id="username" name="username" style="width:300px">
				</div>
			</div>

			<input type="hidden" name="encryptionPassword" id="encryptionPassword"/>

			<div class="row cl" id="section" name="section">
				<label class="form-label col-xs-4 col-sm-2">区域：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<span class="select-box inline">							
						<select name="provinceId"  id="provinceId" class="select">
							<option value="">-- 省 --</option>
							<c:forEach items="${provinceList}" var="province">
								<option value="${province.id}">${province.name}</option> 
							</c:forEach>
						</select>
					</span>	
					<span class="select-box inline">
						<select name="cityId"  id="cityId" class="select">
							<option value="">-- 市 --</option>
						</select>
					</span>
				</div>
			</div>

			<div class="row cl" id="resource" name="resource">
				<label class="form-label col-xs-4 col-sm-2">资源分类：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input type="text" class="input-text hidden" id="resourceTypeIds" name="resourceTypeIds" style="width:300px">
					<input type="text" class="input-text" id="resourceTypeNames" name="resourceTypeNames" style="width:300px">
					<span class="select-box inline">							
						<select name="resourceTypes"  id="resourceTypes" class="select">
							<option value="">-- 资源分类 --</option>
							<c:forEach items="${resourceTypes}" var="resourceType">
								<option value="${resourceType.id}-${resourceType.name}">${resourceType.name}</option> 
							</c:forEach>
						</select>
					</span>
				</div>
			</div>

			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<button type="submit" class="btn btn-success radius" id="confirm-add-btn">
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
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>
	<script type="text/javascript" src="/javascript/sha256.js"></script>

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">

		$(function () {
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

			$('#resourceTypes').change(function() {
				var val = $(this).val();

				var id = val.split('-')[0];
				var name = val.split('-')[1];

				var ids = $('#resourceTypeIds').val();
				if (ids.indexOf(id) == -1) {
					if (ids.length == 0) {
						$('#resourceTypeIds').val(id);
					} else {
						$('#resourceTypeIds').val(ids + ',' + id);
					}
				}

				var names = $('#resourceTypeNames').val();
				console.log(names)
				if (names.indexOf(name) == -1) {
					if (names.length == 0) {
						$('#resourceTypeNames').val(name);
					} else {
						$('#resourceTypeNames').val(names + ',' + name);
					}
				}
			});

			$('#section').hide();
			$('#resource').hide();
			
			$('#radio-1').change(function() {
				$('#section').hide();
				$('#resource').hide();
			});
			$('#radio-2').change(function() {
				$('#section').hide();
				$('#resource').hide();
			});
			$('#radio-3').change(function() {
				$('#section').show();
				$('#resource').hide();
			});
			$('#radio-4').change(function() {
				$('#section').hide();
				$('#resource').hide();
			});
			$('#radio-5').change(function() {
				$('#section').show();
				$('#resource').show();
			});
		});
	
		var formId = "add-banner-form";
		
		var formRules = {
        	title: {
                required : true
            },
            imageFile: {
            	required: true
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

		/**
		 * 表单提交之前
		 */
		var func1 = function() { 							
			var new_pass = sha256_digest("cityjy123456");
			$("#encryptionPassword").val(new_pass);
		}
		
		var func2 = function(data) {   //表单提交之后回调 
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