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

@Entity
@Table(name = "my_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "USER_DESCRIPTION")
	private String userDescription;

	@Column(name = "LOGIN_NAME")
	private String loginName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ORG_ID")
	private Long orgId;// 用户对应的部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORG_ID", insertable = false, updatable = false)
	private Organization organization;// 用户对应的部门

	@Column(name = "ROLE_ID")
	private String roleId;// 用户对应的岗位

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", insertable = false, updatable = false)
	private Role role;// 用户对应的岗位

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	@JSON(serialize = false)
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@JSON(serialize = false)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
