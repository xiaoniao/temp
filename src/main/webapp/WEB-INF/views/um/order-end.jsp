<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<title>提前结束订单</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>订单管理 <span class="c-gray en">&gt;</span> 提前结束订单
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l"></span> 
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table">
					<thead>
						<thead>
							<tr class="text-c">
								<th>订单编号</th>
								<th>订单时间</th>
								<th>用户信息</th>
								<th>服务日期</th>
								<th>金鹰信息</th>
								<th>订单状态</th>
								<th>操作</th>
							</tr>
						</thead>
					</thead>
				</table>
				<div class="mt-20">
					<div id="laypage-div"></div>
				</div>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20" id="no-data-div">
				<span class="l">&nbsp&nbsp暂无订单数据</span>
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
		
		<script type="text/javascript">
			
				
			
			function operatBtnContro() {
				// $("#data-list-table").find("tr").each(function(index) {
				// 	if (index != 0) {
				// 		var status = $(this).find("input[name='status']").eq(0).val();
						
						 
				// 		if (status == 6 || status == 7 || status == 8) {
				// 			$(this).find("a[name='order-details']").show();
				// 			$(this).find("a[name='order-stroke']").show();
				// 		} else{
				// 			$(this).find("a[name='order-details']").show();
				// 			$(this).find("a[name='order-stroke']").hide();
				// 		}	
				// 	}
				// });
			} 
			
			/**
			 * 列表数据、分页绑定
			 */
			var queryRowUrl  = "/rest/um/order/orderEndCount";
			var queryDataUrl = "/rest/um/order/orderEndList";
			
			var dataListKey = [
			    {key:'appendDataA', type:'text'}, 
			    {key:'appendDataB', type:'text'},
			    {key:'appendDataC', type:'text'},
			    {key:"appendDataD", type:'text'},
			    {key:"appendDataE", type:'text'},
			    {key:'appendDataF', type:'text'},
				{key:'status', type:'hidden'}
			];
					
			var pagesize = 12;
			var searchInput = [''];
			var operatHtml = "";
			
				operatHtml += "<a class=\"ml-10\" style=\"color:#0A6999\" title=\"订单详情\" href=\"javascript:;\" name=\"order-details\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-gengduo\"></i>详情</a>";	
				operatHtml += "<a class=\"ml-10\" style=\"color:#0A6999\" title=\"订单行程\" href=\"javascript:;\" name=\"order-stroke\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-renwu\"></i>行程</a>";
			
			var operatWindows = [];
			
		
			
			var orderDetailsWindow = {};
			orderDetailsWindow.domName = "order-details";
			orderDetailsWindow.title = "订单详情";
			orderDetailsWindow.type = "confirm";
			orderDetailsWindow.url   = "/rest/um/order/toOrderDetail";
			orderDetailsWindow.type = "full";
			
			var orderStrokeWindow = {};
			orderStrokeWindow.domName = "order-stroke";
			orderStrokeWindow.title = "订单行程";
			orderStrokeWindow.type = "confirm";
			orderStrokeWindow.url   = "/rest/um/order/toTravelDetails";
			orderStrokeWindow.type = "full";
			
			operatWindows[0] = orderDetailsWindow;
			operatWindows[1] = orderStrokeWindow;
				
			
			var bindFunc = "operatBtnContro();";
			
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, bindFunc);	
			
		</script>
	</body>
</html>