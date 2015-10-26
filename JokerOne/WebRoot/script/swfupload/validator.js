/**
 * 计算字符串的字节长度
 * @param str
 * @returns {Number}
 */
function strlen(str) {
	var len = 0;
	for ( var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		// 单字节加1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		} else {
			len += 2;
		}
	}
	return len;
}

/**
 * 生成弹窗提示信息
 */
function genAlertInfo(title,value,len,type){
	var message='';
	if(type=='required'){
		if($.trim(value)<1){
			message+='\"'+title+'\":必填字段<br />';
		}
	}else if(type=='maxlength'){
		if(strlen(value)>len){
			message+='\"'+title+'\":请输入一个长度最多是'+len+'的字符串<br />';
		}
	}
	return message;
	
}
