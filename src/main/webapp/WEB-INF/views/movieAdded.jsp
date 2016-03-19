<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Movie added</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Movie added</h2>

<p>The movie ${movie.title} has been added. It needs to be validated by the page admin to become visible for all users. 
It should take no more than 48 hours. In the meantime, you can view the movie, edit it and check its validation status in your user panel. </p>
<p><a href="displaypendingmovie?id=${movie.id}">View the movie</a></p>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>