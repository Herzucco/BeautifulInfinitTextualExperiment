package com.api;

import java.sql.Timestamp;

import android.util.Log;

import com.base64.Base64Coder;
import com.stratos.beautifulinfinittextualexperiment.*;

public class Message {
	private String author;
	private String content;
	private String id;
	private boolean fromBITE;
	
	public String edition;
	public ViewHolder view;

	public boolean isFromBITE() {
		return fromBITE;
	}

	public void setFromBITE(boolean fromBITE) {
		this.fromBITE = fromBITE;
	}

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
		
		if(toParse.length > 1){
			id = toParse[1];
			//Log.i("[Id generation]", id);
			setFromBITE(true);
		}else{
			setFromBITE(false);
		}
	}
	public void send(){
		if(id == null){
			java.util.Date date= new java.util.Date();
			Timestamp tmsp = new Timestamp(date.getTime());
			id = tmsp.toString()+getAuthor();
		}
		MessageService.send(Base64Coder.encodeString(content+"!!---!!"+id));
	}
	public void edit(){
		setContent(edition);
		if(view != null){
			view.content.setText(getContent());
		}
	}
	public String getFormatedContent(){
		return getAuthor() + " : "+ getContent();
	}
}
