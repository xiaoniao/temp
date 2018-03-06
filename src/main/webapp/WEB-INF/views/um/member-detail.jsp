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

		<title>会员详情</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
<body>
	<article class="page-container form-horizontal">
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">账号信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">审核状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<c:choose> 
							<c:when test="${memberInfo.idcardCheckPassStatus == 0}">
								未审核
							</c:when>
							<c:when test="${memberInfo.idcardCheckPassStatus == 1}">
								审核通过
							</c:when>
							<c:when test="${memberInfo.idcardCheckPassStatus == 2}">
								审核不通过
							</c:when>
						</c:choose>	
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">帐户余额：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.balance}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">登录账号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.userName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">注册时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.createDate}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">最后登录时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.lastLoginDate}
					</label>
				</div>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">基本信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.name}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">性别：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<c:if test="${memberInfo.sex == 1}">女</c:if>
						<c:if test="${memberInfo.sex == 0}">男</c:if>
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">工作地：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.address}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">籍贯：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.nativePlace}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">现居地：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.livePlace}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">行业：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.business}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">公司：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.company}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">职位：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.position}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">出差频率：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.travelFrequency}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">喜好：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.habit}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">忌讳：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.taboo}
					</label>
				</div>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">实名信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">身份证号码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${memberInfo.idcard}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">身份证照片：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<table>
							<tr>
								<td width="50%">
									<img src="${memberInfo.idcardPic1}" width="300">
								</td>
								<td width="50%">
									<img src="${memberInfo.idcardPic2}" width="300">
								</td>
							</tr>
						</table>
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<div class="radio-box">
							<input type="radio" id="radio-1" name="status" value="1" <c:if test="${memberInfo.status == '1'}">checked</c:if>>
							<label for="radio-1">启用</label>
						</div>
						<div class="radio-box">
							<input type="radio" id="radio-2" name="status" value="0" <c:if test="${memberInfo.status == '0'}">checked</c:if>>
							<label for="radio-2">禁用</label>
						</div>
					</label>
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
		var userId = ${memberInfo.userId};
		$("#radio-1").change(cheangeStatus);
		$("#radio-2").change(cheangeStatus);
		function cheangeStatus() {
			var params = {};
			params.status = $(this).val();
			params.userId = userId;
			$.ajax({
				type : "post",
				dataType : "JSON",
				url : "/rest//um/member/edit",
				data : params,
				success : function(result) {
					if(result.statusCode == 0) {
						// 成功
						layer.alert('操作成功', {icon: 6});
					}
				}
			})
		}

	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>