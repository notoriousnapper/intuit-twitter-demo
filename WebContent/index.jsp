<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.twitter.tweetsMVC.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>
<% 
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

	session.setAttribute("profileUrl", "http://lorempixel.com/200/200/sports/");

	List<Tweet>  tweets = (List<Tweet>) request.getAttribute("timeLine");
	if (userId == userPageId && tweets == null) {
	    request.setAttribute("command", "TIMELINE");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

%>

<html>
  <head>
    <link rel="stylesheet" href="index.css">
    <link href="https://fonts.googleapis.com/css?family=Lato:300,400,700" rel="stylesheet">
  </head>
  UserName: <%=userName %>
  UserHandle: <%=userHandle %>
  
  <jsp:include page="profile.jsp"/> <!--  Profile Indicator -->
  <% if (tweets != null) {
	  for (Tweet t: tweets) {
 		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		  String string  = dateFormat.format(t.getTimeStamp());
		  System.out.println(string);
 	  %>
  <!--  Start Of a Single Tweet -->
    <div class="tweet">
      <img class="profile-picture" src="http://allthingsd.com/files/2012/06/brad_smith_intuit.png" alt="">
      <div class="top-line">
        <span class="title">
            <!-- Brad D. Smith-->
            <%=t.getUserId() %>
        </span>
        <span class="handle">@IntuitBrad</span>
        <span class="date">Jan. 7</span>
      </div>


      <div class="twitter-line">
      		<%=t.getMessage() %>
		  </div>
      <div class="twitter-line">
        <span><button>Like</button></span>
      </div>
    </div> <!-- End of Single Tweet -->
<% }

  }%>
  
  

</html>
