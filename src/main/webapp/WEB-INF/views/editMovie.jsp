<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Edit movie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Edit movie</h2>

<form:form method="POST" commandName="movie">
<table>
<tr>
<td>Title: </td>
<td><form:input path="title" value="${movie.title}" /></td>
<td><form:errors path="title" class="error" /></td>
</tr><tr>
<td>Director: </td>
<td><form:input path="director" value="${movie.director}" /></td>
</tr><tr>
<td>Lead actors: </td>
<td><form:input path="leadActors" value="${movie.leadActors}" /></td>
</tr><tr>
<td>Genre: </td>
<td><form:input path="genre" value="${movie.genre}" /></td>
</tr><tr>
<td>Year: </td>
<td><form:input path="year" value="${movie.year}" /></td>
<td><form:errors path="year" class="error" /></td>
</tr><tr>
<td>Country: </td>
<td><form:input path="country" value="${movie.country}" /></td>
</tr><tr>
<td>Description: </td>
<td><form:textarea path="description" rows="5" cols="30" value="${movie.description}" /></td>
</tr>
</table>
<form:hidden path="id" value="${movie.id}" />
<input type="submit" value="Edit movie" />
</form:form>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>