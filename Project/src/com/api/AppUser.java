package com.api;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppUser {
	private static User user;
	public static User getInstance(){
		if(user == null){
			user = new User();
		}
		
		return user;
	}
	
	public static void save(Editor editor){
		editor.putString("name", getInstance().getName());
		editor.putString("password", getInstance().getPassword());
		editor.commit();
	}
	
	public static User load(SharedPreferences preferences){
		
		String name = preferences.getString("name", "");
		String password = preferences.getString("password", "");
		
		if(name != "" && password != ""){
			return getInstance().name(name).password(password);
		}
		
		return null;
	}
	
	private static class User {
		private String name;
		private String password;
		
		
		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public User name(String name){
			setName(name);
			return this;
		}
		
		public User password(String password){
			setPassword(password);
			return this;
		}
	}
}
