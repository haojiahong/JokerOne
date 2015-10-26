package cn.itcast.oa.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.common.service.FileOperateService;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.domain.bean.UserInformationBean;
import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.model.SortParamList;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.util.CommonUtil;
import cn.itcast.oa.util.JPAUtil;
import cn.itcast.oa.util.JasperUtil;
import cn.itcast.oa.util.PoiExUtil;
import cn.itcast.oa.util.PoiImUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	FileOperateService fileOperateService;

	@Override
	public List<User> retrieveAll(String userNameSch, SortParamList sortParamList, PageInfo pageInfo) {
		String jpql = "select user from User user where 1=1";
		QueryParamList params = new QueryParamList();
		if (userNameSch != null && !"".equals(userNameSch)) {
			params.addParam("userNameSch", "%" + userNameSch + "%");
			jpql += " and user.userName like :userNameSch";
		}
		List<User> result = JPAUtil.find(jpql, params, sortParamList, pageInfo);
		return result;
	}

	@Override
	public void save(User user) {
		if (CommonUtil.strIsNull(user.getUserId())) {
			user.setUserId(CommonUtil.genUUID());
			JPAUtil.create(user);
		} else {
			JPAUtil.update(user);
		}

	}

	@Override
	public User retrieveOne(String userId) {
		return JPAUtil.loadById(User.class, userId);
	}

	@Override
	public void remove(User user) {
		JPAUtil.refresh(user);
		JPAUtil.remove(User.class, user.getUserId());

	}

	@Override
	@Transactional
	public void delUsers(String userIds) {
		List<String> userIdLs = CommonUtil.paraseStrs(userIds);
		for (String userId : userIdLs) {
			JPAUtil.remove(User.class, userId);
		}
	}

	@Override
	@Transactional
	public void saveUsers(List<User> userLs) {
		for (User user : userLs) {
			if (CommonUtil.strIsNull(user.getUserId())) {
				user.setUserId(CommonUtil.genUUID());
				JPAUtil.create(user);
			} else {
				JPAUtil.update(user);
			}
		}
	}

	@Override
	public InputStream exportExcel() throws Exception {
		PoiExUtil excelUtil = new PoiExUtil("基本信息");
		excelUtil.setColumnWidth(new int[] { 6, 6, 6, 4, 5, 15, 10, 6, 6, 6, 6, 8, 15, 8, 8, 10, 10, 10 });
		excelUtil.addHiddenValue(new String[] { "userName", "userDescription" });
		excelUtil.createHeaderRow();
		excelUtil.addHeaderValue(new String[] { "用户名称", "用户说明" });
		for (int i = 0; i < 152; i++) {
			excelUtil.createRow();

			excelUtil.addRequiredValue("");// 姓名
			excelUtil.addValue("");// 描述

		}

		// 创建名称管理器

		return excelUtil.write();
	}

	@Override
	public List<User> importExcel(String fileName) throws Exception {
		File file = fileOperateService.getFileFromServer(fileName, "fileTemp");
		PoiImUtil poi = new PoiImUtil();
		int rowsize = poi.importExcel(file);
		List<UserInformationBean> userBeans = new ArrayList<UserInformationBean>();
		poi.nextRow();
		poi.nextRow();
		for (int i = 2; i <= rowsize; i++) {
			UserInformationBean bean = this.genBean(poi);
			if (bean == null) {
				break;
			}
			userBeans.add(bean);
			poi.nextRow();
		}
		if (userBeans.size() > 150) {
			throw new Exception("一次最多可导入150条");
		}
		List<User> userLs = new ArrayList<User>();
		initBeanToList(userLs, userBeans);
		return userLs;
	}

	private void initBeanToList(List<User> userLs, List<UserInformationBean> userBeans) {
		for (UserInformationBean userBean : userBeans) {
			User user = new User();
			user.setUserName(userBean.getUserName());
			user.setUserDescription(userBean.getUserDescription());
			userLs.add(user);
		}
	}

	private UserInformationBean genBean(PoiImUtil poi) throws Exception {
		UserInformationBean bean = new UserInformationBean();
		try {
			// 姓名为空，跳出循环
			if (CommonUtil.strIsNull(poi.getStr("userName"))) {
				return null;
			}
			List<String> list = new ArrayList<String>();
			String userName = poi.getStr("userName");
			if (userName.getBytes().length > 20) {
				list.add("姓名超过最大长度20");
			}
			bean.setUserName(userName);
			String userDescription = poi.getStr("userDescription");
			if (!CommonUtil.strIsNull(userDescription)) {
				if (userDescription.getBytes().length > 20) {
					list.add("工号超过最大长度20");
				}
				bean.setUserDescription(userDescription);
			}

			if (list.size() > 0) {
				bean.setErrMsg(list.toString());
				throw new Exception(list.toString());
			} else {
				bean.setErrMsg("验证成功");
			}
		} catch (Exception e) {
			throw new Exception("模版文件错误，请重新导出！");
		}
		return bean;
	}

	@Override
	public void initPassword(User user) {
		String md5Digest = DigestUtils.md5Hex("1234");
		user.setPassword(md5Digest);
		JPAUtil.update(user);
	}

	@Override
	public User findByLoginNameAndPassword(String loginName, String loginPassword) {
		// 将用户输入的明文密码加密后数据库对比
		String md5Digest = DigestUtils.md5Hex(loginPassword);
		String jpql = "select user from User user "
				+ " where user.loginName =:loginName and user.password =:loginPassword";
		QueryParamList params = new QueryParamList();
		params.addParam("loginName", loginName);
		params.addParam("loginPassword", md5Digest);
		List<User> userLs = JPAUtil.find(jpql, params, null, null);
		if (userLs.size() > 0) {
			return userLs.get(0);
		}
		return null;
	}

	@Override
	public InputStream genPdf() {
		List<User> userLs = this.retrieveAll(null, null, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("printDate", CommonUtil.getChineseDate());

		return JasperUtil.exportPdfDir("materialCost.jasper", map, userLs);
	}

	@Override
	public Long retrieveUserMale() {
		String jpql = "select count(user.userId) from User user where user.gender = 1";
		List<Object> userLs = JPAUtil.find(jpql, null, null, null);
		Long maleNum = null;
		if (userLs.size() > 0) {
			maleNum = (Long) userLs.get(0);

		}
		return maleNum;
	}

	@Override
	public Object retrieveUserFemale() {
		String jpql = "select count(user.userId) from User user where user.gender = 2";
		List<Object> userLs = JPAUtil.find(jpql, null, null, null);
		Long femaleNum = null;
		if (userLs.size() > 0) {
			femaleNum = (Long) userLs.get(0);

		}
		return femaleNum;
	}
}
