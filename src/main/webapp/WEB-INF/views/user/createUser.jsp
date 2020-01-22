<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${pageContext.request.contextPath}/user/save" method="POST" modelAttribute="userView">
<table>
	<tr>
		<td><form:label path="loginId">Login Id : </form:label></td>
		<td><form:input path="loginId" placeholder="Login Id" /></td>
		<td><form:errors path="loginId"/></td>
	</tr>
	<tr>
		<td><form:label path="password">Password : </form:label></td>
		<td><form:input path="password" placeholder="Password" /></td>
		<td><form:errors path="password"/></td>
	</tr>
	<tr>
		<td><form:label path="firstName">First Name : </form:label></td>
		<td><form:input path="firstName" placeholder="First Name" /></td>
		<td><form:errors path="firstName"/></td>
	</tr>
	<tr>
		<td><form:label path="lastName">Last Name : </form:label></td>
		<td><form:input path="lastName" placeholder="Last Name" /></td>
		<td><form:errors path="lastName"/></td>
	</tr>
	<tr>
		<td><form:label path="emailId">Email Id : </form:label></td>
		<td><form:input path="emailId" placeholder="Email Id" /></td>
		<td><form:errors path="emailId"/></td>
	</tr>
	<tr>
		<td><form:label path="roleViews">Role : </form:label></td>
		<td>
			<form:select path="roleViews">
                <form:option value="-" label="--Select Roles--"/>
                <form:options items="${roles}" />
            </form:select>
        </td>
		<td><form:errors path="roleViews"/></td>
	</tr>
	<tr>
		<td colspan="3">
			<input type="submit" value="Create User"/>
			<form:hidden path="active" value = "1" />
		</td>	
	</tr>
</table>
</form:form>
<br><br>
<c:if test="${createdUser ne null}">
<table>
	<tr>
		<th>Login Id</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>Email Id</th>		
	</tr>
	<tr>
		<td>${createdUser.loginId}</td>
		<td>${createdUser.firstName}</td>
		<td>${createdUser.lastName}</td>
		<td>${createdUser.emailId}</td>
	</tr>
</table>
</c:if>

                   
                    
                          
                
                    
                    
                
                    
                           
                
                
           