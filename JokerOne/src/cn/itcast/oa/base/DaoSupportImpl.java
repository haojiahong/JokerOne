package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.domain.PageBean;
import cn.itcast.oa.model.QueryParam;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.util.JpqlUtil;

//transational 可以被继承
@Transactional
@SuppressWarnings("unchecked")
public class DaoSupportImpl<T> implements DaoSupport<T> {
	@Resource
	private SessionFactory sessionFactory;
	private Class<T> clazz;

	public DaoSupportImpl() {
		// 使用反射得到T的真是类型
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();// 获取当前new的对象的 泛型父类 类型。
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
																// User或者Role
	}

	/**
	 * 获取当前可用session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T entity) {
		getSession().save(entity);

	}

	public void delete(Long id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}

	}

	public void update(T entity) {
		getSession().update(entity);

	}

	public T getById(Long id) {
		return (T) getSession().get(this.clazz, id);
	}

	public List<T> getByIds(Long[] ids) {
		if (ids.length == 0 || ids == null) {
			return Collections.EMPTY_LIST;
		} else {
			return getSession()
					.createQuery(
							"from " + this.clazz.getSimpleName()
									+ " where id in(:ids)")
					.setParameterList("ids", ids).list();
		}
	}

	public List<T> findAll() {
		return getSession().createQuery("from " + this.clazz.getSimpleName())
				.list();
	}

	public PageBean getPageBeanForRetrieve(String hql, List<Object> parameters,
			int pageNum, int pageSize) {
		Query query = getSession().createQuery(hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				query.setParameter(i, parameters.get(i));

			}
		}
		query.setFirstResult((pageNum - 1) * pageSize);
		query.setMaxResults(pageSize);
		List list = query.list();
		// 查询总数量
		Query countQuery = getSession().createQuery("select count(*) " + hql);
		if (parameters != null) {
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult();
		return new PageBean(pageNum, pageSize, count.intValue(), list);
	}

	@SuppressWarnings("unchecked")
	public PageBean find(String jpql, QueryParamList params, int pageNum,
			int pageSize) {
		List<Object> returnList = new ArrayList<Object>();
		Long count = Long.valueOf(0);
		int totalCount = 0;
		try {
			// // 增加OrderBy条件
			// if (sortParams != null && sortParams.getParams().size() > 0) {
			// tempjpql = JpqlUtil.addSortParam(tempjpql, sortParams);
			// }
			Query query = getSession().createQuery(jpql);

			QueryParamList allParams = new QueryParamList();
			allParams.addParamList(params);

			setQueryParamList(query, allParams);
			// 处理分页
			query.setFirstResult((pageNum - 1) * pageSize);
			query.setMaxResults(pageSize);
			returnList = query.list();
			//查询总数量
			String pageJpql = JpqlUtil.deleteOuterOrderBy(jpql);
			Query pageQuery = getSession().createQuery(pageJpql);
//			pageQuery.setCacheable(true);
			setQueryParamList(pageQuery, allParams);
			ScrollableResults scrollCursor = pageQuery.scroll(ScrollMode.SCROLL_INSENSITIVE);
			scrollCursor.last();
			totalCount = Integer.valueOf(scrollCursor.getRowNumber() + 1);
//			 count = (Long) pageQuery.uniqueResult();
		} catch (Throwable cause) {
			new Exception("采用JPQL查询出错!" + cause.getMessage(), cause);
		}
		
		return new PageBean(pageNum, pageSize, totalCount, returnList);
	}

	private void setQueryParamList(Query query, QueryParamList paramLs) {
		if (query != null && paramLs != null && paramLs.getParams().size() > 0) {
			List<QueryParam> list = paramLs.getParams();
			for (int i = 0; i < list.size(); i++) {
				QueryParam param = (QueryParam) list.get(i);
				if (param == null) {
					continue;
				}
				setQueryParam(query, param);
			}
		}
	}

	private void setQueryParam(Query query, QueryParam param) {
		if (param.getValue() instanceof Collection) {
			query.setParameterList(param.getName(), (List) param.getValue());
		} else {
			query.setParameter(param.getName(), param.getValue());
		}
	}
}
