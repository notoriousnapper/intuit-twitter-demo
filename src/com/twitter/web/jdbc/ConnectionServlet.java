package com.twitter.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import redis.clients.jedis.Jedis;


/**
 * Servlet implementation class ConnectionServlet
 */
@WebServlet("/ConnectionServlet")
public class ConnectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Define datasource/connection pool for Resource Injection
	@Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConnectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Step 0: Set up Jedis
		
		try {
			Jedis jedis = new Jedis("localhost");
			System.out.println("Connection Successful");
			String userId = "123";
			String userId2 = "123";
			String tweetId = "321";

			System.out.println("List push one two: " + jedis.lpush(userId, "1"));
			System.out.println("List push one two: " + jedis.lpush(userId, "2"));
			System.out.println("List push one two: " + jedis.lpush(userId, "3"));

			System.out.println("List push one two: " + jedis.lpush(userId2, tweetId));
			System.out.println("List push one two: " + jedis.lpush(userId2, "2"));
			System.out.println("List pop : " + jedis.lpop(userId));
			System.out.println("List pop : " + jedis.lpop(userId2));
			System.out.println("List pop : " + jedis.lpop(userId));
			System.out.println("List pop : " + jedis.lpop(userId2));
		}
		catch(Exception e)
		{
			System.out.println(e);
			
		}
		// Step 1: Set up PrintWriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		// Step 2: Connection to DB
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			// Step 3: Create SQL Statements
			myConn =  dataSource.getConnection(); // from Connection Pool
			String sql = "select * from student";
			myStmt = (Statement) myConn.createStatement();
		
			// Step 4: Execute SQL query
			myRs = myStmt.executeQuery(sql);
			
			// Step 5: Process Result Set
			while (myRs.next()) {
				String email = myRs.getString("email");
				out.println(email);
			}

		}
		
		catch (Exception exc) {
			exc.printStackTrace();
		}
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
