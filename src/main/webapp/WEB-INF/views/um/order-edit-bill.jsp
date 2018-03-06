<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!--_meta 作为公共模版分离出去-->
<!DOCTYPE HTML>
<html>
<head>
	<meta charset="utf-8">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<LINK rel="Bookmark" href="/favicon.ico" >
	<LINK rel="Shortcut Icon" href="/favicon.ico" />
	
	<link rel="stylesheet" type="text/css" href="/static/h-ui/css/H-ui.min.css" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/H-ui.admin.css" />
	<link rel="stylesheet" type="text/css" href="/lib/Hui-iconfont/1.0.7/iconfont.css" />
	<link rel="stylesheet" type="text/css" href="/lib/icheck/icheck.css" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/skin/default/skin.css" id="skin" />
	<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/style.css" />
	
	<title>编辑账单</title>
</head>
<body>
	<article class="page-container">
		<form action="/rest/um/order/updateBill" method="post" enctype="multipart/form-data" class="form form-horizontal" id="form-article-add">
			<input type="hidden" name="id" value="${costId}"/>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">账单类目:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<select name="costItemId" id="costItemId" class="select input-text help-inline">
						<c:forEach items="${costItemList}" var="costItem" >
							<option value="${costItem.id}" <c:if test="${costItem.id == orderCost.costItemId}">selected='selected'</c:if>>
								${costItem.costName}
							</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">账单金额:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="money" name="money" type="text" value="${orderCost.money}" class="input-text" style="width: 300px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">账单备注:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<input  id="remark" name="remark" type="text" value="${orderCost.remark}" class="input-text" style="width: 300px;">
				</div>
			</div>
			<div class="row cl">
				<label class="form-label col-xs-4 col-sm-2">账单状态:</label>
				<div class="formControls col-xs-8 col-sm-9">
					<select name="status" id="status" class="select input-text help-inline">
						<option value="0" <c:if test='${orderCost.status == 0}'>selected='selected' </c:if>>正常</option>
						<option value="2" <c:if test='${orderCost.status == 2}'>selected='selected' </c:if>>关闭</option>
					</select>
				</div>
			</div>
			<div class="row cl">
				<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<button class="btn btn-secondary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 确认</button>
					<button class="btn btn-default radius" onClick="layer_close();" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
				</div>
			</div>
		</form>
	</article>

	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script> 
	<script type="text/javascript" src="/lib/icheck/jquery.icheck.min.js"></script> 
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script> 
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/validate-methods.js"></script> 
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.min.js"></script> 
	<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script> 
	

	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>
	
	<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
	<script type="text/javascript">
	
		var formId = "form-article-add";
		
		var formRules = {
		}
		
		var message = {
		}
		
		var func1 = function() { }
		
		var func2 = function(data) {
			if (data.statusCode == 0) {
				layer.confirm('操作成功', {
					btn: ['确认'] //按钮
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
				
			} else if (data.statusCode == 40001) {
				layer.alert('服务器参数验证出错，请确认填写内容重新提交', {icon: 6});
			} else if (data.statusCode == 40002) {
				layer.alert('服务器运行异常', {icon: 6});
			} else {
				layer.alert('未知错误', {icon: 6});
			}
		}
		
		FormObj.init(formId, formRules, message, func1, func2);
	</script>
</body>
</html>