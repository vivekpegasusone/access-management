<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${pageContext.request.contextPath}/application/save" method="POST" modelAttribute="applicationVO">
<table>
	<tr>
		<td colspan="3"><c:out value="${message}"/></td>
	</tr>
	<tr>
		<td><form:label path="name">Name : </form:label></td>
		<td><form:input path="name" placeholder="Application Name" value="${applicationVO.name}" /></td>
		<td><form:errors path="name"/></td>
	</tr>
	<tr>
		<td><form:label path="description">Description : </form:label></td>
		<td><form:input path="description" placeholder="Application Description" value="${applicationVO.description}" /></td>
		<td><form:errors path="description"/></td>
	</tr>
	<tr>
		<td colspan="3">
			<c:choose>
			<c:when test="${empty applicationVO.id}">
				<input type="submit" value="Create Application"/>
			</c:when>
			<c:otherwise>
				<input type="submit" value="Update Application"/>
			</c:otherwise>
			</c:choose>			
			<form:hidden path="id" />
			<form:hidden path="active"/>
		</td>	
	</tr>
</table>
</form:form>