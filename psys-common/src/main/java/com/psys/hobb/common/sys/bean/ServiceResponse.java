package com.psys.hobb.common.sys.bean;

import com.psys.hobb.common.sys.util.constant.SysExceptionEnum;

/**
 * ServiceResponse
 * 编  号：<br/>
 * 名  称：ServiceResponse<br/>
 * 描  述：<br/>
 * 完成日期：2017年12月10日 上午1:26:40<br/>
 * 编码作者：shaohj<br/>
 * @param <T>
 */
public class ServiceResponse<T> {
	
	/** 返回码 */
	private int errorNO;

	/** 返回信息 */
	private String errorMsg;

	/** 返回对象 */
	private T result;

	public ServiceResponse() {

	}

	public ServiceResponse(SysExceptionEnum exEnum) {
		this.errorNO = exEnum.getCode();
	}

	@SuppressWarnings("unchecked")
	public ServiceResponse(SysExceptionEnum exEnum, Object obj) {
		this.errorNO = exEnum.getCode();
		if(obj instanceof String){
			this.errorMsg = obj.toString();
		}else{
			this.result = (T)obj;
		}
	}

	public ServiceResponse(SysExceptionEnum exEnum, String errorMsg, T result) {
		this.errorNO = exEnum.getCode();
		this.errorMsg = errorMsg;
		this.result = result;
	}

	public int getErrorNO() {
		return errorNO;
	}

	public void setErrorNO(int errorNo) {
		this.errorNO = errorNo;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
