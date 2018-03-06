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
		<title>资源审核</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>资源管理 <span class="c-gray en">&gt;</span> 资源审核
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="text-c" style="margin-top: 10px;">
				<c:if test="${hidenCity != 1}">
				<span class="select-box inline" style="padding:0">
					<select name="provinceId" id="provinceId" class="select input-text help-inline">
						<option value="">-- 省 --</option>
						<c:forEach items="${provinceList}" var="province" varStatus="s">
							<option value="${province.id}">${province.name}</option>
						</c:forEach>
					</select>
				</span>
				<span class="select-box inline" style="padding:0">
					<select name="cityId" id="cityId" class="select input-text help-inline">
						<option value="">-- 市 --</option>
					</select>
				</span>
				</c:if>
				<span class="select-box inline" style="padding:0">
					<select name="typeId" id="typeId" class="select input-text help-inline">
						<option value="">--所属分类 --</option>
						<c:forEach items="${resourceTypes}" var="resourceType" varStatus="s">
							<option value="${resourceType.id}">${resourceType.name}</option>
						</c:forEach>
					</select>
				</span>
				<input type="text" name="title" id="title" placeholder="关键字" style="width:200px" class="input-text">			
				
				<button id="search-btn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table"> 
					<thead>
						<tr class="text-c">
							<th>所属城市</th>
							<th>所属分类</th>
							<th>资源标题</th>
							<th>提交时间</th>
							<th>提交人</th>
							<th>资源状态</th>
							<th width="100">操作</th>
						</tr>
					</thead>
				</table>
				<div class="mt-20">
					<div id="laypage-div"></div>
				</div>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20" id="no-data-div">
				<span class="l">&nbsp&nbsp暂无资源数据，您可以添加资源后再继续查看</span>
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

			/**
			 * 列表数据、分页绑定
			 */
			var queryRowUrl  = "/rest/um/resourceSuggest/lscount";
			var queryDataUrl = "/rest/um/resourceSuggest/list";
			var dataListKey = [
				{key:'provinceAndcityName', type:'text'}, 
				{key:'typeName', type:'text'},
				{key:'title', type:'text'},
				{key:'createDate', type:'text'},
				{key:'submitUserName', type:'text'},
			    {key:'checkStatus', type:'label', valLabels: [
   			    	{val:0, label:'label-success', text:'等待审核'},
   			    	{val:1, label:'label-danger', text:'未通过'},
					{val:2, label:'label-success', text:'通过'}
   			    ]}
			];
			var pagesize = 12;
			var searchInput = ['provinceId', 'cityId', 'typeId', 'title'];
			var operatHtml = "";
				operatHtml += "<a title=\"编辑\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"resource-edit\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-edit\"></i>详情</a>";

			var operatWindows = [];
			
			var editWindow = {};
			editWindow.domName = "resource-edit";
			editWindow.url   = "/rest/um/resourceSuggest/toEdit";
			editWindow.title = "编辑资源";
			editWindow.width = 800;
			editWindow.height = 500;
			editWindow.type = "full";
			
			operatWindows[0] = editWindow;
			
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, "");
		</script>
	</body>
</html>