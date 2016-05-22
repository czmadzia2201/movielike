<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Add movie</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
	<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
    <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
    <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
	<script src="resources/functions.js"></script>
    <script type="text/javascript">
		var directorList = ${directors};
		var actorList = ${actors};
	</script>
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Add movie</h2>

<form:form method="POST" commandName="movie">
<p class="error">${movieExists}</p>
<table>
<tr>
<td style="width:120px;">Title: </td>
<td><form:input path="title" /><form:errors path="title" class="error" /></td>
</tr><tr>
<td>Director: </td>
<td>
	<span id="addDirectors"><form:input path="directorsNames" id="listOfDirectors" style="margin-right:4px;" /></span> 
	<input type="button" id="addFieldsForDirectors" value="More" />
</td>
</tr><tr>
<td>Lead actors: </td>
<td>
	<span id="addActors">	
	<form:input path="leadActorsNames" id="listOfActors" />
	<form:input path="leadActorsNames" id="listOfActors" />
	<form:input path="leadActorsNames" id="listOfActors" style="margin-right:4px;" /></span> 
	<input type="button" id="addFieldsForActors" value="More" />
</td>
</tr><tr>
<td>Genre: </td>
<td><div><form:checkboxes items="${genreList}" path="genreList" element="span class='checkboxes'" />
<span style="float: left; width: 100%;">Other: <form:input path="genreOther" /></span></div></td>
</tr><tr>
<td>Year: </td>
<td><form:input path="year" /><form:errors path="year" class="error" /></td>
</tr><tr>
<td>Country: </td>
<td>
<div><form:checkboxes items="${countryListMain}" path="countryList" element="span class='checkboxes'" /></div>
<span style="float: left; width: 100%;"><a href="#" onclick="return show('showCountries')">Show more countries</a>
| <a href="#" onclick="return hide('showCountries')">Hide more countries</a></span>
<div id="showCountries" style="display:none;">
<form:checkboxes items="${countryListOther}" path="countryList" element="span class='checkboxes'" />
<span style="float: left; width: 100%;">Other: <form:input path="countryOther" /></span></div>
</td>
</tr><tr>
<td>Description: </td>
<td><form:textarea path="description" rows="5" cols="30" /></td>
</tr>
</table>
<form:hidden path="addedBy" value="${pageContext.request.userPrincipal.name}" />
<input type="submit" value="Add movie" />
</form:form>

</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>