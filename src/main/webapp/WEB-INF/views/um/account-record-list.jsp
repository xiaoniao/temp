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
		<title>帐户明细</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>帐户明细
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">


			<!-- 搜索 -->
			<div class="text-c" style="margin-top: 10px;">
				<input type="text" name="orderCard" id="orderCard" placeholder="订单编号" style="width:200px" class="input-text">
				<input type="text" name="bankMobile" id="bankMobile" placeholder="会员帐号" style="width:120px" class="input-text">
				<span class="select-box inline" style="padding:0">
					<select name="type" id="type" class="select input-text help-inline">
						<option value="">-- 请选择类型 --</option>
						<option value="0">充值</option>
						<option value="1">服务费</option>
						<option value="2">延时费</option>
						<option value="3">退款</option>
						<option value="4">账单扣款</option>
						<option value="5">提现</option>
					</select>
				</span>			
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
								<th>编号</th>
								<th>订单编号</th>
								<th>登录账号</th>
								<th>金额</th>
								<th>类型</th>
								<th>创建时间</th>
							</tr>
						</thead>
					</thead>
				</table>
				<div class="mt-20">
					<div id="laypage-div"></div>
				</div>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20" id="no-data-div">
				<span class="l">&nbsp&nbsp暂无数据</span>
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
			var queryRowUrl  = "/rest/um/accountRecord/listPageCount?pageType=month";
			var queryDataUrl = "/rest/um/accountRecord/listPage?pageType=month";
			
			var dataListKey = [
				{key:'id', type:'text'},
				{key:'orderCard', type:'text'},
				{key:'bankMobile', type:'text'},
				{key:'money', type:'text'},
				{key:'typeStr', type:'text'},
				{key:"operateTimeStr", type:'text'}
			];
			
			var pagesize = 12;
			var searchInput = ['orderCard', 'bankMobile', 'type'];
			var operatHtml = "";
			var operatWindows = [];
			var orderDetailsWindow = {};
			var bindFunc = "";
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, bindFunc);
		</script>
	</body>
</html>