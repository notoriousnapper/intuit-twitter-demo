package com.twitter.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.twitter.tweetsMVC.Tweet;
import com.twitter.tweetsMVC.TweetDbUtil;
import com.twitter.tweetsMVC.User;
import com.twitter.tweetsMVC.UserDbUtil;

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

	}
	private List<Tweet> listHomeTweets(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		
		List<Tweet> tweets = null;
//		int[] userIdList = {1, 2, 3};
		int userId = Integer.parseInt(request.getParameter("userId"));
		tweets = tweetDbUtil.getHomeFeed(userId);
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print("<html><body> Tweets By User: \n");
		for (Tweet tweet: tweets) {
			out.print(tweet.getUserId());
			out.print(tweet.getMessage());
			out.print(tweet.getTimeStamp());
			out.print("\n\n");
		}
		out.print("</body></html>");

		return tweets;
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
//			out.print(tweet.getTimeStamp()); // will be incorrect
			out.print("\n\n");
		}
		out.print("</body></html>");

//		request.setAttribute("timeLine",  tweets);
////		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
//		dispatcher.forward(request, response);
		}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		// create our student db util, and pass in conn pool / datasource
		
		try {
			tweetDbUtil = new TweetDbUtil(dataSource);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
		
	}
}
