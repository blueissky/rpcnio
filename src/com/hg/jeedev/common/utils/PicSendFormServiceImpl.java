package com.hg.jeedev.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

import com.hg.jeedev.modules.zhdd.jcbk.service.PersonService;

import top.rpc.anno.RpcService;
@RpcService(PicSendFormService.class)
@Component
public class PicSendFormServiceImpl implements PicSendFormService{
	
	public static void main(String[] args) {
		
		for(int i=0;i<100000;i++) {
			
//			String url="http://localhost:8193/FaceDeal/FaceUrlServlet";
//			String Authorization="aaa"; 
//			Map<String, String> paramMap=new HashMap();
//			paramMap.put("password", "12321");
//			paramMap.put("username", "accccc");
//			paramMap.put("grant_type", "2eeeeeeeee");
//			paramMap.put("scope", "zzzzzzzzzzz");
//			httpPost(url,Authorization,paramMap);
			
			
			///////////上传图片测试////////////////
//			String url="http://localhost:8193/FaceDeal/FaceServlet?param1=ABC";
//			String filePath="E:\\\\temp\\\\README.txt";
//			String param="face=true&type=0";
//			String Authorization="Y2xpZW50YXBwOjEyMzQ1Ng";
//			String json = upload(url,filePath,param,Authorization);
//			Map map=(Map)JSONValue.parse(json);
//			Map obj = (Map)map.get("data");
//			String id=(String)obj.get("id");
//			System.out.println(id);
			///////////上传图片测试////////////////
		}
		
	
	}
	
	/**
	 * 上传图片
	 * @param url 云天励飞接口服务地址
	 * @param filePath 大华图片地址
	 * @param param 云天励飞上传参数
	 * @return
	 */
	public String upload(String url,String filePath,String param,String Authorization) { 
		   String json="";
	       CloseableHttpClient httpclient = HttpClients.createDefault(); 
	       try { 
	           HttpPost httppost = new HttpPost(url); 
	  
	           RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
	           httppost.setConfig(requestConfig);
	           File file = new File(filePath);
	           MultipartEntityBuilder meb = MultipartEntityBuilder.create();
	           meb.addBinaryBody("FILE", file);
	           
//	           if(param!=null) {
//	        	   
//	        	   String[] ps = param.split("&");
//		           for (String string : ps) {
//	//		   			System.out.println(string);
//			   			String[] map = string.split("=");
//			   			String key=map[0];
//			   			String val=map[1];
//	//		   			System.out.println(key+":"+val);
//			   			meb.addTextBody(key, val);
//			   	   }
//	           }
	           
	           HttpEntity reqEntity = meb.build();
	           httppost.setEntity(reqEntity);
	           httppost.setHeader("Authorization", Authorization);
	           System.out.println("executing request " + httppost.getRequestLine()); 
	           CloseableHttpResponse response = httpclient.execute(httppost); 
	           try {
	               System.out.println(response.getStatusLine()); 
	               HttpEntity resEntity = response.getEntity(); 
	               if (resEntity != null) { 
	                   String responseEntityStr = EntityUtils.toString(response.getEntity());
	                   System.out.println(responseEntityStr);
	                   json=responseEntityStr;
	                   System.out.println("Response content length: " + resEntity.getContentLength()); 
	               } 
	               EntityUtils.consume(resEntity); 
	           } finally {
	               response.close(); 
	           } 
	           return json;
	       } catch (Exception e) { 
	           e.printStackTrace(); 
	           return json;
	       } finally { 
	           try { 
	               httpclient.close();
	           } catch (IOException e) { 
	               e.printStackTrace(); 
	           } 
	       } 
	   }
	/**
	 * POST请求接口服务
	 * @param url 云天励飞接口服务地址
	 * @param Authorization 云天励飞认证TOKEN
	 * @param paramMap  云天励飞上传参数
	 * @return
	 */
	public String httpPost(String url,String Authorization, Map<String, String> paramMap) {
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		try {
			 HttpPost httppost = new HttpPost(url); 
			 List<NameValuePair> list = new ArrayList<>();
			 for (Map.Entry<String, String> entry : paramMap.entrySet()) {
	                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	         }
			 httppost.setEntity(new UrlEncodedFormEntity(list,  "utf-8"));
			 httppost.setHeader("Authorization", Authorization);
			 CloseableHttpResponse response = httpclient.execute(httppost);
			 String responseEntityStr = EntityUtils.toString(response.getEntity());
			 System.out.println(responseEntityStr);
			 response.close(); 
			 return responseEntityStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally { 
	           try { 
	               httpclient.close();
	           } catch (IOException e) { 
	               e.printStackTrace(); 
	           } 
	       } 
		 
	}
}
