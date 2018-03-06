<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<link rel="stylesheet" type="text/css" href="/static/h-ui/css/jquery.treetable.css" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui/css/jquery.treetable.theme.default.css" />
		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<title>开通城市</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>系统设置 <span class="c-gray en">&gt;</span> 开通城市 
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table">
					<thead>
						<tr class="text-c">
							<th>名称</th>
							<th>状态</th>
							<th width="220">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${provinceList != null && fn:length(provinceList) > 0}">
							<c:forEach var="province" items="${provinceList}" varStatus="s1">
								<tr class="text-c" data-tt-id="${s1.index }">
									<td style="text-align: left;">${province.name }</td>
									<td>-</td>
									<td>-</td>
								</tr>									
								<c:if test="${province.cityList != null && fn:length(province.cityList) > 0}">
									<c:forEach var="city" items="${province.cityList}" varStatus="s2">
										<tr class="text-c" data-tt-id="${(s1.index + 1) * 10000 + s2.index}" data-tt-parent-id="${s1.index }">
											<td style="text-align: left;">${city.name }</td>
											<td>
												<c:if test="${city.status == 1 }">已开通</c:if>
												<c:if test="${city.status == 0 }">未开通</c:if>
											</td>
											<td>
												<c:if test="${city.status == 0 }"><a style="text-decoration:none;color:green;" " class="ml-5 a-area-edit" href="javascript:open(${city.id});" title="开启"><i class="Hui-iconfont Hui-iconfont-shenhe-tongguo"></i>开启</a></c:if>
												<c:if test="${city.status == 1 }"><a style="text-decoration:none;color:red;" class="ml-5 a-area-edit" href="javascript:close(${city.id});" title="关闭"><i class="Hui-iconfont Hui-iconfont-shenhe-butongguo2"></i>关闭</a></c:if>
											</td>
										</tr>
									</c:forEach>
								</c:if>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script>
		<script type="text/javascript" src="/lib/My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript" src="/lib/laypage/1.2/laypage.js"></script>
		<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
		<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
		<script type="text/javascript" src="/static/h-ui/js/jquery.treetable.js"></script> 
		<script type="text/javascript" src="/javascript/window-utils.js"></script>
		<script type="text/javascript" src="/javascript/plist-utils.js"></script>
		
		<script type="text/javascript">
		
			/**
			 * 加载treetable
			 */
			$("#data-list-table").treetable({ expandable: true });
					
		
		    function open(cityId) {
				layer.confirm('确定开启吗？', function(index) {
						$.ajax({  
						type : "GET",  
						url : "/rest/um/city/open/?cityId=" + cityId,
						async : false,  
						dataType: 'JSON',
						success : function(data) {  
							if (data.statusCode == 0) {
								layer.confirm('操作成功', {
									btn: ['确认'] //按钮
								}, function() {
									location.replace(location.href);
								});
							} else {
								layer.alert('操作失败', {icon: 6});
							}
						}
					});
				});
			}

			function close(cityId) {
				layer.confirm('确定关闭吗？', function(index) {
					$.ajax({  
						type : "GET",  
						url : "/rest/um/city/close/?cityId=" + cityId,
						async : false,  
						dataType: 'JSON',
						success : function(data) {  
							if (data.statusCode == 0) {
								layer.confirm('操作成功', {
									btn: ['确认'] //按钮
								}, function() {
									location.replace(location.href);
								});
							} else if (data.statusCode == 40016) {
								layer.alert('该城市仍有服务金鹰或服务订单正在进行中，请确认后关闭;该城市仍有服务金鹰或服务订单正在进行中，请确认后关闭', {icon: 6});
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