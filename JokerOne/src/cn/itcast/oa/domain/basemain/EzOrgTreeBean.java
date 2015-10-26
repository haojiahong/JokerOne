package cn.itcast.oa.domain.basemain;

import java.sql.Timestamp;
/**
 * 部门树表treeBean
 * @author haojiahong
 *
 */
public class EzOrgTreeBean extends EasyTree {

	private String groupCode;
	private String groupNameShort;
	private String groupName;
	private Long groupAtt;
	private String groupAttName;
	private Timestamp fromDate;
	private String remark;
	private String havaSub;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupNameShort() {
		return groupNameShort;
	}

	public void setGroupNameShort(String groupNameShort) {
		this.groupNameShort = groupNameShort;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Timestamp getFromDate() {
		return fromDate;
	}

	public void setFromDate(Timestamp fromDate) {
		this.fromDate = fromDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHavaSub() {
		return havaSub;
	}

	public void setHavaSub(String havaSub) {
		this.havaSub = havaSub;
	}

	public Long getGroupAtt() {
		return groupAtt;
	}

	public void setGroupAtt(Long groupAtt) {
		this.groupAtt = groupAtt;
	}

	public String getGroupAttName() {
		return groupAttName;
	}

	public void setGroupAttName(String groupAttName) {
		this.groupAttName = groupAttName;
	}

}
