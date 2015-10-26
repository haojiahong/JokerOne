package cn.itcast.oa.domain.basemain;

import java.util.ArrayList;
import java.util.List;
/**
 * 实现easyUI数据表格分页
 * @author haojiahong
 *
 * @param <T>
 */
public class EasyGridData<T> {
	private int total=0;
	private List<T> rows = new ArrayList<T>();
	private String msg;
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	
	public int getTotal() {
		return total;
	}

	
	public void setTotal(int total) {
		this.total = total;
	}

	
	public String getMsg() {
		return msg;
	}

	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
