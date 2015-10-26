package cn.itcast.oa.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.itcast.oa.base.EasyGridAction;
import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.basemain.EasyGridData;
import cn.itcast.oa.service.PrivilegeService;

@Namespace("/oa")
@ParentPackage("json-default")
@Action(value = "/ezPrivDetailAction")
@Results({ @Result(name = "detail", location = "privdetailez.jsp") })
public class EzPrivDetailAction extends EasyGridAction<Privilege> {

	private static final long serialVersionUID = 1L;

	@Resource
	private PrivilegeService privilegeService;

	private EasyGridData<Privilege> gridData = new EasyGridData<Privilege>();
	private Privilege privilege = new Privilege();

	public String add() {
		return "detail";
	}

	public String load() {
		privilege = privilegeService.retrieveOne(privilege.getPrivilegeId());
		return "detail";
	}

	public String save() {
		privilegeService.save(privilege);
		this.msg = "保存成功";
		return "msg";
	}

	public String delete() {
		Long num = privilegeService.remove(privilege.getPrivilegeId());
		if (num == 0L) {
			this.msg = "删除成功！";
		} else {
			this.msg = "此部门有下级部门，请先删除下级部门！";
		}
		return "msg";
	}

	@Override
	public Privilege getModel() {

		return privilege;// 正好将返回的对象赋值给了privilege
	}

	public EasyGridData<Privilege> getGridData() {
		return gridData;
	}

	public void setGridData(EasyGridData<Privilege> gridData) {
		this.gridData = gridData;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

}
