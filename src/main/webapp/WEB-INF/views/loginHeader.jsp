<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="header"><div id="header-inside">

<table style="cellspacing: 0; cellpadding: 0; height: 100px; width: 100%"><tr><td style="vertical-align: bottom;">
<p class="error">${loginFailed}</p>
<c:choose>
<c:when test="${pageContext.request.userPrincipal.name != null}">
	<p style="line-height: 20px">Welcome ${pageContext.request.userPrincipal.name} | <a class="headerLink" href="j_spring_security_logout">Logout</a> | <a class="headerLink" href="displayuser?username=${pageContext.request.userPrincipal.name}">My profile</a></p>
</c:when>
<c:otherwise>
	<form method="POST" action="j_spring_security_check">
	<p>Login: <input type="text" name="username" /> Password: <input type="password" name="password" /> <input type="submit" value="Login" /> 
	| <a class="headerLink" href="adduser">Register</a></p>  		
	</form> 
</c:otherwise>
</c:choose>
</td></tr></table>
	
</div>
</div>

<div id="menu-bar">
<div id="header-inside">

<c:set var="goBack" value="javascript:history.go(-1)" />
<c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/movielike/reviewadded' || 
			requestScope['javax.servlet.forward.request_uri'] == '/movielike/reviewedited' ||
			requestScope['javax.servlet.forward.request_uri'] == '/movielike/moviedeleted'}">
	<c:set var="goBack" value="javascript:history.go(-2)" />
</c:if>
<c:if test="${requestScope['javax.servlet.forward.request_uri'] == '/movielike/editmovie' || 
			requestScope['javax.servlet.forward.request_uri'] == '/movielike/movieadded' || 
			requestScope['javax.servlet.forward.request_uri'] == '/movielike/useradded'}">
	<c:set var="goBack" value="${referrerUrl}" />
</c:if>
	
<a class="headerLink" href="/movielike/">Main</a> | <a class="headerLink" href="<c:out value="${goBack}"/>">&#171; Back</a>

</div></div>