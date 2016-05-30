<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Display celebrity</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Celebrity details</h2>

<p>name: ${celebrity}</p> 
<p>director: ${celebrity.isDirector}</p>
<p>actor: ${celebrity.isActor}</p>
<p>scriptwriter: ${celebrity.isScriptwriter}</p>

<c:choose>
<c:when test="${celebrity.validationStatus != 1}">
<form:form method="POST" action="celebritiestovalidate">
	<input type="submit" value="Validate" onclick="alert('Celebrity ${celebrity.name} has been validated.'); 
		form.celebId.value=${celebrity.id}; form.isValidated.value='validated'" />
	<input type="submit" value="Delete" onclick="alert('Celebrity ${celebrity.name} has been deleted.');
		form.celebId.value=${celebrity.id}; form.isValidated.value='deleted'" />
	<input type="hidden" name="celebId" />
	<input type="hidden" name="isValidated" />
</form:form>
</c:when>
<c:otherwise>
<p>This celebrity is validated.</p>
</c:otherwise>
</c:choose>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>