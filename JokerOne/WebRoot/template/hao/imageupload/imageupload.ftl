<#--
/*
 * $Id: head.ftl 590812 2007-10-31 20:32:54Z apetrelli $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
-->
<div id="upload_${parameters.id?string}_wrap" height="100%" width="${parameters.width}" style="padding-top:10px;padding-left:7px;">
<#if parameters.fileIdValue?string!=''>
	<#list parameters.fileIdValue?split(",") as fileId>
	<div class='img_wrap'>
		<img width='72' height='72' fileId="${fileId}" style="cursor:pointer" onclick="pwiw_init('${parameters.contextPath?string}/szgl/common/preview.do?fileId=${fileId}')" src="../../szgl/common/preview.do?fileId=${fileId}" title='点击预览原图'>
		<#if !parameters.readonly>
		<span class="upload-item-del" title='删除' onclick="delFile(${fileId},'${parameters.id?string}')" style="background-image:url('${parameters.contextPath?string}/template/szgl/imageupload/images/del.png')"></span>
		</#if>
	</div>
	</#list>
</#if>&nbsp;&nbsp;<span id="spanButtonPlaceholder" class="test"></span>	
</div>
<input type="hidden" id="${parameters.id?string}" <#if parameters.fileIdName?string!='' > name="${parameters.fileIdName}" </#if> <#if parameters.upload >upload="true"</#if> <#if parameters.fileIdValue?string!=''>value="${parameters.fileIdValue},"</#if>/>
<#if !parameters.readonly>
<script type="text/javascript">
	$(function(){
		$("#upload_${parameters.id?string}_wrap div").hover(function(){
			$(this).find("span").show();
		},function(){
			$(this).find("span").hide();
		});
	})

	var swfupload = new SWFUpload({
		upload_url:$$pageContextPath+"hr/hrcommon/simUploadAction!save.do;jsessionid=<%=request.getSession().getId()%>?pathType=${parameters.pathType}",
		file_size_limit : "5MB",
		file_types : "${parameters.allowedType}",
		file_types_description : "图片文件",
		file_upload_limit : "10",
		file_queue_limit : '10', 
		file_dialog_start_handler : fileDialogStart,
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError, 
		upload_success_handler : myuploadSuccess,
		upload_complete_handler : uploadComplete,
		
		button_cursor : SWFUpload.CURSOR.HAND,
		button_image_url : $$pageContextPath+"/szgl/docmager/images/plus+.png",
		button_placeholder_id : "spanButtonPlaceholder",
		button_width: 72,
		button_height: 72,
		button_text_style : ".test { margin-top:20px; }",
		
		flash_url : $$pageContextPath+"/edoc/js/swfupload.swf"
	});
	function myuploadSuccess(file, serverData) {
		var result = (JSON.parse(serverData)).succFileId,oldFiles = $('#${parameters.id?string}').val(),divIndex = oldFiles.split(",").length-1;
		var htmlstr = "<img width='72' height='72' fileId=\""+result+"\" style=\"cursor:pointer\" onclick=\"pwiw_init('${parameters.contextPath?string}/szgl/common/preview.do?fileId="+result+"')\" src=\"../../szgl/common/preview.do?fileId="+result+"\" title='点击预览原图'>";
		htmlstr+="<span class=\"upload-item-del\" title='删除' onclick=\"delFile("+result+",'${parameters.id?string}')\" style=\"background-image:url('${parameters.contextPath?string}/template/szgl/imageupload/images/del.png')\"></span>";
		$("#upload_${parameters.id?string}_wrap div:eq("+divIndex+")").html(htmlstr);
		$('#${parameters.id?string}').val(oldFiles + result + ",");
	} 
	
	function fileDialogComplete(numFilesSelected, numFilesQueued) {
		try {
			var htmlstr = "";
			for(var i = 0;i<numFilesQueued;i++){
				htmlstr += "<div class='img_wrap' style=\"background-image:url('${parameters.contextPath?string}/template/szgl/imageupload/images/loading.gif')\"></div>";				
			}
			if($("#upload_${parameters.id?string}_wrap div").size()==0){
				$("#upload_${parameters.id?string}_wrap").prepend(htmlstr);
			}else{
				$("#upload_${parameters.id?string}_wrap div:last").after(htmlstr);
			}
		   	swfupload.startUpload();
		} catch (ex) {
		   this.debug(ex);
		}
	}
	
	function uploadComplete(file){
		$("#upload_${parameters.id?string}_wrap div").hover(function(){
			$(this).find("span").show();
		},function(){
			$(this).find("span").hide();
		});
	}
</script>
</#if>