package cn.itcast.oa.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.QueryParam;
import cn.itcast.oa.model.QueryParamList;
import cn.itcast.oa.model.SortParamList;
import cn.itcast.oa.util.JPAUtil;
import cn.itcast.oa.util.JpqlUtil;

@Service("baseDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
public class BaseDao implements IDAO {
	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 获取当前可用session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public List find(String jpql, QueryParamList params,
			SortParamList sortParams, PageInfo pageInfo) {
		List<Object> returnList = new ArrayList<Object>();
		try {
			// 增加OrderBy条件
			if (sortParams != null && sortParams.getParams().size() > 0) {
				jpql = JpqlUtil.addSortParam(jpql, sortParams);
			}
			Query query = getSession().createQuery(jpql);
			// if (ThreadLocalStore.ifUseQueryCache()) {
			// query.setCacheable(true);
			// }

			QueryParamList allParams = new QueryParamList();
			allParams.addParamList(params);
			// allParams.addParamList(extParams);
			// if (pageInfo != null) {->wxl
			// String querySql =
			// convertJpqlToSql(JpqlUtil.deleteOuterOrderBy(tempjpql));
			// QueryParamList newParams = JpqlUtil.getParamInfo(tempjpql,
			// allParams);
			// querySql = JpqlUtil.replaceSqlParam(querySql, newParams);
			// pageQuery = getSession().createSQLQuery(querySql);
			// }
			setQueryParamList(query, allParams);
			// 处理分页
			if (pageInfo != null) {
				if (pageInfo.getStartIndex() != null) {// new Grid不查询总数
					query.setFirstResult(pageInfo.getStartIndex());
					query.setMaxResults(pageInfo.getEndIndex()
							- pageInfo.getStartIndex());
				} else {

					// 设置总行数
					// 使用游标形式计算总页数,效率会稍慢,大数据必须单独写查询总数sql
					int totalCount = 0;
					if (pageInfo.getPageHql() == null) {// pageInfo增加了自定义分页(使用hibernate的游标进行查询总数，效率较慢)
						String pageJpql = JpqlUtil.deleteOuterOrderBy(jpql);
						Query pageQuery = getSession().createQuery(pageJpql);
						setQueryParamList(pageQuery, allParams);
						ScrollableResults scrollCursor = pageQuery
								.scroll(ScrollMode.SCROLL_SENSITIVE);
						scrollCursor.last();
						totalCount = Integer.valueOf(scrollCursor
								.getRowNumber() + 1);
					} else {// PageInfo有hql,使用自定义hql(手动分页,查询总数的效率比hibernate快)
						Query innPageQuery = getSession().createQuery(
								pageInfo.getPageHql());
						innPageQuery.setCacheable(true);// 使用缓存
						setQueryParamList(innPageQuery,
								pageInfo.getPageParams());
						totalCount = Integer.valueOf(innPageQuery
								.uniqueResult() + "");
					}
					pageInfo.setAllRowNum(totalCount);

					// 只有大于0才分页
					if (pageInfo.getRowOfPage() > 0) {
						int startRowNo = 0;
						if (totalCount == 0) {
							pageInfo.setAllPageNum(0);
							pageInfo.setCurPageNum(0);
						} else {
							// 设置总页数
							int tpPageNum = totalCount
									% pageInfo.getRowOfPage();
							int allPageNum = (totalCount - tpPageNum)
									/ pageInfo.getRowOfPage();
							if (tpPageNum > 0) {
								allPageNum = allPageNum + 1;
							}
							pageInfo.setAllPageNum(allPageNum);

							if (pageInfo.getCurPageNum() == 0) {
								pageInfo.setCurPageNum(1);
							}
							startRowNo = (pageInfo.getCurPageNum() - 1)
									* pageInfo.getRowOfPage();
							/* 如果指定的页面没有数据，则检索第一页的数据 */
							if (totalCount <= startRowNo) {
								pageInfo.setCurPageNum(1);
								startRowNo = 0;
							}
						}

						query.setFirstResult(startRowNo);
						query.setMaxResults(pageInfo.getRowOfPage());

					}

				}
			}
			returnList = query.list();
		} catch (Throwable cause) {
			new Exception("采用JPQL查询出错!" + cause.getMessage(), cause);
		}
		return returnList;
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

	@Override
	public <T> void create(T t) {
		try {
			getSession().save(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> T loadById(Class<T> clazz, Serializable id) {
		return (T) getSession().load(clazz, id);
	}

	@Override
	public <T> void update(T t) {
		getSession().saveOrUpdate(t);
	};

	@Override
	public <T> void refresh(T t) {
		getSession().refresh(t);
	};

	@Override
	public <T> void remove(Class<T> clazz, Serializable id) {
		this.remove(this.loadById(clazz, id));
	}

	@Override
	public <T> void remove(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			Iterator<T> iterator = entities.iterator();

			while (iterator.hasNext()) {
				T t = iterator.next();
				this.remove(t);
			}

		}
	}
	
	public <T> void remove(T t) {
		getSession().delete(t);
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public Long getTotalCount(String jpql, QueryParamList params) {
		Long returnCount = 0L;
		try {
			// 附加查询条件
			String countJpql = JpqlUtil.deleteOuterOrderBy(jpql);
			Query pageQuery = getSession().createQuery(countJpql);
			if (JPAUtil.ifUseQueryCache()) {
				pageQuery.setCacheable(true);
			}
			setQueryParamList(pageQuery, params);
			ScrollableResults scrollCursor = pageQuery
					.scroll(ScrollMode.SCROLL_INSENSITIVE);
			scrollCursor.last();// 使游标移动到最后一行
			return Long.valueOf(scrollCursor.getRowNumber() + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnCount;
	}
}
