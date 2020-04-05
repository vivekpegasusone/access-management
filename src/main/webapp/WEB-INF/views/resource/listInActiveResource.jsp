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
			</tr>
			<c:forEach items="${resourceVOList}" var="resourceVO">
			<tr>
 				<td>${resourceVO.name}</td>
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
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>      