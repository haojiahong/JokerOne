<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>部门（组织结构）</title>
    <%@ include file="/jsp/public/commons.jspf" %>
</head>
<body>

	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false">
			<div class="gridtoolbar">
				<div class="toolbartitle">组织机构</div>
			</div>
		</div>
		<div data-options="region:'center',border:false">
			<table id="org_tree" class="easyui-treegrid"
				data-options="url:'ezOrgTreeGridAction.do',idField:'id',treeField:'text',fit:true">
				<thead>
					<tr>
						<th field="text" width="200">名称</th>
						<th field="groupCode" width="80" align="center">编号</th>
						<th field="groupNameShort" width="80" align="center">简称</th>
						<th field="groupAttName" width="80" align="center">部门属性</th>
						<th field="fromDate"  data-options="formatter:formatDateToD"
							width="120" align="center">创建日期</th>
						<th field="remark" width="220" align="center">备注</th>
						<th field="id" data-options="formatter:ezEditFromat" width="200"
							align="center">编辑</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<script>
		function ezEditFromat(value, row, index) {
			if(value==-1){
				return "";
			}
			return '<a href="javascript:void(0)" onclick="showorg('
					+ value
					+ ')" ><img src="../script/images/edit.png" width=\"15\" height=\"15\"title="编辑"/></a>&nbsp;&nbsp;'
					+ '<a href="javascript:void(0)" onclick="addorg('
					+ value
					+ ')" ><img src="../script/images/new_add.png" width=\"15\" height=\"15\" title="增加"/></a>&nbsp;&nbsp;'
					+ '<a href="javascript:void(0)" onclick="delorg('
					+ value
					+ ')" ><img src="../script/images/delete.png" width=\"15\" height=\"15\" title="删除"/></a>&nbsp;&nbsp;';
		}

		var myBtn1 = '<button type="button" class="btn btn-primary">确定</button>';
		function showorg(value) {

			$.modalDialog({
				title : '组织机构详情',
				width : 700,
				height : 550,
				href : 'ezOrgDetailAction!load.do?orgId=' + value,

			});
		}

		function addorg(value) {
			$.modalDialog({
				title : '组织机构详情',
				width : 700,
				height : 550,
				href : 'ezOrgDetailAction!add.do?upOrgId=' + value,

			});
		}
		function delorg(value) {
			$.ajax({
				  url: "ezOrgDetailAction!delete.do?orgId="+value,
				  success: function(json){
					  $.messager.alert('提醒', json, 'info');
						$('#org_tree').treegrid('reload');
				  }
			});
		}
	</script>
</body>
</html>
