package com.twitter.tweetsMVC;

public class User {
	
	private int id;
	private String userName;
	private String password;
	private String email;
	private String imageUrl;
	private String userHandle;
	
	public User(int id, String userName, String password, String email,
			String imageUrl, String handle) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.imageUrl = imageUrl;
		this.userHandle = handle;
	}

	public int getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getHandle() {
		return userHandle;
	}

	public void setHandle(String handle) {
		this.userHandle = handle;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password="
				+ password + ", email=" + email + ", imageUrl=" + imageUrl
				+ ", handle=" + userHandle + "]";
	}


}
