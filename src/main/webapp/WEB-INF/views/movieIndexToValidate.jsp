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
	<title>Movies to validate</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Movies to validate</h2>
<p>
<c:forEach items="${movieList}" var="movie">
<a href="displaymovietovalidate?id=${movie.id}">${movie.title}</a><br />
</c:forEach>
</p>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>