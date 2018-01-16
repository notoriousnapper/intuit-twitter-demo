<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="user" required="true" type="com.twitter.model.User"%> <!--  Main Input! -->
<%@attribute name="isFollowing" required="true" type="java.lang.Boolean"%> <!--  Main Input! -->
<%@attribute name="pageUser" required="true" type="com.twitter.model.User"%> <!--  Main Input! -->
<%@attribute name="disabled" required="false" type="java.lang.Boolean"%> <!--  Main Input! -->
<%@attribute name="imageUrl" required="true" type="java.lang.String"%> <!--  Main Input! -->
<link type="text/css" rel="stylesheet" href="<c:url value="/css/banner.css" />"/>

    <div class="banner">
      <div class="banner-top"></div>
      <img class="profile-main" src="${imageUrl}" alt="">
      <div class="banner-bottom">

        <div class="stats">
          <span> Tweets </span>
          <div class="stats-number"> <span> ? </span> </div> <!-- will loop over anyways -->
        </div>
        <div class="stats">
          <span> Following  </span>
          <div class="stats-number"> <span> ? </span> </div> <!-- will loop over anyways -->
        </div class="stats">
        <div class="stats">
          <span> Followers</span>
          <div class="stats-number"> <span> ? </span> </div> <!-- will loop over anyways -->
        </div class="stats">
        <div class="stats">
          <span> Likes</span>
          <div class="stats-number"> <span> ? </span> </div> <!-- will loop over anyways -->
        </div class="stats">

          
          <!--  Enter Conditions  -->
        <div class="stats-right" style="right: 0;">
            <c:if test="${isFollowing == null}"><c:set var="isFollowing" value="${true}" /></c:if>
		  <c:choose>

		  <c:when test="${disabled}">
		  </c:when>
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
				<div class="button-container">
					<input class="button-follow" type="submit" value="follow"/>
				</div>
			  </form>
		  </c:when>
		  <c:otherwise>
			<form action="${pageContext.request.contextPath}/UserControllerServlet" method="GET">
				<input type="hidden" name="command" value="UNFOLLOW" />
				<input type="hidden" name="follower" value="${user.id}"/>
				<input type="hidden" name="followee" value="${pageUser.id}"/>
				<input type="hidden" name="pageUserName" value="${pageUser.userName}"/>
				<div class="button-container">
					<input class="button-unfollow" type="submit" value="unfollow"/>
				</div>
			</form>
		  </c:otherwise>
		</c:choose>
      </div>
      </div>
    </div> <!-- End of Banner -->

