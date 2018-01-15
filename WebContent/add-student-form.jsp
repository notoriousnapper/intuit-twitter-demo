<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


<!--  Need to give different form depending on if following  -->
<form action="UserControllerServlet" method="GET">
	<input type="hidden" name="command" value="FOLLOW" />
	<label> Follower name: </label>
	<input type="text" name="follower"/>

	<label> To Follow name: </label>
	<input type="text" name="followee"/>
	
	<input type="submit" value="follow"/>
</form>

<form action="UserControllerServlet" method="GET">
	<input type="hidden" name="command" value="UNFOLLOW" />
	<label> Follower name: </label>
	<input type="text" name="follower"/>

	<label> To UnFollow name: </label>
	<input type="text" name="followee"/>
	
	<input type="submit" value="follow"/>
</form>

<form action="UserControllerServlet" method="GET">
	<label> Does User1 follow User2 according to FOLLOWEE Table</label>
	<input type="hidden" name="command" value="CHECKFOLLOW" />
	<label> Follower name: </label>
	<input type="text" name="follower"/>

	<label> To UnFollow name: </label>
	<input type="text" name="followee"/>
	<input type="submit" value="IsFollower?"/>
</form>


<br/>
<br/>
<br/>
<br/>

<label> Get User </label>
<form action="UserControllerServlet" method="GET">
	<label> Does User1 follow User2 according to FOLLOWEE Table</label>
	<input type="hidden" name="command" value="LISTSINGLE" />
	<label> UserID name: </label>
	<input type="text" name="userId"/>
	<input type="submit" value="find if user exists"/>
</form>


<br/>
<br/>
<br/>
<!-- Things -->
<form action="TweetControllerServlet" method="GET">
	<label>Create a Tweet Here </label>
	<input type="hidden" name="command" value="TWEET" />
	<br/>

	<label> UserID: </label>
	<input type="text" name="userId"/>
	<br/>

	<label> Message: </label>
	<input type="text" name="message"/>
	<br/>
	
	<label> imageUrl: </label>
	<input type="text" name="imageUrl"/>
	<br/>
	
	<input type="submit" value="Create"/>

</form>


<!-- Things -->
<form action="TweetControllerServlet" method="GET">
	<label> GET USER WALL TWEETS</label>
	<input type="hidden" name="command" value="TIMELINE" />
	<br/>
	<label> UserID: </label>
	<input type="text" name="userId"/>
	<br/>
	<input type="submit" value="Get Tweets"/>
</form>

<br/>
<form action="TweetControllerServlet" method="GET">
	<label> GET USER HOME TWEETS</label>
	<input type="hidden" name="command" value="HOME" />
	<br/>
	<label> UserID: </label>
	<input type="text" name="userId"/>
	<input type="submit" value="Get Tweets"/>
</form>

<br/>

</body>
</html>