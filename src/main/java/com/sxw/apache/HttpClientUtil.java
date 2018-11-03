package com.sxw.apache; /*
 * @(#)HttpClientUtil.java 1.0 2018/11/3
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/11/3
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

import com.sxw.entry.PostRequestParam;
import com.sxw.exception.HttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Logger;

public class HttpClientUtil {

    private static String DEFAULT_CHARSET = "UTF-8";
    /**
     * 最大连接数
     */
    private static int CONNTIONPOOL_MAX_TOTAL = 1000;
    /**
     * 路由最大连接数
     */
    private static int CONNTIONPOOL_DEFAULT_MAX_PER_ROUTE = 1000;
    /**
     * Socket连接超时时间(ms)
     */
    private static int SOCKET_TIMEOUT = 60000;
    /**
     * connect 超时时间(ms)
     */
    private static int CONNECT_TIMEOUT = 60000;
    private static CloseableHttpClient httpClient;

    public static String  doPost(Map<String,String> heads, PostRequestParam pram)throws Exception{

        String result = null;
        HttpEntity resEntity = null;
        CloseableHttpResponse response = null;
        try{
            HttpPost post = new HttpPost(pram.getReqUrl());

            //设置头部
            post.setHeader("Content-Type", pram.getContentType()); // 设置通用的请求属性
            post.setHeader("Accept-Charset", "utf-8");
            for(Map.Entry<String,String> entriey : heads.entrySet()){
                post.setHeader(entriey.getKey(), entriey.getValue());
            }

            //从http连接池中获取httpClient
            CloseableHttpClient client = getHttpClient();
            if (client == null || post == null) {
                //记录日志
                return null;
            }
            //执行请求
            response = client.execute(post);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new HttpClientException("网络故障！ STATUS CODE:"
                        + response.getStatusLine().getStatusCode());
            }

            resEntity = response.getEntity();
            result = null == resEntity ? "" : EntityUtils.toString(resEntity,
                    DEFAULT_CHARSET);

        } catch (UnsupportedEncodingException e) {
            throw new HttpClientException(e);
        } catch (ClientProtocolException e) {
            throw new HttpClientException(e);
        } catch (IOException e) {
            throw new HttpClientException(e);
        } finally {
            try {
                if (resEntity != null) {
                    EntityUtils.consume(resEntity);
                }
                if (response != null) {
                    response.close();
                }

            } catch (IOException e) {
                throw new HttpClientException(e);
            }
         }
        return result;

    }


    private synchronized static CloseableHttpClient getHttpClient() {
        if (httpClient != null) {
            return httpClient;
        }

        try {

            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            cm.setMaxTotal(CONNTIONPOOL_MAX_TOTAL);//最大连接数
            cm.setDefaultMaxPerRoute(CONNTIONPOOL_DEFAULT_MAX_PER_ROUTE);//路由最大连接数
            RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setSocketTimeout(SOCKET_TIMEOUT) //请求超时时间
                    .setConnectTimeout(CONNECT_TIMEOUT).build();

            httpClient = HttpClients.custom().setConnectionManager(cm)
                    .setDefaultRequestConfig(defaultRequestConfig).build();
            return httpClient;
        } catch (Exception e) {
            //需要记录日志
            return null;
        }
    }
}
