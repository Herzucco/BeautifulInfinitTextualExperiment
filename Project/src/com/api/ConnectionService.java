package com.api;

public class ConnectionService {
	public static String webserviceURL = "http://parlezvous.herokuapp.com/connect/";
	public static boolean connect(String user, String password){
		String contentStr = GETService.get(webserviceURL, user, password);
		if(contentStr != null && contentStr.equalsIgnoreCase("true")){
			return true;
		}
		return false;
	}
}
