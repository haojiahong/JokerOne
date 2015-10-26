package cn.itcast.oa.domain.basemain;

import java.util.ArrayList;
import java.util.List;
/**
 * 树表的通用实体，树表treeBean要继承的实体
 */
public class EasyTree  {
	public final static String OPEN = "open";
	public final static String CLOSE = "closed";
	private String id;
	private String text;
	private String iconCls;
	private String state;
	private String checked;
	private Attributes attributes;
	private boolean selected;
	private List<EasyTree> children = new ArrayList<EasyTree>();
	private String recursionCode;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setId(Long id) {
		this.id = id + "";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		if (state == null) {
			if (getChildren().size() > 0) {
				return "closed";
			}
		}
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public List<EasyTree> getChildren() {
		return children;
	}

	public void setChildren(List<EasyTree> children) {
		this.children = children;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getRecursionCode() {
		return recursionCode;
	}

	public void setRecursionCode(String recursionCode) {
		this.recursionCode = recursionCode;
	}

	public Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}


	

	
}
