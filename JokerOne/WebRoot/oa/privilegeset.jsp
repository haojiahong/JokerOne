<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<html>
<head>
    <title>角色设置权限</title>
    <%@ include file="/jsp/public/commons.jspf" %>
</head>
<body>
<s:form action="roleAction">

	<s:hidden name="roleId" id="roleId" type="hidden" upload="true"/>
	<fieldset>
		<legend>角色授权</legend>
		<ul id="tree"></ul>
	</fieldset>
</s:form>
<script type="text/javascript">
	var submitForm = function($dialog, $grid, $pjq) {
		var nodes = $('#tree').tree('getChecked', [ 'checked', 'indeterminate' ]);
		var privIds = [];
		for (var i = 0; i < nodes.length; i++) {
			privIds.push(nodes[i].id);
		}
		$.post("roleAction!grant.do", {
			roleId : $('#roleId').val(),
			privIds : privIds.join(',')
		}, function(result) {
			if (result=='操作成功') {
				$dialog.dialog('destroy');
			} else {
				$pjq.messager.alert('提示', result, 'error');
			}
			$pjq.messager.alert('提示', '授权成功！', 'info');
		}, 'json');
	};
	
	$(function() {
		parent.$.messager.progress({
			text : '数据加载中....'
		});
		$('#tree').tree({
			url : 'ezPrivTreeGridAction.do',
			checkbox : true,
			onLoadSuccess : function(node, data) {
				$.post('roleAction!getResources.do', {
					roleId : $('#roleId').val()
				}, function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
							var node = $('#tree').tree('find', result[i].privilegeId);
							if (node) {
								var isLeaf = $('#tree').tree('isLeaf', node.target);
								if (isLeaf) {
									$('#tree').tree('check', node.target);
								}
							}
						}
					}
					parent.$.messager.progress('close');
				}, 'json');
			}
		});
	});
</script>
</body>
</html>
