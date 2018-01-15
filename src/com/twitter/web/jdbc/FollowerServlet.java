package com.twitter.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.twitter.tweetsMVC.User;
import com.twitter.tweetsMVC.UserDbUtil;

/**
 * Servlet implementation class FollowerServlet
 */
@WebServlet("/FollowerServlet")
public class FollowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDbUtil userDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
       

	/**
     * @see HttpServlet#HttpServlet()
     */
    public FollowerServlet() {
        super();
        // TODO Auto-generated constructor stub
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

				case "LIST": 
					listUsers(request, response);
					break;
					
				case "ADD":
					addFollowers(request, response);

				default: 
					listUsers(request, response);
			}
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private void addFollowers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String followerId = request.getParameter("follower");
		String followeeId = request.getParameter("followee");
		userDbUtil.addFollower(followerId, followeeId);
		
		listUsers(request, response);

		
	}

	private void listUsers(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get users from db util 
		List<User> users = userDbUtil.getUsers();
		
		// add users to the request
		request.setAttribute("USERS_LIST",  users);
		
		// send to JSP page (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-users.jsp");
		dispatcher.forward(request, response);
		
		
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
			userDbUtil = new UserDbUtil(dataSource);
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
		
		
	}

}
