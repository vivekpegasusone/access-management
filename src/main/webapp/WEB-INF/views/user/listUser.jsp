<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${users ne null}">
		<table>
			<tr>
				<th>Login Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Email Id</th>	
				<th>Active</th>	
			</tr>
			<c:forEach items="${users}" var="user">
			<tr>
 				<td>${user.loginId}</td>
				<td>${user.firstName}</td>
				<td>${user.lastName}</td>
				<td>${user.emailId}</td>	
				<td>
				<c:choose>
					<c:when test="${user.active}">
						True	
					</c:when>
					<c:otherwise>
						False
					</c:otherwise>					
				</c:choose>
				</td>			
			</tr>
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<c:out value="${message}" default="No Result."/>
	</c:otherwise>
</c:choose>

                   
                    
                          
                
                    
                    
                
                    
                           
                
                
           