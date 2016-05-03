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
		<table  class="movielist">
		<tr><th class="title">Title</th><th class="director">Director</th><th class="genre">Genre</th>
		<th class="leadActors">Lead actors</th><th class="country">Country</th><th class="year">Year</th></tr>
		  <c:forEach items="${movieList}" var="movie">
		    <tr>
		      <td>${movie.title}</td>
		      <td>${movie.directorString}</td>
		      <td>${movie.genreString}</td>
		      <td>${movie.leadActorsString}</td>
		      <td>${movie.countryString}</td>
		      <td>${movie.year}</td>
 		      <td><a href="displaymovie?id=${movie.id}">See&nbsp;more&nbsp;&#187;</a></td>
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