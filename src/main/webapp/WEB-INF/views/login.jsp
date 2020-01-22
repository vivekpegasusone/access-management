<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Please sign in</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M" crossorigin="anonymous">
        <link href="https://getbootstrap.com/docs/4.0/examples/signin/signin.css" rel="stylesheet" crossorigin="anonymous"/>
    </head>
    <body>
        <div class="container">
    	<form class="form-signin" action="${pageContext.request.contextPath}/home" method="post">
    	    <h2 class="form-signin-heading">Please sign in</h2>
            <p>
              <label for="username" class="sr-only">Username</label>
              <input type="text" id="username" name="userId" class="form-control" placeholder="Username" required autofocus>
            </p>
            <p>
              <label for="password" class="sr-only">Password</label>
              <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
            </p>
	     	<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
	     	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	     	<c:if test="${not empty requestScope.message}">
            <div class="alert alert-info" role="alert">${requestScope.message}</div>
            </c:if>            
	    </form>
	    </div>
    </body>
</html>
