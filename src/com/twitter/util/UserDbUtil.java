package com.twitter.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import com.twitter.model.User;

import redis.clients.jedis.Jedis;

public class UserDbUtil {

	private DataSource dataSource;

	public UserDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	public User getSingleUserById(String userId) throws Exception {
		User tempUser = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection and create sql
			myConn = dataSource.getConnection();
			String sql = "select * from user where id = ? ";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, userId);
			
			System.out.println("The sql line is: " + sql + userId);

			// execute query
			myRs = myStmt.executeQuery();
			// process result set
			if (myRs.next()) {
				// retrive data from result set row
				int id = myRs.getInt("id");
				String userName = myRs.getString("userName");
				String password = myRs.getString("password");
				String email = myRs.getString("email");
				String imageUrl = myRs.getString("imageUrl");
				String userHandle = myRs.getString("userHandle");

				tempUser = new User(id, userName, password, email,
						imageUrl, userHandle);
				System.out.println(tempUser.toString());

			}
			else {
				System.out.println("Where is the User?");
			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		// close JDBC objects

		return tempUser;

	}
	public User getSingleUserByUserName(String userName) throws Exception {
		User tempUser = null;
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection and create sql
			myConn = dataSource.getConnection();
			String sql = "select * from user where userName = ? ";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, userName);
			

			// execute query
			myRs = myStmt.executeQuery();
			// process result set
			if (myRs.next()) {
				// retrive data from result set row
				int id = myRs.getInt("id");
				String password = myRs.getString("password");
				String email = myRs.getString("email");
				String imageUrl = myRs.getString("imageUrl");
				String userHandle = myRs.getString("userHandle");

				tempUser = new User(id, userName, password, email,
						imageUrl, userHandle);

			}
			else {
				System.err.println("ERROR Getting user, does not exist.");
			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		// close JDBC objects

		return tempUser;

	}
	public List<User> getUsers() throws Exception {

		List<User> users = new ArrayList<>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection
			myConn = dataSource.getConnection();
			// create a sql statement
			String sql = "select * from user";
			myStmt = myConn.createStatement();

			// execute query
			myRs = myStmt.executeQuery(sql);

			// process result set
			while (myRs.next()) {

				// retrieve data from result set row
				int id = myRs.getInt("id");
				String userName = myRs.getString("userName");
				String password = myRs.getString("password");
				String email = myRs.getString("email");
				String imageUrl = myRs.getString("imageUrl");
				String userHandle = myRs.getString("userHandle");

				User tempUser = new User(id, userName, password, email,
						imageUrl, userHandle);
				users.add(tempUser);

			}
		} finally {
			close(myConn, myStmt, myRs);
		}

		// close JDBC objects

		return users;
	}
	
	public boolean checkFollower(String followerId, String followeeId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		boolean isFollower = false;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			String sql = "Select 1 from followee where userId = ? and followeeId = ? ";  

			PreparedStatement ps = myConn.prepareStatement(sql);
			ps.setString(1, followerId);
			ps.setString(2, followeeId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()){
				isFollower = true; 
			}

		} finally {
			close(myConn, myStmt, null);
		}
		return isFollower;
	}

	public void removeFollower(String followerId, String followeeId) throws Exception {
		// Step 1: Delete from Redis Feed for given follower
		// Step 2: Delete from Followee's list
		// Step 3: Finally, Delete in user's own follower list
	}

	// TOTAL STEPS FOR A FOLLOW: 
		// Steps 1: Add followee to follow list,  
		// Steps 2: Update followee's follower list
		// Steps 3: [EXTRA] Update followee's stats +1, follower +1 followed[

	/* Following a Celebrity: table of who you follow */
	public void followUser(String userId, String followeeId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// get db connection
			myConn = dataSource.getConnection();
			// create a sql statement
			String sql = "insert into followee "
					+ "(userId, followeeId) "
					+ "values(?, ?)";
			myStmt = myConn.prepareStatement(sql);

			// set the param values for student
			myStmt.setString(1, userId);
			myStmt.setString(2, followeeId);

			// execute query
			myStmt.execute();


			// process result set
		} finally {
			close(myConn, myStmt, null);
		}

		// close JDBC objects



	}
	
	
	// A Celebrity's [Followee] # of followers grows
	public void addFollower(String userId, String followerId) throws Exception {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		try {
			// get db connection
			myConn = dataSource.getConnection();
			// create a sql statement
			String sql = "insert into follower "
					+ "(userId, followerId) "
					+ "values(?, ?)";
			myStmt = myConn.prepareStatement(sql);

			// set the param values for student
			myStmt.setString(1, userId);
			myStmt.setString(2, followerId);

			// execute query
			myStmt.execute();
			
			// Add user/follower set to Redis for fast access
			Jedis jedis = new Jedis("localhost");
			jedis.sadd(userId, followerId);
			jedis.close();


			// process result set
		} finally {
			close(myConn, myStmt, null);
		}

		// close JDBC objects

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
