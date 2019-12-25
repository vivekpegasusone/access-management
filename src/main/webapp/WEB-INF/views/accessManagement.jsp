<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
		<link rel="stylesheet" href="" type="text/css" />
		<style type="text/css">
		*{
			margin: 0px;
			padding: 0px;
		}
		
		header{
			height: 100px;
			background-color: darkorange;
		}
		
		a {
		  text-decoration: none;
		}

		nav {
			font-family: monospace;		
			width: 100%;
			float: left;
		}

		ul {
			background: darkorange;
			list-style: none;
			margin: 0px;
			padding-left: 0px;
		}

		li {
			color: #fff;
			background: darkorange;
			display: block;
			float: left;
			padding: 1rem;
			position: relative;
			text-decoration: none;
			transition-duration: 0.5s;
		}
		  
		li a {
			color: #fff;
		}

		li:hover {
			background: red;
			cursor: pointer;
		}

		ul li ul {
			background: orange;
			visibility: hidden;
			opacity: 0;
			min-width: 8rem;
			position: absolute;
			transition: all 0.5s ease;
			margin-top: 1rem;
			display: none;
		}

		ul li:hover > ul, ul li ul:hover {
			visibility: visible;
			opacity: 1;
			display: block;
		}

		ul li ul li {
			clear: both;
			width: 100%;
		}
		
		.logout{
			background: darkorange;
			color: white;
			display: block;
			float: right;
			padding: 1rem;			
			text-decoration: none;
			transition-duration: 0.5s;
		}
		
		.content{
			height: 100%;
		}
		
		.center {
			color: white;
			margin: auto;
			text-align: center;
			width: 50%;
			padding: 10px;
		}

		footer{
			background-color: darkorange;
            width: 100%;
            bottom: 0;
            position: fixed;
		}
		</style>
    </head>
    <body>
	    <header>
			<h2 class="center">Access Management System</h2>
			<nav>
				<ul>
					<li><a href="#">User</a>
						<ul class="dropdown">
							<li><a href="#">Create User</a></li>
							<li><a href="#">List Users</a></li>
						</ul>
					</li>
					<li><a href="#">Role</a>
						<ul class="dropdown">
							<li><a href="#">Create Role</a></li>
							<li><a href="#">List Roles</a></li>
						</ul>
					</li>
					<li><a href="#">Action</a>
						<ul class="dropdown">
							<li><a href="#">Create Action</a></li>
							<li><a href="#">List Actions</a></li>
						</ul>
					</li>
					<li><a href="#">Resource</a>
						<ul class="dropdown">
							<li><a href="#">Create Resource</a></li>
							<li><a href="#">List Resources</a></li>
						</ul>
					</li>
					<li><a href="#">Permission</a>
						<ul class="dropdown">
							<li><a href="#">Create Permission</a></li>
							<li><a href="#">List Permissions</a></li>
						</ul>
					</li>					
				</ul>
				<a class="logout" href="#">Logout</a>
			</nav>					
		</header>		
		<div class="content"><c:import url="${contentPage}"/></div>
		<footer>
			<h2>This is footer.</h2>
		</footer>
    </body>
</html>
