package cn.itcast.oa.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.itcast.oa.base.EasyGridAction;
import cn.itcast.oa.domain.Organization;
import cn.itcast.oa.domain.basemain.EasyGridData;
import cn.itcast.oa.domain.basemain.EasyUIDrop;
import cn.itcast.oa.domain.ezdrop.EzOrgDrop;
import cn.itcast.oa.service.OrganizationService;
import cn.itcast.oa.util.CommonUtil;

@Namespace("/oa")
@ParentPackage("json-default")
@Action(value = "/ezOrgDetailAction")
@Results({
		@Result(name = "droplist", type = "json", params = { "root", "droplist" }),
		@Result(name = "detail", location = "orgdetailez.jsp") })
public class EzOrgDetailAction extends EasyGridAction<Organization> {

	private static final long serialVersionUID = 1L;

	@Resource
	private OrganizationService organizationService;

	private EasyGridData<Organization> gridData = new EasyGridData<Organization>();
	private Organization organization = new Organization();

	private List<EasyUIDrop> droplist = new ArrayList<EasyUIDrop>();

	public String droplist() {
		droplist.addAll(EzOrgDrop.getGroupAttList());
		return "droplist";
	}

	public String add() {
		organization.setFromDate(CommonUtil.getDate());
		return "detail";
	}

	public String load() {
		organization = organizationService.retrieveOne(organization.getOrgId());
		return "detail";
	}

	public String save() {
		organizationService.save(organization);
		this.msg = "保存成功";
		return "msg";
	}

	public String delete() {
		Long num = organizationService.remove(organization.getOrgId());
		if(num==0L){
			this.msg = "删除成功！";
		}else{
			this.msg="此部门有下级部门，请先删除下级部门！";
		}
		return "msg";
	}

	@Override
	public Organization getModel() {

		return organization;// 正好将返回的对象赋值给了organization
	}

	public EasyGridData<Organization> getGridData() {
		return gridData;
	}

	public void setGridData(EasyGridData<Organization> gridData) {
		this.gridData = gridData;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public List<EasyUIDrop> getDroplist() {
		return droplist;
	}

	public void setDroplist(List<EasyUIDrop> droplist) {
		this.droplist = droplist;
	}

}
