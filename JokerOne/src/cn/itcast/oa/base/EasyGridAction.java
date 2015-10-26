package cn.itcast.oa.base;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.itcast.oa.model.PageInfo;
import cn.itcast.oa.model.SortParamList;
@Results({
	@Result(name = "msg", type = "json",params = { "root", "msg","contentType","text/html" })
})
public class EasyGridAction<T> extends BaseAction<T> {

	private static final long serialVersionUID = 1L;

	private int page;
	private int rows = 20;// 默认值
	private String sort; 
	private String order;
	private PageInfo pageInfo = new PageInfo();
	private SortParamList sortInfo = new SortParamList();
	protected String msg = "操作成功";

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public PageInfo getPageInfo() {
		pageInfo.setRowOfPage(rows);//一页几行
		pageInfo.setCurPageNum(page);//当前页数
		return pageInfo;
	}

	public SortParamList getSortInfo() {
		if (sort != null)
			sortInfo.addParam(sort, order);
		return sortInfo;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
