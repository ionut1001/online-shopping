<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
      <div class="container">
        <a class="navbar-brand" href="${contextRoot}/home">Online Shopping</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
          <ul class="nav navbar-nav navbar-left">
            <li class="nav-item" id="about">
              <a class="nav-link" href="${contextRoot}/about">About</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${contextRoot}/contact" id="contact">Contact</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="${contextRoot}/show/all/products" id="listProducts">View Products</a>
            </li>
            <security:authorize access="hasAuthority('ADMIN')">
            <li class="nav-item">
              <a class="nav-link" href="${contextRoot}/manage/products" id="manageProducts">Manage Products</a>
            </li>
            </security:authorize>
          </ul>
          
          <ul class="nav navbar-nav ml-auto navbar-right">
          <security:authorize access="isAnonymous()">
	          <li class="nav-item" id="register">
	              <a class="nav-link" href="${contextRoot}/register">Sign Up</a>
	            </li>
	            <li class="nav-item" id="login">
	              <a class="nav-link" href="${contextRoot}/login">Login</a>
	            </li>
            </security:authorize>
            
            <%-- <li class="dropdown">
            	<a href="javascript:void(0)" class="btn btn-default dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown">
            		Full Name
            		<span class="caret"></span>
            	</a>
            	<ul class="dropdown-menu">
            		<li>
            			<a href="${contextRoot}/cart">
            			<i class="fas fa-cart-plus"></i>
            			<span class="badge-pill">0</span>- &euro; 0.0
            			</a>
            		</li>
            		<li class="divider" role="separator"><hr></li>
            		<li><a href="${contextRoot}/logout">Logout</a></li>
            	</ul>
            </li> --%>
            
            <security:authorize access="isAuthenticated()">
	            <li class="dropdown nav-item">
	            	<a href="javascript:void(0)" class="nav-link dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown">
	            		<i class="fa fa-user"></i>
	            		${userModel.fullName}
	            		<span class="caret"></span>
	            	</a>
	            	<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
	            	<security:authorize access="hasAuthority('USER')">
		            	<li>
		            		<a class="dropdown-item" href="${contextRoot}/cart">
		            		<i class="fas fa-cart-plus"></i>
		            		<span class="badge badge-primary badge-pill">${userModel.cart.cartLines}</span>- &euro; ${userModel.cart.grandTotal}
		            		</a>
		            	</li>
		            	<li><hr></li>
	            	</security:authorize>
	            	<li><a class="dropdown-item" href="${contextRoot}/perform-logout">Logout</a></li>
	            	</ul>
	            </li>
            </security:authorize>
            
          </ul>
        </div>
      </div>
    </nav>
    
<script>

	window.userRole = '${userModel.role}';

</script>