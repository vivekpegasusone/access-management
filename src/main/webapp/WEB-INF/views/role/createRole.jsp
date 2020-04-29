<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${pageContext.request.contextPath}/role/save" method="POST" modelAttribute="roleVO">
<table>
	<tr>
		<td colspan="3"><c:out value="${message}"/></td>
	</tr>
	<tr>
		<td><form:label path="applicationVO">Application : </form:label></td>
		<td>
		<form:select path="applicationVO" id="appRolePermission">
		<c:if test="${empty roleVO.applicationVO}">
			<form:option value="-" label="--Select Appliction--" />			
		</c:if>
		<form:options items="${applicationVOList}" itemValue="id" itemLabel="name" />
		</form:select>
		</td>
		<td><form:errors path="applicationVO"/></td>
	</tr>
	<tr>
		<td><form:label path="permissionVO">Permission : </form:label></td>
		<td>
		<form:select path="permissionVO" id="permission">
		<c:if test="${empty roleVO.permissionVO}">
			<form:option value="-" label="--Select Permission--" />			
		</c:if>
		</form:select>
		</td>
		<td><form:errors path="permissionVO"/></td>
	</tr>
	<tr>
		<td><form:label path="name">Name : </form:label></td>
		<td><form:input path="name" placeholder="Role Name" value="${roleVO.name}" /></td>
		<td><form:errors path="name"/></td>
	</tr>
	<tr>
		<td><form:label path="description">Description : </form:label></td>
		<td><form:input path="description" placeholder="Role Description" value="${roleVO.description}" /></td>
		<td><form:errors path="description"/></td>
	</tr>
	
	<tr>
		<td colspan="3">
			<c:choose>
			<c:when test="${empty roleVO.id}">
				<input type="submit" value="Create Role"/>
			</c:when>
			<c:otherwise>
				<input type="submit" value="Update Role"/>
			</c:otherwise>
			</c:choose>			
			<form:hidden path="id"/>
			<form:hidden path="active"/>
			<input id="permissionId" type="hidden" value="${roleVO.permissionVO.id}" />
		</td>	
	</tr>
</table>
</form:form>