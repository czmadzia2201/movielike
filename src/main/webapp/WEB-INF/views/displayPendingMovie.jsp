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
	<title>Display pending movie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Movie details</h2>

<p> ${movie}</p> 

<c:if test="${(pageContext.request.userPrincipal.name == movie.addedBy || isAdmin == true) && movie.status != -1}">
	<table><tr><td>
	<form:form method="GET" action="editmovie" >
	<input type="submit" value="Edit movie" />
	</form:form>
	</td><td>
	<form:form method="POST" action="submitdeletemovie" >
	<input type="hidden" name="movieId" value="${movie.id}" />
	<input type="submit" value="Delete movie" />
	</form:form>
	</td></tr></table>
</c:if>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>