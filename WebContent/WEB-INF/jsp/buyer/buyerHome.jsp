<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Shop</title>
</head>
<body>
	<jsp:include page="../common/header.jsp" />
	<h1>Shop</h1>
	<h2>Products</h2>
	<sf:form method="POST" commandName="selectedProducts" action="../buyer/createOrder.html">
		<table>
		<tr>
		<td></td>
		<td>Product Name</td>
		<td>SKU</td>
		<td>Unit of Measure</td>
		</tr>
			<c:forEach var="product" items="${productList}">
				<tr>
				<td><sf:checkbox path="itemList" value="${product.productId}" /></td>
				<td><c:out value="${product.productName}" /></td>
				<td><c:out value="${product.sku}" /></td>
				<td><c:out value="${product.uom}" /></td>
				</tr>
			</c:forEach>
		</table>
		<br>
 		<input type="submit" value="Order" />
	</sf:form>
	<br><br>
	<a href="../buyer/manageOrders.html">Manage Orders</a>
</body>
</html>