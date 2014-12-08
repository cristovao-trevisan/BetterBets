package com.cristovaotrevisan.betterbets;

import java.util.List;

public class User {
	private String id;
	private List<User> friendsList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFriendsList(List<User> friends){
		this.friendsList = friends;
	}
	public List<User> getFriendsList(){
		return friendsList;
	}
	
	public User(String id, List<User> friends){
		this.id = id;
		this.friendsList = friends;
	}
}
