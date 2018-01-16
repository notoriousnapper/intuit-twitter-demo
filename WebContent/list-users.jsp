<%@ page import="java.util.*, com.twitter.web.*" %>
<%@ page import="java.util.*, com.twitter.model.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title> Intuit Twitter Demo </title>
</head>
<body>

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