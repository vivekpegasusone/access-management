<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${pageContext.request.contextPath}/permission/save" method="POST" modelAttribute="permissionVO">
<table>
	<tr>
		<td colspan="3"><c:out value="${message}"/></td>
	</tr>
	<tr>
		<td><form:label path="applicationVO">Application : </form:label></td>
		<td>
		<form:select path="applicationVO" id="appPermission">
			<c:if test="${empty permissionVO.applicationVO}">
				<form:option value="-" label="--Select Appliction--"/>	
			</c:if>	
			<form:options items="${applicationVOList}" itemValue="id" itemLabel="name" />
		</form:select>
		</td>
		<td><form:errors path="applicationVO"/></td>
	</tr>
	<tr>
		<td><form:label path="name">Name : </form:label></td>
		<td><form:input path="name" placeholder="Permission Name" value="${permissionVO.name}" /></td>
		<td><form:errors path="name"/></td>
	</tr>
	<tr>
		<td><form:label path="description">Description : </form:label></td>
		<td><form:input path="description" placeholder="Permission Description" value="${permissionVO.description}" /></td>
		<td><form:errors path="description"/></td>
	</tr>
	<tr>
		<td><form:label path="actionVO">Action : </form:label></td>
		<td>
		<form:select path="actionVO" id="action">
		<c:if test="${empty permissionVO.actionVO}">
			<form:option value="-" label="--Select Action--" />			
		</c:if>
		</form:select>
		</td>
		<td><form:errors path="actionVO"/></td>
	</tr>
	<tr>
		<td><form:label path="resourceVO">Resource : </form:label></td>
		<td>
		<form:select path="resourceVO" id="resource">
		<c:if test="${empty permissionVO.resourceVO}">
			<form:option value="-" label="--Select Resource--" />			
		</c:if>
		</form:select>
		</td>
		<td><form:errors path="resourceVO"/></td>
	</tr>
	<tr>
		<td colspan="3">
			<c:choose>
			<c:when test="${empty permissionVO.id}">
				<input type="submit" value="Create Permission"/>
			</c:when>
			<c:otherwise>
				<input type="submit" value="Update Permission"/>
			</c:otherwise>
			</c:choose>			
			<form:hidden path="id"/>
			<form:hidden path="active"/>
			<input id="actionId" type="hidden" value="${permissionVO.actionVO.id}" />
			<input id="resourceId" type="hidden" value="${permissionVO.resourceVO.id}" />
		</td>	
	</tr>
</table>
</form:form>