package com.api;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class MessageService {
	public static List<Message> messageList = new ArrayList<Message>();
	private static String webserviceURL = "http://parlezvous.herokuapp.com/message";
	
	public static boolean send(String message){
		String contentStr = GETService.get(webserviceURL, "sdzsdz", "dszsd", message);
		Log.i("[Message Send]", contentStr);
		if(contentStr != null){
			return true;
		}
		return false;
	}
	public static List<Message> getList(){
		String url = webserviceURL+"s";
		String contentStr = GETService.get(url, "lel");
		Log.i("[Message List]", contentStr);
		if(contentStr != null){
			return parseString(contentStr);
		}
		return null;
	}
	private static List<Message> parseString(String str){
		String[] splitted = str.split(";");
		for(int i = 0; i < splitted.length; i++){
			String chunk = splitted[i];
			String[] infos = chunk.split(":");
			
			messageList.add(new Message(infos[0], infos[1]));
		}
		
		return messageList;
	}
}
