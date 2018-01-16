<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="userId" required="true" type="java.lang.Integer"%> <!--  Main Input! -->
<link type="text/css" rel="stylesheet" href="<c:url value="/css/create-tweet.css" />"/>

<div class="create-tweet">
	<form  action="TweetControllerServlet" method="GET">
		<label>Create a Tweet Here </label>
		<input type="hidden" name="command" value="TWEET" />
		<input type="hidden" name="userId" value="${userId}"/>
		<input type="hidden" name="imageUrl" value=""/>
		<label> Message: </label>
		<input type="text" name="message"/>
		<input type="submit" value="Create"/>
	</form>
</div>