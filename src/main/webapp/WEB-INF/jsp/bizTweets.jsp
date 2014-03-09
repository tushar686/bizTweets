<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>bizTweets</h2>
	<table border="1">
		<tr>
			<td align="left" width="50%"><a href="./getTweets?cursor=${cursor-20}&field=0">Previous</a></td>
			<td align="right"><a href="./getTweets?cursor=${cursor}&field=0">Next</a></td>
		</tr>
		<c:forEach var="bizTweet" items="${bizTweets}">
			<tr>
				<td colspan=2>${bizTweet}/></td>
			</tr>
		</c:forEach>
		<tr>
			<td align="left" width="50%"><a href="./getTweets?cursor=${cursor-20}&field=0">Previous</a></td>
			<td align="right"><a href="./getTweets?cursor=${cursor}&field=0">Next</a></td>
		</tr>
	</table>	
</body>
</html>