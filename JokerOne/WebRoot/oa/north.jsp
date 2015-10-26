<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
<title>主页</title>
<%@ include file="/jsp/public/commons.jspf"%>
</head>
<body>
<script type="text/javascript">
$(function(){
	$('#loginDialog').show().dialog({
		modal : true,
		closable : false,
		iconCls : 'ext-icon-lock_open',
		buttons : [ {
			id : 'loginBtn',
			text : '登录',
			handler : function() {
				loginFun();
			}
		} ],
		onOpen : function() {
			$('#loginDialog form :input[name="data.pwd"]').val('');
			$('form :input').keyup(function(event) {
				if (event.keyCode == 13) {
					loginFun();
				}
			});
		}
	}).dialog('close');
	
	var loginFun = function() {
		if ($('#loginDialog form').form('validate')) {
			$('#loginBtn').linkbutton('disable');
			$.post('${pageContext.request.contextPath}/oa/loginAction!loginAgain.do', $('#loginDialog form').serialize(), function(result) {
				if (result) {
					$('#loginDialog').dialog('close');
				} else {
					$.messager.alert('提示', '密码错误！', 'error', function() {
						$('#loginDialog form :input:eq(1)').focus();
					});
				}
				$('#loginBtn').linkbutton('enable');
			}, 'json');
		}
	};
})

var logoutFun = function() {
	$.post('${pageContext.request.contextPath}/oa/loginAction!logout.do', function(result) {
		location.replace('${pageContext.request.contextPath}/index.jsp');
	}, 'json');
};

var lockWindowFun = function() {
	$.post('${pageContext.request.contextPath}/oa/loginAction!logout.do', function(result) {
		$('#loginDialog').dialog('open');
	}, 'json');
};
</script>
<div id="sessionInfoDiv" style="position: absolute; right: 10px; top: 5px;">
	欢迎 ${user.userName} ！
</div>
<div style="position: absolute; right: 0px; bottom: 0px;">
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#north_pfMenu',iconCls:'ext-icon-rainbow'">更换皮肤</a> 
<%--	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#north_kzmbMenu',iconCls:'ext-icon-cog'">控制面板</a> --%>
	<a href="javascript:void(0);" class="easyui-menubutton" data-options="menu:'#north_zxMenu',iconCls:'ext-icon-disconnect'">注销</a>
</div>
<div id="north_pfMenu" style="width: 120px; display: none;">
	<div onclick="hjh.changeTheme('default');" title="default">default</div>
	<div onclick="hjh.changeTheme('gray');" title="gray">gray</div>
	<div class="menu-sep"></div>
	<div onclick="hjh.changeTheme('metro');" title="metro">metro</div>
	<div onclick="hjh.changeTheme('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="hjh.changeTheme('black');" title="black">black</div>
	
</div>
<div id="north_kzmbMenu" style="width: 100px; display: none;">
	<div data-options="iconCls:'ext-icon-user_edit'" onclick="$('#passwordDialog').dialog('open');">修改密码</div>
	<div class="menu-sep"></div>
	<div data-options="iconCls:'ext-icon-user'" onclick="showMyInfoFun();">我的信息</div>
</div>
<div id="north_zxMenu" style="width: 100px; display: none;">
<%--	<div data-options="iconCls:'ext-icon-lock'" onclick="lockWindowFun();">锁定窗口</div>--%>
<%--	<div class="menu-sep"></div>--%>
	<div data-options="iconCls:'ext-icon-door_out'" onclick="logoutFun();">退出系统</div>
</div>

<div id="loginDialog" title="解锁登录" style="display: none;">
		<form method="post" class="form" onsubmit="return false;">
			<table class="table">
				<tr>
					<th width="80">登录名</th>
					<td>${user.userName}<input name="loginName" readonly="readonly" type="hidden" value="${user.loginName}" /></td>
				</tr>
				<tr>
					<th>密码</th>
					<td><input name="password" type="password" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</form>
</div>
</body>
</html>