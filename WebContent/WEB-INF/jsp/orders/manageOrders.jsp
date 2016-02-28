<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Manage Orders</title>
</head>
<body>
	<h1>Manage Orders</h1>
	Orders:<br><br>

		<table border="0">
		<tr>
			<td>Order #</td>
			<td>Date</td>
			<td>Confirmation #</td>
			<td>Action</td>
		</tr>

			<c:forEach var="order" items="${orderList}">
				<tr>
				<td><c:out value="${order.orderId}" /></td>
				<td><c:out value="${order.orderCreated}" /></td>
				<td><c:out value="${order.confirmNumber}" /></td>
				<td>
					<c:if test="${order.confirmNumber == 0}">
						<a href="../orders/loadOrder.html?orderId=${order.orderId}">Edit</a>
					</c:if>
					<c:if test="${order.confirmNumber != 0}">
						<a href="viewOrder.html?orderId=${order.orderId}">View</a>
					</c:if>
				</td>
				</tr>
			</c:forEach>

		</table>
	<br><br>
	<a href="../catalog/shop.html">Shop</a>
</body>
</html>