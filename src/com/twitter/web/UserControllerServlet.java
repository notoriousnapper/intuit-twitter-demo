package com.twitter.web;

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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.twitter.model.User;
import com.twitter.util.UserDbUtil;
/**
 * Servlet implementation class UserControllerServlet
 */
@WebServlet("/UserControllerServlet")
public class UserControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Step 1: Reference util, inject datasource
	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try { 
			// read command parameter
			String theCommand = request.getParameter("command");
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			switch (theCommand) {

				case "LISTSINGLE": 
					listSingleUser(request, response);
					break;

				case "LIST": 
					listUsers(request, response);
					break;
					
				case "FOLLOW":
					addFollowers(request, response);
					break;

				case "UNFOLLOW":
					removeFollowers(request, response);
					break;

				case "CHECKFOLLOW":
					checkFollower(request, response);
					break;
					
				case "AUTHENTICATE":
					authUser(request, response);
					break;

				default: 
					RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
					dispatcher.forward(request, response);
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}

	}

	private void authUser(HttpServletRequest request,
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
						
						RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
						dispatcher.forward(request, response);
						return;
					}
			}

			// If All Cases Fall Through
			request.setAttribute("USER", null);
			request.setAttribute("ERROR", true);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
			dispatcher.forward(request, response);
			return;
	}


	private void listSingleUser(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{
		// get users from db util 
		String userId = request.getParameter("userId");
		User user = userDbUtil.getSingleUserById(userId);
		
		// add users to the request
		request.setAttribute("USER",  user);
		
		// send to JSP page (view)
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print("<html><body>" + user + "</body></html>");
	}

	private boolean checkFollower(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception{

		String followerId = request.getParameter("follower");
		String followingId = request.getParameter("followee");
		boolean isFollower = userDbUtil.checkFollower(followerId, followingId);
		
		return isFollower;
		
	}

	private void listUsers(HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		
		// get users from db util 
		List<User> users = userDbUtil.getUsers();
		
		// add users to the request
		request.setAttribute("USERS_LIST",  users);
		
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-users.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addFollowers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String followerId = request.getParameter("follower");
		String followingId = request.getParameter("followee");
		
		if (!checkFollower(request, response)){
			userDbUtil.addFollower(followingId, followerId); // follower 2nd field
			userDbUtil.followUser(followingId, followerId);
		}
		
		String PageUserName = (String) request.getParameter("pageUserName");
		if (PageUserName != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/timeline/" + PageUserName);
			dispatcher.forward(request, response);
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/feed.jsp");
			dispatcher.forward(request, response);
		}
		
		return;
	}

	private void removeFollowers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String followerId = request.getParameter("follower");
		String followingId = request.getParameter("followee");
		userDbUtil.removeFollower(followingId, followerId);
		userDbUtil.removeFollowing(followingId, followerId);
		System.out.println("Called Both Remove Functions from Redis and SQL");

		String PageUserName = (String) request.getParameter("pageUserName");
		if (PageUserName != null) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/timeline/" + PageUserName);
			dispatcher.forward(request, response);
		}
		else {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/feed.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		// create our student db util, and pass in conn pool / datasource
		
		try {
			userDbUtil = new UserDbUtil(dataSource);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
		
	}

}
