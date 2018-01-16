package com.twitter.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.twitter.model.Tweet;
import com.twitter.model.User;
import com.twitter.util.TweetDbUtil;
import com.twitter.util.UserDbUtil;

/**
 * Servlet implementation class TweetControllerServlet
 */
@WebServlet("/TweetControllerServlet")
public class TweetControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Step 1: Reference util, inject datasource
	private TweetDbUtil tweetDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
			// read command parameter
			String theCommand = request.getParameter("command");
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			switch (theCommand) {
				case "TWEET": 
					createTweet(request, response);
					break;

				case "TIMELINE": 
					listTimeLineTweets(request, response);
					break;

				case "HOME": 
					listHomeTweets(request, response);
					break;

				default: 
					createTweet(request, response);
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}

	}

	private void createTweet(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		String message = request.getParameter("message");
		String imageUrl = request.getParameter("imageUrl");
		tweetDbUtil.createTweet(userId, message, imageUrl);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	private void listHomeTweets(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		
		List<Tweet> tweets = new ArrayList<Tweet>();
		int userId = Integer.parseInt(request.getParameter("userId"));
		try {
			HttpSession session = request.getSession();
				tweets = tweetDbUtil.getHomeFeed(userId);
				request.setAttribute("Feed", tweets);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}

		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print("<html><body> Tweets By User: \n");
		for (Tweet tweet: tweets) {
			out.print(tweet.getUserId());
			out.print(tweet.getMessage());
//			out.print(tweet.getTimeStamp()); // will be incorrect
			out.print("\n\n");
		}

		out.print("</body></html>");
				
		return;
					
	}

	private void listTimeLineTweets(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		List<Tweet> tweets = null;
		tweets = tweetDbUtil.getTweets(userId);
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print("<html><body> Tweets By User: \n");
		for (Tweet tweet: tweets) {
			out.print(tweet.getUserId());
			out.print(tweet.getMessage());
			out.print("\n\n");
		}

		out.print("</body></html>");

		}

	@Override
	public void init() throws ServletException {
		super.init();

		try {
			tweetDbUtil = new TweetDbUtil(dataSource);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
		
	}
}
