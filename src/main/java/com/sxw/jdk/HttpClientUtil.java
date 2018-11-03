package com.sxw.jdk; /*
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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;


public class HttpClientUtil {

    public static String  doPost(Map<String,String> heads, PostRequestParam pram)throws Exception{

        //1.
        URL url = new URL(pram.getReqUrl());

        //2. 打开和URL之间的连接
        URLConnection conn = url.openConnection();

        //3.设置请求头
        conn.setRequestProperty("Content-Type", pram.getContentType()); // 设置通用的请求属性
        conn.setRequestProperty("Accept-Charset", "utf-8");
        for(Map.Entry<String,String> entriey : heads.entrySet()){
            conn.setRequestProperty(entriey.getKey(), entriey.getValue());
        }

        //4.设置请求参数
        String reqStr = pram.getJsonStr();
        if("application/x-www-form-urlencoded".equals(pram.getContentType())){
            for (Map.Entry<String,String> entry : pram.getFormUrlencodedData().entrySet()){
                //name=张三&sex=男&tel=5354169
                reqStr = entry.getKey()+"="+ entry.getValue()+"&";
            }
            reqStr = reqStr.substring(0,reqStr.length()-1);
        }

        // 获取URLConnection对象对应的输出流
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        // 发送请求参数
        out.write(reqStr);

        out.close();
        // 定义BufferedReader输入流来读取URL的响应

        InputStream inputStream=conn.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        StringBuilder sb = new StringBuilder();
        sb.append("");

        String line;
        while ((line = in.readLine()) != null) {
            //resp += line;
            sb.append(line);
        }

        //关闭流
        inputStream.close();

        return sb.toString();

    }


    public static String  doGet(Map<String,String> heads, PostRequestParam pram)throws Exception{

        //1.
        URL url = new URL(pram.getReqUrl());

        //2. 打开和URL之间的连接
        URLConnection conn = url.openConnection();

        //3.设置请求头(get请求只能是application/x-www-form-urlencoded)
        if(!"application/x-www-form-urlencoded".equals(pram.getContentType())){
            //todo 待优化以日志形式打印，抛出异常
            System.out.println("get请求只能是application/x-www-form-urlencoded");
            return "";
        }
        conn.setRequestProperty("Content-Type", pram.getContentType()); // 设置通用的请求属性
        conn.setRequestProperty("Accept-Charset", "utf-8");
        for(Map.Entry<String,String> entriey : heads.entrySet()){
            conn.setRequestProperty(entriey.getKey(), entriey.getValue());
        }

        //4.设置请求参数
        String reqStr = pram.getJsonStr();
        for (Map.Entry<String,String> entry : pram.getFormUrlencodedData().entrySet()){
            //name=张三&sex=男&tel=5354169
            reqStr = entry.getKey()+"="+ entry.getValue()+"&";
        }
        reqStr = reqStr.substring(0,reqStr.length()-1);

        // 获取URLConnection对象对应的输出流
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        // 发送请求参数
        out.write(reqStr);

        out.close();
        // 定义BufferedReader输入流来读取URL的响应

        InputStream inputStream=conn.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        StringBuilder sb = new StringBuilder();
        sb.append("");

        String line;
        while ((line = in.readLine()) != null) {
            //resp += line;
            sb.append(line);
        }

        //关闭流
        inputStream.close();

        return sb.toString();

    }
}
