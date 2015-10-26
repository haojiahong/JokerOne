<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>菜单树表</title>
   
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false">
		</div>
		<div data-options="region:'center',border:false">
			<table id="priv_tree" class="easyui-treegrid"
				data-options="url:'ezPrivTreeGridAction.do',idField:'id',treeField:'text',fit:true">
				<thead>
					<tr>
						<th field="text" width="200">菜单名称</th>
						<th field="privilegeId" width="80" align="center">菜单编号</th>
						<th field="privilegeName" width="80" align="center">菜单名称</th>
						<th field="url" width="120" align="center">菜单地址</th>
						
						<th field="id" data-options="formatter:ezEditFromat" width="200"
							align="center">编辑</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script>
		function ezEditFromat(value, row, index) {
			var str = "";
			if(value==0){
				return "";
			}
			str += hjh.formatString('<img class="iconImg ext-icon-note" title="编辑" onclick="showorg(\'{0}\');"/>', row.id);
			str += hjh.formatString('<img class="iconImg ext-icon-note_edit" title="添加" onclick="addorg(\'{0}\');"/>', row.id);
			str += hjh.formatString('<img class="iconImg ext-icon-note_delete" title="删除" onclick="delorg(\'{0}\');"/>', row.id);
			return str;
		}

		var myBtn1 = '<button type="button" class="btn btn-primary">确定</button>';
		function showorg(value) {
			alert(value);
			$.modalDialog({
				title : '组织机构详情',
				width : 700,
				height : 550,
				href : 'ezPrivDetailAction!load.do?privilegeId=' + value,

			});
		}

		function addorg(value) {
			$.modalDialog({
				title : '组织机构详情',
				width : 700,
				height : 550,
				href : 'ezPrivDetailAction!add.do?upPrivilegeId=' + value,

			});
		}
		function delorg(value) {
			$.ajax({
				  url: "ezPrivDetailAction!delete.do?privilegeId="+value,
				  success: function(json){
					  $.messager.alert('提醒', json, 'info');
						$('#priv_tree').treegrid('reload');
				  }
			});
		}
	</script>
</body>
</html>
