package com.api;

import java.util.ArrayList;
import java.util.List;

import com.base64.Base64Coder;
import com.stratos.beautifulinfinittextualexperiment.BeautifulInfinitTextualExperiment;
import com.stratos.beautifulinfinittextualexperiment.MessagesActivity;
import com.stratos.beautifulinfinittextualexperiment.R;

import android.util.Log;
import android.widget.ArrayAdapter;

public class MessageService {
	public static ArrayList<Message> messageList = new ArrayList<Message>();
	private static String webserviceURL = "http://parlezvous.herokuapp.com/message";
	private static String contentStr;
	
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
		String name = AppUser.getInstance().getName();
		String password = AppUser.getInstance().getPassword();
		
		contentStr = GETService.get(url, name, password);
		//Log.i("[Message List]", contentStr);
		if(contentStr != null){
			return parseString(contentStr);
		}
		return null;
	}
	
	public static List<Message> parseString(String str){
		String[] splitted = str.split(";");
		//Log.i("[Message Service]", String.valueOf(splitted.length));
		for(int i = 0; i < splitted.length; i++){
			String chunk = splitted[i];
			String[] infos = chunk.split(":");
			
			try{
				Message msg = new Message(infos[0], Base64Coder.decodeString(infos[1]));
				Message found = find(msg.getId()); 
				if(found == null && msg.isFromBITE()){
					Log.i("[Adding msg]", msg.getContent());
					messageList.add(msg);
				}
			} catch (Exception e) {
			}
			
		}
		
		return messageList;
	}
	
	public static String getContentStr() {
		return contentStr;
	}
	public static void setContentStr(String contentStr) {
		MessageService.contentStr = contentStr;
	}
	public static Message find(String id){
		for(int i = 0; i < messageList.size(); i++){
			Message msg = messageList.get(i);
			
			//Log.i("[message list]","for i = "+String.valueOf(i)+ " and size = "+ String.valueOf(messageList.size()) +" : " + msg.getId()+" == "+id);
			if(msg.getId() != null && msg.getId().equals(id)){
				return msg;
			}
		}
		return null;
	}
}
