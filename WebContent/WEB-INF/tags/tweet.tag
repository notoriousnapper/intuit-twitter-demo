<%@tag description="Simple Wrapper Tag" pageEncoding="UTF-8"%>
<%@tag import="com.twitter.model.Tweet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@attribute name="data" required="true" type="com.twitter.model.Tweet"%> <!--  Main Input! -->

<link type="text/css" rel="stylesheet" href="<c:url value="/css/tweet.css" />"/>


<html>
<body>
  <head>
  </head>

  <div class="tweet">
  <img class="profile-picture" src="http://allthingsd.com/files/2012/06/brad_smith_intuit.png" alt="">

<!-- Main Content -->
  <div class="main">
  <div class="top-line">
    <span class="title"> ${data.userName}  </span>
    <span class="handle">@ ${data.userHandle}</span>
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
