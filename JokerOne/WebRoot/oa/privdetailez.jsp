<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
<title>详细设置</title>
<style>
#form th {
	text-align: right
}

.formWidth {
	width: 180px
}
</style>

</head>
<body>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
		<div title="基本信息" style="padding: 20px;">
			<div class="easyui-layout" data-options="border:false,fit:true"
				style="padding: 15px">
				<div data-options="region:'center',border:false">
					<form id="form" method="post" action="ezPrivDetailAction!save.do">
						<input type="hidden" name="privilegeId" value="${privilege.privilegeId }" id="tree_orgId" upload="true"/> 
						<input type="hidden" name="upPrivilegeId" id="tree_upId" value="${privilege.upPrivilegeId }" upload="true"/> 
						<input type="hidden" name="haveSub" value="${privilege.haveSub }" upload="true"/>

						
						<table class="table">
							<tr>
								<th>菜单名称：</th>
								<td><input class="easyui-validatebox textbox formWidth"
									name="privilegeName" value="${privilege.privilegeName }"
									data-options="required:true,validType:'length[0,255]'" /></td>
								<th>菜单编号：</th>
								<td><input class="easyui-validatebox textbox formWidth"
									name="privilegeId" value="${privilege.privilegeId }"
									data-options="required:true" readonly="readonly" /></td>
							</tr>
							<tr>
								<th>菜单地址：</th>
								<td><input class="easyui-validatebox textbox formWidth"
									name="url" value="${privilege.url }"
									data-options="validType:'length[0,255]'" /></td>
								
							</tr>
							
						</table>

						<div data-options="region:'south',border:true">
							<button type="button" class="btn btn-primary pull-right"
								onclick="goDetail()">确定</button>

						</div>

					</form>
				</div>
			</div>
		</div>
		
		
	</div>

	<script type="text/javascript">
		$(function() {
			$('#tt').tabs({
				onSelect : function(panel) {
					var alltab = $('#tt').tabs('tabs');
					for ( var i = 0; i < alltab.length; i++) {
						if (i == 0) {
							continue;
						}
						alltab[i].empty();

					}

				}
			})
		});
		function goDetail() {
			$('#form').form('submit', {
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
				
					$('#priv_tree').treegrid('reload');
					$.messager.progress('close');
					$.messager.alert('提醒', data, 'info');
					$.modalDialog.handler.dialog('close');

				}
			});
		}
	</script>
</body>
</html>
