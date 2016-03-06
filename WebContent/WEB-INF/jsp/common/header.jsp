<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<style>
		.error { color: #FF0000; }
 		.msg { color: #0000FF; }
	</style>
</head>
<body>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		Welcome : ${pageContext.request.userPrincipal.name} 
		   | <a href="/Store/home.html">Home</a>
		   | <a href="/Store/j_spring_security_logout">Logout</a>
	</c:if>
	<c:if test="${pageContext.request.userPrincipal.name == null}">
		   <a href="/Store/index.jsp">Home</a>
	</c:if>

	<c:if test="${not empty error}">
		<br>
		<div class="error">${error}</div>
	</c:if>
	<c:if test="${not empty msg}">
		<br>
		<div class="msg">${msg}</div>
	</c:if>
	<hr/><br/> 
	 
</body>
</html>
