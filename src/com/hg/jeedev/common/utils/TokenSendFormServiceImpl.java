package com.hg.jeedev.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONValue;
import org.springframework.stereotype.Component;

import com.hg.jeedev.modules.zhdd.jcbk.service.PersonService;

import top.rpc.anno.RpcService;
@RpcService(TokenSendFormService.class)
@Component
public class TokenSendFormServiceImpl implements TokenSendFormService{
	

	public static void main(String[] args) {
		for(int i=0;i<100000;i++) {
//			String url="http://localhost:8193/FaceDeal/FaceToken";
//			String param="password=*******&username=*****&grant_type=password&scope=read+write&client_secret=123456&client_id=clientapp";
//			String json = TokenGet(url,param);
//			Map map=(Map)JSONValue.parse(json);
//			System.out.println(map.get("access_token"));
		}
		
	}
	
	public String TokenGet(String url,String param) { 
		   String json="";
	       CloseableHttpClient httpclient = HttpClients.createDefault(); 
	       try { 
	           HttpPost httppost = new HttpPost(url); 
	  
	           RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
	           httppost.setConfig(requestConfig);
	            
	           // 构造post参数
	           List<NameValuePair> params = new ArrayList<NameValuePair>();
	           String[] ps = param.split("&");
		   	   for (String string : ps) {
//		   			System.out.println(string);
		   			String[] map = string.split("=");
		   			String key=map[0];
		   			String val=map[1];
//		   			System.out.println(key+":"+val);
		   			params.add(new BasicNameValuePair(key,val));
		   	   }
	           
	           // 编码格式转换
	           UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
	           // 传入请求体
	           httppost.setEntity(entity);
	           
	           System.out.println("executing request " + httppost.getRequestLine()); 
	           CloseableHttpResponse response = httpclient.execute(httppost); 
	           try { 
	               System.out.println(response.getStatusLine()); 
	               HttpEntity resEntity = response.getEntity(); 
	               if (resEntity != null) { 
	                   json = EntityUtils.toString(response.getEntity());
//	                   System.out.println(responseEntityStr);
//	                   Map map=(Map)JSONValue.parse(responseEntityStr);
//	                   System.out.println(map.get("access_token"));
//	                   System.out.println(responseEntityStr);
//	                   System.out.println("Response content length: " + resEntity.getContentLength()); 
	               } 
	               EntityUtils.consume(resEntity); //判断是否为流,如果是则关闭.
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
}
