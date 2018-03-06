<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
	<LINK rel="Bookmark" href="/favicon.ico" >
	<LINK rel="Shortcut Icon" href="/favicon.ico" />
	
	<link rel="stylesheet" type="text/css" href="/static/h-ui/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/H-ui.admin.css" />
	<link rel="stylesheet" type="text/css" href="/lib/Hui-iconfont/1.0.7/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="/lib/icheck/icheck.css" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/skin/default/skin.css" id="skin" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/style.css" />
	
	<title>审核身份证</title>
</head>
<body>
	<article class="page-container">
			<div class="row cl" style="margin:10px 0">
				<label class="form-label col-xs-3 col-sm-2" style="text-align:right;">身份证号码:</label>
				<div class="formControls col-xs-9 col-sm-12">
					<label class="col-xs-16 col-sm-8">
						${memberInfo.idcard}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0">
				<label class="form-label col-xs-3 col-sm-2" style="text-align:right;">身份证照片:</label>
				<div class="formControls col-xs-9 col-sm-12">
					<label class="col-xs-16 col-sm-8">
						<img src="${memberInfo.idcardPic1}" width="90%" border="1px solid #ccc">
					</label>
					<label class="col-xs-16 col-sm-8">
						<img src="${memberInfo.idcardPic2}" width="90%" border="1px solid #ccc">
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<a class="ml-10 btn btn-primary radius " href="javascript:auditPass(${memberInfo.id});" title="审核通过">审核通过</a>
					<a class="ml-5 btn btn-danger radius a-order-refused" href="javascript:auditFailure(${memberInfo.id});" title="审核不通过">审核不通过</a>
				</div>
			</div>
	</article>

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script> 
	<script type="text/javascript" src="/lib/icheck/jquery.icheck.min.js"></script> 
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.min.js"></script> 
	<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script> 
	

	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>
	
	<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript">
	
		   function auditPass(id) {
				layer.confirm('确定审核通过吗？', function(index) {
						$.ajax({  
						type : "GET",  
						url : "/rest/um/member/auditPass/?id=" + id,
						async : false,  
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
			
			function auditFailure(id) {
				layer.confirm('确定审核不通过吗？', function(index) {
						$.ajax({  
						type : "GET",  
						url : "/rest/um/member/auditFailure/?id=" + id,
						async : false,  
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
</body>
</html>