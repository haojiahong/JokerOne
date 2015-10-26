package cn.itcast.oa.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.model.SortParamList;

public abstract interface IDAO {
	/**
	 * find查询
	 * 
	 * @param jpql
	 * @param params
	 * @param sortParams
	 * @param pageInfo
	 * @return
	 */
	public abstract List find(String jpql, QueryParamList params, SortParamList sortParams, PageInfo pageInfo);

	/**
	 * 创建一条数据
	 * 
	 * @param paramT
	 */
	public abstract <T> void create(T paramT);

	/**
	 * 按照id查询数据
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public abstract <T> T loadById(Class<T> clazz, Serializable id);

	/**
	 * 更新数据
	 * 
	 * @param t
	 */
	public abstract <T> void update(T t);

	/**
	 * 
	 * @param t
	 */
	public abstract <T> void refresh(T t);

	/**
	 * 删除
	 * 
	 * @param clazz
	 * @param id
	 */
	public abstract <T> void remove(Class<T> clazz, Serializable id);

	/**
	 * 批量删除
	 * 
	 * @param entities
	 */
	public abstract <T> void remove(Collection<T> entities);

	public abstract void flush();

	/**
	 * 
	 * @param jpql
	 * @param params
	 * @return
	 */
	public abstract Long getTotalCount(String jpql, QueryParamList params);
}
