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
			</tr>
			<c:forEach items="${applicationVOList}" var="app">
			<tr>
 				<td>${app.name}</td>
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
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>

                   
                    
                          
                
                    
                    
                
                    
                           
                
                
           