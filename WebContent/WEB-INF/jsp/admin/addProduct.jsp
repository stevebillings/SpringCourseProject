<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
    <title>Add Product</title>
</head>
<body>
	<jsp:include page="../common/header.jsp" />
    <h1>Add New Product</h1>
<br>
<sf:form method="POST" modelAttribute="product">
<table border="0">
	<tr> 
		<th><label for="product_name">Product Name:</label></th>
    	<td><sf:input path="productName" size="20" id="product_name" /><br/>
        	<sf:errors path="productName" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr> 
		<th><label for="product_sku">SKU:</label></th>
    	<td><sf:input path="sku" size="20" id="product_sku" /><br/>
        	<sf:errors path="sku" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr> 
		<th><label for="product_uom">Unit of Measure</label></th>
    	<td><sf:input path="uom" size="20" id="product_uom" /><br/>
        	<sf:errors path="uom" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr> 
		<th><label for="product_qty">Available Quantity:</label></th>
    	<td><sf:input path="availableQuantity" size="10" id="product_qty" /><br/>
        	<sf:errors path="availableQuantity" cssClass="error" /> 
    	</td> 
	</tr> 
	<tr>
		<th></th>
		<td><input type="submit" value="Save" /></td>
	</tr>
</table>
</sf:form>
    
</body>
</html>