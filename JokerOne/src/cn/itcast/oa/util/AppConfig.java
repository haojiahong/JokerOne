package cn.itcast.oa.util;

import java.util.Map;
/**
 * app设置基础参数，excel上传的时候文件临时路径初始化放在appextProp中了。
 * @author haojiahong
 *
 * @createtime：2015-7-16 下午2:06:32 
 *
 *
 */
public class AppConfig {
	private Map appExtProp;

	public Map getAppExtProp() {
		return appExtProp;
	}

	public void setAppExtProp(Map appExtProp) {
		this.appExtProp = appExtProp;
	}
}
