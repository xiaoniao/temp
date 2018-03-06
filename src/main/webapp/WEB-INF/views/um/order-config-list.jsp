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

		<title>系统设置</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container form-horizontal">
			<div class="row cl" style="margin:10px 0;">
				<input type="hidden" value="${orderConfigInfo.id}" name="id" id="id"/>
				<label class="form-label col-xs-4 col-sm-3">服务费定价：</label>
				<div class="formControls col-xs-6 col-sm-6">
					<input  id="serviceMoney" name="serviceMoney" type="text"value="${orderConfigInfo.serviceMoney}" class="input-text" style="width: 300px;">（元）　
					<button class="btn btn-primary" id="serviceMoneyButton">确认修改</button>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-3">延时费定价：</label>
				<div class="formControls col-xs-6 col-sm-6">
					<input  id="delayMoney" name="delayMoney" type="text"value="${orderConfigInfo.delayMoney}" class="input-text" style="width: 300px;">（元）　
					<button class="btn btn-primary" id="delayMoneyButton">确认修改</button>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-3">订单轮巡时间(1-59)：</label>
				<div class="formControls col-xs-6 col-sm-6">
					<input  id="loopTime" name="loopTime" type="text" value="${orderConfigInfo.loopTime}" class="input-text" style="width: 300px;">（分钟）
					<button class="btn btn-primary" id="loopTimeButton">确认修改</button>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-3">优惠券使用限额(单天)：</label>
				<div class="formControls col-xs-6 col-sm-6">
					<input  id="maxDiscount" name="maxDiscount" type="text" value="${orderConfigInfo.maxDiscount}" class="input-text" style="width: 300px;">（元）　
					<button class="btn btn-primary" id="maxDiscountButton">确认修改</button>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-3">代支付满分金额(能量柱)：</label>
				<div class="formControls col-xs-6 col-sm-6">
					<input  id="fullPayment" name="fullPayment" type="text" value="${orderConfigInfo.fullPayment}" class="input-text" style="width: 300px;">（元）　
					<button class="btn btn-primary" id="fullPaymentButton">确认修改</button>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-3">余额满分金额(能量柱)：</label>
				<div class="formControls col-xs-6 col-sm-6">
					<input  id="fullBalance" name="fullBalance" type="text" value="${orderConfigInfo.fullBalance}" class="input-text" style="width: 300px;">（元）　
					<button class="btn btn-primary" id="fullBalanceButton">确认修改</button>
				</div>
			</div>
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
	
	<!-- 国际化中文支持 -->  
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/i18n/zh_CN.js"></script>  

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
	   
	    $(function () {	
			var id = $("#id").val();
		    $("#serviceMoneyButton").click(function(){
			   var serviceMoney = $("#serviceMoney").val();
 
			   layer.confirm('是否确认修改', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/orderConfig/updateServiceMoney/?serviceMoney=" + serviceMoney + "&id=" + id,
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
				
		    });
		   
		    $("#delayMoneyButton").click(function(){
			   var delayMoney = $("#delayMoney").val();
			   
			     layer.confirm('是否确认修改', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/orderConfig/updateDelayMoney/?delayMoney=" + delayMoney + "&id=" + id,
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
				
				
		    });
		   
		    $("#loopTimeButton").click(function(){
			   var loopTime = $("#loopTime").val();
			   
			     layer.confirm('修改轮训时间会导致服务一段时间不可用，是否确认修改？', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/orderConfig/updateLoopTime/?loopTime=" + loopTime + "&id=" + id,
						success : function(result) {
							if (result.statusCode == 0) {
								layer.confirm('操作成功，请隔10秒后重新登录系统', {
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
				
		    });
			
			$("#maxDiscountButton").click(function(){
			   var maxDiscount = $("#maxDiscount").val();
			   
			     layer.confirm('是否确认修改', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/orderConfig/updateMaxDiscount/?maxDiscount=" + maxDiscount + "&id=" + id,
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
				
		    });
			
			$("#fullPaymentButton").click(function(){
			   var fullPayment = $("#fullPayment").val();
			   
			     layer.confirm('是否确认修改', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/orderConfig/updateFullPayment/?fullPayment=" + fullPayment + "&id=" + id,
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
				
		    });
			
			$("#fullBalanceButton").click(function(){
			   var fullBalance = $("#fullBalance").val();
			   
			     layer.confirm('是否确认修改', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/orderConfig/updateFullBalance/?fullBalance=" + fullBalance + "&id=" + id,
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
				
		    });
	    });
		
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>