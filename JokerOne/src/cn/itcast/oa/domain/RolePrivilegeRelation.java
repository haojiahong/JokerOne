package cn.itcast.oa.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

/**
 * 权限与角色多对多中间表 中间表中含有两方的主键，所以可以吧中间表看成多的一方，将另两方看成一的一方 实现多对多变成两个多对一。
 * 
 * @author haojiahong
 * 
 * @createtime 2015-8-2
 */
@Entity
@Table(name = "my_role_privilege")
public class RolePrivilegeRelation implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ROLE_PRIVILEGE_ID")
	private String rolePrivilegeId;
	@Column(name = "ROLE_ID")
	private String roleId;
	@Column(name = "PRIVILEGE_ID")
	private String privilegeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
	private Role role;// 用户对应的角色(岗位)

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRIVILEGE_ID", insertable = false, updatable = false)
	private Privilege privilege;// 用户对应的权限

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}

	@JSON(serialize = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@JSON(serialize = false)
	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

	public String getRolePrivilegeId() {
		return rolePrivilegeId;
	}

	public void setRolePrivilegeId(String rolePrivilegeId) {
		this.rolePrivilegeId = rolePrivilegeId;
	}

}
