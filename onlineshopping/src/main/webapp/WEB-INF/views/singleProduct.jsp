<div class="container">
	<!-- Breadcrumb -->
	<div class="row">
		<div class="col-xs-12">
			<ol class="breadcrumb">
			<li class="breadcrumb-item"><a href="${contextRoot}/home">Home</a></li>
			<li class="breadcrumb-item"><a href="${contextRoot}/show/all/products">Products</a></li>
			<li class="breadcrumb-item active">${product.name}</li>
			</ol>
		</div>
	</div>
	
	<div class="row">
		<!--  display product image -->
		<div class="col-sm-4">
			<div >
				<img src="${images}/${product.code}.jpg" class="img-thumbnail" />
			</div>
		
		</div>
		
		<!--  display product description -->
		<div class="col-sm-8">
			<h3>${product.name}</h3>
			<hr>
			<p>${product.description}</p>
			<hr>
			<h4>Price: <strong>&euro; ${product.unitPrice} /-</strong></h4>
			<hr>
			
			<security:authorize access="hasAuthority('USER') or isAnonymous()">
			<c:choose>
				<c:when test="${product.quantity<1}">
					<h6>Qty. Available: <font color="red">Out of Stock!</font></h6>
					<a href="javascript: void(0)" class="btn btn-success disabled">
						<strike><i class="fas fa-cart-plus"></i>Add To Cart</strike>
					</a>
				</c:when>
				<c:otherwise>
					<h6>Qty. Available: ${product.quantity}</h6>
					<a href="${contextRoot}/cart/add/${product.id}/product" class="btn btn-success">
						<i class="fas fa-cart-plus"></i>Add To Cart
					</a>
				</c:otherwise>
			</c:choose>
			</security:authorize>
			
			<security:authorize access="hasAuthority('ADMIN')">
				<a href="${contextRoot}/manage/${product.id}/product" class="btn btn-warning">
					<i class="fas fa-pencil-alt" style="color:red"></i>Edit
				</a>
			</security:authorize>
			
			<%-- <a href="${contextRoot}/cart/add/${product.id}/product" class="btn btn-success">
				<span><i class="fas fa-cart-plus"></i>Add To Cart</span>
			</a> --%>
			
			<a href="${contextRoot}/show/all/products" class="btn btn-primary">Back</a>
		</div>
		
	</div>
</div>