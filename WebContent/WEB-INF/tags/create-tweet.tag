<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="userId" required="true" type="java.lang.Integer"%> <!--  Main Input! -->

<form action="TweetControllerServlet" method="GET">
	<label>Create a Tweet Here </label>
	<input type="hidden" name="command" value="TWEET" />
	<br/>

	<label> UserID: </label>
	<input type="text" name="userId" value="${userId}"/>
	<br/>

	<label> Message: </label>
	<input type="text" name="message"/>
	<br/>
	
	<label> imageUrl: </label>
	<input type="text" name="imageUrl"/>
	<br/>
	
	<input type="submit" value="Create"/>
</form>