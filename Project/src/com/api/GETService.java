package com.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GETService {
	public static String get(String baseURL, String arg){
		String builtURL = baseURL+'/'+arg;
		return send(builtURL);
	}
	
	public static String get(String baseURL, String arg1, String arg2){
		String builtURL = baseURL+'/'+arg1+'/'+arg2;
		return send(builtURL);
	}
	
	public static String get(String baseURL, String arg1, String arg2, String arg3){
		String builtURL = baseURL+'/'+arg1+'/'+arg2+'/'+arg3;
		Log.i("[GET 3params]", builtURL);
		return send(builtURL);
	}

	private static String send(String builtURL) {
		try{
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(builtURL);
			HttpResponse response = client.execute(request);
		 
			InputStream content = response.getEntity().getContent();
			String contentStr = getStringFromInputStream(content);
			
			if(contentStr.equalsIgnoreCase("access denied")){
				return null;
			}else{
				return contentStr;
			}
		}catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Log.i("[GET send - fail]", "failed");
		return null;
	}
	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is) {
 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
}
