/**
 * 附件上传
 * entityId:实体主键
 *entityType:实体类型
 */
function simUpload(entityId, entityType) {
	var url = "common/simUploadAction.do?entityId="+entityId+"&entityType="+entityType;
	window.open (url,"_blank","width=800,height=600");
}

/**
 * 附件管理通用界面
 *entityId:实体主键
 *entityType:实体类型
 */
function comFileUpload(entityId, entityType) {
	var url = "common/comFileUpAction.do?entityId="+entityId+"&entityType="+entityType;
	$.modalDialog({
		title : '附件管理',
		width : 800,
		height : 500,
		href : url	
	});
}

/**
 * 文件下载
 * @param uploadId simupload中的主键
 */
function downloadFile(uploadId) {
	var url = "common/download.do?uploadId=" + uploadId;
	window.open(url);
}
/**
 * excel等文件的上传
 */
var _dialog;
function uploadifyFile(fileCallback, fileType, fileExts) {
	if (_dialog) {
		_dialog.dialog("destroy");
	}
	_dialog = $("<div style='padding:2px'/>")
			.dialog(
					{
						href : "common/uploadify.jsp?fileType=" + fileType
								+ "&fileExts=" + fileExts,
						title : "文件上传",
						width : 500,
						height : 230,
						modal : true,
						buttons : [ {
							text : '<button type="button" class="btn btn-primary">上传</button>',
							handler : function() {
								_uploadFile(fileCallback);
							}
						} ]
					});
}