<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>Click 'Follow' against the entities you wish to receive tweets for</h2>

	<table border="1">
		<c:forEach var="schema" items="${bizEntitiesSchema}">
			<tr>
				<td>${schema}/></td>
				<td><a href="./getTweets?cursor=0&field=0">Follow</a></td>
			</tr>
		</c:forEach>
	</table>	
</body>
</html>