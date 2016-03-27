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
	<title>Add review</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
	<script src="resources/functions.js"></script>
	<script type="text/javascript">
		var emptyValue = "${emptyValue}";
	</script>	
</head>

<body onunload="clearForm()">

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Write review</h2>

	<form:form name="addEditReview" commandName="review" method="POST" onsubmit="return validateReview(emptyValue)" action="submitaddreview">
		<div style="position: relative; height: 100px">
		<form:hidden path="author" value="${username}" />
		<form:hidden path="movieId" value="${movieId}" />
		<form:textarea path="content" />
		</div>
		<input class="buttonForm" type="submit" value="Submit review" /> 
		<span id="errorMsg" class="error"></span>
	</form:form>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>