<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<title>Edit Order</title>
</head>
<body>
	<h1>Edit Order</h1>
	Order #: <c:out value="${order.orderId}" /> for User: <c:out value="${order.user}" /><br><br>
	Composite Order #: <c:out value="${compositeOrder.orderId}" /> for User: <c:out value="${compositeOrder.user}" /><br><br>
	Order items:<br><br>
	<sf:form method="POST" modelAttribute="compositeOrder" action="completeOrder.html">
		<sf:input path="orderId" id="order_id" name="orderId" type="hidden" value="${compositeOrder.orderId}"/>
		<sf:input path="user" id="order_user" name="user" type="hidden" value="${compositeOrder.user}"/>
		<table border="0">
		<div>
			<tr>
			<td>Product</td>
			<td>Quantity</td>
			</tr>

			<c:forEach var="productOrder" items="${compositeOrder.productOrders}" varStatus="vs">
				<sf:input path="productOrders[${vs.index}].productOrderId" name="productOrders[${vs.index}].productOrderId" type="hidden" value="${productOrder.productOrderId}"/>
				<tr>
				<td><c:out value="${productOrder.product.productName}" /></td>
				<td>
					<sf:input path="productOrders[${vs.index}].orderAmount" size="10" id="order_amount[${vs.index}]" /><br/>
        			<sf:errors path="productOrders[${vs.index}].orderAmount" cssClass="error" /> 
				</td>
				</tr>
			</c:forEach>
		</div>
		</table>
		<br>
 		<input type="submit" value="Complete Order" />
	</sf:form>

</body>
</html>