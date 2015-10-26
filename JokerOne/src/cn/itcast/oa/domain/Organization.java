package cn.itcast.oa.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name = "my_organization")
public class Organization implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "ORG_ID")
	private Long orgId;

	@Column(name = "GROUP_CODE")
	private String groupCode;

	@Column(name = "GROUP_NAME")
	private String groupName;

	@Column(name = "GROUP_NAME_SHORT")
	private String groupNameShort;

	@Column(name = "GROUP_LEVEL")
	private Long groupLevel;

	@Column(name = "FROM_DATE")
	private Timestamp fromDate;// 创建日期

	@Column(name = "UP_ORG_ID")
	private Long upOrgId;// 上级部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UP_ORG_ID", insertable = false, updatable = false)
	private Organization upOrganization;

	@OneToMany(mappedBy = "upOrganization", cascade = CascadeType.REMOVE)
	private List<Organization> subOrgLs;

	@Column(name = "HAVE_SUB")
	private String haveSub;// 是否有下级节点,树用

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "GROUP_ATT")
	private Long groupAtt;// 部门属性，职能

	@Column(name = "SORT_CODE")
	private Long sortCode;// 排序号

	@OneToMany(mappedBy = "organization", cascade = CascadeType.REMOVE)
	private List<User> userLs;// 用户

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNameShort() {
		return groupNameShort;
	}

	public void setGroupNameShort(String groupNameShort) {
		this.groupNameShort = groupNameShort;
	}

	public Long getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(Long groupLevel) {
		this.groupLevel = groupLevel;
	}

	public Long getUpOrgId() {
		return upOrgId;
	}

	public void setUpOrgId(Long upOrgId) {
		this.upOrgId = upOrgId;
	}

	public Organization getUpOrganization() {
		return upOrganization;
	}

	public void setUpOrganization(Organization upOrganization) {
		this.upOrganization = upOrganization;
	}

	@JSON(serialize = false)
	public List<Organization> getSubOrgLs() {
		return subOrgLs;
	}

	public void setSubOrgLs(List<Organization> subOrgLs) {
		this.subOrgLs = subOrgLs;
	}

	public String getHaveSub() {
		return haveSub;
	}

	public void setHaveSub(String haveSub) {
		this.haveSub = haveSub;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getGroupAtt() {
		return groupAtt;
	}

	public void setGroupAtt(Long groupAtt) {
		this.groupAtt = groupAtt;
	}

	public Long getSortCode() {
		return sortCode;
	}

	public void setSortCode(Long sortCode) {
		this.sortCode = sortCode;
	}

	@JSON(serialize=false)
	public List<User> getUserLs() {
		return userLs;
	}

	public void setUserLs(List<User> userLs) {
		this.userLs = userLs;
	}

}
