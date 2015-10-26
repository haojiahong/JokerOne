package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.domain.Organization;

public interface OrganizationService  {
	
	public List<Organization> retrieveSubOrgLs(Long orgId);
	/**
	 * 查询所有部门
	 * @param orgId
	 * @return
	 */
	public List<Organization> retrieveSubOrgLsNoRight(Long orgId);

	/**
	 * 根据id查询部门，进行编辑
	 * @param orgId
	 * @return
	 */
	public Organization retrieveOne(Long orgId);
	/**
	 * 保存
	 * @param organization
	 */
	public void save(Organization organization);
	/**
	 * 删除
	 * @param orgId
	 */
	public Long remove(Long orgId);

}
