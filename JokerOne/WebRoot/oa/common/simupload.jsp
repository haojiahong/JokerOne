<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<html>
<head>
<title>附件管理</title>
<%@ include file="/jsp/public/commons.jspf" %>
<link href="${pageContext.request.contextPath}/script/swfupload/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/script/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/swfupload/handlers.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/swfupload/validator.js"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true">

		<div data-options="region:'north',border:false">
			<table width="100%" cellspacing="0" cellpadding="0"
				style="padding:0px;">
				<tr height="20">
					<td width="40" align="center"></td>
					<td width="90">附件上传：</td>
					<td width="70"><span id="spanButtonPlaceholder"></span></td>
					<td><span id="resultSpan" style="color:red;margin-left: 20px;"></span>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',border:true">
			<fieldset
				style="height: 280;width:470px;overflow: auto;margin-left: 5px;">
				<legend style="margin-left:20px;background-color:#F3F3F3; ">附件列表</legend>
				<div style="width:470px;height:220px;overflow:auto;">
					<div id="fsUploadProgressDiv"></div>
					<div id="fsUploadProgress"></div>
				</div>
			</fieldset>
		</div>
		<div data-options="region:'south',border:false">
			<input type="button" value="保存" onclick="_sub()" /> 
			<input type="button" value="返回" onclick="" />
		</div>
	<input type="hidden" id="upFileIds" />
	</div>
	
<script type="text/javascript">
//变量名为swfupload,不能为其他
		var swfupload;
		window.onload = function() {
			swfupload = new SWFUpload({
				//提交路径
				upload_url:"simUploadAction!save.do;jsessionid=<%=session.getId()%>?entityId=${entityId}&entityType=${entityType}",
				//上传文件的名称,对应到后台Action的File属性
				file_size_limit : 50240,
				file_upload_limit : 10,
				file_types : '${alowFileTypes}',
				file_types_description : '${alowFileTypes}',
				// 事件处理
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError, 
				upload_success_handler : myuploadSuccess,
				upload_complete_handler : uploadComplete,
				// 按钮的处理
				button_image_url : "<%=path%>/script/images/swf/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder",
				button_width: 61,
				button_height: 22,
				button_text_left_padding : 0,
	            button_text_top_padding : 0,
						
				// Flash Settings
				flash_url : "<%=path%>/script/swfupload/swfupload.swf",
				custom_settings : {
					progressTarget : "fsUploadProgress",
					cancelButtonId : "btnCancel1"
				},
				debug : false
				});
	}

	function uploadStart(file) {
		try {
			if (strlen(file.name) > 200) {
				swfupload.addFileParam(file.id, "fileName", fileName);
				var progress = new FileProgress(file,
						this.customSettings.progressTarget);
				progress.setError();
				this.cancelUpload(file.id, false);
				progress.setStatus("上传的文件名长度太大，请修改文件名后再上传");
				alert("文件名为：" + file.name + "的文件，文件名长度太大，系统将取消该文件的上传");
			} else {
				var fileName = encodeURIComponent(file.name);
				swfupload.addFileParam(file.id, "fileName", fileName);
				var progress = new FileProgress(file,
						this.customSettings.progressTarget);
				progress.setStatus("上传...");
				progress.toggleCancel(true, this);
				return true;

			}
		} catch (ex) {
		}
	}
	function myuploadSuccess(file, serverData) {
		var result = (JSON.parse(serverData)).succFileId;
		var oldFiles = $('#upFileIds').val();
		$('#upFileIds').val(oldFiles + result + ",");
		
	}
	function uploadComplete(file) {
		if (this.getStats().files_queued == 0) {
			$("#resultSpan").html("成功上传"+($('#upFileIds').val().split(",").length-1)+"个文件！");
			//setTimeout(function(){$.close($('#upFileIds').val());},1000);
			setTimeout(function(){
				window.close();
			},1000);
		} else {
			this.startUpload();
		}
	}
	function _sub() {
		if ($('#fsUploadProgress').html() == '') {
			alert("请选择上传文件！");
			return false;
		}
		swfupload.startUpload();
	}
</script> 
</body>

</html>
