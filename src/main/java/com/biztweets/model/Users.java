package com.biztweets.model;

public class Users {
	private String user;
	private String followingEntity;
	
	public Users() {}
	
	public Users(String user, String followingEntity) {
		super();
		this.user = user;
		this.followingEntity = followingEntity;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getFollowingEntity() {
		return followingEntity;
	}
	public void setFollowingEntity(String followingEntity) {
		this.followingEntity = followingEntity;
	}
	
}
