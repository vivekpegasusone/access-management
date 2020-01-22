<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <h1>Error Page</h1>
<p>Application has encountered an error. Please contact support.</p>
<!--
Failed URL: ${url}
<br>
Exception:  ${exception.message}
<br>
<c:forEach items="${exception.stackTrace}" var="st">    
	${st} 
</c:forEach>
-->    