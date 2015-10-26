package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.domain.Role;
import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.SortParamList;

public interface RoleService  {
	
	public List<Role> retrieveAll(SortParamList sortParamList, PageInfo pageInfo);

	public void save(Role role);

	public Role retrieveOne(String roleid);

	public Long remove(Role role);


}
