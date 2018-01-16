package com.twitter.web;

import java.io.IOException;
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
 * Servlet implementation class MainControllerServlet
 */
@WebServlet("/timeline/*")
public class MainControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Step 1: Reference util, inject datasource
	private UserDbUtil userDbUtil;
	private TweetDbUtil tweetDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
       
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSourceTweet;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainControllerServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Redirect to home page
		// with username, will grab - user by username!! Wow 
		String pathInfo = request.getPathInfo();
		if(pathInfo == null || pathInfo.equals("/")){
			return;
		}

		String[] token = pathInfo.split("/");
		String username = token[1];
		System.out.println(username + " is username!");

		if (username != null) {
			User user = null;
			try {
				user = userDbUtil.getSingleUserByUserName(username);
				HttpSession session = request.getSession();
				if(user != null){
						
					session.setAttribute("pageUserId", Integer.toString(user.getId()));
					session.setAttribute("pageUserName", user.getUserName());
					session.setAttribute("pageUserHandle", user.getHandle());
					session.setAttribute("pageUserImageUrl", user.getImageUrl());
					session.setAttribute("pageUserPageId", Integer.toString(user.getId()));
					
					List<Tweet> tweets = tweetDbUtil.getTweets(user.getId());
					System.out.println("LOGGING: Timeline Tweets - " + tweets);
					request.setAttribute("Timeline", tweets);

					// Check if Following, return to page, current user is userId
					String userId = (String) session.getAttribute("userId");
					boolean isFollowing = false;
					isFollowing = userDbUtil.checkFollower(userId,Integer.toString(user.getId()));
					request.setAttribute("isFollowing", isFollowing);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

				RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
				return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
