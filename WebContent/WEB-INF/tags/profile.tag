<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="com.twitter.model.Tweet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="user" required="true" type="com.twitter.model.User"%> <!--  Main Input! -->
<%@attribute name="isFollowing" required="true" type="java.lang.Boolean"%> <!--  Main Input! -->
<%@attribute name="pageUser" required="true" type="com.twitter.model.User"%> <!--  Main Input! -->

<link type="text/css" rel="stylesheet" href="<c:url value="/css/profile.css" />"/>

  <div class="title"> ${pageUser.userName} </div>
  <div class="handle">@ ${pageUser.userHandle}</div>
  <div class="bio">
      Pasta lover. I don't tweet much. My new Netflix series Master of None is now streaming on Netflix. I wrote a book called Modern Romance.
  </div>
  <br/>
  <br/>

  ${user.userName}
  ${pageUser.userName}
  <c:if test="${isFollowing == null}"><c:set var="isFollowing" value="${true}" /></c:if>
  <c:choose>

  <c:when test="${user.userName == pageUser.userName}">
	  <br/>
	  <br/>
	  Its yourself!
  </c:when>
  
  <c:when test="${!isFollowing}">
	  <form action="${pageContext.request.contextPath}/UserControllerServlet" method="GET">
		<input type="hidden" name="command" value="FOLLOW" />
		<input type="hidden" name="follower" value="${user.id}"/>
		<input type="hidden" name="followee" value="${pageUser.id}"/>
		<input type="hidden" name="pageUserName" value="${pageUser.userName}"/>
		<input type="submit" value="follow"/>
	  </form>
  </c:when>
  <c:otherwise>
	<form action="${pageContext.request.contextPath}/UserControllerServlet" method="GET">
		<input type="hidden" name="command" value="UNFOLLOW" />
		<input type="text" name="follower" value="${user.id}"/>
		<input type="text" name="followee" value="${pageUser.id}"/>
		<input type="hidden" name="pageUserName" value="${pageUser.userName}"/>
		<input type="submit" value="unfollow"/>
	</form>
  </c:otherwise>
</c:choose>
