<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
	<h2>Here is a simple CRUD using Spring MVC and MongoDB.</h2>

		

	<table border="1">
		<c:forEach var="zip" items="${zips}">
			<tr>
				<td>${zip}/></td>
			</tr>
		</c:forEach>
	</table>	
</body>
</html>