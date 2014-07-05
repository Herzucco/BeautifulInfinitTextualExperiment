package com.api;

import java.sql.Timestamp;

import com.base64.Base64Coder;

public class Message {
	private String author;
	private String content;
	private String id;
	
	public String getId() {
		return id;
	}
	
	public Message(String author, String content) {
		setAuthor(author);
		setContent(content);
		generateId();
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void generateId(){
		String[] toParse = content.split("!!---!!");
		content = toParse[0];
		
		if(toParse[1] != null){
			id = toParse[1];
		}else{
			java.util.Date date= new java.util.Date();
			String time = new Timestamp(date.getTime()).toString();
			id = time + AppUser.getInstance().getName();
		}
	}
	public void send(){
		MessageService.send(Base64Coder.encodeString(content+"!!---!!"+id));
	}
	public void edit(String newContent){
		//edit ui
		setContent(newContent);
	}
}
