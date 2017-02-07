package com.baomidou.springwind.job;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClientUtil {

	public static Log log = LogFactory.getLog(HttpClientUtil.class);

	public static String requestGet(String url) {
		String resp = "";
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpclient.getParams().setSoTimeout(60000);

		GetMethod getMethod = new GetMethod(url);
		try {
			getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			int statusCode = httpclient.executeMethod(getMethod);
			resp = getMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e.getMessage());
			return e.getMessage();
		} finally {
			getMethod.releaseConnection();
		}
		return resp;
	}
	
	public static String requestGet(String url, int outTime) {
		String resp = "";
		HttpClient httpclient = new HttpClient();
		httpclient.getParams().setParameter(
				HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		httpclient.getHttpConnectionManager().getParams().setSoTimeout(outTime);
		httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(outTime);
		GetMethod getMethod = new GetMethod(url);
		getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
		try {
			int statusCode = httpclient.executeMethod(getMethod);
			resp = getMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			getMethod.releaseConnection();
		}
		return resp;
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值List
	 * @return 调用得到的字符串
	 */
	public static String httpClientPost(String url,List<NameValuePair[]> values,String ... charset){
		log.debug("post url: "+url);
		StringBuilder sb =new StringBuilder();
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		String str1=null;
		if(charset==null||charset.length==0){
			str1="UTF-8";
		}else if("GBK".equals(charset[0].toUpperCase())){
			str1="GBK";
		}
		//将表单的值放入postMethod中
		for (NameValuePair[] value : values) {
			postMethod.addParameters(value);
		}
		
		try {
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, str1);
			//执行postMethod
			httpClient.getHttpConnectionManager().getParams().setSoTimeout(20000);
			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
			httpClient.executeMethod(postMethod);
			//以流的行式读入，避免中文乱码
//			InputStream is = postMethod.getResponseBodyAsStream();
//			BufferedReader dis=new BufferedReader(new InputStreamReader(is,str1));   
//			 String str ="";                           
//			 while((str =dis.readLine())!=null){
//				 sb.append(str);
//				 //sb.append("\r\n"); // 默认这里没有换行，而是让所有的字符出现在一行里面。如需要换行，请去掉前面的注释
//			 }
			
			return postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			log.error("HttpClientUtil.httpClientPost():httpClient调用远程出错;发生网络异常");
			e.printStackTrace();
		}finally{
			postMethod.releaseConnection();
		}
		return sb.toString();
	}
	
	
	
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值
	 * @return 调用得到的字符串
	 */
	public static String httpClientPost(String url,NameValuePair[] values,String ... charset){
		List<NameValuePair[]> list = new ArrayList<NameValuePair[]>();
		list.add(values);
		return httpClientPost(url, list,charset);
	}
	
	/**
	 * 使用get方式调用
	 * @param url调用的URL
	 * @return 调用得到的字符串
	 */
	public static String httpClientGet(String url,String ...  charset ){
		log.debug("get url: "+url);
		StringBuilder sb =new StringBuilder();
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		String str1=null;
		if(charset==null||charset.length==0){
			str1="UTF-8";
		}else if("GBK".equals(charset[0].toUpperCase())){
			str1="GBK";
		}
		try {
			// 执行getMethod
			httpClient.executeMethod(getMethod);
			//以流的行式读入，避免中文乱码
			InputStream is = getMethod.getResponseBodyAsStream();
			BufferedReader dis=new BufferedReader(new InputStreamReader(is,str1));   
			 String str ="";                           
			 while((str =dis.readLine())!=null){
				 sb.append(str);
			 }
		} catch (Exception e) {
			// 发生网络异常
			log.error("HttpClientUtil.httpClientGet():httpClient调用远程出错;发生网络异常");//e.printStackTrace();
		} finally {
			// 关闭连接
			getMethod.releaseConnection();
		}
		return sb.toString(); 
	}
	
	
	/**
	 * 将MAP转换成HTTP请求参数
	 * @param pairArr
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static NameValuePair[] praseParameterMap(Map map){
		
		NameValuePair[] nvps = new NameValuePair[map.size()];
		
		Set<String> keys = map.keySet();
		int i=0;
		for(String key:keys){
			nvps[i] = new NameValuePair();
			nvps[i].setName(key);
			nvps[i].setValue(String.valueOf(map.get(key)));
			i++;
		}
		              
		return nvps;
	}
	
	
	
}
