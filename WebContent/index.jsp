<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.twitter.model.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.*" %>
<% 
    // This Page is Timeline Page 
	List<Tweet> tweets = (List<Tweet>) request.getAttribute("Timeline");
	
    // Let this be the wall Page
	String pageUserId = (String) session.getAttribute("PageUserId");
	String pageUserName = (String) session.getAttribute("PageUserName");
	String pageUserHandle  = (String) session.getAttribute("PageUserHandle");
	String pageUserPageId = (String) session.getAttribute("PageUserPageId");
	String pageUserImageUrl = (String) session.getAttribute("PageUserImageUrl");
	

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
    User pageUser = new User(Integer.parseInt(pageUserId),pageUserName, "", "", pageUserImageUrl,  pageUserHandle);

	session.setAttribute("profileUrl", "http://lorempixel.com/200/200/sports/");
	request.setAttribute("user", user);
	request.setAttribute("pageUser", pageUser);

	boolean isFollowing = false;
	if (request.getAttribute("isFollowing") != null){
		isFollowing = (boolean) request.getAttribute("isFollowing");
	}


%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:profile user="${user}" isFollowing="${isFollowing}" pageUser="${pageUser}">
</t:profile>

<t:create-tweet userId="${userId}"></t:create-tweet>

<% 
	Timestamp tstamp = new Timestamp(System.currentTimeMillis());
	Tweet tweet = new Tweet(1, 1, "Happy 2018", "", tstamp );
	tweet.setUserName("Brad D. Smith");
	tweet.setUserHandle("Things");
	request.setAttribute("tweet", tweet);
    %>




<html>
  <head>
    <link rel="stylesheet" href="index.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css" />"/>
  </head>
  UserName: <%=userName %>
  UserHandle: <%=userHandle %>
  <body>
  

  <form action="${pageContext.request.contextPath}/FeedControllerServlet" method="GET">
	<label> GO TO HOME FEED OF TWEETS</label>
	<input type="hidden" name="command" value="HOME" />
	<input type="text" name="userId" value="<%=userId%>"/>
	<input type="submit" value="Get Tweets"/>
</form>
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

