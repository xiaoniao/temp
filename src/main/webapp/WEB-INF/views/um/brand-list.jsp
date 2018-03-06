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
		<title>合作品牌</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>资源管理 <span class="c-gray en">&gt;</span> 合作品牌 
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="text-c" style="margin-top: 10px;">
				<span class="select-box inline" style="padding:0">
					<select name="provinceId" id="provinceId" class="select input-text help-inline">
						<option value="">-- 请选择所属省 --</option>
						<c:forEach items="${provinceList}" var="province" varStatus="s">
							<option value="${province.id}">${province.name}</option>
						</c:forEach>
					</select>
				</span>
				<span class="select-box inline" style="padding:0">
					<select name="cityId" id="cityId" class="select input-text help-inline">
						<option value="">-- 请选择所属市--</option>
					</select>
				</span>
				<input type="text" name="name" id="name" placeholder="品牌名称" style="width:200px" class="input-text">			
				<button id="search-btn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l">
					<a id="brand-add" class="btn btn-primary radius a-facilities-add" data-title="添加品牌"  href="javascript:;">
						<i class="Hui-iconfont">&#xe600;</i> 添加品牌
					</a>
				</span>
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table"> 
					<thead>
						<tr class="text-c">
							<th>品牌图片</th>
							<th>品牌名称</th>
							<th>所属城市</th>
							<th>链接</th>
							<th>状态</th>
							<th>排序</th>
							<th width="100">操作</th>
						</tr>
					</thead>
				</table>
				<div class="mt-20">
					<div id="laypage-div"></div>
				</div>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20" id="no-data-div">
				<span class="l">&nbsp&nbsp暂无品牌数据，您可以添加品牌后再继续查看</span>
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
			
			$('#provinceId').change(function() {
				if($(this).val() > 0) {
					var cityName = $("#provinceId option:selected").text();
								
					$("#cityId option:gt(0)").remove();

					var params = {};
					params.provinceId = $(this).val();

					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/brand/cityList",
						data : params,
						success : function(result) {
							for (var i = 0; i < result.data.length; i++) {
								$("<option value='" + result.data[i].id + "'>" + result.data[i].name + "</option>").appendTo("#cityId");
							}		
						}
					})
				}
				$("#cityId option:gt(0)").remove();
			});
		
			//跳转详情页面
			function queryDetail(orderId) {
				var params = {};
				params.type = 2;
				params.area = ['800px','500px'];
				params.fix = false;
				params.maxmin = true;
				params.shade = 0.4;
				params.title = "添加商圈";
				params.content = "/rest/um/brand/toPic/?id=" + orderId;
				params.end = function() {
					location.replace(location.href);
			    };
				layer.open(params);
			}
			
			/**
			 * 列表数据、分页绑定
			 */
			var queryRowUrl  = "/rest/um/brand/lscount";
			var queryDataUrl = "/rest/um/brand/list";
			var dataListKey = [
				{key:'img', type:'imageLinkUrl', linkUrl:"javascript:queryDetail(operatId);", height: 58}, 
				{key:'name', type:'text'},
				{key:'cityName', type:'text'},
				{key:'url', type:'text'},
			    {key:'status', type:'label', valLabels: [
   			    	{val:1, label:'label-success', text:'已启用'},
   			    	{val:0, label:'label-danger', text:'已禁用'}
   			    ]},
				{key:'sort', type:'text'}
			];
			var pagesize = 12;
			var searchInput = ['name','provinceId','cityId'];
			var operatHtml = "";
			
				operatHtml += "<a title=\"编辑\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"brand-edit\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-edit\"></i>编辑</a>";
			
			
			var operatWindows = [];
			
			var editWindow = {};
			editWindow.domName = "brand-edit";
			editWindow.url   = "/rest/um/brand/toEdit";
			editWindow.title = "编辑消费类目";
			editWindow.width = 800;
			editWindow.height = 500;
			editWindow.type = "local";
			
			operatWindows[0] = editWindow;
			
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, "");
			
			/**
			 * 控制添加/编辑子页面 Layer 弹出层操作
			 */
			var addDomId = "brand-add";
			var addUrl   = "/rest/um/brand/toAdd";
			var addTitle = "添加消费类目";
			var addWidth = 800;
			var addHeight = 500;
			var addType = 'local';
			
			OperatWindows.bindAddWindow(addDomId, addUrl, addTitle, addWidth, addHeight, null, addType);
		</script>
	</body>
</html>