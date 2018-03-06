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
		<title>订单详情</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>全部订单 <span class="c-gray en">&gt;</span> 订单详情
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container form-horizontal">
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l" >
					<span style="font-size: 20px; color: orange; font-weight: bold;">
						<c:choose> 
							<c:when test="${not empty orderComplaintInfo}">
								<a class="ml-5 btn btn-warning radius a-order-receive" href="javascript:;" title="更换金鹰" onclick="replaceCross(${orderDetail.id});">
									更换金鹰
								</a>
								<a class="ml-5 btn btn-danger radius a-order-refused" href="javascript:;" title="结束服务" onclick="toComplaintOrder(${orderDetail.id});">
									结束服务
								</a>
								<c:if test="${orderDetail.status > 5}">
									<a align="right" class="ml-5 btn btn-danger radius a-order-refused" href="javascript:;" title="查看行程" onclick="toTravelDetails(${orderDetail.id});">
										查看行程
									</a>
								</c:if>
							</c:when>
							<c:when test="${not empty orderCancelInfo}">
								<a class="ml-5 btn btn-warning radius a-order-receive" href="javascript:;" title="更换金鹰" onclick="crossReplaceCross(${orderDetail.id});">
									更换金鹰
								</a>
								<a class="ml-5 btn btn-danger radius a-order-refused" href="javascript:;" title="结束服务" onclick="endService(${orderDetail.id});">
									结束服务
								</a>
								<c:if test="${orderDetail.status > 5}">
									<a align="right" class="ml-5 btn btn-danger radius a-order-refused" href="javascript:;" title="查看行程" onclick="toTravelDetails(${orderDetail.id});">
										查看行程
									</a>
								</c:if>
							</c:when>
							<c:otherwise>   
							</c:otherwise> 
						</c:choose> 	
					</span>
				</span>
				<span class="r">
				
				</span>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">订单信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">订单状态：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.orderStatus}
						<c:if test="${orderDetail.status == 10 || orderDetail.status == 8}">
							<font color='#FF0000'>
								<c:if test="${not empty orderCancelRemark}">${orderCancelRemark.remark}</c:if>
								<c:if test="${not empty orderComplaintRemark}">${orderComplaintRemark.remark}</c:if>
							</font>
						</c:if>	
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">订单编号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.orderCard}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">服务编码：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.serviceCode}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">下单时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${orderDetail.createTime}"/>
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">服务部接到需求时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${orderDetail.assignTime}"/>
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">完成行程单时间：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${orderDetail.travelFinishTime}"/>
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">服务城市：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.cityName}
					</label>
				</div>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">用户信息</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">金鹰工号：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.crossNumber}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">金鹰姓名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.crossName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">金鹰工作名：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.workName}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">金鹰联系电话：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.crossContactPhone}
					</label>
				</div>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">行程要求</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">讲述要求：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.travelRequireText}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">发票信息：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.invoiceTitle}
					</label>
				</div>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">邮寄地址：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						${orderDetail.invoiceTitle}
					</label>
				</div>
			</div>	
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">支付方式：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<c:if test="${orderDetail.costPayMethod == 0}">
							自己付
						</c:if>
						<c:if test="${orderDetail.costPayMethod == 1}">
							金鹰代付
						</c:if>
					</label>
				</div>
			</div>
			
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">延时费信息：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<table class="table table-border table-bordered table-hover table-bg table-sort" id="order_list_table">
							<tr class="text-c">
								<th width="70">支付时间</th>
								<th width="80">延时时间</th>
								<th width="60">支付金额</th>
							</tr>
							<c:forEach items="${orderDelayList}" var="orderDelay">
								<tr>
									<td style="text-align:center;">${orderDelay.createDate }</td>
									<td style="text-align:center;">${orderDelay.hour}</td>
									<td style="text-align:center;">${orderDelay.money }</td>
								</tr>
							</c:forEach>
						</table>
					</label>
				</div>
			</div>
			
			<div class="row cl">
				<label class="col-xs-12 col-sm-12" style="font-size: 20px; margin: 20px; border-bottom: 1px solid #F3F3F3; padding: 10px;">订单日志</label>
			</div>
			<div class="row cl" style="margin:10px 0;">
				<label class="form-label col-xs-4 col-sm-2">订单日志：</label>
				<div class="formControls col-xs-8 col-sm-9">
					<label class="col-xs-8 col-sm-9">
						<table class="table table-border table-bordered table-hover table-bg table-sort" id="order_list_table">
							<tr class="text-c">
								<th width="70">操作人</th>
								<th width="80">操作时间</th>
								<th width="60">操作内容</th>
							</tr>
							<c:forEach items="${orderLogInfo}" var="orderLog">
								<tr>
									<td style="text-align:center;">${orderLog.userName }</td>
									<td style="text-align:center;">${orderLog.logDate}</td>
									<td style="text-align:center;">${orderLog.logInfo }</td>
								</tr>
							</c:forEach>
						</table>
					</label>
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
				params.area = ['600px','400px'];
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