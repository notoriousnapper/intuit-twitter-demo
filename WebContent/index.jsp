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

	if ((pageUserImageUrl.equals(""))){
		pageUserImageUrl = "http://allthingsd.com/files/2012/06/brad_smith_intuit.png";
	}
	

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

<html>
  <head>
    <link rel="stylesheet" href="index.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css" />"/>
  </head>
  <body>
	<t:banner user="${user}" isFollowing="${isFollowing}" pageUser="${pageUser}"></t:banner>



	<t:left-column>
	<t:profile user="${user}" pageUser="${pageUser}">
	</t:profile>
	</t:left-column>


	<div class="mid-column">
		<t:create-tweet userId="${userId}"></t:create-tweet> <!--  Entry Form for Tweets -->
		<% if (tweets != null) {
			  for (Tweet t: tweets) {
				   SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				  String string  = dateFormat.format(t.getTimeStamp());
				  System.out.println("Found a tweet: " + string);
			   %>
			<t:tweet data="<%=t%>" userName="<%=pageUserName%>" userHandle="<%=pageUserHandle%>"
			  imageUrl="<%= pageUserImageUrl%>" ></t:tweet>
		<% } }%>
	</div>
	
	
	  <form action="${pageContext.request.contextPath}/FeedControllerServlet" method="GET">
		<label> GO TO HOME FEED OF TWEETS</label>
		<input type="hidden" name="command" value="HOME" />
		<input type="text" name="userId" value="<%=userId%>"/>
		<input type="submit" value="Get Tweets"/>
	</form>
	  <br/>

  </body>
</html>

