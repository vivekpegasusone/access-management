<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/access-management.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>    
	<title>Access Management System</title>	
  </head>
  <body>
  	<div class="container-fluid px-0">
  		<div class="bg-primary">
  			<div class="text-center text-white"><h2>Access Management System</h2></div>
			<nav class="navbar navbar-expand-sm">
				<ul class="navbar-nav">
					<c:import url="${navigationPage}"/>					
				</ul>
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
				        <a class="nav-link text-white font-weight-bold" href="javascript:document.logoutForm.submit()">Logout</a>
				    </li>
				</ul>
			</nav>
		</div>
  		<div class="container"><c:import url="${contentPage}"/></div>
  		<footer>
	    	<div>Version: xxx&nbsp;&nbsp;&nbsp;&nbsp;</div>
	    </footer>  		
	</div>
	<form name="logoutForm" action="<c:url value="/processLogout"/>" method="post">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
  </body>
  <script src="${pageContext.request.contextPath}/js/loadRoles.js"></script>
  <script src="${pageContext.request.contextPath}/js/loadActionsAndResources.js"></script>
  <script src="${pageContext.request.contextPath}/js/loadPermissions.js"></script>
</html>