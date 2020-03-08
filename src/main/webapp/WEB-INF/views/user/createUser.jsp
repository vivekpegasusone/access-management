<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${pageContext.request.contextPath}/user/save" method="POST" modelAttribute="userVO">
<table>
	<tr>
		<td colspan="3"><c:out value="${message}"/></td>
	</tr>
	<tr>
		<td><form:label path="applicationVO">Application : </form:label></td>
		<td>
		<form:select path="applicationVO" id="application">
			<c:if test="${empty userView.applicationVO}">
				<form:option value="-" label="--Select Appliction--"/>	
			</c:if>	        
	        <form:options items="${applicationVOList}" itemValue="id" itemLabel="name"/>                
        </form:select>
        </td>
		<td><form:errors path="applicationVO"/></td>
	</tr>
	<tr>
		<td><form:label path="roleVO">Role : </form:label></td>
		<td>
			<form:select path="roleVO" id="role">
				<form:option value="-" label="--Select Role--"/>
			</form:select>
        </td>
		<td><form:errors path="roleVO"/></td>
	</tr>
	<tr>
		<td><form:label path="loginId">Login Id : </form:label></td>
		<td><form:input path="loginId" placeholder="Login Id" /></td>
		<td><form:errors path="loginId"/></td>
	</tr>
	<c:if test="${empty userVO.id}">				
	<tr>
		<td><form:label path="password">Password : </form:label></td>
		<td><form:password path="password" placeholder="Password" /></td>
		<td><form:errors path="password"/></td>
	</tr>
	</c:if>
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
		<td colspan="3">
			<c:choose>
				<c:when test="${empty userVO.id}">
					<input type="submit" value="Create User"/>
				</c:when>
				<c:otherwise>
					<input type="submit" value="Update User"/>
				</c:otherwise>
			</c:choose>			
			<form:hidden path="id" />
			<form:hidden path="active"/>
			<input id="roleId" type="hidden" value="${userVO.roleVO.id}" />
		</td>	
	</tr>
</table>
</form:form>