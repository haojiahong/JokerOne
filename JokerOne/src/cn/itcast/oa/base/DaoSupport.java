package cn.itcast.oa.base;

import java.util.List;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.model.QueryParamList;

public interface DaoSupport<T> {
	/**
	 * 保存
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	public void delete(Long id);

	/**
	 * 更新
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 查询
	 * 
	 * @param id
	 * @return
	 */
	public T getById(Long id);

	/**
	 * 批量查询
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> getByIds(Long[] ids);

	/**
	 * 全查
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 带有分页信息的查询
	 * 
	 * @param hql
	 * @param parameters
	 *            参数信息
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageBean getPageBeanForRetrieve(String hql, List<Object> parameters,
			int pageNum, int pageSize);

	/**
	 * 测试分页查询
	 * 
	 * @param jpql
	 * @param params
	 * @param pageBean
	 * @return
	 */
	public PageBean find(String jpql, QueryParamList params, int pageNum,
			int pageSize);
}
