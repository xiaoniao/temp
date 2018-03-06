var Login = function() {
	
	/**
	 * 点击切换验证码
	 */
	var changeCode = function(dom) {
		var formKey = $("#formKey").val();
		$(dom).attr("src", "/rest/user/kaptcha?formKey=" + formKey);
	}
	
	/**
	 * 提交前 加密密码
	 */
	var changePassword = function() {
		var password = $("#password").val();
		var new_pass = sha256_digest(password);
		$("#password").val(new_pass);
	}
	
	var validateOptions = {
		
		// 使用什么标签显示错误信息
		errorElement: 'label',   
		
		// 指定错误提示的css类名,可以自定义错误提示的样式
		errorClass: 'help-inline', 
		
		// 提交表单后,未通过验证的表单会获得焦点
        focusInvalid: false,       
        
        // 表单验证规则
        rules: {
        	username: {
                required : true,
                minlength : 6
            },
            password: {
                required: true
            },
            captcha: {
                required: true
            }
        },
        
        // 规则验证未通过时，需显示的文本内容
        messages: {
            username: {
                required: "请输入正确的登录名",
                minlength: "登录名不能少于6个字符"
            },
            password: {
                required: "请输入正确的密码"
            },
            captcha: {
                required: "请输入验证码"
            }
        },
        
        // 验证成功
        success: function (label) {
            label.closest('.control-group').removeClass('error');
            label.remove();
        },
        
        // 表单提交
        submitHandler: function (form) {
        	changePassword();
        	form.submit();
        }
	};
	
	return {
		/**
		 * 页面初始化 - 绑定页面事件
		 */
		init : function() {
			
			var changeCodeA = $("#kanbuq");
			var codeImg = changeCodeA.parent().find("img").eq(0);
			changeCodeA.click(function() { changeCode(codeImg); });
			
			$("#login-form").validate(validateOptions);
		}
	}
}();