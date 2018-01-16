<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.twitter.model.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.twitter.util.PreLoad" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<link type="text/css" rel="stylesheet" href="
<c:url value="/css/index.css" />
"/>
<link type="text/css" rel="stylesheet" href="
<c:url value="/css/column.css" />
"/>
<html>
   <head>
      <link rel="stylesheet" href="index.css">
      <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700" rel="stylesheet">
   </head>
   <body>
      <% 
         // I. Load Feed of 100 Most Recent Tweets, sorted by most recent
         List<Tweet> tweets = (List<Tweet>) request.getAttribute("Feed");
         if (tweets != null){
         	Collections.reverse(tweets);
         }
         
         // II. Pre-Load User
         User user = PreLoad.preloadUserBySession(request.getSession(false));
         request.setAttribute("user", user);
         boolean isFollowing = false;
         
         /* III. Redirect if not Logged In */
            if (session.getAttribute("userId") == null) {
            	RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
         	dispatcher.forward(request, response);
            }
         %>



      Home Page for <c:out value="${user.userName}"/>

      <t:create-tweet userId="${user.id}"></t:create-tweet>
      <!--  Entry Form for Tweets -->
      <t:banner user="${user}" isFollowing="false" pageUser="${user}" disabled="true" imageUrl="${user.imageUrl}>"></t:banner>
      <t:left-column>
         <t:profile user="${user}" pageUser="${user}">
         </t:profile>
      </t:left-column>
      
      <!--  DISPLAY TWEETS -->
      <div class="mid-column" style="vertical-align: top; display: inline-block; height: 100%;">
         <c:forEach var="t" items="<%=tweets%>">
            <t:tweet data="${t}">" ></t:tweet>
         </c:forEach>
      </div>
   </body>
</html>