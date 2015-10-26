package cn.itcast.oa.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.itcast.oa.action.LoginAction;
import cn.itcast.oa.domain.User;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SessionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		if (invocation.getAction().getClass() == (LoginAction.class)) {
			log.info("登陆注销请求，无需拦截");
			return invocation.invoke();
		}
		User user = (User) invocation.getInvocationContext().getSession().get("user");
		if (user == null) {
			String errMsg = "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！";
			log.info(errMsg);
			invocation.getInvocationContext().getSession().put("msg", errMsg);
			return "noSession";
		}
		return invocation.invoke();
	}
}
