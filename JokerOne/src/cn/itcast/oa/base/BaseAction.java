package cn.itcast.oa.base;

import java.lang.reflect.ParameterizedType;

import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements
		ModelDriven<T> {

	private static final long serialVersionUID = 1L;

	// modelDriven的支持
	// 使用modelDriven之后，可以在对象栈中直接取到model对象。
	public BaseAction() {
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
		try {
			model = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 获取当前登陆的用户
	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}

	public T model;

	public T getModel() {// 实现了modeldriven接口，将role对象push到了对象栈
		return model;
	}

	
}
