package com.sxw.exception;

/**
 * HTTPCLIENT 异常
 * @author whl126
 *
 */
public class HttpClientException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2539149131580615300L;

	public HttpClientException(){
		super();
	}
	
	public HttpClientException(String msg){
		super(msg);
	}
	
	public HttpClientException(Throwable cause){
		super(cause);
	}
	
	public HttpClientException(String msg, Throwable cause){
		super(msg, cause);
	}

	@Override
	public String getErrCode() {
		return FrmErrCodeConstants.HTTPCLIENT_EXCEPTION;
	}

}
