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

<c:if test="${isAdmin == true && pendingValidation > 0 && pageContext.request.userPrincipal.name == user.username}">
	<p><a href="moviestovalidate" class="error">There are movies pending validation.</a></p>
</c:if>

<c:if test="${pageContext.request.userPrincipal.name == user.username || isAdmin == true}">
	<form:form method="POST" action="userdeleted">
		<input type="hidden" name="username" value="${user.username}" />
		<input type="submit" value="Delete profile" />
	</form:form>
</c:if>
<c:if test="${pageContext.request.userPrincipal.name == user.username}">
	<form:form method="POST" action="changepassword">
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
		<table border=1>
		<tr><th>Title</th><th>Director</th><th>Genre</th><th>Lead actors</th><th>Country</th><th>Year</th></tr>
		  <c:forEach items="${favedMovielist}" var="movie">
		    <tr>
		      <td><c:out value="${movie.title}" /></td>
		      <td><c:out value="${movie.director}" /></td>
		      <td><c:out value="${movie.genre}" /></td>
		      <td><c:out value="${movie.leadActors}" /></td>
		      <td><c:out value="${movie.country}" /></td>
		      <td><c:out value="${movie.year}" /></td>
 		      <td><a href="displaymovie?id=${movie.id}">See movie details</a></td>
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
		<table border=1>
		<tr><th>Title</th><th>Director</th><th>Genre</th><th>Lead actors</th><th>Country</th><th>Year</th></tr>
		  <c:forEach items="${disfavedMovielist}" var="movie">
		    <tr>
		      <td><c:out value="${movie.title}" /></td>
		      <td><c:out value="${movie.director}" /></td>
		      <td><c:out value="${movie.genre}" /></td>
		      <td><c:out value="${movie.leadActors}" /></td>
		      <td><c:out value="${movie.country}" /></td>
		      <td><c:out value="${movie.year}" /></td>
  		      <td><a href="displaymovie?id=${movie.id}">See movie details</a></td>
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
		<table border=1>
		<tr><th>Title</th><th>Director</th><th>Genre</th><th>Lead actors</th><th>Country</th><th>Year</th></tr>
		  <c:forEach items="${addedMovies}" var="movie">
		    <tr>
		      <td><c:out value="${movie.title}" /></td>
		      <td><c:out value="${movie.director}" /></td>
		      <td><c:out value="${movie.genre}" /></td>
		      <td><c:out value="${movie.leadActors}" /></td>
		      <td><c:out value="${movie.country}" /></td>
		      <td><c:out value="${movie.year}" /></td>
  		      <td><a href="displaymovie?id=${movie.id}">See movie details</a></td>
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
		<table border=0>
		  <c:forEach items="${reviewList}" var="review">
		    <tr>
		      <th colspan=2><a href="displaymovie?id=${review.movieId}">${review.movieTitle}</a></th>
		    </tr>
		    <tr>
		      <td colspan=2>${review.content}</td>
		    </tr>
		    <c:if test="${pageContext.request.userPrincipal.name == review.author || isAdmin == true}">
		    <tr><td>
		    	<c:if test="${pageContext.request.userPrincipal.name == review.author}">
		    	<form:form method="POST" action="editreview">
		    	<input type="hidden" name="reviewId" value="${review.id}" />
		    	<input type="submit" value="Edit review" />
		    	</form:form>
		    	</c:if>
		    	</td><td>
		    	<form:form method="POST" action="submitdeletereview">
		    	<input type="hidden" name="reviewId" value="${review.id}" />
		    	<input type="submit" value="Delete review" />
		    	</form:form>
		    </td></tr>
		    </c:if>
		  </c:forEach>
		</table>
	</c:otherwise>
</c:choose> 

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>