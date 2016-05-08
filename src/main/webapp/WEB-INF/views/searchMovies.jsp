<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Search movies</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
	<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script src="resources/functions.js"></script>
	<script type="text/javascript">
		var emptyValue = "${emptyValue}";
		var formatError = "${formatError}";
		var directorList = ${directors};
		var actorList = ${actors};
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
		<option value="directors" ${searchCriteria == "directors" ? "selected":""}>Director</option>
		<option value="leadActors" ${searchCriteria == "leadActors" ? "selected":""}>Lead actors</option>
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
	<c:when test="${searchCriteria == 'directors'}">
         <input name="criteriaValue" id="listOfDirectors" style="width:95%">
	</c:when>
	<c:when test="${searchCriteria == 'leadActors'}">
         <input name="criteriaValue" id="listOfActors" style="width:95%">
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
	<div><input class="buttonForm" type="button" value="Add movie" onclick="location.href='addmovie'" /></div>
	</c:if>
	
	<h4>About movielike</h4>
	
	<p>Do you have a list of your favourite movies? Do you want to share this list with the others? 
	Maybe you also have a list of your most disliked movies? Wouldn't it be fun to create such a list and compare it 
	with the most disliked movie lists of other people? Very bad movies are fun after all. :)</p>
	
	<p>On this website you can create list of your most liked and most disliked movies. 
	If the movie you want to add to the list is not in our database, you can add it yourself for the benefit of all the users. 
	You can also review a movie, and simply search and read about movies that interest you.</p>
	
	<p>Welcome to movielike!</p>
	
	<h4>Latest movies</h4>
	
	<table  class="movielist">
		<tr><th class="title">Title</th><th class="director">Director</th><th class="genre">Genre</th>
		<th class="leadActors">Lead actors</th><th class="country">Country</th><th class="year">Year</th></tr>
		  <c:forEach items="${latestMovies}" var="movie">
		    <tr>
		      <td>${movie.title}</td>
		      <td>${movie.directorString}</td>
		      <td>${movie.genreString}</td>
		      <td>${movie.leadActorsString}</td>
		      <td>${movie.countryString}</td>
		      <td>${movie.year}</td>
 		      <td><a href="displaymovie?id=${movie.id}">See&nbsp;more&nbsp;&#187;</a></td>
		    </tr>
		  </c:forEach>
	</table>
	
	<h4>Most popular movies</h4>
	
	<table  class="movielist">
		<tr><th class="title">Title</th><th class="director">Director</th><th class="genre">Genre</th>
		<th class="leadActors">Lead actors</th><th class="country">Country</th><th class="year">Year</th></tr>
		  <c:forEach items="${mostPopular}" var="movie">
		    <tr>
		      <td>${movie.title}</td>
		      <td>${movie.directorString}</td>
		      <td>${movie.genreString}</td>
		      <td>${movie.leadActorsString}</td>
		      <td>${movie.countryString}</td>
		      <td>${movie.year}</td>
 		      <td><a href="displaymovie?id=${movie.id}">See&nbsp;more&nbsp;&#187;</a></td>
		    </tr>
		  </c:forEach>
	</table>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>