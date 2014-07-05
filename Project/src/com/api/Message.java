package com.api;

public class Message {
	private String author;
	private String content;
	public Message(String author, String content) {
		setAuthor(author);
		setContent(content);
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
}
