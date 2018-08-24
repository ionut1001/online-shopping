<%@taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="container">
	<div class="row">

		<c:if test="${not empty messageSuccess}">
			<div class="col-xs-12" id="myDiv">
				<div class="alert alert-success alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					${messageSuccess}
				</div>
			</div>
		</c:if>
		<c:if test="${not empty messageFail}">
			<div class="col-xs-12" id="myDiv">
				<div class="alert alert-danger alert-dismissible">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					${messageFail}
				</div>
			</div>
		</c:if>

		<div class="col-md-offset-2 col-md-8">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h4>Product Management</h4>
				</div>
				<div class="panel-body">
					<!-- Form elements -->
					<sf:form class="form-horizonatal" modelAttribute="product"
						action="${contextRoot}/manage/products" method="POST"
						enctype="multipart/form-data">

						<%-- <div class="form-group row">
							<label class="control-label col-md-3" for="code">Product Code</label>
							<div class="col-md-9">
								<sf:input type="text" path="code" id="code" class="form-control" disabled="true" />
							</div>
						</div> --%>

						<div class="form-group row">
							<label class="control-label col-md-3" for="name">Product Name</label>
							<div class="col-md-9">
								<sf:input type="text" path="name" id="name" placeholder="Product Name" class="form-control" />
								<sf:errors path="name" cssClass="help-block" element="em"></sf:errors>
								<!-- <em class="help-block">Please enter product name</em> -->
							</div>
						</div>

						<div class="form-group row">
							<label class="control-label col-md-3" for="brand">Brand Name</label>
							<div class="col-md-9">
								<sf:input type="text" path="brand" id="brand" placeholder="Brand Name" class="form-control" />
								<sf:errors path="brand" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>

						<div class="form-group row">
							<label class="control-label col-md-3" for="description">Product Description</label>
							<div class="col-md-9">
								<sf:textarea rows="4" path="description" id="description" placeholder="Product description" class="form-control"></sf:textarea>
								<sf:errors path="description" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>

						<div class="form-group row">
							<label class="control-label col-md-3" for="unitPrice">Unit Price</label>
							<div class="col-md-9">
								<sf:input type="text" path="unitPrice" id="unitPrice" placeholder="Unit Price in &euro;" class="form-control" />
								<sf:errors path="unitPrice" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>

						<div class="form-group row">
							<label class="control-label col-md-3" for="quantity">Quantity Available</label>
							<div class="col-md-9">
								<sf:input type="number" path="quantity" id="quantity" placeholder="Quantity Available" class="form-control" />
							</div>
						</div>

						<!-- File element for image upload -->
						<div class="form-group row">
							<label class="control-label col-md-3" for="file">Select an Image</label>
							<div class="col-md-9">
								<sf:input type="file" path="file" id="file" class="form-control" />
								<sf:errors path="file" cssClass="help-block" element="em"></sf:errors>
							</div>
						</div>

						<div class="form-group row">
							<label class="control-label col-md-3" for="quantity">Select Category</label>
							<div class="col-md-7">
								<sf:select class="form-control" id="categoryId" path="categoryId" 
									items="${categories}" 
									itemLabel="name"
									itemValue="id">
								</sf:select>
							</div>
							<c:if test="${product.id==0}">
								<div class="text-right col-md-2">
									<button type="button" data-toggle="modal" data-target="#myCategoryModal" class="btn btn-warning btn-sm">Add Category</button>
								</div>
							</c:if>
						</div>

						<%-- <div class="form-group row">
						  <label for="example-datetime-local-input" class="col-2 col-form-label">Date and time</label>
						  <div class="col-10">
						    <sf:input class="form-control" type="datetime-local" value="2011-08-19T13:45:00" id="example-datetime-local-input" />
						  </div>
						</div> --%>

						<div class="form-group">
							<div class="col-md-offset-3 col-md-9">
								<input type="submit" name="submit" id="submit" value="Submit" class="btn btn-primary">
							</div>
						</div>

						<!-- Hidden fields. We adding them not to be overridden -->
						<sf:hidden path="id" />
						<sf:hidden path="code" />
						<sf:hidden path="supplierId" />
						<sf:hidden path="active" />
						<sf:hidden path="purchases" />
						<sf:hidden path="views" />


					</sf:form>
				</div>
			</div>
		</div>

		
		<!-- <div class="row"> -->
		<div class="col-lg-12">
			<h3>Available Products</h3>
			<hr>
			<table id="adminProductsTable" class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>Id</th>
						<th>&#160;</th>
						<th>Brand</th>
						<th>Name</th>
						<th>Quantity</th>
						<th>Unit Price</th>
						<th>Active</th>
						<th>Edit</th>
					</tr>
				</thead>
				
				<tfoot>
					<tr>
						<th>Id</th>
						<th>&#160;</th>
						<th>Brand</th>
						<th>Name</th>
						<th>Quantity</th>
						<th>Unit Price</th>
						<th>Active</th>
						<th>Edit</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- </div> -->
	</div>
	
	<div class="modal fade" id="myCategoryModal"  tabindex="-1">
		<div class="modal-dialog" >
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">Add new Category</h4>
				</div>
				<div class="modal-body">
					<!--  Category Form -->
					<sf:form id="categoryForm" modelAttribute="category" action="${contextRoot}/manage/category" method="POST" class="form-horizontal">
					
						<div class="form-group row">
							<label for="category_name" class="control-label col-md-4">Category Name</label>
							<div class="col-md-8">
								<sf:input type="text" path="name" id="category_name" class="form-control"></sf:input>
							</div>
						</div>
						
						<div class="form-group row">
							<label for="category_description" class="control-label col-md-4">Description</label>
							<div class="col-md-8">
								<sf:textarea cols="" rows="4" path="description" id="category_description" class="form-control" />
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-md-offset-4 col-md-8">
								<input type="submit" value="Add Category" class="btn btn-primary" />
							</div>
						</div>
					</sf:form>
				</div>
			</div>
		</div>
	</div>

</div>