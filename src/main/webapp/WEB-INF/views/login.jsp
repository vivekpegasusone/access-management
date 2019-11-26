<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
         <title>Login Page</title>
        <style>
        </style>
    </head>
    <body>
    	<form:form action="${pageContext.request.contextPath}/home" method="post">
	        Login Id : <input type="text" id="userId" name="userId" value="vivek"/>
		    <br><br>
	        Password : <input type="password" id="password" name="password" value="123"/>
		    <br><br>
	     	<input type="submit" />
	    </form:form>
    </body>
</html>
