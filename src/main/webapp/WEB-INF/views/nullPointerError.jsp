<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Movie not found</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link href="resources/main.css" rel="stylesheet" />
</head>
<body>

<div id="wrapper">
<div id="content">

<jsp:include page="${request.contextPath}/loginheader" />

<div id="content-inside">

<h2>Page cannot be displayed</h2>
<p>Sorry, page cannot be displayed. Parameter missing.</p>
			
</div>
<div id="content-inside"></div>

<jsp:include page="footer.jsp" />

</div>
</div>

</body>
</html>