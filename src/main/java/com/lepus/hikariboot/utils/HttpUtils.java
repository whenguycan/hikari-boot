package com.lepus.hikariboot.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author whenguycan
 */
public class HttpUtils {
	
	public static void main(String[] args){
//		String url = "http://localhost:28080/hikari/plugins/jquery/jquery-1.9.1.min.js";
		String url = "http://localhost:28080/hikari/login.do";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("user", "jim");
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("Path", "/hikari/");
		String resp = post(url, headers, params, XReadCharset.UTF8);
		System.out.println(resp);
	}

	public static String post(String url, Map<String,Object> headers, Map<String,Object> params, XReadCharset readCharset){
		return connect(url, headers, params, XRequestMethod.POST, XReadCharset.UTF8);
	}
	
	public static String get(String url, Map<String,Object> headers, Map<String,Object> params, XReadCharset readCharset){
		return connect(url, headers, params, XRequestMethod.GET, XReadCharset.UTF8);
	}
	
	private static String connect(String url, Map<String,Object> headers, Map<String,Object> params, XRequestMethod requestMethod, XReadCharset readCharset) 
			throws RuntimeException{
		try {
			URL u = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)u.openConnection();
			conn.setRequestMethod(requestMethod.name());
			conn.setConnectTimeout(16000);
			conn.setReadTimeout(16000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			StringBuffer p = new StringBuffer();
			if(headers != null && !headers.isEmpty()){
				for(Entry<String,Object> e : headers.entrySet()){
					if(e.getKey() != null && e.getValue() != null){
						conn.addRequestProperty(e.getKey(), e.getValue().toString());
					}
				}
			}
			if(params != null && !params.isEmpty()){
				for(Entry<String,Object> e : params.entrySet()){
					if(e.getKey() != null && e.getValue() != null){
						p.append("&").append(e.getKey()).append("=").append(e.getValue());
					}
				}
			}
			conn.getOutputStream().write(p.toString().getBytes());
			conn.getOutputStream().flush();
			String charset = readCharset!=null?readCharset.charset():XReadCharset.UTF8.charset();
			//获取响应头
			Map<String, List<String>> respHeaders = conn.getHeaderFields();
			for(String s : respHeaders.keySet()){
				List<String> vals = respHeaders.get(s);
				System.out.print(s + "=");
				for(String val : vals){
					System.out.print(val);
				}
				System.out.println();
			}
			//获取响应信息
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
			String line = "";
			StringBuffer content = new StringBuffer();
			while((line=reader.readLine())!=null){
				content.append(line);
			}
			conn.disconnect();
			return content.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("connect error");
		}
	}
	
	public static enum XRequestMethod{
		POST,GET;
	}
	
	public static enum XReadCharset{
		UTF8("UTF-8"),GBK("GBK"),ISO88591("ISO-8859-1");
		private String charset;
		private XReadCharset(String charset){
			this.charset = charset;
		}
		public String charset(){
			return this.charset;
		}
	}
	
}
