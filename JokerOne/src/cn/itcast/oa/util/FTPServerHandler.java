package cn.itcast.oa.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import cn.itcast.oa.model.FtpConfig;

/**
 * Ftp服务器连接，上传文件，下载文件。处理类。
 * 
 * @author haojiahong
 * 
 */
public class FTPServerHandler {
	private Log log = LogFactory.getLog(this.getClass());
	private FTPClient ftp;
	private FtpConfig ftpConfig;

	public FTPServerHandler(FtpConfig config) {
		this.ftpConfig = config;
	}

	/**
	 * FTP服务器连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public boolean connect() throws Exception {
		boolean result = false;
		ftp = new FTPClient();
		int reply;
		ftp.connect(this.ftpConfig.getServerIP(),
				this.ftpConfig.getServerPort());
		ftp.login(this.ftpConfig.getUserId(), this.ftpConfig.getPassword());
		ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftp.setControlEncoding("UTF-8");
		reply = ftp.getReplyCode();
		
		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			log.debug("ftp拒绝连接");
			return result;
		}
		
		ftp.changeWorkingDirectory(this.ftpConfig.getPath());
		result = true;
		return result;
	}

	/**
	 * ftp服务器上传文件或文件夹
	 * 
	 * @param filedata
	 * @param filedataFileName
	 * @throws Exception
	 */
	public void upload(File filedata, String filedataFileName) throws Exception {
		this.connect();
		if (filedata.isDirectory()) {
			ftp.makeDirectory(filedata.getName());
			ftp.changeWorkingDirectory(filedata.getName());
			String[] files = filedata.list();
			for (int i = 0; i < files.length; i++) {
				File file1 = new File(filedata.getPath() + "\\" + files[i]);
				if (file1.isDirectory()) {
					upload(file1, null);
					ftp.changeToParentDirectory();
				} else {
					File file2 = new File(filedata.getPath() + "\\" + files[i]);
					FileInputStream input = new FileInputStream(file2);
					ftp.storeFile(file2.getName(), input);
					input.close();
				}
			}
		} else {
			// File file2 = new File(filedata.getPath());
			FileInputStream input = new FileInputStream(filedata);
			//这样改变编码后可以上传中文名称的文件，但是还是下载不了
			ftp.storeFile(new String( filedataFileName.getBytes("utf-8"),"iso-8859-1"), input);
			input.close();
		}
	}

	/**
	 * ftp服务器上下载文件
	 * @param remoteFileName
	 * @return
	 * @throws Exception
	 */
	public InputStream downloadStream(String remoteFileName) throws Exception {
		this.connect();
		InputStream is = null;
		ByteArrayOutputStream os;
		os = new ByteArrayOutputStream();
		//下载要考虑两个方面，1.这边的文件名转换成iso-8859-1然后到服务器目录查找。2.struts的文件名要改成utf-8的，以保持和服务器储存文件名的统一，其实这个不改也能拿到有数据的文件。
		boolean flag = ftp.retrieveFile(new String (remoteFileName.getBytes("utf-8"),"iso-8859-1"), os);
		System.out.println(flag);
		is = new ByteArrayInputStream(os.toByteArray());
		if (os != null)
			os.close();
		return is;
	}

}
