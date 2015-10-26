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
	<form>
			<input id="file_upload" name="file_upload" type="file" >
			<div id="queue"></div>
			<input type="hidden" id="_fileName"/>
		</form>
		<script type="text/javascript">
			var uploadLimit = 1;
			$(function(){
				var fileType = '${param.fileType}',fileExts='${param.fileExts}',rows = fileExts.split(';'),exts = "";
				for(var i = 0;i<rows.length;i++){
					exts+="*."+rows[i]+";";
				}
				$('#file_upload').uploadify({
					'auto':false,
					'swf':'<%=path%>/js/uploadify/uploadify.swf',
					'uploader':'common/fileUploadAction.do;jsessionid=<%=session.getId()%>',
					'fileObjName' : 'uploadFile',
					'button_image_url':'<%=request.getContextPath()%>',
					'buttonText':'文件选择',
					'formData':{'saveType':fileType||'fileTemp'},
					'multi': false,
					'fileSizeLimit':'2MB',
			        'fileTypeExts':exts||"*.*",
					'uploadLimit':1,
					'removeTimeout':1,
					'onUploadSuccess':function(file, data, response){
						var d = eval("("+data+")");
						if(d.newFileName)
						$("#_fileName").val(d.newFileName);
					}
		        });
			});

			function _uploadFile(fileCallback){
				$('#file_upload').uploadify('settings','onUploadComplete',function(){
					if($("#_fileName").val()){
						fileCallback($("#_fileName").val());
						_dialog.dialog("close");
					}else{
						alert("文件上传失败，请重新上传！");
						$('#file_upload').uploadify('settings','uploadLimit', ++uploadLimit);
					}
				});
				$('#file_upload').uploadify('upload','*');
			}
			
		</script>
</body>
</html>
