package com.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class SignUpService {
	public static String webserviceURL = "http://parlezvous.herokuapp.com/subscribe";
	public static boolean signUp(String user, String password){
		List<NameValuePair> args = new ArrayList<NameValuePair>(2);
		args.add(new BasicNameValuePair("username", user));
		args.add(new BasicNameValuePair("password", password));
		
		String contentStr = POSTService.post(webserviceURL, args);
		Log.i("[SignUp]", contentStr);
		if(contentStr != null && contentStr.split("Inscription ratée!").length <= 1){
			Log.i("[SignUp]", "Successed");
			return true;
		}
		
		Log.i("[SignUp]", "Failed");
		return false;
	}
}
