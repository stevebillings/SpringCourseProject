<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>View Product</title>
</head>
<body>
	<jsp:include page="../common/header.jsp" />
    <h1>Product Details</h1>
<table border="0">
	<tr> 
		<th align="left">Product Id:</th>
    	<td align="left">${product.productId}</td> 
	</tr> 
	<tr> 
		<th align="left">Product Name:</th>
    	<td align="left">${product.productName}</td> 
	</tr> 
	
	<tr> 
		<th align="left">SKU:</th>
    	<td align="left">${product.sku}</td> 
	</tr> 
	<tr> 
		<th align="left">Unit of Measure:</th>
    	<td align="left">${product.uom}</td> 
	</tr> 
	<tr> 
		<th align="left">Available Quantity:</th>
    	<td align="left">${product.availableQuantity}</td> 
	</tr> 
	
	<tr> 
		<th></th>
    	<td><a href="edit/${product.productId}.html">Edit</a></td> 
	</tr> 
</table>
<br>
<a href="admin.html">Back to Catalog Administration</a>
<br><br>

</body>
</html>