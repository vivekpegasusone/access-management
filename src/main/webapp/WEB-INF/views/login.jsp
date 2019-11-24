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
	    <form:form action="${pageContext.request.contextPath}/home" modelAttribute="login">
	        First Name : <form:input path="userId" />
		    <form:errors path="userId" cssClass="error"></form:errors>
		    <br><br>
	        Last Name : <form:input path="password" />
		    <form:errors path="password" cssClass="error"></form:errors>
		    <br><br>
	     	<input type="submit" />
	    </form:form>
    </body>
</html>