<%@include file="../shared/flows-header.jsp"%>

<div class="container">

	<div class="text-center">
		<h1>Welcome <strong>${registerModel.user.firstName} ${registerModel.user.lastName} !</strong></h1>
		<h3>onlineshopping.com</h3>
		<h6>You can use your email address <i>${registerModel.user.email}</i> as username to login!</h6>
		<div>
			<a href="${contextRoot}/login" class="btn btn-lg btn-success">Login Here</a>
		</div>
	</div>

</div>
<%@include file="../shared/flows-footer.jsp"%>