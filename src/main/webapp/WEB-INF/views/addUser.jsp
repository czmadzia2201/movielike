<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Add user</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>User registration</h2>

<c:choose>
<c:when test="${pageContext.request.userPrincipal.name != null}">
	<p>A logged in user cannot register user.</p>
</c:when>
<c:otherwise>
	<form:form method="POST" commandName="user">
	<table>
	<tr>
	<td>Username: </td>
	<td><form:input path="username" /></td>
	<td><form:errors path="username" class="error" /><span class="error">${userExists}</span></td>
	</tr><tr>
	<td>Password: </td>
	<td><form:password path="password" /></td>
	<td><form:errors path="password" class="error" /></td>
	</tr><tr>	
	<td>Confirm password: </td>
	<td><form:password path="confirmPassword" /></td>
	<td><form:errors path="confirmPassword" class="error" /></td>
	</tr>
	</table>
	<input type="submit" value="Register" />
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