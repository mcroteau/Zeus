<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Signin</title>
	</head>
	<body>

		<c:if test="${not empty message}">
			<div class="notify notify-info">${message}</div>
		</c:if>

		<c:if test="${not empty error}">
			<div class="notify notify-alert">${error}</div>
		</c:if>

		<style type="text/css">
		    .form-group{
		        margin-bottom:20px;
		    }
		</style>

		<div id="signin-container">


            <h2>Signin</h2>

			<form action="${pageContext.request.contextPath}/authenticate" modelAttribute="signon" method="post" >

                <input type="hidden" name="uri" value="${uri}"/>

                <div class="form-group">
				  	<label for="username">Username</label>
				  	<input type="text" name="username" class="form-control" id="username" placeholder="" style="width:100%;">
				</div>

				<div class="form-group">
				  	<label for="password">Password</label>
				  	<input type="password" name="password" class="form-control" id="password" style="width:100%;" placeholder="&#9679;&#9679;&#9679;&#9679;&#9679;&#9679;">
				</div>

                <div style="text-align:right; margin-top:30px;">
			        <input type="hidden" name="targetUri" value="${targetUri}" />
                    <input type="submit" class="button retro" value="Signin" style="width:100%;">
                </div>

                <br/>

                <br/>
				<a href="${pageContext.request.contextPath}/account/reset" class="href-dotted">Forgot Password</a>&nbsp;&nbsp;

			</form>
		</div>
		
	</div>
			
	</body>
</html>

