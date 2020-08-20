<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="edit-account-form">

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

		
	<h1>Update Password</h1>
	
	<form action="${pageContext.request.contextPath}/account/update_password/${account.id}" class="form" modelAttribute="account" method="post">
		
		<input type="hidden" name="id" value="${account.id}"/>

		<div class="form-group">
		  	<label for="password">Password</label>
		  	<input type="password" name="password" class="form-control" id="password" placeholder="******" value="">
		</div>
		
		
		<div class="form-group">
			<a href="${pageContext.request.contextPath}/account/edit/${account.id}">Cancel</a>
			<input type="submit" class="button" id="update" value="Update"/>
		</div>
		
	</form>
	
</div>
	
		
