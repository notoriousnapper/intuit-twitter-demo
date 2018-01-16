<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.twitter.model.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.*" %>
<% 

	List<Tweet> tweets = (List<Tweet>) request.getAttribute("Feed");
	if (tweets != null){
		Collections.reverse(tweets);
	}
	System.out.println("Getting HOME FEED" + tweets);
	
    // Let this be the wall Page
	String userId = (String) session.getAttribute("userId");
	String userName = (String) session.getAttribute("userName");
	String userHandle  = (String) session.getAttribute("userHandle");
	String userPageId = (String) session.getAttribute("userPageId");
	String imageUrl = (String) session.getAttribute("imageUrl");

    if (userId == null) {
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
		dispatcher.forward(request, response);
    }

    User user = new User(Integer.parseInt(userId), userName, "", "", imageUrl, userHandle);
    User pageUser = new User(2, "Daniel Radcliffe", "wtv", "", "",  "Harry Potter");

	session.setAttribute("profileUrl", "http://lorempixel.com/200/200/sports/");
	request.setAttribute("user", user);
	request.setAttribute("pageUser", pageUser);
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>




<html>
  <head>
    <link rel="stylesheet" href="index.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css" />"/>
  </head>
  UserName: <%=userName %>
  UserHandle: <%=userHandle %>
  <body>
  

  <br/>
  <% if (tweets != null) {
	  for (Tweet t: tweets) {
 		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		  String string  = dateFormat.format(t.getTimeStamp());
		  System.out.println("Found a tweet: " + string);
 	  %>
	<t:tweet data="<%=t%>" ></t:tweet>
<% }

  }%>
  </body>
</html>

