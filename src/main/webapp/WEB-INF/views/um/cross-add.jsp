<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" import="javax.servlet.jsp.jstl.*" %>
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
		<LINK rel="Bookmark" href="/favicon.ico">
		<LINK rel="Shortcut Icon" href="/favicon.ico" />
		
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
		<link rel="stylesheet" type="text/css" href="/lib/webuploader/0.1.5/webuploader.css" />
		<link type="text/css" rel="stylesheet" href="/lib/jquery-ui/jquery-ui.min.css"/>
		<link type="text/css" rel="stylesheet" href="/lib/jquery.ui.plupload/css/jquery.ui.plupload.css"/>		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		<!--/meta 作为公共模版分离出去-->

		<title>金鹰添加</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
	</head>
	<body>
		<div class="page-container">
			
			<form class="form form-horizontal" id="add-cross-form" action="/rest/um/crossInfo/add" method="post" enctype="multipart/form-data">
				<div id="tab-system" class="HuiTab">
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>展示图（750*600）:</label>
						<div class="formControls col-xs-8 col-sm-9">
							<div class="row cl">
								<div style="width:750px; margin-left: 13px;">  
									<div id="uploader">  
										<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>  
									</div>  
								</div> 
							</div>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>工作照（150*150）:</label>
						<div class="formControls col-xs-8 col-sm-9">
							<span class="btn-upload form-group">
								<input class="input-text upload-url radius" type="text" readonly  placeholder="请选择工作照" name="filePath">
								<a href="javascript:void();" class="btn btn-primary radius upload-btn"><i class="Hui-iconfont">&#xe642;</i> 浏览文件</a>
								<input id="imageFile" type="file" multiple name="imageFile" class="input-file" accept=".jpg">
							</span>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>服务城市：</label>
						<input type="hidden" name="banners" id="banners"/>
						<div class="formControls col-xs-8 col-sm-9">						
							<select name="provinceId" class="select input-text" id="provinceId" style="width: 136px;">
								<option value="0">-- 省 --</option>
								<c:forEach items="${provinceList}" var="province">
									<option value="${province.id}" >${province.name}</option> 
								</c:forEach>
							</select>
							<select name="cityId" class="select input-text" id="cityId" style="width: 160px;">
								<option value="0">-- 市 --</option>
							</select>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>性别：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input name="sex" type="radio" value="1" class="screening-radio" checked="checked">男
							<input name="sex" type="radio" value="2" class="screening-radio" >女
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>姓名：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input  id="name" name="name" type="text" placeholder="控制在25个字、50个字节以内" value="" class="input-text" style="width: 300px;">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>登录手机号：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input  id="mobile" name="mobile" type="text" value="" class="input-text" style="width: 300px;">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>联系手机号：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input  id="contactPhone" name="contactPhone" type="text" value="" class="input-text" style="width: 300px;">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>工作名：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input  id="workName" name="workName" type="text" value="" class="input-text" style="width: 300px;">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>身高：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input  id="height" name="height" type="text"  value="" class="input-text" style="width: 300px;">cm
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>毕业院校：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<input  id="school" name="school" type="text"  value="" class="input-text" style="width: 300px;">
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>学历：</label>
						<div class="formControls col-xs-8 col-sm-9">
							<select name="education" class="select input-text" id="education" style="width: 160px;">
								<option value="大专">大专</option>
								<option value="本科">本科</option>
								<option value="硕士">硕士</option>
								<option value="博士">博士</option>
							</select>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>籍贯：</label>
						<div class="formControls col-xs-8 col-sm-9">						
							<select name="nativePlaceProvinceId" class="select input-text" id="nativePlaceProvinceId" style="width: 136px;">
								<option value="0">-- 省 --</option>
								<c:forEach items="${provinceList}" var="province">
									<option value="${province.id}" >${province.name}</option> 
								</c:forEach>
							</select>
							<select name="nativePlaceCityId" class="select input-text" id="nativePlaceCityId" style="width: 160px;">
								<option value="0">-- 市 --</option>
							</select>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>星座：</label>
						<div class="formControls col-xs-8 col-sm-9">						
							<select name="constellation" class="select input-text" id="constellation" style="width: 160px;">
								<option value="白羊座">白羊座</option>
								<option value="金牛座">金牛座</option>
								<option value="双子座">双子座</option>
								<option value="巨蟹座">巨蟹座</option>
								<option value="狮子座">狮子座</option>
								<option value="处女座">处女座</option>
								<option value="天秤座">天秤座</option>
								<option value="天蝎座">天蝎座</option>
								<option value="射手座">射手座</option>
								<option value="摩羯座">摩羯座</option>
								<option value="水瓶座">水瓶座</option>
								<option value="双鱼座">双鱼座</option>
							</select>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>属相：</label>
						<div class="formControls col-xs-8 col-sm-9">						
							<select name="yearBirth" class="select input-text" id="yearBirth" style="width: 160px;">
								<option value="鼠">鼠</option>
								<option value="牛">牛</option>
								<option value="虎">虎</option>
								<option value="兔">兔</option>
								<option value="龙">龙</option>
								<option value="蛇">蛇</option>
								<option value="马">马</option>
								<option value="羊">羊</option>
								<option value="猴">猴</option>
								<option value="鸡">鸡</option>
								<option value="狗">狗</option>
								<option value="猪">猪</option>
							</select>
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2"><span class="c-red">*</span>工龄：</label>
						<div class="formControls col-xs-8 col-sm-9">						
							<input type="text" name="workAge" value="" id="workAge" class="input-text" style="width: 300px;">（月）
						</div>
					</div>
					<div class="row cl">
						<label class="form-label col-xs-4 col-sm-2">特长：</label>
						<div class="formControls col-xs-8 col-sm-9"> 
							<textarea name="specialty" style="width:400px;height:100px;">${cross.specialty}</textarea>
						</div>
					</div>
				</div>
				<input type="hidden" name="encryptionPassword" id="encryptionPassword"/>
				<div class="row cl">
					<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
						<button type="submit" class="btn btn-primary radius" type="submit"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
						<button onClick="layer_close();" class="btn btn-default radius" type="button">&nbsp;&nbsp;取消&nbsp;&nbsp;</button>
					</div>
				</div>
			</form>
		</div>
	</body>
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script>
	<script type="text/javascript" src="/lib/icheck/jquery.icheck.min.js"></script>

	<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
	<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer /作为公共模版分离出去-->
	
	<!-- 表单验证 -->
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/messages_zh.js"></script>
	<script type="text/javascript" src="/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<script type="text/javascript" src="/lib/jquery.form/3.51.0/jquery.form.js"></script>
	<script type="text/javascript" src="/javascript/form-utils.js"></script>
	
    
    <script type="text/javascript" src="/lib/My97DatePicker/WdatePicker.js"></script>  
    <script type="text/javascript" src="/lib/kindeditor-4.1.7/kindeditor-min.js"></script> 
    <script type="text/javascript" src="/lib/kindeditor-4.1.7/lang/zh_CN.js"></script> 
	<script type="text/javascript" src="/lib/webuploader/0.1.5/webuploader.min.js"></script> 
	<script type="text/javascript" src="/lib/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/plupload.full.min.js"></script>  
	<script type="text/javascript" src="/lib/jquery.ui.plupload/jquery.ui.plupload.min.js"></script>
	<script type="text/javascript" src="/javascript/sha256.js"></script>	
	
	<!-- 国际化中文支持 -->  
	<script type="text/javascript" src="/lib/plupload-2.1.9/js/i18n/zh_CN.js"></script>  
	<!--请在下方写此页面业务相关的脚本-->
	<script type="text/javascript">
	
		/**
		 * 编辑器初始化
		 */
		KindEditor.ready(function(K) {
			window.editor = K.create('#text-editor');
		});
		
		var picture = "";
		
		$(function() {
			
			$("#uploader").plupload({           
				// General settings           
				runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
				url : "/rest/um/crossInfo/upload",    
				//unique_names: true,  
				chunk_size : '1mb',           
				//rename : true,  
				dragdrop: true,  
				filters : {               
					// Maximum file size               
					max_file_size : '10mb',  
					// Specify what files to browse for  
					mime_types: [  
						{title : "Image files", extensions : "jpg,gif,png"},   
					],
					prevent_duplicates : true //不允许选取重复文件							
				},             
				// Resize images on clientside if we can           
				resize: {               
						width : 200,                
						height : 200,                
						quality : 90,               
						crop: true   
					// crop to exact dimensions           
				},             
				views: {
					list: true,
					thumbs: true, // Show thumbs
					active: 'thumbs'
				},
				// Flash settings           
				flash_swf_url : '/lib/plupload-2.1.9/js/Moxie.swf',                 
				// Silverlight settings           
				silverlight_xap_url : '/lib/plupload-2.1.9/js/Moxie.xap' ,     
				// 参数  
				//multipart_params: {'user': 'Rocky', 'time': '2012-06-12'},
				init : {
					FileUploaded : function(up, file, info) {
						if(info.status == 200){
							picture += info.response + ";";
						}else{
							layer.alert('图片上传失败！', {icon: 6});
						}
					},
					UploadComplete : function(uploader,files) {
						$("#banners").val(picture);
					},
					QueueChanged : function(uploader){
						var count=uploader.files.length; 
						if(count > 4){
							layer.alert('最多只能上传四张图片,请重新上传', {icon: 6});
							uploader.splice();
						}
					}
				}			  
			});  		
			
			
			/**
			 * 单选、复选样式加载
			 */
			$(function() {
				$('.skin-minimal input').iCheck({
					checkboxClass: 'icheckbox-blue',
					radioClass: 'iradio-blue',
					increaseArea: '20%'
				});
			});
			
		
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
			
			$('#nativePlaceProvinceId').change(function() {
				if($(this).val() > 0) {
					var cityName = $("#nativePlaceProvinceId option:selected").text();
							
					$("#nativePlaceCityId option:gt(0)").remove();

					var params = {};
					params.nativePlaceProvinceId = $(this).val();

					$.ajax({
						type : "post",
						dataType : "JSON",
						url : "/rest/um/crossInfo/nativePlaceCityList",
						data : params,
						success : function(result) {
							for (var i = 0; i < result.data.length; i++) {
								$("<option value='" + result.data[i].id + "'>" + result.data[i].name + "</option>").appendTo("#nativePlaceCityId");
							}		
						}
					})
				}
				$("#nativePlaceCityId option:gt(0)").remove();
			});
			
		});
		
		var formId = "add-cross-form";
		
		var formRules = {
			
         	provinceId: {
                min : 1
            },
			cityId: {
                min : 1
            },
			imageFile: {
				required: true
            },
			name: {
                required : true
            },
			mobile: {
                required : true,
				isTel : true
            },
			contactPhone: {
                required : true,
				isTel : true
            },
			workName: {
                required : true
            },
			height: {
                required : true,
				isFloat : true
            },
			school: {
                required : true
            },
			education: {
				required : true
            },
			nativePlaceProvinceId: {
				min : 1
            },
			nativePlaceCityId: {
				min : 1
            },
			constellation: {
				required : true
            },
			yearBirth: {
				required : true
            },
			workAge: {
                required : true
            },
			specialty: {
                required : true,
				minlength:10
            }
		}
		
		var message = {
				
			provinceId: {
                min : '省份不能为空'
            },
			cityId: {
                min : '城市不能为空'
            },
			name: {
                required : '姓名不能为空'
            },
			mobile: {
                required : '登录手机号码不能为空',
				isTel : '请正确输入手机号码'
            },
			contactPhone: {
                required : '联系手机号码不能为空',
				isTel : '请正确输入手机号码'
            },
			workName: {
                required : '工作名不能为空'
            },
			height: {
                required : '身高不能为空',
				isFloat : '请填入数字'
            },
			school: {
                required : '毕业院校不能为空'
            },
			education: {
				required : '学历不能为空'
            },
			nativePlaceProvinceId: {
				min : '省不能为空'
            },
			nativePlaceCityId: {
				min : '市不能为空'
            },
			constellation: {
				required : '星座不能为空'
            },
			yearBirth: {
				required : '属相不能为空'
            },
			workAge: {
                required : '工龄不能为空'
            },
			specialty: {
                required : '特长不能为空',
				minlength: '特长不能小于十个字' 
            },
			imageFile: {
				required : '工作照不能为空'
			}
		}
		
		/**
		 * 表单提交之前
		 */
		var func1 = function() { 							
			var new_pass = sha256_digest("cityjy123456");
			$("#encryptionPassword").val(new_pass);
		}  
		
		/**
		 * 表单提交之后回调 
		 */
		var func2 = function(data) {   
			if (data.statusCode == 0) {
				
				layer.confirm('操作成功', {
					btn: ['确认'] // 按钮
				}, function() {
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
				
			} else if (data.statusCode == 40001) {
				layer.alert('服务器参数验证出错，请确认填写内容重新提交', {icon: 6});
			} else if (data.statusCode == 40002) {
				layer.alert('服务器运行异常', {icon: 6});
			} else if (data.statusCode == 40003) {
				layer.alert('图片上传失败', {icon: 6});
			} else if (data.statusCode == 40019) {
				layer.alert('登录手机已经存在', {icon: 6});
			} else {
				layer.alert('未知错误', {icon: 6});
			}
		}
		
		FormObj.init(formId, formRules, message, func1, func2);

	</script>
	<!--/请在上方写此页面业务相关的脚本-->
</html>