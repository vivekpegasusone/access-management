<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<form:form action="${pageContext.request.contextPath}/action/save" method="POST" modelAttribute="actionVO">
<table>
	<tr>
		<td colspan="3"><c:out value="${message}"/></td>
	</tr>
	<tr>
		<td><form:label path="name">Name : </form:label></td>
		<td><form:input path="name" placeholder="Action Name" value="${actionVO.name}" /></td>
		<td><form:errors path="name"/></td>
	</tr>
	<tr>
		<td><form:label path="description">Description : </form:label></td>
		<td><form:input path="description" placeholder="Action Description" value="${actionVO.description}" /></td>
		<td><form:errors path="description"/></td>
	</tr>
	<tr>
		<td><form:label path="applicationVO">Application : </form:label></td>
		<td>
		<form:select path="applicationVO">
		<c:if test="${empty actionVO.applicationVO}">
			<form:option value="-" label="--Select Appliction--" />			
		</c:if>
		<form:options items="${applicationVOList}" itemValue="id" itemLabel="name" />
		</form:select>
		</td>
		<td><form:errors path="applicationVO"/></td>
	</tr>
	<tr>
		<td colspan="3">
			<c:choose>
			<c:when test="${empty actionVO.id}">
				<input type="submit" value="Create Action"/>
			</c:when>
			<c:otherwise>
				<input type="submit" value="Update Action"/>
			</c:otherwise>
			</c:choose>			
			<form:hidden path="id"/>
			<form:hidden path="active"/>
		</td>	
	</tr>
</table>
</form:form>