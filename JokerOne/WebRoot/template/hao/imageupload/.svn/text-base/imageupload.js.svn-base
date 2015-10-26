/*******************************************************************************
 * 图片上传预览  开发信息 时间：08.21 作者：王岗
 * 
 * @version 1.0
 * 
 ******************************************************************************/
function delFile(fileId,objId){
	$.confirm("确认","确认删除该文件？",function(){
		$.post($$pageContextPath+"szgl/docmager/docAction!delFileOnlyCurVersion.do",{'fileId':fileId},function(result){
			$("img[fileId="+fileId+"]").parent().remove();
			var newvalue = $("#"+objId).val().replace(fileId+",","");
			$("#"+objId).val(newvalue);
		});	
	});
}
