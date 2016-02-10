<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Find movies</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>List of movies</h2>

<c:choose>
	<c:when test="${empty movieList}">
		<p>No results found.</p>
	</c:when>
	<c:otherwise>
		<table border=1>
		<tr><th>Title</th><th>Director</th><th>Genre</th><th>Lead actors</th><th>Country</th><th>Year</th></tr>
		  <c:forEach items="${movieList}" var="movie">
		    <tr>
		      <td><c:out value="${movie.title}" /></td>
		      <td><c:out value="${movie.director}" /></td>
		      <td><c:out value="${movie.genre}" /></td>
		      <td><c:out value="${movie.leadActors}" /></td>
		      <td><c:out value="${movie.country}" /></td>
		      <td><c:out value="${movie.year}" /></td>
 		      <td><a href="displaymovie?id=${movie.id}">See movie details</a></td>
		    </tr>
		  </c:forEach>
		</table>
	</c:otherwise>
</c:choose> 

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>