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
		<title>资源分类</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>资源管理 <span class="c-gray en">&gt;</span> 资源分类 
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">


			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l">
					<a id="resource-type-add" class="btn btn-primary radius a-facilities-add" data-title="添加品牌"  href="javascript:;">
						<i class="Hui-iconfont">&#xe600;</i> 添加分类
					</a>
				</span>
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>


			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table"> 
					<thead>
						<tr class="text-c">
							<th>分类名称</th>
							<th>状态</th>
							<th width="100">操作</th>
						</tr>
					</thead>
				</table>
				<div class="mt-20">
					<div id="laypage-div"></div>
				</div>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20" id="no-data-div">
				<span class="l">&nbsp&nbsp暂无分类数据，您可以添加分类后再继续查看</span>
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
			var queryRowUrl  = "/rest/um/resourceType/lscount";
			var queryDataUrl = "/rest/um/resourceType/type_list";
			var dataListKey = [
				{key:'name', type:'text'},	
			    {key:'status', type:'label', valLabels: [
   			    	{val:1, label:'label-success', text:'已启用'},
   			    	{val:0, label:'label-danger', text:'已禁用'}
   			    ]},
			];
			var pagesize = 12;
			var searchInput = [];
			var operatHtml = "";
				operatHtml += "<a title=\"编辑\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"resource-type-edit\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-edit\"></i>编辑</a>";
			
			var operatWindows = [];
			var editWindow = {};
			editWindow.domName = "resource-type-edit";
			editWindow.url   = "/rest/um/resourceType/toEdit";
			editWindow.title = "编辑资源分类";
			editWindow.width = 800;
			editWindow.height = 500;
			editWindow.type = "local";
			operatWindows[0] = editWindow;
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, "");
			
			/**
			 * 控制添加/编辑子页面 Layer 弹出层操作
			 */
			var addDomId = "resource-type-add";
			var addUrl   = "/rest/um/resourceType/toAdd";
			var addTitle = "添加资源分类";
			var addWidth = 600;
			var addHeight = 400;
			var addType = 'local';
			OperatWindows.bindAddWindow(addDomId, addUrl, addTitle, addWidth, addHeight, null, addType);
		</script>
	</body>
</html>