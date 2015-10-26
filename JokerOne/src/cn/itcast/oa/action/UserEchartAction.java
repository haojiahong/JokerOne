package cn.itcast.oa.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.oa.service.UserService;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 图表echart action
 * 
 * @author haojiahong
 * 
 * @createtime：2015-8-17 下午1:48:13
 * 
 * 
 */
@Namespace("/oa")
@ParentPackage("json-default")
@Action(value = "/userEchartAction")
@Results({
		@Result(name = "success", location = "userecharts.jsp"),
		@Result(name = "resultData", type = "json", params = {
				"includeproperties", "resultData.*" }) })
public class UserEchartAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserService userService;

	private Map<String, Object> resultData = new HashMap<String, Object>();

	@Override
	public String execute() throws Exception {
		return "success";
	}

	public String retrieveUserNums() {
		resultData.put("userMaleNum", userService.retrieveUserMale());
		resultData.put("userFemaleNum", userService.retrieveUserFemale());
		return "resultData";
	}

	public Map<String, Object> getResultData() {
		return resultData;
	}

	public void setResultData(Map<String, Object> resultData) {
		this.resultData = resultData;
	}
}
