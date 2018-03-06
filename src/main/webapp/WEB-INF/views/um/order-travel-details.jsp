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
		<title>行程花费详情</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>全部订单 <span class="c-gray en">&gt;</span> 行程花费详情
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="mt-20">
				<table class="table">
					<tbody>
						<tr>
							<th>总花费</th><th>${money}</th>
						</tr>
					</tbody>
				</table>
				<c:forEach items="${costDtoList}" var="costDto">
					<br/>
					<table class="table" border="1">
						<tbody>
							<tr>
								<th width="20%">${costDto.date}</th><th width="30%">￥${costDto.travelMoney}</th>
								<th width="30">
									<c:choose> 
										<c:when test="${costDto.travelStatus == 1}">
											等待确认	
										</c:when>
										<c:when test="${costDto.travelStatus == 1}">
											已确认	
										</c:when>
										<c:when test="${costDto.travelStatus == 2}">
											服务中
										</c:when>
										<c:when test="${costDto.travelStatus == 3}">
											待确认
										</c:when>
										<c:when test="${costDto.travelStatus == 4}">
											已完成
										</c:when>	
										<c:when test="${costDto.travelStatus == 5}">
											行程取消
										</c:when>
										<c:when test="${costDto.travelStatus == -1}">
											账单存疑
										</c:when>
									</c:choose>
								</th>
								<th width="20%">
									<c:if test="${costDto.travelStatus == -1}">									
										<a style="text-decoration:none" class="ml-5 a-area-edit" href="javascript:;" 
												onclick="placeOrder('${costDto.date}',${costDto.orderId},${money});" title="提交">提交</a>
									</c:if>
								</th>
							</tr>		
					<br/>
						<c:forEach items="${costDto.list}" var="costItemList">	
								<th width="20%">${costItemList.startDate}-${costItemList.endDate}</th><th width="30%">${costItemList.title}</th>
								<th width="30%">￥${costItemList.costMoney}</th><th width="20%"></th>
									<c:forEach items="${costItemList.costs}" var="costs">
										<tr>
											<c:choose> 
												<c:when test="${costDto.travelStatus == -1}">
													<td width="30%">${costs.costName}</td>
													<td width="30%">
														${costs.money}
														<a style="text-decoration:none" class="ml-5 a-area-edit" href="javascript:;" 
														onclick="orderEditBill(${costs.id});" title="编辑">编辑</a>
													</td>
													<td width="20%"></td>
													<td width="20%"></td>
												</c:when>							
												<c:otherwise>
													<td width="30%">${costs.costName}</td><td width="30%">${costs.money}</td><td width="20%"></td><td width="20%"></td>
												</c:otherwise> 
											</c:choose>
										</tr>			
									</c:forEach>
									<tr><td colspan="4"></td></tr>
						</c:forEach>
					</tbody>					
					</table>
					<br/>
				</c:forEach>
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
		
			
			function orderEditBill(costId) {
				var params = {};
				params.type = 2;
				params.area = ['800px','500px'];
				params.fix = false;
				params.maxmin = true;
				params.shade = 0.4;
				params.title = "编辑账单";
				params.content = '/rest/um/order/orderEditBill/?costId=' + costId;
				params.end = function() {
					location.replace(location.href);
			    };
				layer.open(params);
			}	
				
			function placeOrder(date, orderId, money) {
				layer.confirm('是否提交', {btn: ['确认', '取消']}, function(index) {
					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/order/placeOrder/?orderId=" + orderId + "&date=" + date + "&money=" + money,
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