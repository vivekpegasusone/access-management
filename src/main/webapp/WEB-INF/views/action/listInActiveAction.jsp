<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${actionVOList ne null}">
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
			<c:forEach items="${actionVOList}" var="actionVO">
			<tr>
 				<td>${actionVO.name}</td>
 				<td>${actionVO.applicationVO.name}</td>
				<td>${actionVO.description}</td>
				<td>
				<c:choose>
					<c:when test="${actionVO.active}">
						True	
					</c:when>
					<c:otherwise>
						False
					</c:otherwise>					
				</c:choose>
				</td>
				<td>${actionVO.createdBy}</td>
				<td>${actionVO.createdOn}</td>	
				<td>${actionVO.updatedBy}</td>	
				<td>${actionVO.updatedOn}</td>										
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>      