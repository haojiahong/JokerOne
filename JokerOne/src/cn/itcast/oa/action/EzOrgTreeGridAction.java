package cn.itcast.oa.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.domain.Organization;
import cn.itcast.oa.domain.basemain.EzOrgTreeBean;
import cn.itcast.oa.domain.ezdrop.EzOrgDrop;
import cn.itcast.oa.model.Constant;
import cn.itcast.oa.service.OrganizationService;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/oa")
@ParentPackage("json-default")
@Action(value = "/ezOrgTreeGridAction")
@Results({
	@Result(name = "success", type = "json",params = { "root", "ezTree" })
})
public class EzOrgTreeGridAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private OrganizationService organizationService;

	private List<EzOrgTreeBean> ezTree = new ArrayList<EzOrgTreeBean>();
	private Long id;//ezUi默认的提交
	public String execute() {
		EzOrgTreeBean treeBean = new EzOrgTreeBean();
		if (id == null) {//全加载树
			id = Constant.TREE_ROOT;
			treeBean.setId(Constant.TREE_ROOT);
			treeBean.setText("组织机构");
			treeBean.setState("open");
			treeBean.setIconCls("icon-org");
			treeBean.setHavaSub(Constant.YES);// 为了效率,默认有下级节点
			ezTree.add(treeBean);
			initChild(treeBean, id + "");//默认2层
		}
		return SUCCESS;
	}

	private void initChild(EzOrgTreeBean zt, String id) {
		
		List<Organization> orgList = organizationService.retrieveSubOrgLsNoRight(Long.valueOf(id));
		
		for (Organization sub : orgList) {
			EzOrgTreeBean treeBean = new EzOrgTreeBean();
			treeBean.setId(sub.getOrgId());
			treeBean.setText(sub.getGroupName());
			treeBean.setIconCls("icon-org");
			treeBean.setGroupCode(sub.getGroupCode());
			treeBean.setFromDate(sub.getFromDate());
			treeBean.setGroupNameShort(sub.getGroupNameShort());
			treeBean.setRemark(sub.getRemark());
			treeBean.setHavaSub(sub.getHaveSub());
			treeBean.setGroupAttName(EzOrgDrop.retrieveGroupAttName(sub.getGroupAtt()));
			if (Constant.YES.equals(sub.getHaveSub())) {
				treeBean.setState("closed");
				
				this.initChild(treeBean, treeBean.getId());
			}
			zt.getChildren().add(treeBean);
			
			
		}

	}

	public List<EzOrgTreeBean> getEzTree() {
		return ezTree;
	}

	public void setEzTree(List<EzOrgTreeBean> ezTree) {
		this.ezTree = ezTree;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
