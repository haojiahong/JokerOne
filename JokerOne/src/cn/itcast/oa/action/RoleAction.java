package cn.itcast.oa.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.base.EasyGridAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.basemain.EasyGridData;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.RoleService;

@Namespace("/oa")
@ParentPackage("json-default")
@Action(value = "/roleAction")
@Results({ @Result(name = "success", type = "json", params = { "root", "gridData" }),
		@Result(name = "resources", type = "json", params = { "root", "privLs" }),
		@Result(name = "detail", location = "roleadd.jsp"), @Result(name = "privilege", location = "privilegeset.jsp") })
public class RoleAction extends EasyGridAction<Role> {

	private static final long serialVersionUID = 1L;

	@Resource
	private RoleService roleService;
	@Autowired
	private PrivilegeService privilegeService;

	private EasyGridData<Role> gridData = new EasyGridData<Role>();
	private Role role = new Role();
	private String roleId;
	private List<Privilege> privLs;
	private String privIds;//给角色赋值菜单是选中的菜单ids

	@Override
	public String execute() throws Exception {
		gridData.setRows(roleService.retrieveAll(getSortInfo(), getPageInfo()));
		gridData.setTotal(getPageInfo().getAllRowNum());
		return "success";

	}

	public String add() {
		return "detail";
	}

	public String save() {
		roleService.save(model);
		this.msg = "保存成功";
		return "msg";
	}

	public String load() {
		role = roleService.retrieveOne(model.getRoleId());
		return "detail";
	}

	public String del() {
		Long num = roleService.remove(model);
		if (num == 0) {
			this.msg = "删除成功";
		} else {
			this.msg = "岗位下存在人员，不允许删除";
		}
		return "msg";
	}

	public String setPrivilege() {

		return "privilege";
	}

	/**
	 * 查询对应角色拥有的菜单权限
	 * 
	 * @return
	 */
	public String getResources() {
		privLs = privilegeService.getResources(model.getRoleId());
		return "resources";
	}

	public String grant(){
		privilegeService.grant(model.getRoleId(),privIds);
		return "msg";
	}
	
	public EasyGridData<Role> getGridData() {
		return gridData;
	}

	public void setGridData(EasyGridData<Role> gridData) {
		this.gridData = gridData;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public List<Privilege> getPrivLs() {
		return privLs;
	}

	public void setPrivLs(List<Privilege> privLs) {
		this.privLs = privLs;
	}

	public String getPrivIds() {
		return privIds;
	}

	public void setPrivIds(String privIds) {
		this.privIds = privIds;
	}

}
