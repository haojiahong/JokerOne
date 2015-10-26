package cn.itcast.oa.service;

import java.io.InputStream;
import java.util.List;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.SortParamList;

public interface UserService {

	/**
	 * 查询所有用户
	 * 
	 * @param userNameSch
	 * @param sortParamList
	 * @param pageInfo
	 * @return
	 */
	public List<User> retrieveAll(String userNameSch, SortParamList sortParamList, PageInfo pageInfo);

	/**
	 * 保存用户
	 * 
	 * @param user
	 */
	public void save(User user);

	/**
	 * 根据id查用户
	 * 
	 * @param userid
	 * @return
	 */
	public User retrieveOne(String userid);

	public void remove(User user);

	/**
	 * 导出excel模板
	 * 
	 * @return
	 * @throws Exception
	 */
	public InputStream exportExcel() throws Exception;

	/**
	 * 通过excel批量导入用户
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public List<User> importExcel(String fileName) throws Exception;

	/**
	 * 批量删除用户
	 * 
	 * @param userIds
	 */
	public void delUsers(String userIds);

	/**
	 * 批量添加用户
	 * 
	 * @param userLs
	 */
	public void saveUsers(List<User> userLs);

	/**
	 * 初始化密码
	 * 
	 * @param model
	 */
	public void initPassword(User model);

	/**
	 * 根据登录名与密码查询用户
	 * 
	 * @param loginName
	 * @param loginPassword
	 * @return
	 */
	public User findByLoginNameAndPassword(String loginName, String loginPassword);

	/**
	 * 用户打印
	 * 
	 * @return
	 */
	public InputStream genPdf();

	/**
	 * 查询男性用户的数量(echarts的使用)
	 * 
	 * @return
	 */
	public Object retrieveUserMale();

	/**
	 * 查询女性用户的数量
	 * 
	 * @return
	 */
	public Object retrieveUserFemale();

}
