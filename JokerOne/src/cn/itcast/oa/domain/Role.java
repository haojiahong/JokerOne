package cn.itcast.oa.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 岗位（在这里同时看做角色）管理
 * 
 * @author haojiahong
 * 
 * @createtime 2015-8-2
 */
@Entity
@Table(name = "my_role")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ROLE_ID")
	private String roleId;
	@Column(name = "ROLE_NAME")
	private String roleName;
	@Column(name = "ROLE_DESCRIPTION")
	private String roleDescription;

	@OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
	private List<User> userLs;// 用户

	@OneToMany(mappedBy = "role", cascade = CascadeType.REMOVE)
	private List<RolePrivilegeRelation> rolePrivilegeRelationLs;// 角色权限对应关系（一个多对多改成两个多对一）

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	@JSON(serialize = false)
	public List<User> getUserLs() {
		return userLs;
	}

	public void setUserLs(List<User> userLs) {
		this.userLs = userLs;
	}

	@JSON(serialize = false)
	public List<RolePrivilegeRelation> getRolePrivilegeRelationLs() {
		return rolePrivilegeRelationLs;
	}

	public void setRolePrivilegeRelationLs(List<RolePrivilegeRelation> rolePrivilegeRelationLs) {
		this.rolePrivilegeRelationLs = rolePrivilegeRelationLs;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
