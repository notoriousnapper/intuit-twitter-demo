package com.twitter.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import com.twitter.tweetsMVC.Tweet;
import com.twitter.tweetsMVC.TweetDbUtil;

/**
 * Servlet implementation class Feed
 */
@WebServlet("/feed/*")
public class Feed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	private TweetDbUtil tweetDbUtil;

	private Gson _gson = null;
	private HashMap<String, Tweet> tweetModel = new HashMap<>();
	
	public Feed() {
        super();
        _gson = new Gson();
    }
       


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){
			return;
		}

		String[] token = pathInfo.split("/");
		
		if(token.length != 2) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		int userId = Integer.parseInt(token[1]);
		System.out.println(userId + "is userId");
		
//		if(!tweetModel.containsKey(userId)) {
//			
//			response.sendError(HttpServletResponse.SC_NOT_FOUND);
//			return;
//		}
		
		
		// Try And Get Home Feed from Redis
		try {
			System.out.println("In Try Catch Loop");
			Collection<Tweet> models = listHomeTweets(userId);
			System.out.println("Finished Receiving Home Tweets: " + models);
			sendAsJson(response, models);
		}
		catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
//		sendAsJson(response, tweetModel.get(modelId));
		return;
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
	
	private List<Tweet> listHomeTweets(int userId)
		throws Exception{
		// Re-usable Logic
		List<Tweet> tweets = null;
		tweets = tweetDbUtil.getHomeFeed(userId);
		// Servlet Specific Logic
		return tweets;
	}
	
	private Collection<Tweet> listTimeLineTweets(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		
		int userId = Integer.parseInt(request.getParameter("userId"));
		Collection<Tweet> tweets = null;
		tweets = tweetDbUtil.getTweets(userId);
		return tweets;
		
		}
	
    private void sendAsJson(
    		HttpServletResponse response, 
    		Object obj) throws IOException {
    		
    		response.setContentType("application/json");
    		
    		String res = _gson.toJson(obj);
    		PrintWriter out = response.getWriter();
    		out.print(res);
    		out.flush();
    }


}
