package cn.itcast.oa.service;

import java.util.List;

import cn.itcast.oa.domain.Privilege;
import cn.itcast.oa.domain.basemain.EasyTree;

public interface PrivilegeService {

	/**
	 * 查询顶级菜单，下面没有url的
	 * 
	 * @return
	 */
	public List<Privilege> findTopList();

	public List<String> getAllPrivilegeUrls();

	/**
	 * 根据menuId查询对应的子菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public List<Privilege> findSubList(String menuId);

	/**
	 * 查询菜单树
	 * 
	 * @param id
	 * @param roleId 
	 * @return
	 */
	public List<EasyTree> findMenuTree(String id, String roleId);

	/**
	 * 菜单树表中查询下级菜单
	 * 
	 * @param id
	 * @return
	 */
	public List<Privilege> retrieveSubPrivLs(String id);

	/**
	 * 查询某一个菜单
	 * 
	 * @param privilegeId
	 * @return
	 */
	public Privilege retrieveOne(String privilegeId);

	public void save(Privilege privilege);

	public Long remove(String privilegeId);

	/**
	 * 角色菜单授权时，查询菜单树列表
	 * 
	 * @return
	 */
	public List<EasyTree> getResourcesTree();

	/**
	 * 角色菜单授权时，查询此角色拥有的菜单权限
	 * 
	 * @return
	 */
	public List<Privilege> getResources(String roleId);

	/**
	 * 角色授权
	 * 
	 * @param roleId
	 * @param privIds
	 */
	public void grant(String roleId, String privIds);

}
