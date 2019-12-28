<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.slim.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<title>Access Management System</title>	
  </head>
  <body>
  	<div class="container-fluid border">
  		<div class="bg-primary">
  			<div class="text-center text-white"><h2>Access Management System</h2></div>
			<nav class="navbar navbar-expand-sm">
				<ul class="navbar-nav">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">User</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#">Create User</a> 
							<a class="dropdown-item" href="#">List Users</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Role</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#">Create Role</a> 
							<a class="dropdown-item" href="#">List Roles</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Action</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#">Create Action</a> 
							<a class="dropdown-item" href="#">List Actions</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Resource</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#">Create Resource</a> 
							<a class="dropdown-item" href="#">List Resources</a>
						</div>
					</li>
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Permission</a>
						<div class="dropdown-menu">
							<a class="dropdown-item" href="#">Create Permission</a> 
							<a class="dropdown-item" href="#">List Permissions</a>
						</div>
					</li>					
				</ul>
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
				        <a class="nav-link text-white" href="javascript:document.logoutForm.submit()">Logout</a>
				    </li>
				</ul>
			</nav>
		</div>
  		<div><c:import url="${contentPage}"/></div>
  		<footer class="footer">
	    	<div class="container-fluid bg-primary text-right text-white">
	    		<p> Version: xxx</p>
	    	</div>
	    </footer>  		
	</div>
	<form name="logoutForm" action="<c:url value="/logout"/>" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </body>
</html>