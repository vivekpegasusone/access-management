<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${permissionVOList ne null}">
		<table>
			<tr>
				<th>Name</th>
				<th>Action Name</th>
				<th>Resource Name</th>
				<th>Description</th>
				<th>Active</th>
				<th>Created By</th>	
				<th>Created On</th>	
				<th>Updated By</th>	
				<th>Updated On</th>	
				<th>Delete</th>
			</tr>
			<c:forEach items="${permissionVOList}" var="permissionVO">
			<tr>
 				<td><a href="<c:url value='/permission/edit?permissionId=${permissionVO.id}'/>">${permissionVO.name}</a></td>
 				<td>${permissionVO.actionVO.name}</td>
 				<td>${permissionVO.resourceVO.name}</td>
				<td>${permissionVO.description}</td>
				<td>
				<c:choose>
					<c:when test="${permissionVO.active}">
						True	
					</c:when>
					<c:otherwise>
						False
					</c:otherwise>					
				</c:choose>
				</td>
				<td>${permissionVO.createdBy}</td>
				<td>${permissionVO.createdOn}</td>	
				<td>${permissionVO.updatedBy}</td>	
				<td>${permissionVO.updatedOn}</td>	
				<td><a href="<c:url value='/permission/delete?permissionId=${permissionVO.id}'/>">Delete</a></td>							
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>      