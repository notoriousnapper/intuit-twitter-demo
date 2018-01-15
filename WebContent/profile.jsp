<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="profile.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String userName = (String) session.getAttribute("userName");
		String userHandle = (String) session.getAttribute("userHandle");
		String userId = (String) session.getAttribute("userId");
		String profileUrl = (String) session.getAttribute("profileUrl");
		String userPageId = (String) session.getAttribute("userPageId");
	%>

	<div class="profile">
			<img class="profile-picture" src="<%= profileUrl %>" />
		<div>
				<span> <%=userName%> </span>
				<span> <%=userHandle %> </span>
			<% if(userId == userPageId ){ %>
				<input type="text" value="Follow"/>
			<% } else { %>
				This is your page!
			<% } %>
		</div>
	</div>
	<br/>
	<br/>
	<br/>
</body>
</html>