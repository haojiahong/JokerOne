<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
%>

<%
Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
Cookie[] cookies = request.getCookies();
if (null != cookies) {
	for (Cookie cookie : cookies) {
		cookieMap.put(cookie.getName(), cookie);
	}
}
String easyuiTheme = "bootstrap";//指定如果用户未选择样式，那么初始化一个默认样式
if (cookieMap.containsKey("easyuiTheme")) {
	Cookie cookie = (Cookie) cookieMap.get("easyuiTheme");
	easyuiTheme = cookie.getValue();
}
%>
<html>
<head>
<title>主页</title>
<%@ include file="/jsp/public/commons.jspf"%>
<%-- 引入EasyUI 皮肤--%>
<link id="easyuiTheme" rel="stylesheet" href="<%=contextPath%>/js/jquery-easyui-1.4.1/themes/<%=easyuiTheme%>/easyui.css" type="text/css">
</head>
<body>

<div class="easyui-layout" style="width:100%;height:100%;">
 	<div data-options="region:'north',border:false,href:'<%=contextPath%>/oa/north.jsp'" style="height:50px"> </div> 

	<div data-options="region:'west',collapsed:true" title="功能导航" style="width:180px;">
		<div id='menu' class="easyui-accordion" data-options="fit:true,border:false">
			<div title="系统菜单" data-options="iconCls:'icon-save'">
				<div class="easyui-tree" data-options="url:'${pageContext.request.contextPath}/oa/loginAction!getMenuTree.do',
				onClick:function(node){if(node.attributes.url){addTab({title:node.text,href:node.attributes.url,closable:true})}}"></div>
			</div>
		</div>
	</div>
	
	<div data-options="region:'center',border:false">
		<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false" >
			<div title="关于项目" data-options="iconCls:'ext-icon-heart'">
				<iframe src="<%=contextPath%>/oa/welcome.jsp" allowTransparency="true" style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
			</div>
		</div>
	</div>

</div>

	<div id="tabsMenu" class="easyui-menu" style="width:120px;">
		<div name="close">关闭</div>
		<div name="Other">关闭其他</div>
		<div name="All">关闭所有</div>
	</div>
</body>
<script type="text/javascript">

//在右边center区域打开菜单，新增tab
function addTab(opts){
	var t = $("#tabs");
	if(t.tabs("exists",opts.title)){
		t.tabs("select",opts.title);
	}else{
		t.tabs("add",opts)
	}
}

$(function() {
//绑定tabs的右键菜单
$("#tabs").tabs({
	onContextMenu : function(e, title) {
		e.preventDefault();
		$('#tabsMenu').menu('show', {
			left : e.pageX,
			top : e.pageY
		}).data("tabTitle", title);
	}
});

//实例化menu的onClick事件
$("#tabsMenu").menu({
	onClick : function(item) {
		CloseTab(this, item.name);
	}
});

//几个关闭事件的实现
function CloseTab(menu, type) {
	var curTabTitle = $(menu).data("tabTitle");
	var tabs = $("#tabs");

	if (type === "close") {
		tabs.tabs("close", curTabTitle);
		return;
	}

	var allTabs = tabs.tabs("tabs");
	var closeTabsTitle = [];

	$.each(allTabs, function() {
		var opt = $(this).panel("options");
		if (opt.closable && opt.title != curTabTitle
				&& type === "Other") {
			closeTabsTitle.push(opt.title);
		} else if (opt.closable && type === "All") {
			closeTabsTitle.push(opt.title);
		}
	});

	for ( var i = 0; i < closeTabsTitle.length; i++) {
		tabs.tabs("close", closeTabsTitle[i]);
	}
}
});


	
</script>
</html>
