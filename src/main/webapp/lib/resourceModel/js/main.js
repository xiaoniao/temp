var city = {'id': 90, 'name' : "杭州"};// 默认杭州
city = localStorage.getItem('city') == null ? city : JSON.parse(localStorage.getItem('city'));
$('[name="localCity"]').html('<i></i>' + city.name);
var cityList = [];
/* 资源页*/
function resourceMethod(){
	//城市列表显示与隐藏
	var $city = $('.header-logo .city'),
	$cityList = $city.find('.city-list');
	$city.hover(
		function(){
			$cityList.show();
		},
		function(){
			$cityList.hide();
		}
	);
	
	//搜索栏背景色变化
	var $searchBar = $('.header-center .search-bar'),
	$searchInput = $searchBar.find('input');
	$searchInput.on('focus',function(){
		$searchBar.css('background','rgba(42,42,42,0.7)');
	});
	$searchInput.on('blur',function(){
		$searchBar.css('background','rgba(98,98,98,0.7)');
	});
	//固定浮动头部显示与隐藏
	var barTop = $searchBar.offset().top + $searchBar.height(),
	$fixedHeader = $('.resource-fixed-header');
	$(window).on('scroll',function(){
		if($(this).scrollTop() >= barTop){
			$fixedHeader.slideDown(200);
		}else{
			$fixedHeader.hide();
		}
	});	
}

//城市切换
function fixedCityTab(){
	var $fixedCity = $('.fixed-city'),
	$fixedCityList = $fixedCity.find('.city-list'),
	timer = null;
	$fixedCity.hover(
		function(){
			clearTimeout(timer);
			$fixedCityList.show();
		},
		function(){
			timer = setTimeout(function(){
				$fixedCityList.hide();
			},500);
		}
	);
}

/** 已开通城市列表 */
function getCityList() {
	let $cityList = $('.city-list');
	$cityList.empty();
	$.ajax({
		url : '/rest/um/city/list',
		type : 'get',
		dataType : 'JSON',
		error : function(data) {
			console.log('error');
			console.log(data);
		},
		success : function(data) {
			if (data.statusCode == 0) {
				let citys = data.data;
				cityList = citys;
				for (let i = 0; i < citys.length; i++) {
					if (citys[i].id == city.id) continue;
					$cityList.append('<li value="'+ i +'"><a href="javascript:;">'+ citys[i].name +'</a></li>');
				}
			} else {
				console.log(data);
			}
		}
	});
}

//搜索页
function searchMethod(){
	//搜索栏背景色变化
	var $fixedSearchBar = $('.fixed-search-bar'),
	$fixedSearchInput = $fixedSearchBar.find('input');
	$fixedSearchInput.on('focus',function(){
		$fixedSearchBar.css('background','#f7f7f7');
	});
	$fixedSearchInput.on('blur',function(){
		$fixedSearchBar.css('background','#fff');
	});
}

//修改指定字的颜色
function repstr(repStr,obj){
	if(repStr != null && repStr != ""){
		var str = '<span style="color:#D9B55D">'+repStr+'</span>';
		var reg = new RegExp(repStr,"g");
		//text()取得所有匹配的元素的内容，html()取得第一个，所以这边必须要用html()
		//html(val)会设置所有匹配到的元素
		$(obj).each(function(){
			$(this).html($(this).html().replace(reg,str));
		});
	}
}

//修改密码弹框
function modifyPass(){
	var $alert = $('.alert'),
	$modifyPass = $('.modify-pass'),
	$cancel = $('.cancel');
	$modifyPass.on('click',function(){
		$alert.show();
	});
	$cancel.on('click',function(){
		$alert.hide();
		$('.modify-form')[0].reset();
	});
}

//滚动展示
function showSlider(){
	var $showSlider = $('.show-slider'),
	$img = $showSlider.find('img'),
	$next = $('.right-arrow'),
	$prev = $('.left-arrow'),
	now = 0;
	$showSlider.width(($img.eq(0).width()+10)*$img.length);
	$next.on('click',function(){
		now ++;
		if(now >= $img.length - 5){
			now = $img.length - 5;
		}
		slideAnimate();
	});
	$prev.on('click',function(){
		now --;
		if(now <= 0){
			now = 0;
		}
		slideAnimate();
	});
	function slideAnimate(){
		$showSlider.stop().animate({'left':-now*($img.eq(0).width()+10)},function(){
			if($showSlider.position().left < 0){
				$prev.show();
			}else{
				$prev.hide();
			}
			if(now >= $img.length - 5){
				$next.css('background-color','rgba(217,217,217,0.9)');
			}else{
				$next.css('background-color','rgba(0,0,0,0.5)');
			}
		});
	}	
}

function logout() {
	if(confirm("是否确认注销当前用户？")) {
		location.href = "/rest/user/logout";
	}
}

$(function() {
	/** 搜索 */
	$('.search-bar').on('click', 'button', function(){
		if ($(this).prev().val() == null || $(this).prev().val() == '') return;
		localStorage.setItem('searchTitle',$(this).prev().val());
		location.href=location.href.substr(0, location.href.lastIndexOf('/') + 1) + 'search_list';
	});

	/** 跳页 */
	$('.page-btn').on('click', 'li', function() {
		if (this.title == 'jump') return;
		currentPage = $(this).val();
		getResouceList();
	});

	$('.page-btn').on('click', 'a', function() {
		if (this.id != 'jump-btn' || $('#pageNum').val() > totalPage || $('#pageNum').val() < 1) return;
		currentPage = $('#pageNum').val();
		getResouceList();
	});
});