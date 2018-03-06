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

		<title>资源审核</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container">
		<form action="" class="form form-horizontal">
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-1">提交时间：</label>
				<div class="formControls col-xs-8 col-sm-10">
					<label>${resourceSuggest.createDate}</label>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-1">提交人：</label>
				<div class="formControls col-xs-8 col-sm-10">
					<label>${resourceSuggest.submitUserName}</label>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-1">所属城市：</label>
				<div class="formControls col-xs-8 col-sm-10">
					<label>${resourceSuggest.provinceAndcityName}</label>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-1">所属分类：</label>
				<div class="formControls col-xs-8 col-sm-10">
					<label>${resourceSuggest.typeName}</label>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-1">资源标题：</label>
				<div class="formControls col-xs-8 col-sm-10">
					<label>${resourceSuggest.title}</label>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-1 col-sm-1">提交内容：</label>
				<div class="formControls col-xs-8 col-sm-10">
					<label>${resourceSuggest.content}</label>
					<c:forEach items="${resourceSuggest.imageList}" var="image" varStatus="s">
						<img src="${image}" class="thumbnail">
					</c:forEach>
				</div>
			</div>
		</form>

		<c:if test='${resourceSuggest.checkStatus == 0}'>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
					<button class="btn btn-danger radius" onClick="save(1)">驳回</button>
					<button class="btn btn-primary radius" onClick="save(2)">采纳</button>
				</div>
			</div>
		</c:if>

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

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">

		var id = ${resourceSuggest.id};

		function save(checkStatus) {
			var parameter = {
				id : id,
				checkStatus : checkStatus
			}

			layer.confirm('确定吗？', function(index) {
				$.ajax({  
					type : "POST",  
					url : "/rest/um/resourceSuggest/operate",
					async : false,
					data:parameter,					
					dataType: 'JSON',
					success : function(data) {
						if (data.statusCode == 0) {
							layer.confirm('操作成功', {
								btn: ['确认'] //按钮
								}, function() {
								parent.layer.close(index);
								location.replace(location.href);
							});
						} else {
							layer.alert('操作失败', {icon: 6});
						}
					}
				});
			});	
		}
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>