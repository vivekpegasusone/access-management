<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${roleVOList ne null}">
		<table>
			<tr>
				<th>Name</th>
				<th>Application Name</th>
				<th>Permission Name</th>
				<th>Description</th>
				<th>Active</th>
				<th>Created By</th>	
				<th>Created On</th>	
				<th>Updated By</th>	
				<th>Updated On</th>	
				<th>Delete</th>
			</tr>
			<c:forEach items="${roleVOList}" var="roleVO">
			<tr>
 				<td><a href="<c:url value='/role/edit?roleId=${roleVO.id}'/>">${roleVO.name}</a></td>
 				<td>${roleVO.applicationVO.name}</td>
 				<td><c:out value="${roleVO.permissionVO.name}" default="-"></c:out></td>
				<td>${roleVO.description}</td>
				<td>
				<c:choose>
					<c:when test="${roleVO.active}">
						True	
					</c:when>
					<c:otherwise>
						False
					</c:otherwise>					
				</c:choose>
				</td>
				<td>${roleVO.createdBy}</td>
				<td>${roleVO.createdOn}</td>	
				<td>${roleVO.updatedBy}</td>	
				<td>${roleVO.updatedOn}</td>	
				<td><a href="<c:url value='/role/delete?roleId=${roleVO.id}'/>">Delete</a></td>							
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>      