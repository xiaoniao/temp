<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="renderer" content="webkit|ie-comp|ie-stand">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<link rel="Bookmark" href="/favicon.ico">
		<link rel="Shortcut Icon" href="/favicon.ico" />
		
		<!--[if lt IE 9]>
		<script type="text/javascript" src="lib/html5.js"></script>
		<script type="text/javascript" src="lib/respond.min.js"></script>
		<script type="text/javascript" src="lib/PIE_IE678.js"></script>
		<![endif]-->
		
		<link rel="stylesheet" type="text/css" href="/static/h-ui/css/H-ui.min.css" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/H-ui.admin.css" />
		<link rel="stylesheet" type="text/css" href="/lib/Hui-iconfont/1.0.7/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="/lib/iconfont/iconfont.css" />
		<link rel="stylesheet" type="text/css" href="/lib/icheck/icheck.css" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/skin/default/skin.css" id="skin" />
		<link rel="stylesheet" type="text/css" href="/static/h-ui.admin/css/style.css" />
		
		<!--[if IE 6]>
		<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
		<script>DD_belatedPNG.fix('*');</script>
		<![endif]-->
		
		<title>后台管理中心</title>
		<meta name="keywords" content="">
		<meta name="description"content="">
	</head>
	<style type="text/css">
		#promptOrder{
			z-index:9999;
			position: fixed;
			bottom: 20px;
			right: 25px;
			font-size: 0;
			line-height: 0;
			display:none;
			background:#fff; 
			color:#444444;
			border:1px solid #dcdcdc;
			padding:10px;
		}
		.float_layer {
			z-index: 99999;
			width:300px;
			border:1px solid #aaaaaa;
			/*display:none;*/
			background:#fff;
		}
		.float_layer h2 {
			height:25px;
			line-height:25px;
			padding-left:10px;
			font-size:14px;
			color:#333;
			background:url(/img/title_bg.gif) repeat-x;
			border-bottom:1px solid #aaaaaa;
			position:relative;
		}
		.float_layer .min {
			width:21px;
			height:20px;
			background:url(/img/min.gif) no-repeat 0 bottom;
			position:absolute;
			top:2px;
			right:25px;
		}
		.float_layer .min:hover {
			background:url(/img/min.gif) no-repeat 0 0;
		}
		.float_layer .max {
			width:21px;
			height:20px;
			background:url(/img/max.gif) no-repeat 0 bottom;
			position:absolute;
			top:2px;
			right:25px;
		}
		.float_layer .max:hover {
			background:url(/img/max.gif) no-repeat 0 0;
		}
		.float_layer .close {
			width:21px;
			height:20px;
			background:url(/img/close.gif) no-repeat 0 bottom;
			position:absolute;
			top:2px;
			right:3px;
		}
		.float_layer .close:hover {
			background:url(/img/close.gif) no-repeat 0 0;
		}
		.float_layer .content {
			height:120px;
			overflow:hidden;
			font-size:14px;
			line-height:18px;
			color:#666;
			text-indent:28px;
		}
		.float_layer .wrap {
			padding:10px;
		}
	</style>
	<body>
		<jsp:include page="/WEB-INF/views/head.jsp"/>
		<aside class="Hui-aside">
			<input runat="server" id="divScrollValue" type="hidden" value="" />
			<div class="menu_dropdown bk_2">

				<shiro:hasAnyRoles name="admin">
				<dl id="menu-article">
						<dt><i class="Hui-iconfont">&#xe627;</i> 订单管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/order/toList" data-title="全部订单" onclick="$('#red-point').hide();" href="javascript:void(0)"><span id="red-point" class="c-red">*</span>全部订单</a></li>
								<li><a _href="/rest/um/order/toOrderPending" data-title="待轮巡订单" href="javascript:void(0)">待轮巡订单</a></li>
								<li><a _href="/rest/um/order/toObjectionOrderList" data-title="账单异议订单" href="javascript:void(0)">账单异议订单</a></li>
								<li><a _href="/rest/um/order/toComplaintOrderList" data-title="投诉订单" href="javascript:void(0)">投诉订单</a></li>
								<li><a _href="/rest/um/order/toOrderEnd" data-title="提前取消订单" href="javascript:void(0)">提前取消订单</a></li>
								<li><a _href="/rest/um/order/toOrderNine" data-title="晚9点未结束订单" href="javascript:void(0)">晚9点未结束订单</a></li>
								<li><a _href="/rest/um/invoice/toInvoiceRecordList" data-title="开票记录" href="javascript:void(0)">开票记录</a></li>
								<li><a _href="/rest/um/orderCost/toList" data-title="账单记录" href="javascript:void(0)">账单记录</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin">
				<dl id="menu-member">
						<dt><i class="Hui-iconfont">&#xe60d;</i> 会员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/member/toList" data-title="会员列表" href="javascript:void(0)">会员列表</a></li>
								<li><a _href="/rest/um/member/toAuditList" data-title="会员审核列表" href="javascript:void(0)">会员审核列表</a></li>
								<li><a _href="/rest/um/accountRecord/toAccountRecordList" data-title="帐户明细" href="javascript:void(0)">帐户明细(当月)</a></li>
								<li><a _href="/rest/um/accountRecord/toAccountRecordHistoryList" data-title="历史明细" href="javascript:void(0)">历史明细</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin,normal_manage">
				<dl id="menu-product">
						<dt><i class="Hui-iconfont">&#xe62d;</i> 金鹰管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/crossInfo/toList" data-title="金鹰列表" href="javascript:void(0)">金鹰列表</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin">
				<dl id="menu-cash">
						<dt><i class="Hui-iconfont">&#xe63a;</i> 资金管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/money/toRechargeList" data-title="充值记录" href="javascript:void(0)">充值记录</a></li>
								<li><a _href="/rest/um/money/toTakeCashList" data-title="提现记录" href="javascript:void(0)">提现记录</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin,normal_manage">
				<dl id="menu-comments">
						<dt><i class="Hui-iconfont">&#xe70c;</i> 评价管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/comment/toCrossCommentList" data-title="对金鹰的评价" href="javascript:void(0)">对金鹰的评价</a></li>
								<li><a _href="/rest/um/comment/toCompanyCommentList" data-title="对公司的评价" href="javascript:void(0)">对公司的评价</a></li>
								<li><a _href="/rest/um/comment/toMemberCommentList" data-title="对用户的评价" href="javascript:void(0)">对用户的评价</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin,res_manage_city,res_manage_all">
				<dl id="menu-brand">
						<dt><i class="Hui-iconfont">&#xe616;</i> 资源管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/brand/toList" data-title="品牌合作" href="javascript:void(0)">品牌合作</a></li>
								<li><a _href="/rest/um/banner/toList" data-title="广告" href="javascript:void(0)">广告</a></li>
								<li><a _href="/rest/um/resourceType/toList" data-title="资源分类" href="javascript:void(0)">资源分类</a></li>
								<li><a _href="/rest/um/resource/toList" data-title="资源列表" href="javascript:void(0)">资源列表</a></li>
								<li><a _href="/rest/um/resourceSuggest/toList" data-title="资源审核" href="javascript:void(0)">资源审核</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin">
				<dl id="menu-system">
						<dt><i class="Hui-iconfont">&#xe63c;</i> 系统设置<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/city/toList" data-title="开通城市" href="javascript:void(0)">开通城市</a></li>
								<li><a _href="/rest/um/orderConfig/toList" data-title="系统设置" href="javascript:void(0)">系统设置</a></li>
								<li><a _href="/rest/um/travelDesc/toList" data-title="服务项目" href="javascript:void(0)">服务项目</a></li>
								<li><a _href="/rest/um/costItem/toList" data-title="账单类目" href="javascript:void(0)">账单类目</a></li>
								<li><a _href="/rest/um/protocolAndDescription/toUserServiceAgreement" data-title="用户信息协议" href="javascript:void(0)">用户信息协议</a></li>
								<li><a _href="/rest/um/protocolAndDescription/toDescription" data-title="信息能量说明" href="javascript:void(0)">信息能量说明</a></li>
							</ul>
						</dd>	
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin">
				<dl id="menu-brand">
						<dt><i class="Hui-iconfont">&#xe6cc;</i> 优惠券管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/coupon/toCouponList" data-title="优惠券管理" href="javascript:void(0)">优惠券管理</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
				<shiro:hasAnyRoles name="admin">
				<dl id="menu-brand">
						<dt><i class="Hui-iconfont">&#xe6cc;</i> 权限管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
						<dd>
							<ul>
								<li><a _href="/rest/um/privilege/toList" data-title="管理员管理" href="javascript:void(0)">管理员管理</a></li>
							</ul>
						</dd>
				</dl>
				</shiro:hasAnyRoles>
			</div>
		</aside>
		<div class="dislpayArrow hidden-xs">
			<a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
		</div>

		<section class="Hui-article-box">
			<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
				<div class="Hui-tabNav-wp">
					<ul id="min_title_list" class="acrossTab cl">
						<li class="active"><span title="工作台" data-href="welcome.html">工作台</span><em></em></li>
					</ul>
				</div>
				<div class="Hui-tabNav-more btn-group">
					<a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;">
						<i class="Hui-iconfont">&#xe6d4;</i>
					</a>
					<a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;">
						<i class="Hui-iconfont">&#xe6d7;</i>
					</a>
				</div>
			</div>
			<div id="iframe_box" class="Hui-article">
				<div class="show_iframe">
					<div style="display: none" class="loading"></div>
					<iframe scrolling="yes" frameborder="0" src="/rest/page/welcome"></iframe>
				</div>
			</div>
			<div id="promptOrder" >
				<article class="page-container">
					<div style="text-align: center; font-size: 14px;margin-bottom:20px" >
						有<span style="color: red" id="count"></span>条待处理订单
					</div>
					<div style="text-align: center;">
						<a href="/rest/page/index" class="ml-5 btn btn-primary radius">查看</a>
					</div>
				</article>
			</div>
		</section>
		<script type="text/javascript" src="/lib/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="/lib/layer/2.1/layer.js"></script>
		<script type="text/javascript" src="/static/h-ui/js/H-ui.js"></script>
		<script type="text/javascript" src="/static/h-ui.admin/js/H-ui.admin.js"></script>
		<script type="text/javascript" src="/lib/socket.io/socket.io.js"></script>
		<script type="text/javascript">

			// socket
			var socket =  io.connect('http://app.cityjinying.com:9092');

			// 连接
			socket.on('connect', function() {
				console.log('已连接');
			});

			// 事件来临
			socket.on('chatevent', function(data) {
				console.log('接收消息：' + JSON.stringify(data));
				openNotifyLayer(data);
			});

			// 断开连接
			socket.on('disconnect', function() {
				console.log('断开连接');
			});

			// 断开连接
			function sendDisconnect() {
				socket.disconnect();
			}

			$('#red-point').hide();

			function openNotifyLayer(data) {
				var oDiv = document.getElementById('miaov_float_layer');
				var oBtnMin = document.getElementById('btn_min');
				var oBtnClose = document.getElementById('btn_close');
				var oDivContent = oDiv.getElementsByTagName('div')[0];

				if(data.content == '您有一条新订单') {
					$('#red-point').show();
				}
				$("#notify_title")[0].textContent = data.title;
				$("#nofity_content")[0].textContent = data.content;

				// 播放声音
				var audio = new Audio('/img/notify.mp3');
				audio.play();

				if(oDiv.style.display != 'block') {
					var iMaxHeight = 0;

					var isIE6 = window.navigator.userAgent.match(/MSIE 6/ig) && !window.navigator.userAgent.match(/MSIE 7|8/ig);

					oDiv.style.display = 'block';
					iMaxHeight = oDivContent.offsetHeight;

					if (isIE6) {
						oDiv.style.position = 'absolute';
						repositionAbsolute();
						miaovAddEvent(window, 'scroll', repositionAbsolute);
						miaovAddEvent(window, 'resize', repositionAbsolute);
					} else {
						oDiv.style.position = 'fixed';
						repositionFixed();
						miaovAddEvent(window, 'resize', repositionFixed);
					}

					// 最小话
					oBtnMin.timer = null;
					oBtnMin.isMax = true;
					oBtnMin.onclick = function() {
						startMove(oDivContent, (this.isMax = !this.isMax) ? iMaxHeight: 0,
						function() {
							oBtnMin.className = oBtnMin.className == 'min' ? 'max': 'min';
						});
					};

					// 关闭
					oBtnClose.onclick = function() {
						oDiv.style.display = 'none';
					};
				} else {
					if(oBtnMin.className == 'max') {
						oBtnMin.click();
					}
				}
			}
		
			function miaovAddEvent(oEle, sEventName, fnHandler) {
				if (oEle.attachEvent) {
					oEle.attachEvent('on' + sEventName, fnHandler);
				} else {
					oEle.addEventListener(sEventName, fnHandler, false);
				}
			}

			function startMove(obj, iTarget, fnCallBackEnd) {
				if (obj.timer) {
					clearInterval(obj.timer);
				}
				obj.timer = setInterval(function() {
					doMove(obj, iTarget, fnCallBackEnd);
				},
				30);
			}

			function doMove(obj, iTarget, fnCallBackEnd) {
				var iSpeed = (iTarget - obj.offsetHeight) / 8;

				if (obj.offsetHeight == iTarget) {
					clearInterval(obj.timer);
					obj.timer = null;
					if (fnCallBackEnd) {
						fnCallBackEnd();
					}
				} else {
					iSpeed = iSpeed > 0 ? Math.ceil(iSpeed) : Math.floor(iSpeed);
					obj.style.height = obj.offsetHeight + iSpeed + 'px';

					((window.navigator.userAgent.match(/MSIE 6/ig) && window.navigator.userAgent.match(/MSIE 6/ig).length == 2) ? repositionAbsolute: repositionFixed)()
				}
			}

			function repositionAbsolute() {
				var oDiv = document.getElementById('miaov_float_layer');
				var left = document.body.scrollLeft || document.documentElement.scrollLeft;
				var top = document.body.scrollTop || document.documentElement.scrollTop;
				var width = document.documentElement.clientWidth;
				var height = document.documentElement.clientHeight;

				oDiv.style.left = left + width - oDiv.offsetWidth + 'px';
				oDiv.style.top = top + height - oDiv.offsetHeight + 'px';
			}

			function repositionFixed() {
				var oDiv = document.getElementById('miaov_float_layer');
				var width = document.documentElement.clientWidth;
				var height = document.documentElement.clientHeight;

				oDiv.style.left = width - oDiv.offsetWidth + 'px';
				oDiv.style.top = height - oDiv.offsetHeight + 'px';
			}
		</script>

		<div class="float_layer" id="miaov_float_layer">
			<h2 style="margin:0px;padding: 4px;">
				<strong ><span id="notify_title">1111111111111111111</span></strong>
				<a id="btn_min" href="javascript:;" class="min"></a>
				<a id="btn_close" href="javascript:;" class="close"></a>
			</h2>
			<div class="content">
				<div class="wrap"<span id="nofity_content">222222222222222222</span></div>
			</div>
		</div>
	</body>
</html>