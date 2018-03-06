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
		<title>全部账单</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>订单管理 <span class="c-gray en">&gt;</span> 全部账单
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			
			<div class="text-c" style="margin-top: 10px;">
				<!--账单状态-->
				<span class="select-box inline" style="padding:0">
					<select name="status" id="status" class="select input-text help-inline">
						<option value="">-- 请选择账单状态 --</option>
						<option value="0">初始化添加</option>
						<option value="1">后续添加</option>
						<option value="2">取消</option>
					</select>
				</span>
				<!--行程状态-->
				<span class="select-box inline" style="padding:0">
					<select name="travelStatus" id="travelStatus" class="select input-text help-inline">
						<option value="">-- 请选择行程状态 --</option>
						<option value="0">等待确认，未开始</option>
						<option value="1">已确认</option>
						<option value="2">服务中</option>
						<option value="3">账单待确认</option>
						<option value="4">服务完成</option>
						<option value="5">行程取消</option>
					</select>
				</span>
				<!--订单状态-->
				<span class="select-box inline" style="padding:0">
					<select name="orderStatus" id="orderStatus" class="select input-text help-inline">
						<option value="">-- 请选择订单状态 --</option>
						<option value="0">新建订单</option>
						<option value="1">用户未付款取消</option>
						<option value="2">待接单</option>
						<option value="3">付款后取消</option>
						<option value="4">预订失败</option>
						<option value="5">待服务</option>
						<option value="6">服务中</option>
						<option value="7">待评价</option>
						<option value="8">已完成</option>
						<option value="9">订单交换</option>
						<option value="10">被管理员审核取消</option>
						<option value="11">待确认行程</option>
						<option value="12">等待输入服务编码</option>
					</select>
				</span>
				<input type="text" name="username" id="username" placeholder="用户账号" style="width:120px" class="input-text">
				<button id="search-btn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</div>

			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l"></span> 
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table">
					<thead>
						<thead>
							<tr class="text-c">
								<th>账单编号</th>
								<th>行程</th>
								<th>消费类目</th>
								<th>金额</th>
								<th>备注</th>
								<th>账单状态</th>
								<th>行程状态</th>
								<th>订单状态</th>
								<th>订单</th>
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
		
			/**
			 * 列表数据、分页绑定
			 */
			var queryRowUrl  = "/rest/um/orderCost/lscount";
			var queryDataUrl = "/rest/um/orderCost/list";
			
			var dataListKey = [
			    {key:'id', type:'text'}, 
			    {key:'title', type:'text'},
			    {key:'costName', type:'text'},
			    {key:"money", type:'text'},
			    {key:'remark', type:'text'},
				{key:'statusName', type:'text'},
				{key:'travelStatusName', type:'text'},
				{key:'orderStatusName', type:'text'},
				{key:'orderId', type:'text'}
			];

			var pagesize = 12;
			var searchInput = ['status', 'travelStatus', 'orderStatus', 'username'];
			var operatHtml = "";
				operatHtml += "<a class=\"ml-10\" style=\"color:#0A6999\" title=\"\" href=\"javascript:;\" name=\"order-details\" dataKey=\"orderId\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-dingwei\"></i>订单</a>";
			var operatWindows = [];

			var orderDetailsWindow = {};
			orderDetailsWindow.domName = "order-details";
			orderDetailsWindow.title = "订单详情";
			orderDetailsWindow.type = "confirm";
			orderDetailsWindow.url   = "/rest/um/order/toOrderDetail";
			orderDetailsWindow.type = "full";

			operatWindows[0] = orderDetailsWindow;
			
			var bindFunc = "";
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, bindFunc);	
			
		</script>
	</body>
</html>