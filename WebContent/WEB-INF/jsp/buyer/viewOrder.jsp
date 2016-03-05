<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>View Order</title>
</head>
<body>
	<h1>View Order</h1>
	Order #: <c:out value="${order.orderId}" /> for user: <c:out value="${order.user}" /><br>
	Order date: <c:out value="${order.orderCreated}" />
	<br><br>
	Order items:<br><br>
		<table border="0">
		<div>
			<tr>
			<td>Product</td>
			<td>Quantity</td>
			<td>Unit of Measure</td>
			</tr>

			<c:forEach var="productOrder" items="${order.productOrders}" varStatus="vs">
				<tr>
				<td><c:out value="${productOrder.product.productName}" /></td>
				<td><c:out value="${productOrder.orderAmount}" /></td>
				<td><c:out value="${productOrder.product.uom}" /></td>
				</tr>
			</c:forEach>
		</div>
		</table>
		<br><br>
	<a href="../buyer/shop.html">Shop</a><br>
	<a href="../buyer/manageOrders.html">Manage Orders</a>


</body>
</html>