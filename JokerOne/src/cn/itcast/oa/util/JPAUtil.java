package cn.itcast.oa.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.base.IDAO;
import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.model.SortParamList;

public class JPAUtil {
	private static ThreadLocal<Boolean> useQueryCache = new ThreadLocal<Boolean>();// 启用查询缓存
	private static ThreadLocal<Boolean> rightFilter = new ThreadLocal<Boolean>();// 启用权限过滤

	public static boolean ifUseQueryCache() {
		if (useQueryCache.get() == null)
			return false;
		return useQueryCache.get();

	}

	public static void enableCache() {
		useQueryCache.set(true);
	}

	public static void disableCache() {
		useQueryCache.set(false);
	}

	public static boolean ifUseFilter() {
		if (rightFilter.get() == null)
			return true;
		return rightFilter.get();

	}

	public static void enableRight() {
		rightFilter.set(true);
	}

	public static void disableRight() {
		rightFilter.set(false);
	}

	// 这是第一种拿到applicationContext的方法。(通过applicationContextAware)
	// 静态属性，spring初始化MyApplicationContextUtil的时候，将applicationContext属性set到这里面了。
	/*
	 * private static ApplicationContext applicationContext;
	 * 
	 * public static Object getBean(String beanName) { return
	 * getCtx().getBean(beanName); }
	 * 
	 * public static void setApplicationContext(ApplicationContext ctx) {
	 * applicationContext = ctx; }
	 * 
	 * private static ApplicationContext getCtx() { return applicationContext; }
	 */

	private static IDAO dao = (IDAO) ApplicationUtil.getBean("baseDao");
	// 这是第二种拿到applicationContext的方法
	// private static ApplicationContext ac = new
	// ClassPathXmlApplicationContext(
	// "applicationContext.xml");

	/**
	 * find条件查询数据带分页排序
	 * 
	 * @param jpql
	 * @param params
	 * @param sortParams
	 * @param pageInfo
	 * @return
	 */
	public static <T> List<T> find(String jpql, QueryParamList params,
			SortParamList sortParams, PageInfo pageInfo) {
		// IDAO dao = null;
		// try {
		// dao = (IDAO) getCtx().getBean("baseDao");
		// } catch (BeansException e) {
		// e.printStackTrace();
		// }
		return dao.find(jpql, params, sortParams, pageInfo);
	}

	/**
	 * 创建一条数据
	 * 
	 * @param t
	 */
	public static <T> void create(T t) {
		dao.create(t);
	}

	/**
	 * 根据id查询数据
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static <T> T loadById(Class<T> clazz, Serializable id) {
		return dao.loadById(clazz, id);
	}

	/**
	 * 更新数据
	 * 
	 * @param t
	 */
	public static <T> void update(T t) {
		dao.update(t);
	}

	/**
	 * 
	 * @param t
	 */
	public static <T> void refresh(T t) {
		dao.refresh(t);
	}

	/**
	 * 删除数据
	 * 
	 * @param clazz
	 * @param id
	 */
	public static <T> void remove(Class<T> clazz, Serializable id) {
		dao.remove(clazz, id);
	}
	
	public static <T> void remove(Collection<T> entities){
		dao.remove(entities);
	}

	public static void flush() {
		dao.flush();
	}

	/**
	 * 
	 * @param jpql
	 * @param params
	 * @return
	 */
	public static Long getTotalCount(String jpql, QueryParamList params) {
		return dao.getTotalCount(jpql, params);
	}

}
