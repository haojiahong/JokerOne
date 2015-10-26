<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/jsp/public/commons.jspf" %>	
<html>
<head>
<style type="text/css">
#form th {
	text-align: right
}

.formWidth {
	width: 180px
}
</style>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false"
		style="overflow: hidden;">
		<form id="form" method="post" action="roleAction!save.do">
			<table>
				<tr>
					<th>岗位名称：</th>
					<td><input id="roleName" name="roleName" type="text"
						value="${role.roleName }" class="easyui-validatebox textbox formWidth"
						data-options="required:true,validType:'length[0,10]'" /></td>
					<th>岗位描述：</th>
					<td><input id="roleDescription" name="roleDescription" type="text"
						value="${role.roleDescription }" class="easyui-validatebox textbox formWidth"
						data-options="required:true,validType:'length[0,20]'" /></td>
					<td>
					<input type="hidden" id="roleId"  name="roleId" value="${role.roleId}">
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$('#form').form(ez_formSub);
	});
	
	var submitForm = function($dialog, $grid, $pjq) {
		if ($('form').form('validate')) {
			var url = "roleAction!save.do";
			$.post(url, hjh.serializeObject($('form')), function(result) {
				if (result) {
					$grid.datagrid('load');
					$dialog.dialog('destroy');
					$pjq.messager.alert('提示', result,'info');
				} else {
					$pjq.messager.alert('提示', result, 'error');
				}
			}, 'json');
		}
	};
</script>
</body>
</html>
