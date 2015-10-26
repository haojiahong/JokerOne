package cn.itcast.oa.domain.basemain;

import java.io.Serializable;
/**
 * easyUI中的下拉
 * @author haojiahong
 *
 */
public class EasyUIDrop implements Serializable{

	private static final long serialVersionUID = 1L;

	private String value;
	private String text;
	private boolean selected = false;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public EasyUIDrop() {

	}

	public EasyUIDrop(String value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	public EasyUIDrop(Long value, String text) {
		super();
		this.value = value + "";
		this.text = text;
	}
}
