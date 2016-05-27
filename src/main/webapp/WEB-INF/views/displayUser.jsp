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
	<title>Display user</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>User ${user.username}</h2>

<c:if test="${isAdmin == true && moviesPendingValidation > 0 && pageContext.request.userPrincipal.name == user.username}">
	<p><a href="moviestovalidate" class="error">There are movies pending validation.</a></p>
</c:if>

<c:if test="${isAdmin == true && celebsPendingValidation > 0 && pageContext.request.userPrincipal.name == user.username}">
	<p><a href="celebritiestovalidate" class="error">There are celebrities pending validation.</a></p>
</c:if>

<c:if test="${pageContext.request.userPrincipal.name == user.username || isAdmin == true}">
	<form:form method="POST" action="userdeleted" class="buttonForm">
		<input type="hidden" name="username" value="${user.username}" />
		<input type="submit" value="Delete profile" onclick="return confirm('Are you sure you want to delete user profile?')" />
	</form:form>
</c:if>
<c:if test="${pageContext.request.userPrincipal.name == user.username}">
	<form:form method="POST" action="changepassword" class="buttonForm">
		<input type="hidden" name="username" value="${user.username}" />
		<input type="submit" value="Change password" /> (TO DO)
	</form:form>	
</c:if>

<h4> Favourite movies</h4>

<c:choose>
	<c:when test="${empty favedMovielist}">
		<p>User doesn't have favourite movies yet.</p>
	</c:when>
	<c:otherwise>
		<table class="movielist">
		<tr><th class="title">Title</th><th class="director">Director</th><th class="genre">Genre</th>
		<th class="leadActors">Lead actors</th><th class="country">Country</th><th class="year">Year</th></tr>
		  <c:forEach items="${favedMovielist}" var="movie">
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
	</c:otherwise>
</c:choose> 
 
<h4> Most disliked movies</h4> 

<c:choose>
	<c:when test="${empty disfavedMovielist}">
		<p>User doesn't have most disliked movies yet.</p>
	</c:when>
	<c:otherwise>
		<table class="movielist">
		<tr><th class="title">Title</th><th class="director">Director</th><th class="genre">Genre</th>
		<th class="leadActors">Lead actors</th><th class="country">Country</th><th class="year">Year</th></tr>
		  <c:forEach items="${disfavedMovielist}" var="movie">
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
	</c:otherwise>
</c:choose> 

<h4> Movies added by ${user.username}</h4> 

<c:choose>
	<c:when test="${empty addedMovies}">
		<p>User didn't add any movies.</p>
	</c:when>
	<c:otherwise>
		<table class="movielist">
		<tr><th class="title">Title</th><th class="director">Director</th><th class="genre">Genre</th>
		<th class="leadActors">Lead actors</th><th class="country">Country</th><th class="year">Year</th></tr>
		  <c:forEach items="${addedMovies}" var="movie">
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
	</c:otherwise>
</c:choose> 

<c:if test="${pendingAndRejected > 0 && pageContext.request.userPrincipal.name == user.username}">
	<p><a href="pendingandrejected?username=${user.username}" class="error">Check pending and rejected</a></p>
</c:if>

<h4> Reviews written by ${user.username}</h4> 

<c:choose>
	<c:when test="${empty reviewList}">
		<p>User didn't post any reviews.</p>
	</c:when>
	<c:otherwise>
		  <c:forEach items="${reviewList}" var="review">
		    	<div class="reviewHeader"><a href="displaymovie?id=${review.movieId}"><b>${review.movieTitle}</b></a></div>
		      	<div class="reviewContent"><span>${review.content}</span>
		    	<c:if test="${pageContext.request.userPrincipal.name == review.author || isAdmin == true}">
		      	<div>
		    	<c:if test="${pageContext.request.userPrincipal.name == review.author}">
		    	<form:form method="POST" action="editreview" class="buttonForm">
		    	<input type="hidden" name="reviewId" value="${review.id}" />
		    	<input type="submit" value="Edit review" />
		    	</form:form>
		    	</c:if>
		    	<form:form method="POST" action="submitdeletereview" class="buttonForm">
		    	<input type="hidden" name="reviewId" value="${review.id}" />
		    	<input type="submit" value="Delete review" onclick="return confirm('Are you sure you want to delete this review?')" />
		    	</form:form>
		    	</div>
		    	</c:if>
		    	</div>
		  </c:forEach>
	</c:otherwise>
</c:choose> 

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>