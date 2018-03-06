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
			<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>提现记录
			<a class="btn btn-success radius r" style="line-height: 1.6em; margin-top: 3px" href="javascript:location.replace(location.href);" title="刷新">
			<i class="Hui-iconfont">&#xe68f;</i></a>
		</nav>
		<div class="page-container">


			<div class="text-c" style="margin-top: 10px;">
				
				<input type="text" name="bankMobile" id="bankMobile" placeholder="会员账号" style="width:120px" class="input-text">

				<input type="text" onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'starDate\')||\'%y-%M-%d\'}'})" id="starDate" name="starDate" class="input-text Wdate" style="width:120px;">
				-
				<input type="text" onfocus="WdatePicker({minDate:'#F{$dp.$D(\'endDate\')}',maxDate:'%y-%M-%d'})" id="endDate" name="endDate" class="input-text Wdate" style="width:120px;">
				
				<span class="select-box inline" style="padding:0">
					<select name="status" id="status" class="select input-text help-inline">
						<option value="">-- 请选择提现状态 --</option>
						<option value="0">提现中</option>
						<option value="1">提现成功</option>
					</select>
				</span>

				<input type="text" name="tradeNo" id="tradeNo" placeholder="交易号" style="width:120px" class="input-text">

				<button id="search-btn" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
			</div>
			<button id="excel-btn" onclick="excel()" class="btn btn-success"><i class="Hui-iconfont">&#xe665;</i> 导出excel</button>
			<!-- 搜索 -->


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
								<th>会员帐号</th>
								<th>金额</th>
								<th>交易号</th>
								<th>提现状态</th>
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
		<script type="text/javascript" src="/lib/xls/xlsx.core.min.js"></script>
		<script type="text/javascript" src="/lib/xls/Blob.js"></script>
		<script type="text/javascript" src="/lib/xls/FileSaver.js"></script>
		
		<script type="text/javascript">
			/**
			* 列表数据、分页绑定
			*/
			var queryRowUrl  = "/rest/um/money/takeCash/listPageCount";
			var queryDataUrl = "/rest/um/money/takeCash/listPage";
			
			var dataListKey = [
				{key:'id', type:'text'},
				{key:'bankMobile', type:'text'},
				{key:'money', type:'text'},
				{key:'tradeNo', type:'text'},
				{key:'statusStr', type:'text'},
				{key:"createDate", type:'text'}
			];
			
			var pagesize = 12;
			var searchInput = ['bankMobile', 'starDate', 'endDate', 'status', 'tradeNo'];
			var operatHtml = "";
			var operatWindows = [];
			var orderDetailsWindow = {};
			var bindFunc = "";
			ListPage.init(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, bindFunc);

			function excel() {

				$.ajax({
					type : "post",
					dataType : "JSON",
					url : "/rest/um/money/takeCash/listAll",
					data : null,
					success : function(result) {
						if (result.statusCode == 0) {
							/* original data */
							var data = [];
							data.push(['编号','联系电话','金额','交易号','提现状态','创建时间']);
							for(var i = 0; i < result.data.length; i++) {
								var item = [];
								item.push(i + 1);
								item.push(result.data[i].bankMobile);
								item.push(result.data[i].money);
								item.push(result.data[i].tradeNo);
								item.push(result.data[i].statusStr);
								item.push(result.data[i].createDate);
								data.push(item);
							}

							console.log(data);
							var ws_name = "提现记录";

							var wb = new Workbook(), ws = sheet_from_array_of_arrays(data);
							
							/* add worksheet to workbook */
							wb.SheetNames.push(ws_name);
							wb.Sheets[ws_name] = ws;
							var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:true, type: 'binary'});
							saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), ws_name + ".xlsx")
						}
					}
				})
			}

			function sheet_from_array_of_arrays(data, opts) {
				var ws = {};
				var range = {s: {c:10000000, r:10000000}, e: {c:0, r:0 }};
				for(var R = 0; R != data.length; ++R) {
					for(var C = 0; C != data[R].length; ++C) {
						if(range.s.r > R) range.s.r = R;
						if(range.s.c > C) range.s.c = C;
						if(range.e.r < R) range.e.r = R;
						if(range.e.c < C) range.e.c = C;
						var cell = {v: data[R][C] };
						if(cell.v == null) continue;
						var cell_ref = XLSX.utils.encode_cell({c:C,r:R});
						
						if(typeof cell.v === 'number') cell.t = 'n';
						else if(typeof cell.v === 'boolean') cell.t = 'b';
						else if(cell.v instanceof Date) {
							cell.t = 'n'; cell.z = XLSX.SSF._table[14];
							cell.v = datenum(cell.v);
						}
						else cell.t = 's';
						
						ws[cell_ref] = cell;
					}
				}
				if(range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
				return ws;
			}
			
			function Workbook() {
				if(!(this instanceof Workbook)) return new Workbook();
				this.SheetNames = [];
				this.Sheets = {};
			}

			function s2ab(s) {
				var buf = new ArrayBuffer(s.length);
				var view = new Uint8Array(buf);
				for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
				return buf;
			}

			function datenum(v, date1904) {
				if(date1904) v+=1462;
				var epoch = Date.parse(v);
				return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
			}
		</script>
	</body>
</html>