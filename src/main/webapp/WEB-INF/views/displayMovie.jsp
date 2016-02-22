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

<div style="display: table;">
<div style="display: table-cell;"> 
<p> ${movie}</p> 

<c:if test="${pageContext.request.userPrincipal.name != null}">
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
</div>

<div id="moviemeter-box">
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<form:form method="POST">
		<input type="hidden" name="add" />
		<input type="hidden" name="remove" />
	<c:choose>
	<c:when test="${isMovieFaved == false}">
		<input type="submit" class="favButton" id="fav-inactive" value="" onclick="form.add.value = 1" />
	</c:when>
	<c:otherwise>
		<input type="submit" class="favButton" id="fav-active" value="" onclick="form.remove.value = 1" />		
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${isMovieDisfaved == false}">
		<input type="submit" class="favButton" id="disfav-inactive" value="" onclick="form.add.value = -1" />
	</c:when>
	<c:otherwise>
		<input type="submit" class="favButton" id="disfav-active" value="" onclick="form.remove.value = -1" />		
	</c:otherwise>
	</c:choose>
	</form:form>
</c:if>
</div>

</div>

<h4>Movie reviews</h4>

<c:choose>
	<c:when test="${empty reviewList}">
		<p>No one has reviewed this movie yet.</p>
	</c:when>
	<c:otherwise>
		  <c:forEach items="${reviewList}" var="review">
		      	<div class="reviewHeader"><p>by <a href="displayuser?username=${review.author}"><b>${review.author}</b></a></p></div>
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
		    	<input type="submit" value="Delete review" />
		    	</form:form>
		    	</div>
		    	</c:if>
				</div>	
		  </c:forEach>
	</c:otherwise>
</c:choose> 

</div>
<div id="content-inside"></div>

<div id="footer-content">
<p style="text-align:right">Movie added by <a href="displayuser?username=${movie.addedBy}">${movie.addedBy}</a>.</p>
</div>
<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>