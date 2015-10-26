package cn.itcast.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.RolePrivilegeRelation;
import cn.itcast.oa.domain.basemain.Attributes;
import cn.itcast.oa.domain.basemain.EasyTree;
import cn.itcast.oa.model.Constant;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.util.CommonUtil;
import cn.itcast.oa.util.JPAUtil;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Override
	public List<Privilege> findTopList() {
		String jpql = "select p from Privilege p where 1=1 and p.upPrivilegeId is null ";
		List<Privilege> priviLs = JPAUtil.find(jpql, null, null, null);
		return priviLs;
	}

	@Override
	public List<String> getAllPrivilegeUrls() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Privilege> findSubList(String menuId) {
		String jpql = "select p from Privilege p where p.upPrivilegeId =:menuId";
		QueryParamList params = new QueryParamList();
		Long id = Long.valueOf(menuId);
		params.addParam("menuId", id);
		List<Privilege> subPrivilegeLs = JPAUtil.find(jpql, params, null, null);
		return subPrivilegeLs;
	}

	/**
	 * 左侧菜单树。
	 */
	@Override
	public List<EasyTree> findMenuTree(String id, String roleId) {
		List<EasyTree> treeLs = new ArrayList<EasyTree>();
		String jpql = null;
		QueryParamList params = new QueryParamList();
		if (CommonUtil.strIsNull(id)) {
			jpql = "select privilege from Privilege privilege join privilege.rolePrivilegeRelationLs relaLs "
					+ " where privilege.upPrivilegeId is null  and relaLs.roleId =:roleId";
			params.addParam("roleId", roleId);
		} else {
			jpql = "select p from Privilege p join p.rolePrivilegeRelationLs relaLs "
					+ " where p.upPrivilegeId =:id and relaLs.roleId =:roleId";
			params.addParam("id", id);
			params.addParam("roleId", roleId);
		}
		List<Privilege> pLs = JPAUtil.find(jpql, params, null, null);
		for (Privilege p : pLs) {
			EasyTree tree = new EasyTree();
			tree.setId(p.getPrivilegeId());
			tree.setText(p.getPrivilegeName());
			Attributes attr = new Attributes();
			if (!CommonUtil.strIsNull(p.getUrl())) {
				attr.setUrl(p.getUrl());
			} else {
				attr.setUrl("");
			}
			tree.setAttributes(attr);
			if (p.getSubPrivilegeLs().size() > 0) {
				tree.setState("closed");
			} else {
				tree.setState("open");
			}
			treeLs.add(tree);
		}
		return treeLs;
	}

	/**
	 * 菜单树表中查询下级菜单
	 */
	@Override
	public List<Privilege> retrieveSubPrivLs(String id) {
		String jpql = "select priv from Privilege priv where priv.upPrivilegeId=:id";
		QueryParamList params = new QueryParamList();
		params.addParam("id", id);

		List<Privilege> privLs = JPAUtil.find(jpql, params, null, null);
		return privLs;
	}

	@Override
	public Privilege retrieveOne(String privilegeId) {
		return JPAUtil.loadById(Privilege.class, privilegeId);
	}

	@Override
	@Transactional
	public void save(Privilege privilege) {
		if (CommonUtil.strIsNull(privilege.getPrivilegeId())) {
			privilege.setPrivilegeId(CommonUtil.genUUID());
			JPAUtil.create(privilege);
			JPAUtil.flush();// 刷新内存
			JPAUtil.refresh(privilege);// 重新加载privilege
			setUpPrivHavaSubTrue(privilege);
		} else {
			JPAUtil.update(privilege);
		}
	}

	private void setUpPrivHavaSubTrue(Privilege privilege) {
		if (Constant.TREE_ROOT_PRIV.equals(privilege.getUpPrivilegeId())) {
			return;
		}
		Privilege upPriv = privilege.getUpPrivilege();
		upPriv.setHaveSub(Constant.YES);
		JPAUtil.update(upPriv);

	}

	@Override
	public Long remove(String privilegeId) {
		Privilege priv = this.retrieveOne(privilegeId);

		if (Constant.YES.equals(priv.getHaveSub())) {
			return 1L;
		} else {
			if (!Constant.TREE_ROOT_PRIV.equals(priv.getUpPrivilegeId())) {
				Privilege upPriv = priv.getUpPrivilege();
				List<Privilege> privList = upPriv.getSubPrivilegeLs();

				if (privList.size() <= 1) {
					upPriv.setHaveSub(Constant.NO);
					JPAUtil.update(upPriv);
				}
			}
			JPAUtil.remove(Privilege.class, priv.getPrivilegeId());
			return 0L;
		}
	}

	@Override
	public List<EasyTree> getResourcesTree() {
		String jpql = "select priv from Privilege priv where 1=1";
		List<EasyTree> privLs = JPAUtil.find(jpql, null, null, null);
		return privLs;
	}

	@Override
	public List<Privilege> getResources(String roleId) {
		String jpql = "select priv from Privilege priv join priv.rolePrivilegeRelationLs rolePriv "
				+ " join rolePriv.role role where 1=1 and role.roleId =:roleId";
		QueryParamList params = new QueryParamList();
		params.addParam("roleId", roleId);
		List<Privilege> privLs = JPAUtil.find(jpql, params, null, null);
		return privLs;
	}

	@Override
	@Transactional
	public void grant(String roleId, String privIds) {
		String jpqlRela = "select rela from RolePrivilegeRelation rela where rela.roleId =:roleId";
		QueryParamList relaParams = new QueryParamList();
		relaParams.addParam("roleId", roleId);
		List<RolePrivilegeRelation> relaLs = JPAUtil.find(jpqlRela, relaParams, null, null);
		if (relaLs.size() > 0) {
			JPAUtil.remove(relaLs);
		}
		List<String> privIdLs = CommonUtil.paraseStrs(privIds);
		if (privIdLs.size() > 0) {
			for (String privilegeId : privIdLs) {
				RolePrivilegeRelation rela = new RolePrivilegeRelation();
				rela.setRolePrivilegeId(CommonUtil.genUUID());
				rela.setRoleId(roleId);
				rela.setPrivilegeId(privilegeId);
				JPAUtil.create(rela);
			}
		}

	}
}
