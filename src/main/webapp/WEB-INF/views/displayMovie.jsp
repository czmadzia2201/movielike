<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Display movie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Movie details</h2>

<p> ${movie}</p> 

<c:if test="${pageContext.request.userPrincipal.name != null}">
	<form:form method="POST">
		<input type="hidden" name="add" />
		<input type="hidden" name="remove" />
	<c:choose>
	<c:when test="${isMovieFaved == false}">
		<input type="submit" value="Add to favourites" onclick="form.add.value = 1" />
	</c:when>
	<c:otherwise>
		<input type="submit" value="Remove from favourites" onclick="form.remove.value = 1" />		
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${isMovieDisfaved == false}">
		<input type="submit" value="Add to most disliked" onclick="form.add.value = -1" />
	</c:when>
	<c:otherwise>
		<input type="submit" value="Remove from most disliked" onclick="form.remove.value = -1" />		
	</c:otherwise>
	</c:choose>
	</form:form>
	<form:form method="POST" action="addreview" class="buttonForm">
		<input type="hidden" name="username" value="${user.username}" />
		<input type="hidden" name="movieId" value="${movie.id}" />
		<input type="submit" value="Review movie" />
	</form:form>
</c:if>
<c:if test="${pageContext.request.userPrincipal.name == movie.addedBy || isAdmin == true}">
	<form:form method="GET" action="editmovie" class="buttonForm">
	<input type="submit" value="Edit movie" />
	</form:form>
</c:if>

<h4>Movie reviews</h4>

<c:choose>
	<c:when test="${empty reviewList}">
		<p>No one has reviewed this movie yet.</p>
	</c:when>
	<c:otherwise>
		  <c:forEach items="${reviewList}" var="review">
		      	<div class="reviewHeader"><p>author: <a href="displayuser?username=${review.author}">${review.author}</a></p></div>
		      	<div class="reviewContent">${review.content}
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
		    	<input type="submit" value="Delete review" />
		    	</form:form>
		    	</div>
		    	</c:if>
				</div>	
		  </c:forEach>
	</c:otherwise>
</c:choose> 

<p style="text-align:right">Movie added by <a href="displayuser?username=${movie.addedBy}">${movie.addedBy}</a>.</p>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>