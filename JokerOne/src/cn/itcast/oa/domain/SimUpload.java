package cn.itcast.oa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 附件上传记录实体
 * 
 * @author haojiahong
 * 
 */
@Entity
@Table(name = "my_simupload")
public class SimUpload implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "UPLOAD_ID")
	private String uploadId;
	@Column(name = "ENTITY_ID")
	private String entityId;
	@Column(name = "ENTITY_TYPE")
	private String entityType;
	@Column(name = "FILE_NAME")
	private String fileName;

	@Transient
	private String suffixName;

	@Transient
	private String fileType;// 附件类型（用来预览时区分是文档还是图片）

	@Transient
	private String visibleFlag;// 是否可预览（附件预览使用目前只有doc跟图片可以预览）

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSuffixName() {
		return suffixName;
	}

	public void setSuffixName(String suffixName) {
		this.suffixName = suffixName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(String visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

}
