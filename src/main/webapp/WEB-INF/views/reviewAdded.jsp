<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Review added</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Review added</h2>

<p>Your review has been added. You can view and edit the review on the movie page and on your user panel.</p>
<p><a href="displaymovie?id=${movieId}">Back to movie page</a></p>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>