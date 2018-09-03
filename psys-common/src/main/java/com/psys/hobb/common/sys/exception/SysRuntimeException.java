package com.psys.hobb.common.sys.exception;

/**
 * 编  号：
 * 名  称：SysRuntimeException
 * 描  述： 系统运行时异常，继承RuntimeException,若抛出该异常，可使得事务回滚
 * 完成日期：2018/5/5 13:05
 * 编码作者：SHJ
 */
public class SysRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public static final int SUCCESS_CODE = 0;
	
	public static final String SUCCESS_MSG = "ok";
	
	public static final int DEFAULT_FAIL_CODE = -1;
	
	public static final String DEFAULT_FAIL_MSG = "default fail";
	
	/** 返回码 */
	private int code;
	
	/** 返回错信息 */
	private String message;
	
	public SysRuntimeException(){
		
	}
	
	public SysRuntimeException(String message){
		super(message);
		this.message =  message;
	}
	
	public SysRuntimeException(int code, String message) {
		this(message);
		this.code = code;
	}
	
	public SysRuntimeException(Throwable t){
		super(t);
	}
	
	public SysRuntimeException(String message, Throwable t){
		super(message, t);
		this.message =  message;
	}
	
	public SysRuntimeException(int code, String message, Throwable t){
		this(message, t);
		this.code =  code;
	}
	
	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static SysRuntimeException getDefaultFailException(){
		return new SysRuntimeException(DEFAULT_FAIL_CODE, DEFAULT_FAIL_MSG);
	}
	
}
