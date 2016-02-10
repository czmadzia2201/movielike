<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
response.setDateHeader("Expires", 0);
response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
response.addHeader("Cache-Control", "post-check=0, pre-check=0");
response.setHeader("Pragma", "no-cache");
%> 

<!DOCTYPE html>
<html>
<head>
	<title>Pending and rejected</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Pending and rejected</h2>

<h4>Pending validation</h4>

<c:choose>
	<c:when test="${empty pendingMoviesList}">
		<p>No pending movies.</p>
	</c:when>
	<c:otherwise>
		<p>
		<c:forEach items="${pendingMoviesList}" var="movie">
			<a href="displaypendingmovie?id=${movie.id}">${movie.title}</a><br />
		</c:forEach>
		</p>
	</c:otherwise>
</c:choose>

<h4>Rejected</h4>

<c:choose>
	<c:when test="${empty rejectedMoviesList}">
		<p>No rejected movies.</p>
	</c:when>
	<c:otherwise>
		<table>
		<tr><th>Title</th><th>Rejection reason</th>
		<c:forEach items="${rejectedMoviesList}" var="movie">
		<tr>
		<td><a href="displayrejectedmovie?id=${movie.id}">${movie.title}</a></td>
		<td>${movie.reason}</td>
		</tr>
		</c:forEach>
		</table>
		<p>I confirm that I have seen and acknowledged which of the movies I added were rejected. Clear the list.</p>
		<form:form method="POST">
		<input type="hidden" name="clearRejected" />	
		<input type="submit" value="OK" onclick="form.clearRejected.value = true" />
		</form:form>
	</c:otherwise>
</c:choose>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>