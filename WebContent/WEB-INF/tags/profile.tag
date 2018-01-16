<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="com.twitter.model.Tweet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="user" required="true" type="com.twitter.model.User"%> <!--  Main Input! -->
<%@attribute name="pageUser" required="true" type="com.twitter.model.User"%> <!--  Main Input! -->

<link type="text/css" rel="stylesheet" href="<c:url value="/css/profile.css" />"/>

  <div class="title"> ${pageUser.userName} </div>
  <div class="handle">@ ${pageUser.userHandle}</div>
  <div class="bio">
      Pasta lover. I don't tweet much. My new Netflix series Master of None is now streaming on Netflix. I wrote a book called Modern Romance.
  </div>
  <br/>
  <br/>

