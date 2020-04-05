<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${resourceVOList ne null}">
		<table>
			<tr>
				<th>Name</th>
				<th>Application Name</th>
				<th>Description</th>
				<th>Active</th>
				<th>Created By</th>	
				<th>Created On</th>	
				<th>Updated By</th>	
				<th>Updated On</th>	
				<th>Delete</th>
			</tr>
			<c:forEach items="${resourceVOList}" var="resourceVO">
			<tr>
 				<td><a href="<c:url value='/resource/edit?resourceId=${resourceVO.id}'/>">${resourceVO.name}</a></td>
 				<td>${resourceVO.applicationVO.name}</td>
				<td>${resourceVO.description}</td>
				<td>
				<c:choose>
					<c:when test="${resourceVO.active}">
						True	
					</c:when>
					<c:otherwise>
						False
					</c:otherwise>					
				</c:choose>
				</td>
				<td>${resourceVO.createdBy}</td>
				<td>${resourceVO.createdOn}</td>	
				<td>${resourceVO.updatedBy}</td>	
				<td>${resourceVO.updatedOn}</td>	
				<td><a href="<c:url value='/resource/delete?resourceId=${resourceVO.id}'/>">Delete</a></td>							
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>      