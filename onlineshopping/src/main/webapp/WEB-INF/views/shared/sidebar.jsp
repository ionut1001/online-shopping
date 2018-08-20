<p class="lead"><b>Shop name</b></p>
<div class="list-group">
	<c:forEach items="${categories}" var="category">
		<a href="${contextRoot}/show/category/${category.id}/products" class="list-group-item list-group-item-action" id="a_${category.name}">${category.name}</a>
	</c:forEach>

</div>