package cn.itcast.oa.common.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.base.EasyGridAction;
import cn.itcast.oa.common.service.UploadService;
import cn.itcast.oa.domain.SimUpload;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.domain.basemain.EasyGridData;
import cn.itcast.oa.util.FtpFileUtil;

@Namespace("/oa/common")
@ParentPackage("json-default")
@Action(value = "/comFileUpAction")
@Results({ @Result(name = "input", location = "comfileup.jsp"),
		@Result(name = "success", type = "json", params = { "includeproperties", "message" }) })
public class ComFileUpAction extends EasyGridAction<User> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UploadService uploadService;

	private EasyGridData<SimUpload> gridData = new EasyGridData<SimUpload>();
	private String entityId;// 各自实体的主键,仅用来查询和保存
	private String entityType;
	private String uploadId;// 通用附件的主键,下载用
	private String message;// 消息提示

	@Override
	public String execute() throws Exception {
		gridData.setRows(uploadService.retrieveAll(entityId, entityType, getPageInfo()));
		gridData.setTotal(getPageInfo().getAllRowNum());
		return "input";
	}

	public String ftpIsConncetion() {
		boolean result = FtpFileUtil.isConnect();
		if (!result) {
			message = "FTP服务器未连接成功";
		}
		return "success";
	}

	public String del() {
		uploadService.remove(uploadId);
		message = "删除成功";
		return "success";
	}

	public EasyGridData<SimUpload> getGridData() {
		return gridData;
	}

	public void setGridData(EasyGridData<SimUpload> gridData) {
		this.gridData = gridData;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

}
