package cn.itcast.oa.common.action;

import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.common.service.UploadService;
import cn.itcast.oa.domain.SimUpload;
import cn.itcast.oa.util.CommonUtil;
import cn.itcast.oa.util.FtpFileUtil;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/oa/common")
@ParentPackage("json-default")
@Action(value = "/photo")
@Results({ @Result(name = "input", type = "stream", params = { "contentType", "application/octet-stream", "inputName",
		"inputStream" }) })
public class PhotoAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UploadService uploadService;
	private InputStream inputStream;
	private String uploadId;// 附件与实体中间表主键

	@Override
	public String execute() throws Exception {
		if (!CommonUtil.strIsNull(uploadId)) {
			SimUpload sim = uploadService.retrieve(uploadId);
			this.inputStream = FtpFileUtil.download(sim);
		}
		return "input";

	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

}
