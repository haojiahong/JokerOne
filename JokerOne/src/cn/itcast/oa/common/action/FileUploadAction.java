package cn.itcast.oa.common.action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.itcast.oa.common.service.FileOperateService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * excel等文件上传
 * 
 * @author haojiahong
 * 
 * @createtime：2015-7-16 下午1:35:55
 * 
 * 
 */
@Namespace("/oa/common")
@ParentPackage("json-default")
@Action(value = "/fileUploadAction")
@Results({ @Result(name = "input", type = "json", params = {
		"includeproperties", "newFileName" }) })
public class FileUploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private FileOperateService fileOperateService;

	private String newFileName;
	private String saveType;// 文件何种形式保存,如:临时文件,照片等等
	private File uploadFile;
	private String uploadFileFileName;

	@Override
	public String execute() throws Exception {
		if (uploadFile != null) {
			try {
				newFileName = fileOperateService.fileCopyToServer(uploadFile,
						newFileName, uploadFileFileName, saveType);
			} catch (Exception e) {
				newFileName="";
			}
		}
		System.out.println(newFileName+"====================================");
		return INPUT;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public FileOperateService getFileOperateService() {
		return fileOperateService;
	}

	public void setFileOperateService(FileOperateService fileOperateService) {
		this.fileOperateService = fileOperateService;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
}
