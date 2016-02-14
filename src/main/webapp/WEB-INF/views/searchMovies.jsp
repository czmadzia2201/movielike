<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Search movies</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
	<script src="resources/validate.js"></script>
	<script type="text/javascript">
		var emptyValue = "${emptyValue}";
		var formatError = "${formatError}";
	</script>
			
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Search movies</h2>

<p>Search criteria:<p>
	<form:form name="searchMovies" method="GET" onsubmit="return validateSearchMovies(emptyValue, formatError)" 
		action="findmovies?searchCriteria=${searchCriteria}&criteriaValue=${criteriaValue}">
	<select name="searchCriteria">
		<option value="title">Title</option>
		<option value="genre">Genre</option>
		<option value="director">Director</option>
		<option value="country">Country</option>
		<option value="year">Year</option>
	</select>
	<input type="text" name="criteriaValue" />
	<input type="submit" value="Find movies" />
	<span id="errorMsg" class="error"></span>
	</form:form>
	
	<c:if test="${pageContext.request.userPrincipal.name != null}">
	<input class="buttonForm" type="button" value="Add movie" onclick="location.href='addmovie'" />
	</c:if>
	
</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>