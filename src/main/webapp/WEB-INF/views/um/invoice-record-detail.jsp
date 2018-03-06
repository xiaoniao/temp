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
		<title>发票详情</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>发票管理 <span class="c-gray en">&gt;</span> 发票详情
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container form-horizontal">
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l" >
					<span style="font-size: 20px; color: orange; font-weight: bold;">
						<c:if test="${invoiceRecord.money == null || invoiceRecord.money == 0}">
						<input type="number" placeholder="开票金额" id="money" name="money" class="input-text" style="width:200px;">
						</c:if>
						<c:if test="${invoiceRecord.status == 0}">
						<a class="ml-5 btn btn-danger radius a-order-refused" href="javascript:;" title="确认寄出" onclick="confirmSend(${invoiceRecord.id});">
							确认寄出
						</a>
						</c:if>
					</span>
				</span>
			</div>
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">发票详情</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">发票类型：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.typeStr}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">开票金额：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.money}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">邮寄状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.statusStr}
					</label>
				</div>
			</div>
			<c:if test="${invoiceRecord.type == 0}">
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">发票抬头：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.title}
					</label>
				</div>
			</div>
			</c:if>
			<c:if test="${invoiceRecord.type == 1}">
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">企业名称：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.companyName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">识别号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.identification}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">信用代码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.code}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">注册地址：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.address}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">联系电话：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.mobile}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">开户行：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.bankName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">开户行帐号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.bankName}
					</label>
				</div>
			</div>
			</c:if>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">收件人信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">收件人姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.receiverName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">收件人手机号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.receiverMobile}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">收件人地址：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.receiverAddress}
					</label>
				</div>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">快递信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">快递公司：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.expressName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">快递单号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.expressCode}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">创建时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${invoiceRecord.createTimeStr}
					</label>
				</div>
			</div>
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">订单信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2"></label>
				<div class="formControls col-xs-8 col-sm-9">
					<a href="/rest/um/order/toOrderDetail?id=${invoiceRecord.referenceId}">查看订单</a>
				</div>
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

			function confirmSend(id) {
				var money = $('#money').val();
				if(money == null || money == '' || money == 0) {
					alert('请输入开票金额');
					return;
				}

				layer.confirm('是否确认寄出', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/invoice/confrimSend/?id=" + id + "&money=" + money,
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
					layer.close(index);
				});
			}

		</script>
	</body>
</html>