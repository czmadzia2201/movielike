<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Movie index</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>User index</h2>
<c:choose>
	<c:when test="${empty userList}">
		<p>No results found.</p>
	</c:when>
	<c:otherwise>
		<p>
		<c:forEach items="${userList}" var="user">
			<a href="displayuser?username=${user.username}">${user.username}</a><br />
		</c:forEach>
		</p>
	</c:otherwise>
</c:choose>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>
