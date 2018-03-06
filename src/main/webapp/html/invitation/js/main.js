$(function(){
	// 查询分享来源
	let _code = window.location.search.split('=')[1];
	console.log(_code);
	$.ajax({
		cache : false,
		type : 'POST',
		url : '/rest/mc/share/source',
		data : {'code':_code},
		success : function(result){
			console.log(result.statusCode);
			if (result.statusCode == 0 && result.data != null) {
				let data = result.data;
				$('#source').html('接受 <span>' + data.sourceMobile + '</span> ' + data.sourceName + '先生 邀请');
			}
		}
	});
	
	//单选框切换
	var $inputGroup = $('.register-group'),
	$radioConGroup = $inputGroup.children('div'),
	$note = $('.note'),
	$mobile = $('#mobile'),
	$realName = $('#realName'),
	$code = $('.code');
	var countDown = 60;
	
	$radioConGroup.on('click',function(){
		$(this).find('input').attr('checked','checked');
		$(this).addClass('selected').siblings('div').removeClass('selected');
	});
	// 获取验证码
	$code.on('click',function(){
		if (countDown < 60) return;
		countDown--;
		$note.hide()
		if((!/^1[34578]\d{9}$/.test($mobile.val()))){
			$('#wrongNumber').show();
			return;
		}else{
			$('#wrongNumber').hide();
		}
		if ($realName.val().replace(/\s+/g,"") == '') {
			$('#noName').show();
			return;
		} else {
			$('#noName').hide();
		}
		$.ajax({
			cache : false,
			type : 'POST',
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			url : '/rest/mc/member/shared_sendsms',
			data : {'mobile':$mobile.val(),'name':$realName.val(),'identifying':_code},
			error: function(request) {
				alert("连接异常");
			},
			success: function(result) {
				if (result.statusCode == 0) {
					$('#codeSended').show();
					let timer = setInterval(function() {
						if (countDown > 0) {
							$code.html(countDown-- + "s重新获取");
						} else {
							$code.html("获取验证码");
							clearInterval(timer);
							countDown = 60;
						}
					}, 1000);
				} else if (result.statusCode == 30020) {
					$('#userExist').show();
					countDown = 60;
				} else if (result.statusCode == 40001) {
					$('#paramsErr').show();
					countDown = 60;
				} else if (result.statusCode == 40014) {
					$('#sendedErr').show();
					countDown = 60;
				} else if (result.statusCode == 40015) {
					$('#timesOver').show();
					countDown = 60;
				} else if (result.statusCode == 40008) {
					$('#mobileForbidden').show();
					countDown = 60;
				} else if (result.statusCode == 40011) {
					$('#nameInvalid').show();
					countDown = 60;
				} else {
					$('#exception').show();
					countDown = 60;
				}
			}
		});
	});
	// 注册
	$('.register-btn').click(function() {
		if((!/^1[34578]\d{9}$/.test($mobile.val()))){
			$('#wrongNumber').show();
			return;
		}else{
			$('#wrongNumber').hide();
		}
		if ($realName.val().replace(/\s+/g,"") == '') {
			$('#noName').show();
			return;
		} else {
			$('#noName').hide();
		}
		if ($("#code").val().replace(/\s+/g,"") == '') {
			$('#noCode').show();
			return;
		} else {
			$('#noCode').hide();
		}
		$.ajax({
			cache : false,
			type : 'POST',
			url : '/rest/mc/member/shared_signup',
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			data : $('.register-form').serialize() + '&identifying=' + _code,
			error: function(request) {
				alert("连接异常");
			},
			success: function(result) {
				console.log('statusCode : ' + result.statusCode);
				if (result.statusCode == 0) {
					$('.register-form')[0].reset();
					$('#reg_success').show();
				} else if (result.statsCode == 40001) {
					$('#paramsErr').show();
				} else if (result.statsCode == 40009) {
					$('#codeErr').show();
				} else if (result.statsCode == 40010) {
					$('#timeout').show();
				} else if (result.statsCode == 40011) {
					$('#nameInvalid').show();
				} else {
					$('#exception').show();
				}
			}
		});
	});
	
	$('input').bind('input oninput', function() {
		$note.hide()
	});
});
