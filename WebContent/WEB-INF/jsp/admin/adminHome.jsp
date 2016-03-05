<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Administration</title>
</head>
<body>
	<h1>Catalog Administration</h1>
	Products:<br><br>
	<sf:form method="POST" commandName="selectedProducts" action="delete.html">
		<table border="0">
		<tr>
			<td></td>
			<td>Product</td>
			<td>Unit of Measure</td>
			<td># available</td>
		</tr>

			<c:forEach var="product" items="${productList}">
				<tr>
				<td><sf:checkbox path="itemList" value="${product.productId}" /></td>
				<td>
					<a href="view.html?productId=${product.productId}">
						<c:out value="${product.productName}" />
					</a>
				</td>
				<td><c:out value="${product.uom}" /></td>
				<td><c:out value="${product.availableQuantity}" /></td>
				</tr>
			</c:forEach>

		</table>
		<br>
 		<input type="submit" value="Delete" />
	</sf:form>
	<br>
	<a href="add.html">Add New</a>
	<br><br>
	<a href="../buyer/shop.html">Shop</a>
</body>
</html>