var FormObj = function() {
	
	var FORM_ID;
	
	var VALIDATE_RULES;
	var VALIDATE_MESSAGE;
	var BEFORE_SUBMIT_FUNC;
	var VALIDATE_OPTIONS;
	
	var AJAX_FORM_OPTIONS;
	var AJAX_SUCCESS_FUNC;
	
	var createOptions = function() {
		
		AJAX_FORM_OPTIONS = {
				
			type:"post",  // 提交方式  
			
            dataType:"json",  // 数据类型  
			
            success:function(data) {
				AJAX_SUCCESS_FUNC(data);
            },

			error: function(msg) {
				alert(msg);
			}
		}
		
		VALIDATE_OPTIONS = {};
		
		VALIDATE_OPTIONS.debug = true;
		
		VALIDATE_OPTIONS.errorElement = 'label';
		
		VALIDATE_OPTIONS.errorClass = 'help-inline';
		
		VALIDATE_OPTIONS.focusInvalid = false;
		
		if (VALIDATE_RULES != null && VALIDATE_RULES != "" && VALIDATE_MESSAGE != null && VALIDATE_MESSAGE != "") {
			VALIDATE_OPTIONS.rules = VALIDATE_RULES;
			VALIDATE_OPTIONS.messages = VALIDATE_MESSAGE;
		}
		
		VALIDATE_OPTIONS.success = function(label) {
			label.closest('.control-group').removeClass('error');
			label.remove();
		};
		
		VALIDATE_OPTIONS.submitHandler = function (form) {
			BEFORE_SUBMIT_FUNC();
			$(form).ajaxSubmit(AJAX_FORM_OPTIONS);
		};
	};
	
	var bindForm = function() {
		$("#" + FORM_ID).validate(VALIDATE_OPTIONS);
	}
	
	return {
		init : function(id, rules, message, func1, func2) { 
			FORM_ID = id;
			VALIDATE_RULES = rules;
			VALIDATE_MESSAGE = message;
			BEFORE_SUBMIT_FUNC = func1;
			AJAX_SUCCESS_FUNC = func2;
			
			createOptions();
			bindForm();
		}
	}
	
}();