package com.twitter.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.twitter.model.Tweet;

import redis.clients.jedis.Jedis;

public class TweetDbUtil {

	private DataSource dataSource;

	public TweetDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public void createTweet(int userId, String message, String imageUrl) throws Exception {
		/* For now, don't worry about imageUrl */
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Tweet tweet = null;
		int returnedKey = -1;

		try {
			myConn = dataSource.getConnection();
			String sql = " insert into tweet (userId, message, imageUrl, timeStamp)"
				+ " values (?, ?, ?, ?)";

			myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			Timestamp tstamp = new Timestamp(System.currentTimeMillis());
			myStmt.setInt(1, userId);
			myStmt.setString(2,message);
			myStmt.setString(3,imageUrl);
			myStmt.setTimestamp(4, tstamp);

			// execute query
			myStmt.executeUpdate();
			ResultSet keys = myStmt.getGeneratedKeys();
			

			// get tweetId of just made row
			keys.next();
			returnedKey = keys.getInt(1);
			keys.close();

			// Open Redis connection, 
			// Cycle through userId's followers, and add tweetId to Redis List

			if (returnedKey != -1) {
				// cycle through userId's followers, and add tweetId 
				Jedis jedis = new Jedis("localhost");
				Set<String> followerList = jedis.smembers(Integer.toString(userId));

				for (String tempUserId : followerList) {
					jedis.lpush(("l:" + tempUserId), Integer.toString(returnedKey));
				}
				jedis.close();
			}

		} finally {
			close(myConn, myStmt, myRs);
		}

	}
	public Tweet getTweetByUserId(int userId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		Tweet tweet = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();
			// create a sql statement
			String sql = "select * from tweet where userId = ? ";
			myStmt = myConn.prepareStatement(sql);

			myStmt.setString(1,Integer.toString(userId));

			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			if (myRs.next()) {
				// retrive data from result set row
				int id = myRs.getInt("id");
				String message = myRs.getString("message");
				int likes = myRs.getInt("likes");
				String imageUrl = myRs.getString("imageUrl");
				Timestamp time = new Timestamp(System.currentTimeMillis());

				tweet = new Tweet(id, userId, message, 
						imageUrl, time);

			}
			else {
			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		// close JDBC objects

		return tweet;

	}
	
	public List<Tweet> getHomeFeed(int userId) throws Exception {
		List<Tweet> tweets = new ArrayList<Tweet>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		
		// cycle through userId's followers, and add tweetId 
		Jedis jedis = new Jedis("localhost");
		List<String> tweetIds = jedis.lrange(("l:" + userId), (-1* Constants.TWEET_LIMIT), -1);
		jedis.close();

		try {
			// get a connection

			myConn = dataSource.getConnection();
//			String sql = 
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM tweet WHERE ID IN (?");
			
			if (tweetIds.isEmpty()){
				return Collections.emptyList();
			}
			
			else {
					
				/* Create Large statement */
				int i = 0;
				while( i <= tweetIds.size() - 2 ) {
					// Linting Rule: squid:S1643)
					sql.append(",?");
					i++;  
				}
				sql.append(") ");
				
				
				myStmt = myConn.prepareStatement(sql.toString());
				
				i = 1;
				for (String tId : tweetIds) {
					myStmt.setInt(i, Integer.parseInt(tId));
					i++;
				}

				// execute query
				myRs = myStmt.executeQuery();

				// process result set
				i = 0;
				while (myRs.next()) {

					// Retrieve data from result set row
					int id = myRs.getInt("id");
					int tempUserId = myRs.getInt("userId");
					String message = myRs.getString("message");
					int likes = myRs.getInt("likes");
					String imageUrl = myRs.getString("imageUrl");
					Timestamp time = new Timestamp(System.currentTimeMillis());

					Tweet tempTweet = new Tweet(id, tempUserId, message, 
							imageUrl, time);
					tweets.add(tempTweet);
					i++;

				}
			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		// close JDBC objects

		return tweets;
	}
	
	

	/* Grab 10 Most Recent Tweets of given user */
	/* Rename to Wall */
	public List<Tweet> getTweets(int userId) throws Exception {
		List<Tweet> tweets = new ArrayList<>();
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();
			String sql = "SELECT * FROM tweet " + 
					   "WHERE userId = ? " + 
					   "ORDER BY timeStamp DESC " + 
					   "LIMIT 10;";
			
			myStmt = myConn.prepareStatement(sql);
			myStmt.setInt(1, userId);
			// execute query
			myRs = myStmt.executeQuery();

			// process result set
			while (myRs.next()) {

				// retrive data from result set row
				int id = myRs.getInt("id");
				String message = myRs.getString("message");
				int likes = myRs.getInt("likes");
				String imageUrl = myRs.getString("imageUrl");
				Timestamp time = new Timestamp(System.currentTimeMillis());

				Tweet tempTweet = new Tweet(id, userId, message, 
						imageUrl, time);
				tweets.add(tempTweet);

			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		// close JDBC objects

		return tweets;
	}


	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close(); // Return for re-use in connection pool

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}


}
