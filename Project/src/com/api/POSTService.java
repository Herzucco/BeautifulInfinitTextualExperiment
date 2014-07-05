package com.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class POSTService {
	public static String post(String baseURL, List<NameValuePair> params){
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(baseURL);
		Log.i("[POST]", "Post preparing");
		// Request parameters and other properties.
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			//Execute and get the response.
			HttpResponse response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			Log.i("[POST]", "Post done");
			if (entity != null) {
			    InputStream content = entity.getContent();
			    String contentStr = getStringFromInputStream(content);
			    
			    return contentStr;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("[POST]", e.toString());
			e.printStackTrace();
		}

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
