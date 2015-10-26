package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 权限管理(相当于菜单吧)
 * 
 * @author haojiahong
 * 
 * @createtime 2015-8-2
 */
@Entity
@Table(name = "my_privilege")
public class Privilege implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRIVILEGE_ID")
	private String privilegeId;
	@Column(name = "PRIVILEGE_NAME")
	private String privilegeName;
	@Column(name = "URL")
	private String url;
	@Column(name = "UP_PRIVILEGE_ID")
	private String upPrivilegeId;// 上级权限
	@Column(name = "HAVE_SUB")
	private String haveSub;// 是否有下级节点,树用

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UP_PRIVILEGE_ID", insertable = false, updatable = false)
	private Privilege upPrivilege;

	@OneToMany(mappedBy = "upPrivilege", cascade = CascadeType.REMOVE)
	private List<Privilege> subPrivilegeLs;

	@OneToMany(mappedBy = "privilege", cascade = CascadeType.REMOVE)
	private List<RolePrivilegeRelation> rolePrivilegeRelationLs;// 角色权限对应关系（一个多对多改成两个多对一）

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

	@JSON(serialize = false)
	public Privilege getUpPrivilege() {
		return upPrivilege;
	}

	public void setUpPrivilege(Privilege upPrivilege) {
		this.upPrivilege = upPrivilege;
	}

	@JSON(serialize = false)
	public List<RolePrivilegeRelation> getRolePrivilegeRelationLs() {
		return rolePrivilegeRelationLs;
	}

	public void setRolePrivilegeRelationLs(List<RolePrivilegeRelation> rolePrivilegeRelationLs) {
		this.rolePrivilegeRelationLs = rolePrivilegeRelationLs;
	}

	@JSON(serialize = false)
	public List<Privilege> getSubPrivilegeLs() {
		return subPrivilegeLs;
	}

	public void setSubPrivilegeLs(List<Privilege> subPrivilegeLs) {
		this.subPrivilegeLs = subPrivilegeLs;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	public String getUpPrivilegeId() {
		return upPrivilegeId;
	}

	public void setUpPrivilegeId(String upPrivilegeId) {
		this.upPrivilegeId = upPrivilegeId;
	}

	public String getHaveSub() {
		return haveSub;
	}

	public void setHaveSub(String haveSub) {
		this.haveSub = haveSub;
	}

}
