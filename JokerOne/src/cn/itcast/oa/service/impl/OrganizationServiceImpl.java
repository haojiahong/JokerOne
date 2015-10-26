package cn.itcast.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.Organization;
import cn.itcast.oa.model.Constant;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.service.OrganizationService;
import cn.itcast.oa.util.JPAUtil;

@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	@Override
	public List<Organization> retrieveSubOrgLs(Long orgId) {
		return null;
	}

	@Override
	public List<Organization> retrieveSubOrgLsNoRight(Long orgId) {
		QueryParamList params = new QueryParamList();
		params.addParam("orgId", orgId);
		String jpql = "select org from Organization org "
				+ " where org.upOrganization.orgId=:orgId order by org.sortCode ";
		
		List<Organization> orgLs= JPAUtil.find(jpql, params,null,null);
		return orgLs;
	}

	@Override
	public Organization retrieveOne(Long orgId) {
		return JPAUtil.loadById(Organization.class, orgId);
	}

	@Override
	@Transactional
	public void save(Organization organization) {
		if(organization.getOrgId()==null){	
			if(organization.getSortCode()==null){
				organization.setSortCode(Constant.LAST_ORDER_NUM);
			}
			JPAUtil.create(organization);
			JPAUtil.flush();
			JPAUtil.refresh(organization);// 重新获取对象,原对象上级部门对象重新获取
			setUpOrgHavaSubTrue(organization);// 设置上级部门HAVA_SUB为true wxl
		}else{
			JPAUtil.update(organization);
		}
	}

	private void setUpOrgHavaSubTrue(Organization organization) {
		if (Constant.TREE_ROOT.equals(organization.getUpOrgId())) {
			return;
		}
		Organization upOrg = organization.getUpOrganization();
		upOrg.setHaveSub(Constant.YES);
		JPAUtil.update(upOrg);
		
	}

	@Override
	@Transactional
	public Long remove(Long orgId) {
		Organization org = this.retrieveOne(orgId);

		if (Constant.YES.equals(org.getHaveSub())) {
			return 1L;
		} else {
			if (!Constant.TREE_ROOT.equals(org.getUpOrgId())) {
				Organization upOrg = org.getUpOrganization();
				List<Organization> orgList = upOrg.getSubOrgLs();

				if (orgList.size() <= 1) {
					upOrg.setHaveSub(Constant.NO);
					JPAUtil.update(upOrg);
				}
			}
			JPAUtil.remove(Organization.class,org.getOrgId());
			return 0L;
		}
		
	}
	
	
}
