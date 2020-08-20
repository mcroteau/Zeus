<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="success-container">
	<c:if test="${not empty message}">
        <p class="notify">${message}</p>
    </c:if>
</div>