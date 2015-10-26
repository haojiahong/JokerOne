package cn.itcast.oa.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.basemain.EzPrivTreeBean;
import cn.itcast.oa.model.Constant;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.util.CommonUtil;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 菜单树表的维护action
 * 
 * @author haojiahong
 * 
 * @createtime 2015-9-19
 */
@Namespace("/oa")
@ParentPackage("json-default")
@Action(value = "/ezPrivTreeGridAction")
@Results({ @Result(name = "success", type = "json", params = { "root", "ezTree" }) })
public class EzPrivTreeGridAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PrivilegeService privilegeService;

	private List<EzPrivTreeBean> ezTree = new ArrayList<EzPrivTreeBean>();
	private String id;// ezUi默认的提交

	public String execute() {
		EzPrivTreeBean treeBean = new EzPrivTreeBean();
		if (CommonUtil.strIsNull(id)) {// 全加载树
			id = Constant.TREE_ROOT_PRIV;
			treeBean.setId(Constant.TREE_ROOT_PRIV);
			treeBean.setText("首页");
			treeBean.setState("open");
			treeBean.setHavaSub(Constant.YES);// 为了效率,默认有下级节点
			ezTree.add(treeBean);
			initChild(treeBean, id + "");// 默认2层
		}
		return SUCCESS;
	}

	private void initChild(EzPrivTreeBean zt, String id) {

		List<Privilege> privList = privilegeService.retrieveSubPrivLs(id);

		for (Privilege sub : privList) {
			EzPrivTreeBean treeBean = new EzPrivTreeBean();
			treeBean.setId(sub.getPrivilegeId());
			treeBean.setText(sub.getPrivilegeName());
			
			treeBean.setPrivilegeId(sub.getPrivilegeId());
			treeBean.setPrivilegeName(sub.getPrivilegeName());
			treeBean.setUrl(sub.getUrl());
			treeBean.setHavaSub(sub.getHaveSub());
			if (Constant.YES.equals(sub.getHaveSub())) {
				treeBean.setState("closed");

				this.initChild(treeBean, treeBean.getId());
			}
			zt.getChildren().add(treeBean);

		}

	}

	public List<EzPrivTreeBean> getEzTree() {
		return ezTree;
	}

	public void setEzTree(List<EzPrivTreeBean> ezTree) {
		this.ezTree = ezTree;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
