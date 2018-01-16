package com.twitter.web;

import java.io.IOException;
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
 * Servlet implementation class FeedControllerServlet
 */
@WebServlet("/FeedControllerServlet")
public class FeedControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDbUtil userDbUtil;
	private TweetDbUtil tweetDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
       
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSourceTweet;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FeedControllerServlet() {
        super();
    }

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
				case "HOME": 
					listHomeTweets(request, response);
					break;
					
				case "AUTH":
					boolean isValidUser = false;
					try {
						isValidUser = authUser(request, response);
					}
					catch (Exception e){
						
					}
					if (isValidUser){
					    listHomeTweets(request, response);
					}
					else {
						RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
						dispatcher.forward(request, response);
					}
					break;
				default: 
					listHomeTweets(request, response);
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private boolean authUser(HttpServletRequest request,
			HttpServletResponse response) 
		throws Exception {
		// get users from db util 
			boolean isValid = false;
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			
			if (username != null) {
				User user = userDbUtil.getSingleUserByUserName(username);
				if (user != null && (user.getPassword().equals(password))) { // User Exists and Found
						HttpSession session = request.getSession();
						session.setAttribute("userId", Integer.toString(user.getId()));
						session.setAttribute("userName", user.getUserName());
						session.setAttribute("userHandle", user.getHandle());
						session.setAttribute("imageUrl", user.getImageUrl());
						session.setAttribute("userPageId", Integer.toString(user.getId()));
						return true;
					}
			}
			// If All Cases Fall Through
			return false;
	}


	private void listHomeTweets(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		
		List<Tweet> tweets = new ArrayList<Tweet>();
		try {
			HttpSession session = request.getSession();
			System.out.println("userId" + session.getAttribute("userId"));
			int userId =  Integer.parseInt((String) (session.getAttribute("userId")));
			tweets = tweetDbUtil.getHomeFeed(userId);
			System.out.println(userId + "Is user and Timeline Tweets!" + tweets);
			request.setAttribute("Feed", tweets);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
				
		RequestDispatcher dispatcher = request.getRequestDispatcher("/feed.jsp");
		dispatcher.forward(request, response);
		return;
	}

	@Override
	public void init() throws ServletException {
		super.init();
		// create our student db util, and pass in conn pool / datasource
		
		try {
			userDbUtil = new UserDbUtil(dataSource);
			tweetDbUtil = new TweetDbUtil(dataSourceTweet);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
		
	}


}
