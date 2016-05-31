<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Display movie to validate</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Movie details</h2>

<p>${movie}</p> 

<p>Movie added by <a href="displayuser?username=${movie.addedBy}">${movie.addedBy}</a>.</p>

<p>Status: ${movie.statusValue}</p>

<c:if test="${movie.status == 0}">
<form:form method="POST">
	<input type="hidden" name="status" />
	<p>Reason for rejection</p>
	<textarea name="reason"></textarea>
	<p><input type="checkbox" name="deleteCelebs" value="1" />Reject also associated non-validated celebrities.</p>
	<input type="submit" value="Validate" onclick="form.status.value = 1" />
	<input type="submit" value="Reject" onclick="form.status.value = -1" />
</form:form>
</c:if>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>