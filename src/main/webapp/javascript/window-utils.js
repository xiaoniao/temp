var OperatWindows = function() {
	
	var ADD_WINDOW_DOMID = "";
	var ADD_URL = "";
	var ADD_WINDOW_TITLE = "";
	var ADD_WINDOW_WIDTH = "";
	var ADD_WINDOW_HEIGHT = "";
	var ADD_WINDOW_FUNC = "";
	var ADD_WINDOW_TYPE = "";
	
	/**
	 * 打开自己窗口 根据type参数区分
	 */
	var openWindow = function(title, url, width, height, func, type) {
		
		if (title == null || title == '')
			title = false;
		
		if (url == null || url == '')
			url = "404.html";
		
		if (width == null || width == '' || width == 0)
			width = 800;
		
		if (height == null || height == '' || height == 0)
			height = ($(window).height() - 50);
		
		if (func == null)
			func = 'ListPage.referesh();';
		
		if (type == 'confirm') {
			
			layer.confirm(title, function() {
				eval(func);
			});
		
		} else if (type == 'alert') {
			
		} else {
			var params = {};
			params.type = 2;
			
			if (type == 'local') {
				params.area = [width + 'px', height +'px'];
				params.fix = false;
				params.maxmin = true;
				params.shade = 0.4;
			}
			
			params.title = title;
			params.content = url;
			params.end = function() {
				eval(func);
			};
			
			if (type == 'local')         // 打开局部窗口
				layer.open(params);
			else if (type == 'full')     // 打开全屏窗口
				layer.full(layer.open(params));
			else if (type == 'iframe')   // 用Iframe打开新页
				openIframe(title, url);
		}
	}
	
	var openIframe = function(title, url) {
		
		var bStop = false;
		var bStopIndex = 0;
		var topWindow = $(window.parent.document);
		var show_navLi = topWindow.find("#min_title_list li");
		
		show_navLi.each(function() {
			if($(this).find('span').attr("data-href") == url) {
				bStop = true;
				bStopIndex = show_navLi.index($(this));
				return false;
			}
		});
		
		if(!bStop) {
			creatIframe(url, title);
			var topWindow = $(window.parent.document);
			var show_nav = topWindow.find("#min_title_list");
			var aLi = show_nav.find("li");
		} else {
			show_navLi.removeClass("active").eq(bStopIndex).addClass("active");
			var iframe_box = topWindow.find("#iframe_box");
			iframe_box.find(".show_iframe").hide().eq(bStopIndex).show().find("iframe").attr("src", url);
		}
	}
	
	var createIframe = function(url, title) {
		var topWindow = $(window.parent.document);
		var show_nav = topWindow.find('#min_title_list');
		show_nav.find('li').removeClass("active");
		var iframe_box = topWindow.find('#iframe_box');
		show_nav.append('<li class="active"><span data-href="' + url + '">' + title + '</span><i></i><em></em></li>');
		
		var taballwidth = 0,
			$tabNav = topWindow.find(".acrossTab"),
			$tabNavWp = topWindow.find(".Hui-tabNav-wp"),
			$tabNavitem = topWindow.find(".acrossTab li"),
			$tabNavmore = topWindow.find(".Hui-tabNav-more");
			
		if (!$tabNav[0])
			return;
			
		$tabNavitem.each(function(index, element) {
	        taballwidth += Number(parseFloat($(this).width()+60))
	    });
	    
		$tabNav.width(taballwidth + 25);
		
		var w = $tabNavWp.width();
		if(taballwidth + 25 > w) {
			$tabNavmore.show()
		} else {
			$tabNavmore.hide();
			$tabNav.css({left:0})
		}
		var iframeBox = iframe_box.find('.show_iframe');
		iframeBox.hide();
		iframe_box.append('<div class="show_iframe"><div class="loading"></div><iframe frameborder="0" src='+href+'></iframe></div>');
		var showBox = iframe_box.find('.show_iframe:visible');
		showBox.find('iframe').load(function(){
			showBox.find('.loading').hide();
		});
	}
	
	var bindAddWindow = function() {
		$("#" + ADD_WINDOW_DOMID).click(function() {
			openWindow(ADD_WINDOW_TITLE, ADD_URL, ADD_WINDOW_WIDTH, ADD_WINDOW_HEIGHT, ADD_WINDOW_FUNC, ADD_WINDOW_TYPE);
		});
	}
	
	var bindOperatWindow = function(operatWindows) {
		
		for (var i = 0; i < operatWindows.length; i ++) {
			
			var window = operatWindows[i];
			$("a[name='" + window.domName + "']").attr("_title", window.title);
			$("a[name='" + window.domName + "']").attr("_href", window.url);
			$("a[name='" + window.domName + "']").attr("_func", window.func);
			$("a[name='" + window.domName + "']").attr("_type", window.type);
			
			if (window.width != null &&  window.height != null) {
				$("a[name='" + window.domName + "']").attr("_width", window.width);
				$("a[name='" + window.domName + "']").attr("_height", window.height);
			}
			
			$("a[name='" + window.domName + "']").click(function() {
				
				var dataId = $(this).attr("dataId");
				var dataKey = $(this).attr("dataKey");
				
				var title = $(this).attr("_title");
				var url = $(this).attr("_href");
				
				var func = $(this).attr("_func");
				var replaceStr = "operatId";
				if (func != null && func != "")
					func = func.replace(new RegExp(replaceStr,'gm'), dataId);
				
				var type = $(this).attr("_type");
				var width   = $(this).attr("_width");
				var height  = $(this).attr("_height");
				
				if (dataId != null && dataId != "")
					url = url + "?" + dataKey + "=" + dataId;
				
				openWindow(title, url, width, height, func, type);
			})
		}
	}
	
	return {
		bindAddWindow : function (domId, url, title, width, height, func, type) {
			ADD_WINDOW_DOMID = domId;
			ADD_URL = url;
			ADD_WINDOW_TITLE = title;
			ADD_WINDOW_WIDTH = width;
			ADD_WINDOW_HEIGHT = height;
			ADD_WINDOW_FUNC = func;
			ADD_WINDOW_TYPE = type;
			
			bindAddWindow();
		},
		
		bindOperatWindow : function(operatWindows) {
			bindOperatWindow(operatWindows);
		}
	}
}();