<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${applicationVOList ne null}">
		<table>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Active</th>
				<th>Created By</th>	
				<th>Created On</th>	
				<th>Updated By</th>	
				<th>Updated On</th>	
				<th>Delete</th>
			</tr>
			<c:forEach items="${applicationVOList}" var="app">
			<tr>
 				<td><a href="<c:url value='/application/edit?applicationId=${app.id}'/>">${app.name}</a></td>
				<td>${app.description}</td>
				<td>
				<c:choose>
					<c:when test="${app.active}">
						True	
					</c:when>
					<c:otherwise>
						False
					</c:otherwise>					
				</c:choose>
				</td>
				<td>${app.createdBy}</td>
				<td>${app.createdOn}</td>	
				<td>${app.updatedBy}</td>	
				<td>${app.updatedOn}</td>	
				<td><a href="<c:url value='/application/delete?applicationId=${app.id}'/>">Delete</a></td>							
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>

                   
                    
                          
                
                    
                    
                
                    
                           
                
                
           