<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		
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
		<title>品牌图片</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
	<body>
		<div class="page-container">
			<div class="mt-20">
				<img src="${brand.img}"/>
			</div>	
		</div>
		
		<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script>
		<script type="text/javascript" src="/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/lib/laypage/1.2/laypage.js"></script>
		<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
		<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
		<script type="text/javascript" src="/javascript/window-utils.js"></script>
		<script type="text/javascript" src="/javascript/plist-utils.js"></script>
		
		<script type="text/javascript" src="/javascript/form-utils.js"></script>
		<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
		<script type="text/javascript">
			
			//跳转详情页面
			function toTravelDetails(orderId) {	
				window.location.href="/rest/um/order/toTravelDetails/?id=" + orderId;
			}
				
			// 用户更换金鹰
			function replaceCross(orderId) {
				var params = {};
				params.type = 2;
				params.area = ['800px','500px'];
				params.fix = false;
				params.maxmin = true;
				params.shade = 0.4;
				params.title = "更换金鹰";
				params.content = '/rest/um/order/replaceCross/?id=' + orderId;
				params.end = function() {
					location.replace(location.href);
			    };
				layer.open(params);
			}	
			
			// 金鹰更换金鹰
			function crossReplaceCross(orderId) {
				var params = {};
				params.type = 2;
				params.area = ['800px','500px'];
				params.fix = false;
				params.maxmin = true;
				params.shade = 0.4;
				params.title = "更换金鹰";
				params.content = '/rest/um/order/toCrossReplaceCross/?id=' + orderId;
				params.end = function() {
					location.replace(location.href);
			    };
				layer.open(params);
			}	
			
			function toComplaintOrder(orderId) {
				var params = {};
				params.type = 2;
				params.area = ['800px','500px'];
				params.fix = false;
				params.maxmin = true;
				params.shade = 0.4;
				params.title = "结束服务";
				params.content = '/rest/um/order/toComplaintOrder/?id=' + orderId;
				params.end = function() {
					location.replace(location.href);
			    };
				layer.open(params);
			}	
			
			function endService(orderId) {
				layer.confirm('是否结束服务', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/order/endService/?id=" + orderId,
						success : function(result) {
							if (result.statusCode == 0) {
								layer.confirm('操作成功', {
									btn: ['确认'] //按钮
								}, function(index) {
									parent.layer.close(index);
									location.replace(location.href);
								});
							} else {
								layer.alert('操作失败');
							}
						}
					})
				}, function(index) {
					parent.layer.close(index);
				});
			}
			
		</script>
	</body>
</html>