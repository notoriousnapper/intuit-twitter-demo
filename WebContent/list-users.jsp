<%@ page import="java.util.*, com.twitter.web.jdbc.*" %>
<%@ page import="java.util.*, com.twitter.tweetsMVC.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title> Intuit Twitter Demo </title>
</head>
<body>

Get List of Users (Hidden API)
<%
	// get students from the request object 
	List<User> theUsers = 
	(List<User>) request.getAttribute("USERS_LIST");

%>

Let's go
<%= theUsers %>
<input type="button" value="Follow"
	onclick="window.location.href='add-student-form.jsp; return false;'
	class="follow-button"
/>



</body>
</html>