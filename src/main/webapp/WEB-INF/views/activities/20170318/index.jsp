<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no" />
		<title>活动专享</title>
		<link rel="icon" href="http://www.cityjinying.com/images/logo.png" type="image/x-icon" />
		<link rel="stylesheet" href="/html/activities/20170318/css/style.css" />
	</head>
	<body>
		<div class="banner"></div>
		<form class="draw-form" action="javascript:;">
			<div class="draw-group">
				<input type="text" id="mobile" name="mobile" placeholder="请输入您的手机号码" maxlength="11"/>
			</div>
			
			<div class="draw-group draw-detail">
				<div>
					<span>姓名：</span>
					<input type="text" name="name" id="realName"/>
				</div>
				<div class="sex-tab selected">
					<i></i>
					<input type="radio" name="sex" id="man" value="0" checked="checked"/>
					<label for="man">先生</label>
				</div>
				<div class="sex-tab">
					<i></i>
					<input type="radio" name="sex"  id="woman" value="1"/>
					<label for="woman">女士</label>
				</div>
			</div>
			<div class="draw-group code-group">
				<input type="text" placeholder="请输入验证码" name="code" id="code"/>
				<span class="right resend" id="sendCode">获取验证码</span>
			</div>
			<button class="draw-btn">领取红包</button>
		</form>
		<!--错误提示-->
		<div class="note note1" id="numberErr">请输入有效的手机号码</div>
		<div class="note note2" id="codeErr">验证码错误</div>
		<div class="note note2" id="codeSended">验证码已发送</div>
		<div class="note note2" id="sendErr">验证码发送失败</div>
		<div class="note note1" id="timesOver">您操作过于频繁，请稍后再试</div>
		<div class="note note1" id="mobileForbidden">该手机号已被禁用，请联系客服</div>
		<div class="note note2" id="nameInvalid">实名认证不匹配</div>
		<div class="note note2" id="timeout">验证码已失效</div>
		<div class="note note2" id="noCode">请填写验证码</div>
		<div class="note note2" id="noName">请填写真实姓名</div>
		<div class="note note2" id="actErr">红包领取失败</div>
		<div class="note note2" id="actNotStart">活动未开始</div>
		<div class="note note2" id="actExpire">活动已结束</div>
		<div class="note note1" id="result_repeat">您已领取过该红包</div>
		<div class="note note1" id="actExpire">领取失败，二维码错误</div>
		<div class="note note1" id="actExpire">领取失败，二维码已失效</div>
		<!--活动规则-->
		<div class="activity-rules">
			<h1>活动规则<i></i></h1>
			<div class="rules-content">
				<h2>2017年会代金券活动规则</h2>
				<ol class="rules-list">
					<li>1.用户扫码进入领取页面，输入手机号点击领取代金券卡包，一个手机号只能领取一次。</li>

					<li>2.卡包中的代金券分为20张500元，10张1000元，共20000元，仅限城侍金鹰app客户端使用。</li>
					
					<li>3.卡包中的所有代金券将在客户端上线的第一时间，发送到客户端“我的钱包”的“我的代金券”中。</li>
					
					<li>4.代金券可以通过客户端转赠给好友。</li>
					
					<li>5.代金券仅限抵扣金鹰服务费且为一次性使用，不拆分，不提现，每次至多使用1000元金额的代金券。</li>
					
					<li>6.代金券的有效使用必须在截至日期前，有效期为城侍金鹰客户端正式上线后的三个月，具体时间将在上线后在消息中通知。</li>
					
					<li>7.参加活动用户若发现存在不正当方式，城侍金鹰有权禁止其参与活动，取消代金券使用资格并收回。</li>
					
					<li>8.本次活动的最终解释权归城侍金鹰所有。</li>
				</ol>
			</div>
		</div>
		
		<!--领取结果:success-->
		<!--<div class="draw-result">
			<div class="backdrop"></div>
			<div class="draw-result-content">
				<div class="close"></div>
				<div class="draw-text">
					<div class="draw-success">恭喜您获得</div>
					<div class="red-package success-package">
						<span class="money"><span class="sign">￥</span>500</span>优惠券红包
					</div>
				</div>
				<div class="tip">您已经注册成功</div>
			</div>
		</div>-->
		<!-- 领取结果：已领取-->
		<div class="draw-result" id="result_success">
			<div class="backdrop"></div>
			<div class="draw-result-content">
				<!-- <div class="close"></div> -->
				<div class="draw-text">
					<div class="draw-tip">红包已在您的钱包中</div>
					<div class="red-package tip-package">
						<div class="left">							
							<span class="money"><span class="sign">￥</span>20000</span>
						</div>
						<div class="right">
							<div>优惠券红包</div>
							<div>有效期2017年4月5日至2017年7月4日</div>
						</div>
						<div class="line"></div>
					</div>
				</div>
				<!-- <a class="go-use" href="http://app.cityjinying.com/webapp/CityAssistantApp/">立即使用</a> -->
			</div>
		</div>
		
		<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js" ></script>
		<script>
	        var oHtml = document.getElementsByTagName('html')[0];
	        var scrWid = document.documentElement.offsetWidth || document.body.offsetWidth;
	        var initWid = 750;
	        oHtml.style.fontSize = 100 * scrWid/initWid + 'px';
			
			$(function(){
				//活动规则卷开收起
				var $slideBtn = $('.activity-rules h1');
				var flag = 1;
				$slideBtn.on('click',function(){
					var now = -90;
					var now2 = 0;
					var  timer = null;
					if(flag == 0){
						timer = setInterval(function(){
							now += 5;
							if(now <= 0){
								$('.activity-rules i').css('transform','rotate('+ now +'deg)');
							}else{
								clearInterval(timer);
							}
							
						},1);
						$('.rules-content').slideDown();
						flag = 1;
						return;
					}
					if(flag == 1){
						timer = setInterval(function(){
							now2 -= 5;
							if(now2 >= -90){
								$('.activity-rules i').css('transform','rotate('+ now2 +'deg)');
							}else{
								clearInterval(timer);
							}
						},1);
						$('.rules-content').slideUp();
						flag = 0;
						return;
					}
					
				});
				
				$('.close').click(function() {
					$('.draw-result').hide();
				});
				
				//单选框切换
				var $radioConGroup = $('.sex-tab');
				$radioConGroup.on('click',function(){
					$(this).find('input').attr('checked','checked');
					$(this).addClass('selected').siblings('div').removeClass('selected');
				});
				
				var $mobile = $('#mobile'),
					$realName = $('#realName'),
					$code = $('#code'),
					$sendCode = $('#sendCode');
				var countDown = 60;
					
				// 获取验证码
				$('#sendCode').click(function() {
					if (countDown < 60) return;
					if((!/^1[34578]\d{9}$/.test($mobile.val()))){
						$('#numberErr').show();
						setTimeout(function() {$('#numberErr').hide()}, 1500);
						return;
					}
					if ($realName.val().replace(/\s+/g,"") == '') {
						$('#noName').show();
						setTimeout(function() {$('#noName').hide()}, 1500);
						return;
					}
					$.ajax({
						cache : false,
						type : 'POST',
						contentType : "application/x-www-form-urlencoded; charset=UTF-8",
						url : '/rest/mc/member/active_sendSms',
						data : {'mobile':$mobile.val(),'name':$realName.val()},
						dataType : 'JSON',
						error: function(request) {
							$('#sendErr').show();
							setTimeout(function() {$('#sendErr').hide()}, 1500);
							console.log('connect error');
						},
						success: function(result) {
							if (result.statusCode == 0) {
								$('#codeSended').show();
								setTimeout(function() {$('#codeSended').hide()}, 1500);
								let timer1 = setInterval(function() {
									if (countDown > 0) {
										$sendCode.html(countDown-- + "s重新获取");
									} else {
										$sendCode.html("获取验证码");
										clearInterval(timer1);
										countDown = 60;
									}
								}, 1000);
							} else if (result.statusCode == 40014) {
								$('#sendErr').show();
								setTimeout(function() {$('#sendErr').hide()}, 1500);
								countDown = 60;
							} else if (result.statusCode == 40015) {
								$('#timesOver').show();
								setTimeout(function() {$('#timesOver').hide()}, 1500);
								countDown = 60;
							} else if (result.statusCode == 40011) {
								$('#nameInvalid').show();
								setTimeout(function() {$('#nameInvalid').hide()}, 1500);
								countDown = 60;
							} else {
								$('#sendErr').show();
								setTimeout(function() {$('#sendErr').hide()}, 1500);
								countDown = 60;
							}
						}
					});
				});
				
				//提交
				$('.draw-btn').click(function() {
					if((!/^1[34578]\d{9}$/.test($mobile.val()))){
						$('#numberErr').show();
						setTimeout(function() {$('#numberErr').hide()}, 1500);
						return;
					}
					if ($realName.val().replace(/\s+/g,"") == '') {
						$('#noName').show();
						setTimeout(function() {$('#noName').hide()}, 1500);
						return;
					}
					if ($code.val().replace(/\s+/g,"") == '') {
						$('#noCode').show();
						setTimeout(function() {$('#noCode').hide()}, 1500);
						return;
					}
					let token = location.search.split("=")[1];
					console.log(token);
					$.ajax({
						cache : false,
						type : 'POST',
						url : '/rest/mc/member/active_bind',
						contentType: "application/x-www-form-urlencoded; charset=UTF-8",
						data : $('.draw-form').serialize(),
						dataType : 'JSON',
						error: function(request) {
							$('#sendErr').show();
							setTimeout(function() {$('#sendErr').hide()}, 1500);
							console.log('connect error');
						},
						success: function(result) {
							if (result.statusCode == 0) {
								$.ajax({
									cache : false,
									type : 'POST',
									url : '/rest/mc/activity/join?activityId=1&userId=' + result.data + "&token=" + token,
									dataType : 'JSON',
									error : function() {
										$('#actErr').show();
										setTimeout(function() {$('#actErr').hide()}, 1500);
									},
									success : function(res) {
										if (res.statusCode == 0) {
											$('#result_success').show();
										} else if (res.statusCode == 32003) {
											$('#result_repeat').show();
											setTimeout(function() {$('#result_repeat').hide()}, 1500);
										} else if (res.statusCode == 32004) {
											$('#actNotStart').show();
											setTimeout(function() {$('#actNotStart').hide()}, 1500);
										} else if (res.statusCode == 32005) {
											$('#actExpire').show();
											setTimeout(function() {$('#actExpire').hide()}, 1500);
										} else if (res.statusCode == 32006) {
											$('#qrErr').show();
											setTimeout(function() {$('#qrErr').hide()}, 1500);
										} else if (res.statusCode == 32007) {
											$('#qrInvalid').show();
											setTimeout(function() {$('#qrInvalid').hide()}, 1500);
										} else {
											$('#actErr').show();
											setTimeout(function() {$('#actErr').hide()}, 1500);
										}
									}
								});
							} else if (result.statsCode == 40008) {
								$('#mobileForbidden').show();
								setTimeout(function() {$('#mobileForbidden').hide()}, 1500);
							} else if (result.statsCode == 40009) {
								$('#codeErr').show();
								setTimeout(function() {$('#codeErr').hide()}, 1500);
							} else if (result.statsCode == 40010) {
								$('#timeout').show();
								setTimeout(function() {$('#timeout').hide()}, 1500);
							} else if (result.statsCode == 40011) {
								$('#nameInvalid').show();
								setTimeout(function() {$('#nameInvalid').hide()}, 1500);
							} else {
								$('#actErr').show();
								setTimeout(function() {$('#actErr').hide()}, 1500);
							}
						}
					});
				});
				
			})

	    </script>
	</body>
</html>
