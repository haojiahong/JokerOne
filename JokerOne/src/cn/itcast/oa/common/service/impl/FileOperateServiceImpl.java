package cn.itcast.oa.common.service.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import cn.itcast.oa.common.service.FileOperateService;
import cn.itcast.oa.util.ApplicationUtil;
import cn.itcast.oa.util.CommonUtil;

@Service("fileOperateService")
public class FileOperateServiceImpl implements FileOperateService {

	@Override
	public String fileCopyToServer(File from, String toSrc, String oldName,
			String fileType) {
		if (toSrc == null || toSrc.trim().length() <= 0) {
			toSrc = "" +CommonUtil.genUUID();
		}
		String path = "";
		File to = null;
		if ("perPic".equals(fileType)) {
			path = ApplicationUtil.getAppConfig().getAppExtProp()
					.get("perPicPath")
					+ "";
			toSrc += ".dat";
			to = new File(path + toSrc);
		} else if ("fileTemp".equals(fileType)) {
			path = ApplicationUtil.getAppConfig().getAppExtProp()
					.get("fileTempPath")
					+ "";
			toSrc += getExtendName(oldName);
			to = new File(path + toSrc);
		} else if ("logo".equals(fileType)) {
			path = ApplicationUtil.getAppConfig().getAppExtProp()
					.get("accessoryFilePath")
					+ "logo/";
			toSrc += ".png";
			to = new File(path + toSrc);
		}
		if (!to.getParentFile().exists()) {
			to.getParentFile().mkdirs();
		}
		try {
			FileUtils.copyFile(from, to);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return toSrc;
	}

	@Override
	public File getFileFromServer(String fileName, String fileType) {
		String path = "";
		if ("perPic".equals(fileType)) {
			path = ApplicationUtil.getAppConfig().getAppExtProp()
					.get("perPicPath")
					+ "";
		} else if ("fileTemp".equals(fileType)) {
			path = ApplicationUtil.getAppConfig().getAppExtProp()
					.get("fileTempPath")
					+ "";
		} else if ("logo".equals(fileType)) {
			path = ApplicationUtil.getAppConfig().getAppExtProp()
					.get("accessoryFilePath")
					+ "logo/";
		}
		return new File(path + fileName);
	}

	private String getExtendName(String fileName) {
		int last = fileName.lastIndexOf(".");
		return fileName.substring(last);
	}
}
