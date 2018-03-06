<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html lang="en-US">

<head>
    <meta charset="UTF-8">
</head>
	<body>
		<div class="page-container">
			<form class="form form-horizontal" id="add-hotel-form" action="/rest/um/hotel/add" method="post" enctype="multipart/form-data">
				<textarea id="text-editor" rows="" cols="" style="width:600px;height:500px;">${string}</textarea>
				<input type="hidden" name="desc" value="">
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
					<!--	<a class="ml-10 btn btn-primary" href="javascript:save();" title="保存">保存</a> -->
						<input type="button" onclick="save()" value="保存" style="width:150px;height:35px"/>
					</div>
				</div>
			</form>
		</div>
	</body>
	<script type="text/javascript" src="/lib/kindeditor-4.1.7/lang/zh_CN.js"></script> 
	<script type="text/javascript" src="/lib/kindeditor-4.1.7/kindeditor-min.js"></script> 
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script> 
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script> 


    <script type="text/javascript">
		
		
		KindEditor.ready(function(K) {
			window.editor = K.create('#text-editor');
		});
		
		
		function save() {
			var descHtml = editor.html(); 
			var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
			descHtml = descHtml.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
			
			var parameter = {}
			parameter.descHtml = descHtml;
				layer.confirm('确定保存吗？', function(index) {
					$.ajax({  
						type : "POST",  
						url : "/rest/um/protocolAndDescription/descriptionSave",
						async : false,
						data:parameter,					
						dataType: 'JSON',
						success : function(data) {  
							if (data.statusCode == 0) {
								layer.confirm('操作成功', {
									btn: ['确认'] //按钮
								}, function() {
									parent.layer.close(index);
									location.replace(location.href);
								});
							} else {
								layer.alert('操作失败', {icon: 6});
							}
						}
					});
				});	
		}		
    </script>
</body>

</html>