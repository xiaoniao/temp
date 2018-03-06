<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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

		<title>金鹰详情</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container form-horizontal">
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">基本信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">金鹰图片：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<c:forEach items="${bannersList}" var="banners">
							<img src="${banners}" height="150">
						</c:forEach>
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">工作照：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<img src="${cross.pic}" height="150">
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">服务城市：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.cityName}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.name}</label>	
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">联系手机号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.contactPhone}</label>			
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">工作名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.workName}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">身高：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.height}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">毕业院校：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.school}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">学历：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.education}</label>	
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">籍贯：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.nativePlace}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">星座：</label>
				<div class="formControls col-xs-8 col-sm-9">					
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.constellation}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">属相：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${cross.yearBirth}</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">工龄：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">
						${cross.workAge}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">金鹰特长：</label>
				<div class="formControls col-xs-8 col-sm-9">		
					<label class="col-xs-4 col-sm-2" style="width:300px">
						${cross.specialty}
					</label>
				</div>
			</div>
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">账户信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">登录手机号码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">
						${cross.mobile}
					</label>
				</div>
			</div>
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">服务信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">综合评分：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px"><c:if test="${comprehensivecSore != NaN}">${comprehensivecSore}分</c:if></label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">各项平局分：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">精神气质：${ethos}分</label>
					<label class="col-xs-4 col-sm-2" style="width:300px">能力素养：${abilityAccomplishment}分</label>
					<label class="col-xs-4 col-sm-2" style="width:300px">服务态度：${serviceAttitude}分</label>
					<label class="col-xs-4 col-sm-2" style="width:300px">行为礼仪：${behaviorEtiquette}分</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">服务订单：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${orderCount}单</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">服务天数：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-4 col-sm-2" style="width:300px">${dayCount}天</label>
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
	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.min.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>

	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
		
	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>