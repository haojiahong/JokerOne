package cn.itcast.oa.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

@Namespace("/")
@ParentPackage("struts-default")
@Action(value = "/testAction", results = { @Result(name = "input", location = "test.jsp") })
public class TestAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		System.out.println("TestAction.execute()");
		return "input";

	}

}
