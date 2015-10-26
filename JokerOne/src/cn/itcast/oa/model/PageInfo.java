package cn.itcast.oa.model;

import java.io.Serializable;

import org.apache.struts2.json.annotations.JSON;


public class PageInfo implements Serializable {

	private Integer startIndex;
	private Integer endIndex;
	private static final long serialVersionUID = 1L;
	private int curPageNum = 1;//当前页
	private int allPageNum;//总页数
	private int rowOfPage;//每页记录数
	private int allRowNum;//总记录数
	private QueryParamList pageParams;
	private String pageHql;

	public int getAllPageNum() {
		return this.allPageNum;
	}

	public void setAllPageNum(int allPageNum) {
		this.allPageNum = allPageNum;
	}

	public int getAllRowNum() {
		return this.allRowNum;
	}

	public void setAllRowNum(int allRowNum) {
		this.allRowNum = allRowNum;
		if (this.rowOfPage > 0)
			if (allRowNum % this.rowOfPage == 0)
				this.allPageNum = (allRowNum / this.rowOfPage);
			else
				this.allPageNum = (allRowNum / this.rowOfPage + 1);
	}

	public int getCurPageNum() {
		return this.curPageNum;
	}

	public void setCurPageNum(int curPageNum) {
		this.curPageNum = curPageNum;
	}

	public int getRowOfPage() {
		return this.rowOfPage;
	}

	public void setRowOfPage(int rowOfPage) {
		this.rowOfPage = rowOfPage;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("curPageNum=" + this.curPageNum).append("\r\n");
		buffer.append("allPageNum=" + this.allPageNum).append("\r\n");
		buffer.append("rowOfPage=" + this.rowOfPage).append("\r\n");
		buffer.append("allRowNum=" + this.allRowNum).append("\r\n");
		return buffer.toString();
	}

	@JSON(serialize = false)
	public QueryParamList getPageParams() {
		return pageParams;
	}

	public void setPageParams(QueryParamList pageParams) {
		this.pageParams = pageParams;
	}

	@JSON(serialize = false)
	public String getPageHql() {
		return pageHql;
	}

	public void setPageHql(String pageHql) {
		this.pageHql = pageHql;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

}
