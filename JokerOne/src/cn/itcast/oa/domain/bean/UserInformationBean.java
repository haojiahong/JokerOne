package cn.itcast.oa.domain.bean;

/**
 * excel批量添加用户所用bean
 * 
 * @author haojiahong
 * 
 * @createtime：2015-7-16 下午3:56:52
 * 
 * 
 */
public class UserInformationBean {
	private String errMsg;
	private String userName;
	private String userDescription;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
