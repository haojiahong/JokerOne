<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<html>
<head>
<title>详细设置</title>
<%@ include file="/jsp/public/commons.jspf" %>	
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
					<form id="form" method="post" action="ezOrgDetailAction!save.do">
						<input type="hidden" name="orgId" value="${organization.orgId }" id="tree_orgId" upload="true"/> 
						<input type="hidden" name="upOrgId" id="tree_upId" value="${organization.upOrgId }" upload="true"/> 
						<input type="hidden" name="haveSub" value="${organization.haveSub }" upload="true"/>

						
						<table class="table">
							<tr>
								<th>组织机构名称：</th>
								<td><input class="easyui-validatebox textbox formWidth"
									name="groupName" value="${organization.groupName }"
									data-options="required:true,validType:'length[0,255]'" /></td>
								<th>编号：</th>
								<td><input class="easyui-validatebox textbox formWidth"
									name="groupCode" value="${organization.groupCode }"
									data-options="validType:'length[0,64]'" /></td>
							</tr>
							<tr>
								<th>简称：</th>
								<td><input class="easyui-validatebox textbox formWidth"
									name="groupNameShort" value="${organization.groupNameShort }"
									data-options="validType:'length[0,255]'" /></td>
								<th>创建日期：</th>
								<td><input class="easyui-datebox formWidth" name="fromDate"
									value="${organization.fromDate }" /></td>
							</tr>
							<tr>
								<th>部门属性：</th>
								<td><input id="groupAtt" name="groupAtt"
									class="easyui-combobox formWidth"
									data-options="url:'ezOrgDetailAction!droplist.do'"
									value="${organization.groupAtt }" /></td>
									
								<th>排序号：</th>
								<td colspan="3"><input class="easyui-numberbox formWidth"
									type="text" name="sortCode" value="${organization.sortCode }"
									data-options="validType:'length[0,10]'" /></td>
							</tr>
							
							<tr>
								<th>备注：</th>
								<td colspan="4"><textarea
										class="easyui-validatebox textbox"
										style="width: 100%; height: 80px"
										data-options="validType:['length[0,120]']" name="remark">${organization.remark }</textarea>
								</td>
							</tr>

							<tr>
						</table>

						<div data-options="region:'south',border:true">
							<button type="button" class="btn btn-primary pull-right"
								onclick="goDetail()">确定</button>

						</div>

					</form>
				</div>
			</div>
		</div>
		<s:if test="organization.orgId!=null">
			<div title="部门负责人"
				data-options="cache:false,href:'hrorgheadez.jsp?orgId=${organization.orgId }'"
				style="overflow: auto; padding: 20px;"></div>
				
			<div title="职位列表"
				data-options="cache:false,href:'hrorgposlistez.jsp?orgId=${organization.orgId }'"
				style="overflow: auto; padding: 20px;"></div>
		</s:if>
		
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
				
					$('#org_tree').treegrid('reload');
					$.messager.progress('close');
					$.messager.alert('提醒', data, 'info');
					$.modalDialog.handler.dialog('close');

				}
			});
		}
	</script>
</body>
</html>
