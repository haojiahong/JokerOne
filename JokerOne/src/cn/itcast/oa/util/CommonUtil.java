package cn.itcast.oa.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 通用工具类
 * 
 * @author haojiahong
 * 
 */
public class CommonUtil {

	public static Timestamp getDate() {
		Date dt = new Date(System.currentTimeMillis());
		dt = Date.valueOf(dt.toString());
		return new Timestamp(dt.getTime());
	}

	public static Timestamp getDateTime() {
		long current = System.currentTimeMillis();
		current -= current % 1000L;
		return new Timestamp(current);
	}

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String genUUID() {
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}

	/**
	 * 判断字符串是否为null。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strIsNull(String str) {
		boolean flag = false;
		if (str == null || str.trim().length() <= 0 || "null".equals(str)) {
			return true;
		}
		return flag;
	}

	/**
	 * Java文件操作 获取文件扩展名
	 * 
	 * @param filename
	 *            String 类型的 文件名称
	 */
	public static String getExtName(String filename) {
		if ((null != filename) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 处理,;分割的数字
	 * 
	 * @param ids
	 * @return
	 */
	public static List<Long> paraseIds(String ids) {
		List<Long> resLs = new ArrayList<Long>();
		if (ids == null) {
			return resLs;
		}
		String[] strArr = ids.split("[,;]");
		for (String str : strArr) {
			if (str == null || "".equals(str.trim())) {
				continue;
			}
			resLs.add(Long.valueOf(str));
		}
		return resLs;
	}

	/**
	 * 处理,分割的字符串
	 * 
	 * @param ids
	 * @return
	 */
	public static List<String> paraseStrs(String strs) {
		List<String> resLs = new ArrayList<String>();
		if (strs == null) {
			return resLs;
		}
		String[] strArr = strs.split(",");
		for (String str : strArr) {
			if (str == null || "".equals(str.trim())) {
				continue;
			}
			resLs.add(str);
		}
		return resLs;
	}

	public static String getChineseDate() {
		return DateFormat.getDateInstance(1, Locale.CHINA).format(
				new Date(System.currentTimeMillis()));
	}

}
