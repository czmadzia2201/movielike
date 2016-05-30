<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Celebrities to validate</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Celebrities to validate</h2>

<h4>Added during editing existing movie</h4>

<p>
<c:forEach items="${editAddedCelebrities}" var="celebrity">
<a href="displaypendingcelebrity?id=${celebrity.id}">${celebrity.name}</a><br />
</c:forEach>
</p>

<h4>Added during adding new movie</h4>

<p>
<c:forEach items="${addAddedCelebrities}" var="celebrity">
<a href="displaypendingcelebrity?id=${celebrity.id}">${celebrity.name}</a><br />
</c:forEach>
</p>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>