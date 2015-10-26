package cn.itcast.oa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.itcast.oa.model.FtpConfig;
/**
 * spring初始化bean，可获得ftp处理类handler
 * @author haojiahong
 *
 */
public class FtpStore {
	
	private Log log = LogFactory.getLog(this.getClass());
	private String serverIP;
	private int serverPort;
	private String userId;
	private String password;
	private String path;
	private FtpConfig ftpConfig;

	private FTPServerHandler ftpServerHandler;

	public void initialize() {
		ftpConfig = new FtpConfig();
		ftpConfig.setServerIP(serverIP);
		ftpConfig.setServerPort(serverPort);
		ftpConfig.setUserId(userId);
		ftpConfig.setPassword(password);
		ftpConfig.setPath(path);
		log.debug("初始化了FTP服务器配置参数");
	}

	public FTPServerHandler getHandler() {
		FTPServerHandler handler = new FTPServerHandler(ftpConfig);
		return handler;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public FTPServerHandler getFtpServerHandler() {
		return ftpServerHandler;
	}

	public void setFtpServerHandler(FTPServerHandler ftpServerHandler) {
		this.ftpServerHandler = ftpServerHandler;
	}
}
