package com.twitter.tweetsMVC;
import java.sql.Timestamp;

public class Tweet {
	
	private int id;
	private int userId;
	private String message;
	private int likes;
	private String imageUrl;
	private Timestamp timeStamp;

	public Tweet(int id, int userId, String message, String imageUrl,
			Timestamp timeStamp) {
		super();
		this.id = id;
		this.userId = userId;
		this.message = message;
		this.imageUrl = imageUrl;
		this.likes = 0;
		this.timeStamp = timeStamp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", userId=" + userId + ", message="
				+ message + ", imageUrl=" + imageUrl + ", likes=" + likes
				+ ", timeStamp=" + timeStamp + "]";
	}

}
