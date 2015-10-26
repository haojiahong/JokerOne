package cn.itcast.oa.common.action;

import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.common.service.UploadService;
import cn.itcast.oa.domain.SimUpload;
import cn.itcast.oa.util.FtpFileUtil;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/oa/common")
@ParentPackage("json-default")
@Action(value = "/download")
@Results({ @Result(name = "success", type = "stream", params = { "contentType", "application/octet-stream",
		"inputName", "inputStream", "contentDisposition", "attachment;filename=${fileName}" }) })
public class DownloadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UploadService uploadService;

	private String fileName;
	private InputStream inputStream;
	private String uploadId;// 附件记录表主键

	@Override
	public String execute() throws Exception {
		SimUpload sim = uploadService.retrieve(uploadId);
		// 防止文件名中文乱码
		this.fileName = URLEncoder.encode(sim.getFileName(), "utf-8");
		this.inputStream = FtpFileUtil.download(sim);
		return "success";

	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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
