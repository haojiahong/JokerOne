/**
 * �����ַ������ֽڳ���
 * @param str
 * @returns {Number}
 */
function strlen(str) {
	var len = 0;
	for ( var i = 0; i < str.length; i++) {
		var c = str.charCodeAt(i);
		// ���ֽڼ�1
		if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
			len++;
		} else {
			len += 2;
		}
	}
	return len;
}

/**
 * ���ɵ�����ʾ��Ϣ
 */
function genAlertInfo(title,value,len,type){
	var message='';
	if(type=='required'){
		if($.trim(value)<1){
			message+='\"'+title+'\":�����ֶ�<br />';
		}
	}else if(type=='maxlength'){
		if(strlen(value)>len){
			message+='\"'+title+'\":������һ�����������'+len+'���ַ���<br />';
		}
	}
	return message;
	
}
