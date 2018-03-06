var ListPage = function() {

	var HAS_DATA_DIV;
	var NO_DATA_DIV;
	var TABLE_ID;         // 列表数据 Table ID
	var LAYPAGE_DIV_ID;   // 分页 Div ID
	var ROW_NUM_ID;       // 总记录数显示ID
	
	var OPERAT_HTML;      // 列表最后一列的操作项
	var OPERAT_SHOW_KEY;  // 显示条件
	var OPERAT_SHOW_VALS; // 显示条件值
	
	var SEARCH_INPUT;     // 搜索input列表，如：['orderCard', 'subCard']
	var SEARCH_BTN_ID;    // 搜索按钮
	
	var QUERY_DATA_URL;   // 数据接口URL
	var QUERY_ROW_URL;    // 查询总数URL
	var QUERY_PARAMS;     // 数据接口请求参数
	var CURRENT_PAGE;     // 当前页
	var PAGE_SIZE;        // 每页显示数据
	var PAGE_DATA;        // 分页数据
	var DATA_LIST_KEY;    // 数据展示Key
	var ROW_NUM;          // 数据总数
	var PAGE_NUM;         // 总页数
	var BIND_FUNC;        // 视图渲染完成后需绑定的事件
	
	var OPERAT_WINDOWS;   // 操作窗口
	
	/**
	 * 页面重置，将当前页、查询条件的input重置成初始值
	 */
	var pageReset = function() {
		
		CURRENT_PAGE = 1;
		
		if (SEARCH_INPUT == null || SEARCH_INPUT.length <= 0) 
			return;
			
		for(var i = 0; i < SEARCH_INPUT.length; i ++) {
			var inputId = SEARCH_INPUT[i];
			$("#" + inputId).val("");
		}
	}
	
	/**
	 * 创建查询参数，通过读取 input[name] 来获取条件查询参数
	 */
	var createQueryParams = function() {
		QUERY_PARAMS = {};
		var model_search = "";
		
		if (SEARCH_INPUT != null && SEARCH_INPUT.length > 0) {
			for(var i = 0; i < SEARCH_INPUT.length; i ++) {
				var keyName = SEARCH_INPUT[i];
				var param = $("#" + keyName).val();
				if (param != null && param != '') {
					if (model_search != null && model_search != '') {
						model_search += ",";
					} else {
						model_search += "{";
					}
					model_search += "\"" + keyName + "\":\"" + param + "\"";
				}
			}
		}
		
		if (model_search != null && model_search != '') {
			model_search += "}";
			QUERY_PARAMS = JSON.parse(model_search);
		}
		
		QUERY_PARAMS.currentPage = CURRENT_PAGE;
		QUERY_PARAMS.pageSize    = PAGE_SIZE;
	};
	
	/**
	 * AJAX 加载列表一页数据
	 */
	var loadData = function() {
    	$.ajax({  
            type : "POST",  
            url : QUERY_DATA_URL,
            data : QUERY_PARAMS,
            async : false,     //false设置为同步，只有当这个ajax执行完成后才可以执行别的操作
            success : function(result) {  
            	
            	PAGE_DATA = result.data;
            	
            	if (PAGE_SIZE == null || PAGE_SIZE <= 0) {
            		if (result.statusCode == 200)
            			ROW_NUM = PAGE_DATA.length;
            		else 
            			ROW_NUM = 0;
            	}
            }
    	});
    };
    
    /**
     * AJAX 获取分页数据
     */
    var loadPages = function() {
    	$.ajax({  
            type : "POST",  
            url : QUERY_ROW_URL,
            data : QUERY_PARAMS,
            async : false,  
            success : function(result) {  
            	ROW_NUM = result.data.allRow;
				PAGE_NUM = result.data.totalPage;    	
            }
    	});
    }
    
    /**
     * 绑定分页显示、及分页出发后得回调
     */
    var bindLaypage = function() {
    	// 创建查询参数
    	createQueryParams();      
		
    	// 获取分页数据：总页数、总数据数
    	loadPages();
    	
    	// 绑定 laypage
    	laypage({
    		
			cont : $("#" + LAYPAGE_DIV_ID),   // 分页容器，支持id名、原生dom对象，jquery对象
			
			pages : PAGE_NUM,         // 总页数
			
			curr : CURRENT_PAGE,      // 当前页
			
			jump : function(obj) {    // 触发分页后的回调
				// 重置当前页
				CURRENT_PAGE = obj.curr;
				// 重置查询参数
				createQueryParams();
				// 加载读取数据
				loadData();
				// 渲染数据视图
				viewRender();
			}
		});
    }
    
    /**
     * 绑定查询按钮点击事件
     */
    var bindSearchBtn = function () {
    	if (searchInput == null || searchInput.length <= 0)
    		return;
    	
    	$("#" + SEARCH_BTN_ID).click(function () { 
    		CURRENT_PAGE = 1;   // 重置当前页
    		bindLaypage();      // 重新绑定分页
		});
    }
    
    /**
     * 渲染页面视图
     */
	var viewRender = function() {
		
		// 清除Table TR
		$("#" + TABLE_ID).find("tr").each(function(index) { if (index != 0) $(this).remove(); });  // 移除Table数据
		// 修改列表数量显示
		$("#" + ROW_NUM_ID).text(ROW_NUM);
		
		if (ROW_NUM <= 0) { // 如果当前无数据
			$("#" + LAYPAGE_DIV_ID).hide(); // 隐藏分页 Div
			$("#" + HAS_DATA_DIV).hide();   // 隐藏数据 Div
			$("#" + NO_DATA_DIV).show();    // 显示无数据Div
			return;
		} else {
			$("#" + LAYPAGE_DIV_ID).show(); 
			$("#" + HAS_DATA_DIV).show();
			$("#" + NO_DATA_DIV).hide();
		}
		
		if (PAGE_DATA != null && PAGE_DATA.length > 0) {
			
			$("#" + TABLE_ID).find("tr").each(function(index) { if (index != 0) $(this).remove();});
			
			for(var i = 0; i < PAGE_DATA.length; i ++) {
				
				var data = PAGE_DATA[i];
				var tr_html = "<tr class=\"text-c\">";
				
				for(var j = 0; j < DATA_LIST_KEY.length; j ++) {
					
					var data_key = DATA_LIST_KEY[j].key;
					var columnstype = DATA_LIST_KEY[j].type;
					
					var td_html = "";
					
					if (columnstype == 'text') {
						
						/**
						 * 文本
						 */
						var columnsdata = data[data_key];
						columnsdata = (columnsdata != null && columnsdata != '') ? columnsdata : '-';

						if (DATA_LIST_KEY[j].width != null) 
							td_html = "<td width='" + DATA_LIST_KEY[j].width + "px'>" + columnsdata + "</td>";
						else
							td_html = "<td>" + columnsdata + "</td>";

					} else if (columnstype == 'image') {
						
						/**
						 * 图片
						 */
						var columnsdata = data[data_key];
						td_html = "<td><img src='" + columnsdata + "' width='" + DATA_LIST_KEY[j].width + "px' height='" + DATA_LIST_KEY[j].height + "px'></td>";

					} else if (columnstype == 'imageLinkUrl') {
						
						/**
						 * 图片
						 */
						var columnsdata = data[data_key];
						var replaceStr = "operatId";
						var linkUrl = DATA_LIST_KEY[j].linkUrl.replace(new RegExp(replaceStr,'gm'), data.id);
						td_html = "<td><a href='"+linkUrl+"'><img src='" + columnsdata + "' width='" + DATA_LIST_KEY[j].width + "px' height='" + DATA_LIST_KEY[j].height + "px'></a></td>";

					} else if (columnstype == 'label') {

						/**
						 * 标签
						 */
						var columnsdata = data[data_key];
						var valLabels = DATA_LIST_KEY[j].valLabels;
						
						for (var k = 0; k < valLabels.length; k ++) {
							if (columnsdata == valLabels[k].val) {
								td_html = "<td><span class='label " + valLabels[k].label + "'>" + valLabels[k].text + "</span></td>";
								break;
							}
						}

					} else if (columnstype == 'linked') { 

						/**
						 * 链接
						 */
						var columnsdata = data[data_key];
						var replaceStr = "operatId";
						var linkUrl = DATA_LIST_KEY[j].linkUrl.replace(new RegExp(replaceStr,'gm'), data.id);
						td_html = "<td><a href='" + linkUrl + "'>" + columnsdata + "</a></td>";

					} else if (columnstype == 'hidden') { 

						/**
						 * 隐藏域 Hidden
						 */
						var columnsdata = data[data_key];
						td_html = "<input type='hidden' name='" + data_key + "' value='" + columnsdata+ "'>"; 

					} else if (columnstype == 'select') {
						
						/**
						 * SELECT 下拉列表
						 */
						var columnsdata = data[data_key];
						td_html += "<td><select ";

						var dom_name = DATA_LIST_KEY[j].name;
						if (dom_name != null && dom_name != '')
							td_html += "name='" + dom_name + "'";

						var disabled = false;
						var disabledKey = DATA_LIST_KEY[j].disabledKey;
						var disabledVals = DATA_LIST_KEY[j].disabledVals;
						for (var k = 0; k < disabledVals.length; k ++) {
							if (data[disabledKey] == disabledVals[k]) {
								disabled = true;
								break;
							}
						}
						if (disabled) 
							td_html += " disabled='disabled'";

						td_html += ">";

						var options = DATA_LIST_KEY[j].options;
						for (var k = 0; k < options.length; k ++) {

							var checked = false;
							if (columnsdata == options[k].value) 
								checked = true;

							td_html += "<option value='" + options[k].value + "'";
							if (checked)
								td_html += " selected='selected'";
							td_html += ">" + options[k].text + "</option>";
						}

						td_html += "</select></td>";
					} else if (columnstype == 'input-text') {
						
						td_html += "<td>";
						
						/**
						 * input-text
						 */
						for (var k = 0; k < data_key.length; k ++) {

							if (k > 0)
								td_html += "<br/>";

							var key = data_key[k];
							
							if (DATA_LIST_KEY[j].placeholder != null){
								
								var placeholderVals = DATA_LIST_KEY[j].placeholder[k];

								for(var p = 0; p < placeholderVals.length; p++){
									td_html += "<input type='text' placeholder='" + placeholderVals[p] + "' name='" + DATA_LIST_KEY[j].name[k] + "' style='" + DATA_LIST_KEY[j].style[k] + "' class='" + DATA_LIST_KEY[j].class[k] + "'";
								}
								
							} else {
								td_html += "<input type='text' name='" + DATA_LIST_KEY[j].name[k] + "' style='" + DATA_LIST_KEY[j].style[k] + "' class='" + DATA_LIST_KEY[j].class[k] + "'";
							}
							
							var disabled = false;
							var disabledKey = DATA_LIST_KEY[j].disabledKey;
							var disabledVals = DATA_LIST_KEY[j].disabledVals[k];
							
							for (var l = 0; l < disabledVals.length; l ++) {
								if (data[disabledKey] == disabledVals[l]) {
									disabled = true;
									break;
								}
							}

							if (disabled)
								td_html += " disabled='disabled'";
							
							if (data[key] != null && data[key] != '')
								td_html += " value='" + data[key] + "'";

							td_html += "/>";
						}
						td_html += "</td>";
					} else if (columnstype == 'input-time') {
						
						/**
						 * JS时间控件
						 */
						td_html += "<td>";
						
						for (var k = 0; k < data_key.length; k ++) {
							if (k > 0)
								td_html += " / ";

							var key = data_key[k];

							td_html += "<input type='text' id='" + key + i + "' value='" + data[key] + "' name='" + DATA_LIST_KEY[j].name[k]
								+ "' style='" + DATA_LIST_KEY[j].style[k] + "' class='" + DATA_LIST_KEY[j].class[k] + "'";
							
							if (k == 0)
								td_html += " onfocus=\"WdatePicker({maxDate:'#F{$dp.$D(\\'" + data_key[1] + i + "\\') || \\'%y-%M-%d\\'}'})\"";
							else 	
								td_html += " onfocus=\"WdatePicker({minDate:'#F{$dp.$D(\\'" + data_key[0] + i + "\\') || \\'%y-%M-%d\\'}'})\"";

							var disabled = false;
							var disabledKey = DATA_LIST_KEY[j].disabledKey;
							var disabledVals = DATA_LIST_KEY[j].disabledVals[k];
							
							for (var l = 0; l < disabledVals.length; l ++) {
								if (data[disabledKey] == disabledVals[l]) {
									disabled = true;
									break;
								}
							}

							if (disabled)
								td_html += " disabled='disabled'";

							td_html += ">";
						}
						
						td_html += "</td>";
					}
					
					tr_html += td_html;
				}
				
				var operat_html = "<td>";
				
				if (OPERAT_SHOW_KEY != null && OPERAT_SHOW_KEY != '') {
					var operat_show = false;
					for (var k = 0; k < OPERAT_SHOW_VALS.length; k ++) {
						if (data[OPERAT_SHOW_KEY] == OPERAT_SHOW_VALS[k]) {
							operat_show = true;
							break;
						}
					}

					if (operat_show) {
						var replaceStr = "operatId";
						operat_html += OPERAT_HTML.replace(new RegExp(replaceStr,'gm'), data.id); //替换成data.id
					}
					
				} else {
					var replaceStr = "operatId";
					operat_html += OPERAT_HTML.replace(new RegExp(replaceStr,'gm'), data.id); //替换成data.id
				}
				operat_html += "</td>";
				
				if (operat_html != '<td></td>') {
					tr_html += operat_html;
				}
				tr_html += "</tr>";
				$("#" + TABLE_ID).append(tr_html);
			}
			
			if (BIND_FUNC != null && BIND_FUNC != "")
				eval(BIND_FUNC);

			if (OPERAT_WINDOWS != null && OPERAT_WINDOWS.length > 0)
				OperatWindows.bindOperatWindow(OPERAT_WINDOWS);
		}
	}
	
	return {
		init : function(queryRowUrl, queryDataUrl, dataListKey, pagesize, searchInput, operatHtml, operatWindows, bindFunc, operateKey, operatVals) {
			
			/**
			 * 页面ID、数据访问URL 绑定
			 */
			HAS_DATA_DIV = "has-data-div";
			NO_DATA_DIV = "no-data-div";
			TABLE_ID = "data-list-table";
			LAYPAGE_DIV_ID = "laypage-div";
			ROW_NUM_ID = "row-num-strong";
			SEARCH_BTN_ID = "search-btn";
			
			QUERY_ROW_URL = queryRowUrl;
			QUERY_DATA_URL = queryDataUrl;
			DATA_LIST_KEY = dataListKey;
			SEARCH_INPUT = searchInput;
			OPERAT_HTML = operatHtml;
			OPERAT_WINDOWS = operatWindows;
			BIND_FUNC = bindFunc;
			OPERAT_SHOW_KEY = operateKey;
			OPERAT_SHOW_VALS = operatVals;
			
			/**
			 * 初始化当前页码和每页查询大小
			 */
			CURRENT_PAGE = 1;
			PAGE_SIZE = pagesize;
			
			if (PAGE_SIZE != null && PAGE_SIZE > 0) {
				/**
				 * 通过 laypage 实现分页数据查询
				 */
				bindLaypage();
				/**
				 * 初始化条件查询
				 */
				bindSearchBtn();
			} else {
				/**
				 * 读取列表数据
				 */
				loadData();
				/**
				 * 渲染数据视图
				 */
				viewRender();
			}
		},
		
		referesh : function() {
			
			if (PAGE_SIZE != null && PAGE_SIZE > 0) {
				/**
				 * 通过 laypage 重新绑定分页数据查询
				 */
				bindLaypage();
			} else {
				/**
				 * 读取列表数据
				 */
				loadData();
				/**
				 * 渲染数据视图
				 */
				viewRender();
			}
		}
	}
}();