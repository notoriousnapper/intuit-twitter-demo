<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.twitter.model.*" %>

<html>
  <form action="FeedControllerServlet" method="GET">
	<input type="hidden" name="command" value="AUTH" />
	<label> Type in your username: </label>
	<input type="text" name="username"/>
	<label> Type in  password: </label>
	<input type="text" name="password"/>
	<input type="submit" value="Login"/>
</form>
  
  

</html>
