<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order Confirmation</title>
</head>
<body>
    Order Confirmation for Order ID: <c:out value="${order.orderId}" /><br>
    Confirmation Number: <c:out value="${order.confirmNumber}" />
<br><br>
<table border="0">
	<tr>
		<td><b>Product</b></td>
		<td><b>Quantity</b></td>
		<td><b>Unit of Measure</b></td>
	</tr>
	<c:forEach var="productOrder" items="${order.productOrders}" varStatus="vs">
		<tr>
			<td><c:out value="${productOrder.product.productName}" /></td>
			<td><c:out value="${productOrder.orderAmount}" /></td>
			<td><c:out value="${productOrder.product.uom}" /></td>
		</tr>
	</c:forEach>
	
</table>
<br>
<a href="../catalog/shop.html">Shop</a>
<br><br>

</body>
</html>