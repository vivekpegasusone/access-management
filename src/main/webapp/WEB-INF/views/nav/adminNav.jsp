<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<li class="nav-item dropdown">
	<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Application</a>
	<div class="dropdown-menu">
		<a class="dropdown-item" href="<c:url value='/application/create'/>">Create Application</a> 
		<a class="dropdown-item" href="<c:url value='/application/listActive'/>">List Active Application</a>
		<a class="dropdown-item" href="<c:url value='/application/listInActive'/>">List InActive Application</a>
	</div>
</li>
<li class="nav-item dropdown">
	<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">Role</a>
	<div class="dropdown-menu">
		<a class="dropdown-item" href="<c:url value='/role/create'/>">Create Role</a> 
				<a class="dropdown-item" href="<c:url value='/role/listActive'/>">List Active Roles</a>
		<a class="dropdown-item" href="<c:url value='/role/listInActive'/>">List InActive Roles</a>
	</div>
</li>
<li class="nav-item dropdown">
	<a class="nav-link dropdown-toggle text-white" href="#" id="navbardrop" data-toggle="dropdown">User</a>
	<div class="dropdown-menu">
		<a class="dropdown-item" href="<c:url value='/user/create'/>">Create User</a> 
		<a class="dropdown-item" href="<c:url value='/user/listActive'/>">List Active Users</a>
		<a class="dropdown-item" href="<c:url value='/user/listInActive'/>">List InActive Users</a>
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