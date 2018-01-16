<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="com.twitter.model.Tweet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="data" required="true" type="com.twitter.model.Tweet"%> <!--  Main Input! -->
<%@attribute name="userName" required="false" type="java.lang.String"%> <!--  Main Input! -->
<%@attribute name="userHandle" required="false"  type="java.lang.String"%> <!--  Main Input! -->
<%@attribute name="imageUrl" required="false"  type="java.lang.String"%> <!--  Main Input! -->

<link type="text/css" rel="stylesheet" href="<c:url value="/css/tweet.css" />"/>

<!--  NOTE: Overriding - user, because it should be the same user -->


<html>
<body>
  <head>
  </head>
  <div class="tweet">
  <img class="profile-picture" src="${imageUrl}" alt="">
<!-- Main Content -->
  <div class="main">
  <div class="top-line">
    <span class="title"> ${userName}  </span>
    <span class="handle">@ ${userHandle}</span>
    <span class="date"> ${data.timeStamp}</span>
  </div>

  <div class="twitter-line"> ${data.message} </div>
  <div class="twitter-line">
    <span><button>Like</button></span>
    <span><button>Retweet</button></span>

  </div>
</div> <!-- End of Single Tweet -->
</div>

</body>
</html>
