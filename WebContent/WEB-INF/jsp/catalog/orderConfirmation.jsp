<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Order Confirmation</title>
</head>
<body>
    Order Confirmation:
<br>
<table border="0">

	<c:forEach var="productOrder" items="${order.productOrders}" varStatus="vs">
		<tr>
			<td><c:out value="${productOrder.product.productName}" /></td>
			<td><c:out value="${productOrder.orderAmount}" /></td>
		</tr>
	</c:forEach>
	
</table>
<br>
<a href="../catalog/shop.html">Shop</a>
<br><br>

</body>
</html>