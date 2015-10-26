package cn.itcast.oa.domain.basemain;

/**
 * 菜单树表treeBean
 * 
 * @author haojiahong
 * 
 */
public class EzPrivTreeBean extends EasyTree {

	private String privilegeId;
	private String privilegeName;
	private String url;
	private String havaSub;

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHavaSub() {
		return havaSub;
	}

	public void setHavaSub(String havaSub) {
		this.havaSub = havaSub;
	}

}
