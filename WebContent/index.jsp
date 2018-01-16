<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.twitter.model.*" %>
<%@ page import="com.twitter.util.PreLoad" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<% 
    // This Page is Timeline Page 
	List<Tweet> tweets = (List<Tweet>) request.getAttribute("Timeline");

	// Pre-Load Front-End Logic
	User pageUser = PreLoad.preloadPageUserBySession(session);
	User user = PreLoad.preloadUserBySession(session);
	request.setAttribute("user", user);
	request.setAttribute("pageUser", pageUser);

	boolean isFollowing = false;
	if (request.getAttribute("isFollowing") != null){
		isFollowing = (boolean) request.getAttribute("isFollowing");
	}

%>

<html>
  <head>
    <link rel="stylesheet" href="index.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="<c:url value="/css/index.css" />"/>
  </head>
  <body>
	<t:banner user="${user}" isFollowing="${isFollowing}" pageUser="${pageUser}" imageUrl="${pageUser.imageUrl}"></t:banner>

	<t:left-column>
	<t:profile user="${user}" pageUser="${pageUser}">
	</t:profile>
	</t:left-column>

	<div class="mid-column">
		<t:create-tweet userId="${userId}"></t:create-tweet> <!--  Entry Form for Tweets -->
         <c:forEach var="t" items="<%=tweets%>">
            <t:tweet data="${t}">" ></t:tweet>
         </c:forEach>
	</div>
	
	
	  <form action="${pageContext.request.contextPath}/FeedControllerServlet" method="GET">
		<label> GO TO HOME FEED OF TWEETS</label>
		<input type="hidden" name="command" value="HOME" />
		<input type="text" name="userId" value="${user.id}"/>
		<input type="submit" value="Get Tweets"/>
	</form>
	  <br/>

  </body>
</html>

