<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Flagged Posts</title>
</head>

<body>

	<h1>${post.id}</h1>

	<c:if test="${not empty error}">
		<div class="notify notify-warning">
			${error}
		</div>
	</c:if>

	<c:if test="${not empty message}">
		<div class="notify notify-info">
			${message}
		</div>
	</c:if>

    <div style="margin-bottom:170px;">
        <div>${post.content}</div>

        <c:forEach var="uri" items="${post.imageFileUris}">
            <div><img src="${pageContext.request.contextPath}/${uri}" style="width:100%;"/></div>
        </c:forEach>

        <video src="${pageContext.request.contextPath}/${post.videoFileUri}"></video>

        <form action="${pageContext.request.contextPath}/post/flag/approve/${post.id}" method="post">
            <input type="submit" value="Remove Post" class="button beauty-light small">
        </form>

		<form action="${pageContext.request.contextPath}/post/flag/revoke/${post.id}" method="post">
			<input type="submit" value="Revoke Flag" class="button light small">
		</form>

    </div>
</body>
</html>