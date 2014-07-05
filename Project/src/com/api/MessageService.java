package com.api;

import java.util.ArrayList;
import java.util.List;

import com.base64.Base64Coder;

import android.util.Log;

public class MessageService {
	public static List<Message> messageList = new ArrayList<Message>();
	private static String webserviceURL = "http://parlezvous.herokuapp.com/message";
	
	public static boolean send(String message){
		String name = AppUser.getInstance().getName();
		String password = AppUser.getInstance().getPassword();
		String contentStr = GETService.get(webserviceURL, name, password, message);
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
			
			try{
				Message msg = new Message(infos[0], Base64Coder.decodeString(infos[1]));
				Message found = find(msg.getId()); 
				if(found == null){
					messageList.add(msg);
				}else{
					found.edit(msg.getContent());
				}
			} catch (Exception e) {
				Log.d("No base64", infos[1]+" "+e.toString());
			}
			
		}
		
		return messageList;
	}
	
	public static Message find(String id){
		for(int i = 0; i < messageList.size(); i++){
			Message msg = messageList.get(i);
			
			if(msg.getId() == id){
				return msg;
			}
		}
		return null;
	}
}
