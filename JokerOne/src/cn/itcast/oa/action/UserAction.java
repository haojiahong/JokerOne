package cn.itcast.oa.action;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.itcast.oa.base.EasyGridAction;
import cn.itcast.oa.domain.User;
import cn.itcast.oa.domain.basemain.EasyGridData;
import cn.itcast.oa.domain.basemain.EasyUIDrop;
import cn.itcast.oa.domain.ezdrop.GenderDrop;
import cn.itcast.oa.domain.ezdrop.RoleDrop;
import cn.itcast.oa.service.UserService;
import cn.itcast.oa.util.CommonUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户管理
 * 
 * @author haojiahong
 * 
 * @createtime 2015-8-2
 */
@Namespace("/oa")
@ParentPackage("hjh-default")
@Action(value = "/userAction")
@Results({
		@Result(name = "success", type = "json", params = { "root", "gridData" }),
		@Result(name = "detail", location = "useradd.jsp"),
		@Result(name = "excel", type = "stream", params = { "contentType", "application/octet-stream", "inputName",
				"inputStream", "contentDisposition", "attachment;filename=${excelFileName}" }),
		@Result(name = "droplist", type = "json", params = { "root", "droplist" }),
		@Result(name = "roleDroplist", type = "json", params = { "root", "roleDroplist" }),
		@Result(name = "mypdf", type = "stream", params = { "contentType", "application/pdf", "contentDisposition",
				"inline;filename='${pdfName}'", "inputName", "pdfStream", "encode", "true" }) })
public class UserAction extends EasyGridAction<User> {

	private static final long serialVersionUID = 1L;

	@Resource
	private UserService userService;

	private EasyGridData<User> gridData = new EasyGridData<User>();
	private User user = new User();
	private String userIds;// 批量删除用户
	private String result;// 收集前台添加的用户json字符串

	private String userNameSch;// 用户名称查询
	private String excelFileName;
	private InputStream inputStream;
	private String fileName;

	private List<EasyUIDrop> droplist = new ArrayList<EasyUIDrop>();// 性别下拉
	private List<EasyUIDrop> roleDroplist = new ArrayList<EasyUIDrop>();// 岗位下拉

	@Override
	public String execute() throws Exception {
		if (!CommonUtil.strIsNull(fileName)) {
			List<User> userLs = new ArrayList<User>();
			userLs.addAll(userService.retrieveAll(userNameSch, getSortInfo(), getPageInfo()));
			userLs.addAll(userService.importExcel(fileName));
			gridData.setRows(userLs);
			gridData.setTotal(userLs.size());
		} else {
			gridData.setRows(userService.retrieveAll(userNameSch, getSortInfo(), getPageInfo()));
			gridData.setTotal(getPageInfo().getAllRowNum());
		}
		return "success";

	}

	// 性别下拉
	public String droplist() {
		droplist.addAll(GenderDrop.getGenderList());
		return "droplist";
	}

	// 岗位下拉
	public String roleDroplist() {
		roleDroplist.addAll(RoleDrop.getRoleList());
		return "roleDroplist";
	}

	public String add() {
		// digestutils是commons-codec.jar包里的一个工具类
		String md5Digest = DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);
		return "detail";
	}

	public String save() {
		userService.save(model);
		this.msg = "保存成功";
		return "msg";
	}

	public String load() {
		user = userService.retrieveOne(model.getUserId());
		return "detail";
	}

	public String del() {
		userService.remove(model);
		this.msg = "删除成功";
		return "msg";
	}

	public String delUsers() {
		System.out.println(userIds);
		userService.delUsers(userIds);
		this.msg = "删除成功";
		return "msg";
	}

	public String saveUsers() {
		Gson gson = new Gson();
		List<User> userLs = gson.fromJson(result, new TypeToken<List<User>>() {
		}.getType());
		userService.saveUsers(userLs);
		this.msg = "保存成功";
		return "msg";
	}

	public String exportExcel() throws Exception {
		this.excelFileName = URLEncoder.encode("员工信息导入模版.xls", "utf-8");
		this.inputStream = userService.exportExcel();
		return "excel";
	}

	public String initPassword() {
		userService.initPassword(userService.retrieveOne(model.getUserId()));
		this.msg = "初始化密码成功";
		return "msg";
	}

	// pdf打印
	public String myprint() throws Exception {
		InputStream in = userService.genPdf();
		ActionContext.getContext().getValueStack().set("pdfStream", in);
		ActionContext.getContext().getValueStack().set("pdfName", "用户名单.pdf");
		return "mypdf";
	}

	public EasyGridData<User> getGridData() {
		return gridData;
	}

	public void setGridData(EasyGridData<User> gridData) {
		this.gridData = gridData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserNameSch() {
		return userNameSch;
	}

	public void setUserNameSch(String userNameSch) {
		this.userNameSch = userNameSch;
	}

	public String getExcelFileName() {
		return excelFileName;
	}

	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<EasyUIDrop> getDroplist() {
		return droplist;
	}

	public void setDroplist(List<EasyUIDrop> droplist) {
		this.droplist = droplist;
	}

	public List<EasyUIDrop> getRoleDroplist() {
		return roleDroplist;
	}

	public void setRoleDroplist(List<EasyUIDrop> roleDroplist) {
		this.roleDroplist = roleDroplist;
	}

}
