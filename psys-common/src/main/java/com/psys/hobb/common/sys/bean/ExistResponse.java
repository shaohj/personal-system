package com.psys.hobb.common.sys.bean;

/**
 * ajax校验
 * @author shaohanjie
 *
 */
public class ExistResponse {

	public static final String SUCCESS = "y";
	public static final String SUCCESS_MSG = "数据可以使用";
	public static final String EXIST_SUCCESS_MSG = "数据未修改";
	
	public static final String EXIST = "n";
	public static final String EXIST_MSG = "数据已经存在";
	public static final String EXIST_ERROR_MSG = "数据异常,不能正常操作,请联系管理员修改";
	
	public static final String FAIL = "n";
	
	/** 状态：y：验证通过;n验证失败  */
	private String status;
	
	/** 返回信息  */
	private String info;

	public ExistResponse(){}
	
	public ExistResponse(String status, String info) {
		super();
		this.status = status;
		this.info = info;
	}
	
	public static ExistResponse getDefaultSuccess(){
		return new ExistResponse(SUCCESS, SUCCESS_MSG);
	}
	
	public static ExistResponse getExistSuccess(){
		return new ExistResponse(SUCCESS, EXIST_SUCCESS_MSG);
	}
	
	public static ExistResponse getDefaultExist(){
		return new ExistResponse(EXIST, EXIST_MSG);
	}
	
	public static ExistResponse getDefaultErrorExist(){
		return new ExistResponse(EXIST, EXIST_ERROR_MSG);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
