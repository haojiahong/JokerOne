package cn.itcast.oa.common.service;

import java.io.File;

public interface FileOperateService {
	// TODO 定时任务是什么东东
	/**
	 * 文件上传,根据类型保存在对应目录下,fileTemp会在定时任务定时删除
	 * 
	 * @param from
	 * @param toSrc
	 * @param fileType
	 * @return
	 * @throws Exception
	 */
	public String fileCopyToServer(File from, String toSrc, String oldName,
			String fileType);

	/**
	 * 获取上传后的文件
	 * 
	 * @param fileName
	 * @param fileType
	 * @return
	 */
	public File getFileFromServer(String fileName, String fileType);
}
