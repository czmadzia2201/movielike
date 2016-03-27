<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Search movies</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
	<script src="resources/functions.js"></script>
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
	
	<div style="display: inline-block">
	<form name="chooseCriteria">
	<select name="searchCriteria" id="sCrit" onchange="this.form.submit()">
		<option disabled selected></option>
		<option value="title" ${searchCriteria == "title" ? "selected":""}>Title</option>
		<option value="genreList" ${searchCriteria == "genreList" ? "selected":""}>Genre</option>
		<option value="director" ${searchCriteria == "director" ? "selected":""}>Director</option>
		<option value="countryList" ${searchCriteria == "countryList" ? "selected":""}>Country</option>
		<option value="year" ${searchCriteria == "year" ? "selected":""}>Year</option>
	</select>
	</form>
	</div>
	
	<div style="display: inline-block" >	
	<form:form name="searchMovies" method="GET" commandName="movie" onsubmit="return validateSearchMovies(emptyValue, formatError)" 
		action="findmovies?searchcriteria=${searchCriteria}&criteriavalue=${criteriaValue}">
	
	<div style="display: inline-block; width:200px"> 
	<c:choose>
	<c:when test="${searchCriteria == 'genreList'}">
	<select name="criteriaValue" style="width:97%">
		<option selected></option>
		<c:forEach items="${genreList}" var="genre">
		<option value="${genre}">${genre}</option>
		</c:forEach>
	</select>
	</c:when>
	<c:when test="${searchCriteria == 'countryList'}">
	<select name="criteriaValue" style="width:97%">
		<option selected></option>
		<c:forEach items="${countryList}" var="country">
		<option value="${country}">${country}</option>
		</c:forEach>
	</select>
	</c:when>
	<c:otherwise>
		<input type="text" name="criteriaValue" style="width:95%" />	
	</c:otherwise>
	</c:choose>
	</div>
	<input type="hidden" name="searchCriteria" value="${searchCriteria}" />
	<input type="submit" value="Find movies" />
	<span id="errorMsg" class="error"></span>
	</form:form>
	</div>
	
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