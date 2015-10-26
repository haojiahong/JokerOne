package cn.itcast.oa.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.domain.User;
import cn.itcast.oa.domain.basemain.EasyTree;
import cn.itcast.oa.service.PrivilegeService;
import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/oa")
@ParentPackage("hjh-default")
@Action(value = "/loginAction")
@Results({ @Result(name = "login", location = "login.jsp"), @Result(name = "success", location = "loginsuccess.jsp"),
		@Result(name = "logout", type = "json", params = { "root", "logoutFlag" }),
		@Result(name = "menuTree", type = "json", params = { "root", "treeBeanLs" }),
		@Result(name = "loginAgain", type = "json", params = { "root", "loginFlag" }) })
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String loginName;// 登陆名称
	private String password;// 登陆密码

	private List<EasyTree> treeBeanLs;
	private String id;// easyTree节点id
	private boolean logoutFlag = false;
	private boolean loginFlag;

	@Autowired
	private UserService userService;

	@Autowired
	private PrivilegeService privilegeService;

	@Override
	public String execute() throws Exception {

		return "login";

	}

	// 登陆成功
	public String login() {
		User user = userService.findByLoginNameAndPassword(loginName, password);
		if (user == null) {
			addFieldError("login", "用户名或密码不正确！");
			return "login";
		} else {
			// 向session中注入user
			ActionContext.getContext().getSession().put("user", user);
			return "success";
		}
	}

	// 解锁登陆成功
	public String loginAgain() {
		User user = userService.findByLoginNameAndPassword(loginName, password);
		if (user == null) {
			this.loginFlag = false;
			return "loginAgain";
		} else {
			this.loginFlag = true;
			// 向session中注入user
			ActionContext.getContext().getSession().put("user", user);
			return "loginAgain";
		}
	}

	// 获取异步菜单树
	public String getMenuTree() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		treeBeanLs = privilegeService.findMenuTree(id,user.getRoleId());

		return "menuTree";
	}

	// 注销成功
	public String logout() {
		// 出去session中保留的登陆的user
		ActionContext.getContext().getSession().remove("user");
		this.logoutFlag = true;
		return "logout";
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<EasyTree> getTreeBeanLs() {
		return treeBeanLs;
	}

	public void setTreeBeanLs(List<EasyTree> treeBeanLs) {
		this.treeBeanLs = treeBeanLs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isLogoutFlag() {
		return logoutFlag;
	}

	public void setLogoutFlag(boolean logoutFlag) {
		this.logoutFlag = logoutFlag;
	}

	public boolean isLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(boolean loginFlag) {
		this.loginFlag = loginFlag;
	}

}
