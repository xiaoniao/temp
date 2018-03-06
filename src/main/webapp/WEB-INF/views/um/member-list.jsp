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
		<title>会员管理</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>会员管理 <span class="c-gray en">&gt;</span> 会员列表 
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="text-c">
			<!--	<div class="text-c">
					注册时间:
					<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'registrationTimeMax\')||\'%y-%M-%d\'}'})" id="registrationTimeMin" name="registrationTimeMin" class="input-text Wdate" style="width:120px;">
					-
					<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'registrationTimeMin\')}',maxDate:'%y-%M-%d'})" id="registrationTimeMax" name="registrationTimeMax" class="input-text Wdate" style="width:120px;">
					　最后登录时间:
					<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'lastLoginTimeMax\')||\'%y-%M-%d\'}'})" id="lastLoginTimeMin" name="lastLoginTimeMin" class="input-text Wdate" style="width:120px;">
					-
					<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'lastLoginTimeMin\')}',maxDate:'%y-%M-%d'})" id="lastLoginTimeMax" name="lastLoginTimeMax" class="input-text Wdate" style="width:120px;">
				</div> -->
				<input type="text" id="userName" name="userName" placeholder="登录账号" value="" class="input-text" style="width:120px" >　
				<input type="text" id="name" name="name" placeholder="姓名" value="" class="input-text" style="width:120px" >　
				<span class="select-box inline" style="padding:0">
					<select name="status" id="status" class="select input-text help-inline">
						<option value="">-- 账号状态 --</option>
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
				</span>　
				<button id="search-btn" class="btn btn-success" type="submit">
					<i class="Hui-iconfont">&#xe665;</i> 搜索
				</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table">
					<thead>
						<tr class="text-c">
							<th>登录账号</th>
							<th>姓名</th>
							<th>性别</th>
							<th>余额</th>
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
				</table>
				<div class="mt-20">
					<div id="laypage-div"></div>
				</div>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20" id="no-data-div">
				<span class="l">&nbsp&nbsp暂无会员数据，您可以添加会员后再继续查看</span>
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
		var queryRowUrl  = "/rest/um/member/lscount";
		var queryDataUrl = "/rest/um/member/list";
		var dataListKey = [
		    {key:'userName', type:'text'},
		    {key:'name', type:'text'}, 
		    {key:'sex', type:'label', valLabels: [
  			    {val:0, label:'label-success', text:'男'},
				{val:1, label:'label-danger', text:'女'}
  			]},
			{key:'balance', type:'text'},
		    {key:'status', type:'label', valLabels: [
  			    {val:0, label:'label-danger', text:'无效'},
				{val:1, label:'label-success', text:'有效'}
  			]}
		];
		var pagesize = 12;
		var searchInput = ['registrationTimeMin','registrationTimeMax','lastLoginTimeMin','lastLoginTimeMax','status','userName','name'];
		var operatHtml = "";
				
			operatHtml += "<a title=\"查看详情\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"member-Details\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-gengduo\"></i>详情</a>" +"&nbsp&nbsp";
		
				
		var operatWindows = [];
		
		var queryWindow = {};
		queryWindow.domName = "member-Details";
		queryWindow.url   = "/rest/um/member/toDetail";
		queryWindow.title = "查看详情";
		queryWindow.width = 700;
		queryWindow.height = 500;
		queryWindow.type = "full";
				
		operatWindows[0] = queryWindow;
		
		ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, "");
		
		</script>
	</body>
</html>