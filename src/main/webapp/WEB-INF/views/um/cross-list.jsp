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
		<title>金鹰管理</title>
	</head>
	<body>
		<nav class="breadcrumb">
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>金鹰管理 <span class="c-gray en">&gt;</span> 金鹰列表 
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">
			<div class="text-c">	
				<span class="select-box inline">							
					<select name="provinceId"  id="provinceId" class="select">
						<option value="">-- 省 --</option>
						<c:forEach items="${provinceList}" var="province">
							<option value="${province.id}">${province.name}</option> 
						</c:forEach>
					</select>
				</span>	
				<span class="select-box inline">
					<select name="cityId"  id="cityId" class="select">
						<option value="">-- 市 --</option>
					</select>
				</span>	
				<input type="text" id="name" name="name" placeholder="姓名" value="" class="input-text" style="width:120px" >
				<input type="text" id="workName" name="workName" placeholder="工作名" value="" class="input-text" style="width:120px" >
				<input type="text" id="crossNumber" name="crossNumber" placeholder="工号" value="" class="input-text" style="width:120px" >
				<button id="search-btn" class="btn btn-success" type="submit">
					<i class="Hui-iconfont">&#xe665;</i> 搜索
				</button>
			</div>
			<div class="cl pd-5 bg-1 bk-gray mt-20">
				<span class="l">
						<a id="cross-add" class="btn btn-primary radius a-facilities-add" data-title="添加金鹰"  href="javascript:;">
							<i class="Hui-iconfont">&#xe600;</i> 添加金鹰
						</a>
				</span>
				<span class="r">共有数据：<strong id="row-num-strong">0</strong> 条</span>
			</div>
			<div class="mt-20" id="has-data-div">
				<table class="table table-border table-bordered table-hover table-bg" id="data-list-table">
					<thead>
						<tr class="text-c">
							<th>工号</th>
							<th>金鹰照片</th>
							<th>服务城市</th>
							<th>姓名</th>
							<th>工作名</th>
							<th>联系手机号码</th>
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
				<span class="l">&nbsp&nbsp暂无金鹰数据，您可以添加金鹰后再继续查看</span>
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
		$(function () {	
			
			$('#provinceId').change(function() {
				if($(this).val() > 0) {
					var cityName = $("#provinceId option:selected").text();
							
					$("#cityId option:gt(0)").remove();

					var params = {};
					params.provinceId = $(this).val();

					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/crossInfo/cityList",
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

		});
		
		/**
		 * 列表数据、分页绑定
		 */
		var queryRowUrl  = "/rest/um/crossInfo/lscount";
		var queryDataUrl = "/rest/um/crossInfo/list";
		var dataListKey = [
		    {key:'crossNumber', type:'text'},
		    {key:'banners', type:'image', width: 80}, 
		    {key:'cityName', type:'text'},
		    {key:'name', type:'text'},
		    {key:'workName', type:'text'}, 
		    {key:'contactPhone', type:'text'}, 
		    {key:'status', type:'label', valLabels: [
  			    {val:1, label:'label-success', text:'在职'},
				{val:0, label:'label-danger', text:'离职'}
  			]},
		];
		var pagesize = 12;
		var searchInput = ['provinceId','cityId','name','workName','crossNumber'];
		var operatHtml = "";
		
			
			operatHtml += "<a title=\"编辑\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"cross-edit\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-edit\"></i>编辑</a>" + "&nbsp&nbsp";
			
			
			
			operatHtml += "<a title=\"查看\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"cross-Details\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-gengduo\"></i>查看</a>" +"&nbsp&nbsp";
		
			
			operatHtml += "<a title=\"排班表\" style=\"color:#0A6999\" href=\"javascript:;\" name=\"cross-time\" dataKey=\"id\" dataId=\"operatId\"><i class=\"Hui-iconfont Hui-iconfont-log\"></i>排班表</a>";
				
		var operatWindows = [];
		
		var editWindow = {};
		editWindow.domName = "cross-edit";
		editWindow.url   = "/rest/um/crossInfo/toEdit";
		editWindow.title = "金鹰编辑";
		editWindow.width = 800;
		editWindow.height = 600;
		editWindow.type = "full";
		
		var queryWindow = {};
		queryWindow.domName = "cross-Details";
		queryWindow.url   = "/rest/um/crossInfo/toDetail";
		queryWindow.title = "金鹰详情";
		queryWindow.width = 800;
		queryWindow.height = 600;
		queryWindow.type = "full";
		
		var interestsWindow = {};
		interestsWindow.domName = "cross-time";
		interestsWindow.url   = "/rest/um/crossInfo/toCrossTime";
		interestsWindow.title = "排班表";
		interestsWindow.width = 800;
		interestsWindow.height = 600;
		interestsWindow.type = "full";
				
		operatWindows[0] = editWindow;
		operatWindows[1] = queryWindow;
		operatWindows[2] = interestsWindow;

		
		ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, "");
		
		/**
		 * 控制添加/编辑子页面 Layer 弹出层操作
		 */
		var addDomId = "cross-add";
		var addUrl   = "/rest/um/crossInfo/toAdd";
		var addTitle = "添加金鹰";
		var addWidth = 800;
		var addHeight = 500;
		var addType = 'full';
		
		OperatWindows.bindAddWindow(addDomId, addUrl, addTitle, addWidth, addHeight, null, addType);
		</script>
	</body>
</html>