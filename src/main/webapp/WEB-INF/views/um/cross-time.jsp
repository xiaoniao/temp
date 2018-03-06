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
	
	<link rel="stylesheet" type="text/css" href="/static/h-ui/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/H-ui.admin.css" />
	<link rel="stylesheet" type="text/css" href="/lib/Hui-iconfont/1.0.7/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="/lib/icheck/icheck.css" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/skin/default/skin.css" id="skin" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/style.css" />
	
	<title>金鹰管理</title>
</head>
<body>
	<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 金鹰管理<span class="c-gray en">&gt;</span> 金鹰列表<span class="c-gray en">&gt;</span> 排班表 <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="page-container">
		<!-- <div class="text-c">
			<span class="select-box inline">
				<select name="" class="select" disabled ="true">
					<option value="0">--选择酒店--</option>
					<option value="1">酒店A</option>
					<option value="2">酒店B</option>
					<option value="3">酒店C</option>
					<option value="4">酒店E</option>
					<option value="5">酒店F</option>
				</select>
			</span>
			<span class="select-box inline">
				<select name="" class="select">
					<option value="0">--选择房型--</option>
					<option value="1">大床房A</option>
					<option value="2">大床房B</option>
					<option value="3">大床房C</option>
				</select>
			</span>
			<button name="" id="" class="btn btn-success" type="submit"><i class="Hui-iconfont">&#xe665;</i> 搜索</button>
		</div> -->
		<div class="cl pd-5 bg-1 bk-gray mt-20">
		*红色日期表示已请假（不可预约）
		</div>
		<div class="mt-20">
			<table class="table table-border table-bordered table-bg table-hover table-sort">
				<thead>
					<tr class="text-c">
						<th>周日</th>
						<th>周一</th>
						<th>周二</th>
						<th>周三</th>
						<th>周四</th>
						<th>周五</th>
						<th>周六</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="crossTimes" items="${crossTimesLists}" varStatus="status">
						<c:choose>
						   <c:when test="${crossTimes.specialCharacter == 'ww'}">  
								<tr class="text-c">
							    </tr>    
						   </c:when>
						   <c:otherwise> 
							    <c:choose>
									<c:when test="${crossTimes.specialCharacter == 'kk'}">  
										<td>		
										</td>										
						            </c:when>
						            <c:otherwise> 
										<td dataid="${crossTimes.id }" <c:if test="${crossTimes.status == 0}"> tatle="解除请假" class="b-close"</c:if> 
											<c:if test="${crossTimes.status == 1}"> style="background-color: #d9534f;"</c:if> 
												<c:if test="${crossTimes.status == 2}"> tatle="请假" style="background-color: #FFD0D3;" class="b-open"</c:if> 
										">
											${crossTimes.timeDate}
										</td>
						            </c:otherwise>
                                </c:choose>
						   </c:otherwise>
                        </c:choose>				
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script> 
	<script type="text/javascript" src="/lib/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript" src="/lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
	<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script> 
	<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript">
		$(function() {
				//确定请假吗
				$("td.b-close").click(function() {
					var crossTimesId = $(this).attr("dataid");			
					layer.confirm('确定请假吗？', function(index) {
						$.ajax({  
							type : "GET",  
							url : "/rest/um/crossInfo/close/?crossTimesId=" + crossTimesId,
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
				})
				
				//确定解除请假
				$("td.b-open").click(function() {
					var crossTimesId = $(this).attr("dataid");
					layer.confirm('确定解除请假吗？',function(index) {
						$.ajax({  
							type : "GET",  
							url : "/rest/um/crossInfo/open/?crossTimesId=" + crossTimesId,
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
				})
		})
	</script>
</body>
</html>