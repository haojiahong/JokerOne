package cn.itcast.oa.domain.ezdrop;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.oa.domain.Role;
import cn.itcast.oa.domain.basemain.EasyUIDrop;
import cn.itcast.oa.service.RoleService;
import cn.itcast.oa.util.ApplicationUtil;

/**
 * 岗位下拉
 * 
 * @author haojiahong
 * 
 * @createtime 2015-8-2
 */
public class RoleDrop {

	private static RoleService roleService = (RoleService) ApplicationUtil
			.getBean("roleService");
	// 性别
	private static List<EasyUIDrop> roleList = genRoleList();

	public static List<EasyUIDrop> getRoleList() {
		return roleList;
	}

	private static List<EasyUIDrop> genRoleList() {
		List<EasyUIDrop> dropLs = new ArrayList<EasyUIDrop>();
		List<Role> roleLs = roleService.retrieveAll(null, null);
		for (Role role : roleLs) {
			dropLs.add(new EasyUIDrop(role.getRoleId() + "", role.getRoleName()));
		}

		return dropLs;
	}


}
