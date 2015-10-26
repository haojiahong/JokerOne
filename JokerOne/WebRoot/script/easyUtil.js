var hjh = hjh || {};

/**
 表单提交,刷新表格,执行reload方法
 */
var ez_formSub = {
	onSubmit : function() {
		var isValid = $(this).form('validate');
		if (isValid) {
			$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
		}
		return isValid;
	},
	success : function(data) {
		reload();
		$.messager.progress('close');
		$.messager.alert('提醒', data, 'info');
		if ($.modalDialog.handler) {
			$.modalDialog.handler.dialog('close');

		}
	}
};
/**
 * 对easyUI对话框的扩展。
 */
$.modalDialog = function(options) {
	if (!$.modalDialog.arr) {
		$.modalDialog.arr = [];
	}
	var opts = $
			.extend(
					{
						title : '',
						modal : true,
						cache : false,
						onClose : function() {
							$.modalDialog.arr.pop();
							if ($.modalDialog.arr.length > 0) {
								$.modalDialog.handler = $.modalDialog.arr[$.modalDialog.arr.length - 1];// 2.关闭窗口,切换到上一个
							}
							$(this).dialog('destroy');
						},
						onOpen : function() {
							// parent.$.messager.progress({
							// title : '提示',
							// text : '数据处理中，请稍后....'
							// });
						}
					}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	var myHandler = $('<div id="myDialog"/>').dialog(opts);
	$.modalDialog.arr.push(myHandler);
	$.modalDialog.handler = myHandler;// 1.最后一次窗口的handler
	return myHandler;

};

function formatDateToD(val, row) {// 格式到天
	return formatDate(val, row, "yyyy-MM-dd");
}

function formatDate(val, row, regx) {// 格式时间
	if (val == null || val == "") {
		return "";
	}
	var myDate = getDate(val);

	return myDate.formate(regx);
}

function getDate(strDate) {
	var st = strDate;
	var a = st.split("T");
	var b = a[0].split("-");
	var c = a[1].split(":");
	var date = new Date(b[0], b[1], b[2], c[0], c[1], c[2]);
	return date;
}

Date.prototype.formate = function(fmt) {
	var o = {
		"M+" : this.getMonth(), // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, // 小时
		"H+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	// 毫秒
	};
	var week = {
		"0" : "\u65e5",
		"1" : "\u4e00",
		"2" : "\u4e8c",
		"3" : "\u4e09",
		"4" : "\u56db",
		"5" : "\u4e94",
		"6" : "\u516d"
	};
	if (/(y+)/.test(fmt)) {
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}
	if (/(E+)/.test(fmt)) {
		fmt = fmt
				.replace(
						RegExp.$1,
						((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "\u661f\u671f"
								: "\u5468")
								: "")
								+ week[this.getDay() + ""]);
	}
	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(fmt)) {
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
		}
	}
	return fmt;
}

/**
 * 防止窗口超出浏览器边界
 * 
 * @param left
 * @param top
 * @returns
 */

var easyuiPanelOnMove = function(left, top) {
	var l = left;
	var t = top;
	if (l < 1) {
		l = 1;
	}
	if (t < 1) {
		t = 1;
	}
	var width = parseInt($(this).parent().css('width')) + 14;
	var height = parseInt($(this).parent().css('height')) + 14;
	var right = l + width;
	var buttom = t + height;
	var browserWidth = $(window).width();
	var browserHeight = $(window).height();
	if (right > browserWidth) {
		l = browserWidth - width;
	}
	if (buttom > browserHeight) {
		t = browserHeight - height;
	}
	$(this).parent().css({/* 修正面板位置 */
		left : l,
		top : t
	});
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * 更换easyUI的皮肤主题
 * @param themeName
 */

hjh.changeTheme = function(themeName) {
	var $easyuiTheme = $('#easyuiTheme');
	var url = $easyuiTheme.attr('href');
	var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	$easyuiTheme.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			try {
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			} catch (e) {
				try {
					ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
				} catch (e) {
				}
			}
		}
	}

	hjh.cookie('easyuiTheme', themeName, {
		expires : 7
	});
};

/**
 * Create a cookie with the given key and value and other optional parameters.
 * @param key
 * @param value
 * @param options
 * @returns
 */
hjh.cookie = function(key, value, options) {
	if (arguments.length > 1 && (value === null || typeof value !== "object")) {
		options = $.extend({}, options);
		if (value === null) {
			options.expires = -1;
		}
		if (typeof options.expires === 'number') {
			var days = options.expires, t = options.expires = new Date();
			t.setDate(t.getDate() + days);
		}
		return (document.cookie = [ encodeURIComponent(key), '=', options.raw ? String(value) : encodeURIComponent(String(value)), options.expires ? '; expires=' + options.expires.toUTCString() : '', options.path ? '; path=' + options.path : '', options.domain ? '; domain=' + options.domain : '', options.secure ? '; secure' : '' ].join(''));
	}
	options = value || {};
	var result, decode = options.raw ? function(s) {
		return s;
	} : decodeURIComponent;
	return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};
/**
 * 增加formatString功能
 * 
 * @author haojiahong
 * 
 * @example sy.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * 
 * @returns 格式化后的字符串
 */
hjh.formatString = function(str) {
	for (var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};
/**
 * 创建一个模式化的dialog(iframe方式)
 * 
 * @author haojiahong
 * 
 * @requires jQuery,EasyUI
 * 
 */
hjh.modalDialog = function(options) {
	var opts = $.extend({
		title : '&nbsp;',
		width : 640,
		height : 480,
		modal : true,
		onClose : function() {
			$(this).dialog('destroy');
		}
	}, options);
	opts.modal = true;// 强制此dialog为模式化，无视传递过来的modal参数
	if (options.url) {
		opts.content = '<iframe id="" src="' + options.url + '" allowTransparency="true" scrolling="auto" width="100%" height="98%" frameBorder="0" name=""></iframe>';
	}
	return $('<div/>').dialog(opts);
};

/**
 * 将form表单元素的值序列化成对象
 * 
 * @example hjh.serializeObject($('#formId'))
 * 
 * @author haojiahong
 * 
 * @requires jQuery
 * 
 * @returns object
 */
hjh.serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index) {
		if (this['value'] != undefined && this['value'].length > 0) {// 如果表单项的值非空，才进行序列化操作
			if (o[this['name']]) {
				o[this['name']] = o[this['name']] + "," + this['value'];
			} else {
				o[this['name']] = this['value'];
			}
		}
	});
	return o;
};
