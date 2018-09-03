package com.psys.hobb.common.sso.bean;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class SsoUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String userCode;

	private String userName;

	private String realName;

	private String mobile;

	private String status;

	private String isAdmin;

	private String enabledFlag;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getEnabledFlag() {
		return enabledFlag;
	}

	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}