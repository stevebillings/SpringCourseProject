<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title>Edit Product</title>
</head>
<body>
    Edit Product Details:
<br>
<sf:form method="POST" modelAttribute="product">
<table border="0">
	<tr> 
		<th align="left">Product Id:</th>
    	<td align="left">${product.productId}</td> 
	</tr> 
	<tr> 
		<th align="left"><label for="product_name">Product Name:</label></th>
    	<td align="left">
    	    <sf:input path="productName" size="20" id="product_name" /><br/>
        	<sf:errors path="productName" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr> 
		<th align="left"><label for="product_sku">SKU:</label></th>
    	<td align="left"><sf:input path="sku" size="20" id="product_sku" /><br/>
        	<sf:errors path="sku" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr> 
		<th align="left"><label for="product_uom">Unit of Measure</label></th>
    	<td align="left"><sf:input path="uom" size="20" id="product_uom" /><br/>
        	<sf:errors path="uom" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr> 
		<th align="left"><label for="product_qty">Available Quantity:</label></th>
    	<td align="left"><sf:input path="availableQuantity" size="10" id="product_qty" /><br/>
        	<sf:errors path="availableQuantity" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr>
		<th></th>
		<td><input type="submit" value="Save" /></td>
	</tr>
</table>
</sf:form>

<br>
<a href="../list.html">Cancel</a>
<br><br>

</body>
</html>