<!DOCTYPE HTML>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<head>
  <title>bizTweets</title>
  <meta name="description" content="website description" />
  <meta name="keywords" content="website keywords, website keywords" />
  <meta http-equiv="content-type" content="text/html; charset=windows-1252" />
  <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Tangerine&amp;v1" />
  <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Yanone+Kaffeesatz" />
  <link rel="stylesheet" type="text/css" href="resources/css/main.css" />
  <link rel="stylesheet" type="text/css" href="resources/css/entities.css" />
</head>

<body>
  <div id="main">
    <div id="header">
      <div id="logo">
        <h1>bizTweets</h1>
        <div id="menubar">
        <ul id="menu">
          <!-- put class="current" in the li tag for the selected page - to highlight which page you're on -->
          <li class="current"><a href="#">Home</a></li>
          <li class=""><a href="#" onclick="loadBizEntities();">bizEntities</a></li>
          <li class=""><a href="#">bizTweets</a></li>
          <li class=""><a href="#">About Us</a></li>
          <li class=""><a href="#">Contact Us</a></li>
        </ul>
      </div>
      </div>
    </div>
    <div id="site_content">
      <div id="sidebar_container">
        <img class="paperclip" src="resources/img/paperclip.png" alt="paperclip" />
        <div class="sidebar">
        <!-- insert your sidebar items here -->
        <h3>Latest News</h3>
        <h4>What's the News?</h4>
        <h5>1st July 2011</h5>
        <p>Put your latest news item here, or anything else you would like in the sidebar!<br /><a href="#">Read more</a></p>
        </div>
      </div>
      <div id="content">
        <h1>Welcome to the bizTweets</h1>
        <div>
        	<form>
        		<input type="text" /></br></br>
        		<input type="text" /></br></br>
        		<input type="button" value="submit" />
        	</form>
        </div>    
      </div>
    </div>
    <div id="footer">
      <p>bizTweets | <a href="http://tushar686.wordpress.com">HTML5</a> </p>
    </div>
  </div>
</body>
<script src="<c:url value="resources/js/jquery-2.1.0.min.js" />"></script>
<script src="<c:url value="resources/js/main.js" />"></script>
<script src="<c:url value="resources/js/entities.js" />"></script>
</html>
