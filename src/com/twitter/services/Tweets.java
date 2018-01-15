package com.twitter.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.twitter.tweetsMVC.Tweet;

/**
 * Servlet implementation class Tweets
 */
@WebServlet("/tweets/*")
public class Tweets extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson _gson = null;
	private HashMap<String, Tweet> tweetModel = new HashMap<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Tweets() {
        super();
        _gson = new Gson();

		int id1 = 1;
		int id2 = 2;
		int id3 = 3;

		Timestamp tstamp = new Timestamp(System.currentTimeMillis());
		
		tweetModel.put(Integer.toString(id1), 
			new Tweet(
			 id1,		
			 id1,		
			"How's everyone doing!", 
			"... base 64 URN ...", 
			tstamp));

		tweetModel.put(Integer.toString(id2), 
			new Tweet(
			 id2,		
			 id2,		
			"Engine", 
			"Its a bit late but I'm powering through!", 
			tstamp));

		tweetModel.put(Integer.toString(id3), 
			new Tweet(
			 id3,		
			 id3,		
			"Engine", 
			"Make use of all the time you have!", 
			tstamp));
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String pathInfo = request.getPathInfo();

		if(pathInfo == null || pathInfo.equals("/")){
			Collection<Tweet> models = tweetModel.values();
			sendAsJson(response, models);
			return;
		}

		String[] token = pathInfo.split("/");
		
		if(token.length != 2) {
			
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String modelId = token[1];
		
		if(!tweetModel.containsKey(modelId)) {
			
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		sendAsJson(response, tweetModel.get(modelId));
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
