<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
    <title>附件管理</title>
    <%@ include file="/jsp/public/commons.jspf" %>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<s:form action="comFileUpAction">
				<div data-options="region:'north',border:false" style="height:29px">
					<input type="button" value="上传" onclick="upload()" />
					
				</div>
				<div data-options="region:'center',border:true">
					<div id="contendDiv">
						<s:iterator value="gridData.rows">
							<div class="fileWrap" title="${fileName }">
								<div class="imgWrap">
									<img src="../script/images/filetype/${suffixName }.png"
										onerror="javascript:this.src='../script/images/filetype/mr.png';" />
								</div>
								<div class="titleWrap">
									${fileName }
									
									<span style="float: right;"> <img title="删除" style="cursor: pointer;"
										src="../script/images/delete.png" onclick="del('${uploadId}')" />
									</span>
									
								</div>
								<div class="detailWrap">
									 <span style="float: right;"> 
										<img src="../script/images/insert.gif" style="cursor: pointer;"
										title="下载" onclick="downAcc('${uploadId}')" /> 
										<img src="../script/images/go.png"
										style="cursor: pointer;display:${visibleFlag};" title="预览"
										onclick="yulan('${uploadId}','${fileType}')" />
									</span>
								</div>
							</div>
						</s:iterator>
					</div>
				</div>
				
			<s:hidden id="entityId" name="entityId"></s:hidden>
			<s:hidden id="uploadId" name="uploadId"></s:hidden>
			<s:hidden id="entityType" name="entityType"></s:hidden>
		</s:form>
	</div>
	<script type="text/javascript">
 		function reload() {
			$.ajax({
				url : "../oa/common/comFileUpAction.do",
				data : {
					"entityId" : $("#entityId").val(),
					"entityType" : $("#entityType").val()
				},
				type : "post",
				dataType : "json",
				success : function(data) {
					window.location.reload();
				}
			});
		}

		function del(uploadId) {
			$.messager.confirm('确认删除', '确定删除本条记录?', function(r) {
				if (r) {
					$.ajax({
						url : "../oa/common/comFileUpAction!del.do?uploadId="
								+ uploadId,
						type : "post",
						dataType : "json",
						success : function(data) {
							if (data) {
								$.messager.alert("提示信息", data, '', reload);
							}
						}
					});
				}
			});
		}

		function upload() {
			$.ajax({
				url : "../oa/common/comFileUpAction!ftpIsConncetion.do",
				
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.message == null) {
						simUpload($("#entityId").val(), $("#entityType").val());
						reload();
					} else {
						$.messager.alert("提示信息", data, '', null);
					}
				}
			});
			
		}

 		function yulan(uploadId, fileType) {
 			var yulandialog;
			$.ajax({
				url : "../oa/common/comFileUpAction!ftpIsConncetion.do",
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.message == null) {
						if (fileType == "pic") {
							
					 		top.artDialog({
								title : '预览',
								content : "<div style='text-align:center;'><img src='"
										+ "../oa/common/photo.do?uploadId=" + uploadId
										+ "' /></div>",
								width : "600px",
								height : "400px",
								lock : true,
								
							});
							
						} else {
							//previewFile(fileId);
							$.messager.alert("提示信息", "不是图片格式文件", '', null);
						}

					} else {
						$.messager.alert("提示信息", data, '', null);
					}
				}
			});

		} 



 		function downAcc(uploadId) {
			$.ajax({
				url : "../oa/common/comFileUpAction!ftpIsConncetion.do",
				
				type : "post",
				dataType : "json",
				success : function(data) {
					if (data.message == null) {
						downloadFile(uploadId);
					} else {
						$.messager.alert("提示信息", data, '', null);
					}
				}
			});
		} 
	</script>	
</body>
</html>
