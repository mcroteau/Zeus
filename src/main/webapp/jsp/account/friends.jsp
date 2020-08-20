<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div id="friends-container">

	<c:if test="${not empty message}">
		<div class="span12">
			<div class="alert alert-info">${message}</div>
		</div>
	</c:if>

	<c:if test="${not empty error}">
		<div>
			<div class="alert alert-danger">${error}</div>
		</div>
	</c:if>

	<style type="text/css">
		.profile-image{
			width:250px;
			height:250px;
		}
	</style>
		
	<h1>Friends</h1>

	<c:choose>
		<c:when test="${friends.size() > 0}">
					
			<h1>Display Friends</h1>
			
			<c:forEach var="friend" items="${friends}">
						${friends.size()}
				${friend.account.nameUsername}
				${friend.account.imageUri}

				<div class="profile-container">
					<div class="profile-image" style="background:url('${pageContext.request.contextPath}/${friend.account.imageUri}')">
					</div>
				</div>
				
			</c:forEach>
				
			
		</c:when>
		<c:otherwise>
			<p>No friends yet.</p>
		</c:otherwise>
	</c:choose>
</div>
	
		
