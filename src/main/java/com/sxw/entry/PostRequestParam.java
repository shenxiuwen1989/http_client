package com.sxw.entry;

import lombok.Data;

import java.util.Map;

/**
 * @author yangjun 2016-05-24
 */
@Data
public class PostRequestParam {

	/**
	 * 通用请求头 content-Type
	 */
	private String contentType = "application/json";
	/**
	 * json请求参数
	 */
	private String jsonStr = "";

	/**
	 * application/x-www-form-urlencoded 提交方式
	 */
	private Map<String,String> formUrlencodedData;
	/**
	 * 请求地址
	 */
	private String reqUrl = "";
	

	

	
}
