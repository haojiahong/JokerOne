package cn.itcast.oa.common.action;

import java.io.File;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.common.service.UploadService;
import cn.itcast.oa.util.FtpFileUtil;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/oa/common")
@ParentPackage("json-default")
@Action(value = "/simUploadAction")
@Results({ @Result(name = "open", location = "simupload.jsp"),
		@Result(name = "success", type = "json", params = { "includeproperties", "msg" }) })
public class SimUploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UploadService uploadService;

	private File filedata;// SWFUPLOAD默认,不能改
	private String filedataFileName;// SWFUPLOAD默认,不能改
	private String succFileId;// 成功之后有fileId
	private String entityType;// 区分不同实体的上传，User，Role等
	private String entityId;
	private String alowFileTypes = "*.*";// *.doc;*.html;这样写
	private String msg;

	@Override
	public String execute() throws Exception {
		return "open";
	}

	public String save() {
		FtpFileUtil.upload(filedata, filedataFileName);
		uploadService.save(entityId, entityType, filedataFileName);
		return SUCCESS;
	}

	public File getFiledata() {
		return filedata;
	}

	public void setFiledata(File filedata) {
		this.filedata = filedata;
	}

	public String getFiledataFileName() {
		return filedataFileName;
	}

	public void setFiledataFileName(String filedataFileName) {
		this.filedataFileName = filedataFileName;
	}

	public String getAlowFileTypes() {
		return alowFileTypes;
	}

	public void setAlowFileTypes(String alowFileTypes) {
		this.alowFileTypes = alowFileTypes;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccFileId() {
		return succFileId;
	}

	public void setSuccFileId(String succFileId) {
		this.succFileId = succFileId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}
