<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Manage Orders</title>
</head>
<body>
	<h1>Manage Orders</h1>
	Orders:<br><br>
	<sf:form method="POST" commandName="selectedOrders" action="delete.html">
		<table border="0">
		<tr>
			<td></td>
			<td>Order #</td>
			<td>Date</td>
			<td>Confirmation #</td>
		</tr>

			<c:forEach var="order" items="${orderList}">
				<tr>
				<td><sf:checkbox path="itemList" value="${order.orderId}" /></td>
				<td>
					<a href="view.html?orderId=${order.orderId}">
						<c:out value="${order.orderId}" />
					</a>
				</td>
				<td><c:out value="${order.orderCreated}" /></td>
				<td><c:out value="${order.confirmNumber}" /></td>
				</tr>
			</c:forEach>

		</table>
		<br>
 		<input type="submit" value="Delete" />
	</sf:form>
	<br><br>
	<a href="../catalog/shop.html">Shop</a>
</body>
</html>