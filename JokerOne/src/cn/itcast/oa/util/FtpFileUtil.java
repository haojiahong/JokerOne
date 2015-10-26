package cn.itcast.oa.util;

import java.io.File;
import java.io.InputStream;

import cn.itcast.oa.domain.SimUpload;

public class FtpFileUtil {
	/**
	 * 判断是否ftp是否可登录
	 * @return
	 */
	public static boolean isConnect() {
		FTPServerHandler handler = null;
		try {
			handler = ((FtpStore) ApplicationUtil.getBean("ftpStore")).getHandler();
			handler.connect();
			return true;
		} catch (Exception e) {
			handler = null;
			return false;
		}//finally 未处理ftp连接断开
	}

	/**
	 * 上传
	 * @param filedata
	 * @param filedataFileName
	 */
	public static void upload(File filedata, String filedataFileName) {
		FTPServerHandler handler = null;
		try {
			handler = ((FtpStore) ApplicationUtil.getBean("ftpStore")).getHandler();
			handler.upload(filedata, filedataFileName);
			
		} catch (Exception e) {
			handler = null;
			e.printStackTrace();
		}//finally 未处理ftp连接断开
	}

	/**
	 * 下载
	 * @param sim
	 * @return
	 */
	public static InputStream download(SimUpload sim) {
		FTPServerHandler handler = null;
		InputStream is = null;
		try {
			handler = ((FtpStore) ApplicationUtil.getBean("ftpStore")).getHandler();
			is = handler.downloadStream(sim.getFileName());
			
		} catch (Exception e) {
			handler = null;
			e.printStackTrace();
		}//finally 未处理ftp连接断开
		
		return is;
		
	}
	
	
}
