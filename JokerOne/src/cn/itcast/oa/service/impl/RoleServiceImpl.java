package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.oa.domain.Role;
import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.model.SortParamList;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.util.CommonUtil;
import cn.itcast.oa.util.JPAUtil;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Override
	public List<Role> retrieveAll(SortParamList sortParamList, PageInfo pageInfo) {
		String jpql = "select role from Role role where 1=1";
		List<Role> result = JPAUtil.find(jpql, null, sortParamList, pageInfo);
		return result;
	}

	@Override
	public void save(Role role) {
		if (CommonUtil.strIsNull(role.getRoleId())) {
			role.setRoleId(CommonUtil.genUUID());
			JPAUtil.create(role);
		} else {
			JPAUtil.update(role);
		}

	}

	@Override
	public Role retrieveOne(String roleId) {
		return JPAUtil.loadById(Role.class, roleId);
	}

	@Override
	public Long remove(Role role) {
		String jpql = "select user from User user where user.roleId =:roleId";
		QueryParamList params = new QueryParamList();
		params.addParam("roleId", role.getRoleId());
		Long num = JPAUtil.getTotalCount(jpql, params);
		if (num > 0) {
			return 1L;
		}
		JPAUtil.refresh(role);
		JPAUtil.remove(Role.class, role.getRoleId());
		return 0L;
	}
}
