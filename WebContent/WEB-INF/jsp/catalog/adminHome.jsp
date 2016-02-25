<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Administration</title>
</head>
<body>
	<h1>Catalog Administration</h1>
	Products:
	<sf:form method="POST" commandName="selectedProducts" action="delete.html">
		<div>
			<c:forEach var="product" items="${productList}">
				<sf:checkbox path="itemList" value="${product.productId}" />
				<a href="view.html?productId=${product.productId}">
					<c:out value="${product.productName}" />
				</a>
				<br>
			</c:forEach>
		</div>
		<br>
 		<input type="submit" value="Delete" />
	</sf:form>
	<br>
	<a href="add.html">Add New</a>
	<br><br>
	<a href="../catalog/shop.html">Shop</a>
</body>
</html>