package com.sxw.exception;

/**
 * <b>异常基类</b>
 * <p>
 * 继承自<code>RuntimeException</code>，所以项目中所有异常非强制拦截，应在最前端做统一异常拦截处理。<br>
 * 每个异常类有一个唯一的异常码，用于唯一定位某个异常。
 * </p>
 * @author whl126
 *
 */
public abstract class BaseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6974597103940757367L;

	public BaseException(){
		super();
	}
	
	public BaseException(String msg){
		super(msg);
	}
	
	public BaseException(Throwable cause){
		super(cause);
	}
	
	public BaseException(String msg, Throwable cause){
		super(msg, cause);
	}
	
	/**
	 * 返回异常码
	 * @return
	 */
	public abstract String getErrCode();
	
}
