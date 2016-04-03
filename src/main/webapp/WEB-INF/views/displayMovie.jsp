<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Display movie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
	<script src="resources/functions.js"></script>
</head>
<body onload="disableRating(${userRating.votes}, ${userRating.hourDiff})">

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Movie details</h2>

<div style="display: table; width: 100%;">
<div style="display: table-cell; vertical-align: top;"> 
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
<div style="background: #b2d6ec; width:100%; text-align: center;"><span id="rating">Moviemeter box</span></div>
<div style="padding: 3px;">
<p>Average rating: <span id="rating">${movie.ratingAvg}</span><br />(${movie.voters} votes)</p>
<c:if test="${pageContext.request.userPrincipal.name != null}">
	<p>Your rating: <span id="rating">${userRating.ratingAvg}</span><br />(${userRating.votes} votes)</p>	
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
	<p id="rateMessage">Rate this movie:</p>
	<form:form method="POST" id="starRating">
		<input type="hidden" name="rating" />
		<c:forEach items="${ratingStars}" var="rS">
			<input type="submit" class="starButtonInactive" id="star-${rS}" value="" onclick="form.rating.value = ${rS}" 
			onmouseover = "lightTheStar(${rS})" onmouseout = "unlightTheStar(${rS})"/>		
		</c:forEach>
	</form:form>
</c:if>
</div></div>

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

<div id="footer-content">
<p style="text-align:right">Movie added by <a href="displayuser?username=${movie.addedBy}">${movie.addedBy}</a>.</p>
</div>
<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>