<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Flagged Posts</title>
</head>

<body>

	<h1>Flagged</h1>

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


	<c:choose>
		<c:when test="${posts.size() > 0}">

			<div class="span12">

				<table class="table table-condensed">
					<thead>
						<tr>
							<th>Id</th>
							<th>Content</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="post" items="${posts}">
							<tr>
								<td>${post.id}</td>
								<td>${post.content}</td>
								<td><a href="${pageContext.request.contextPath}/post/review/${post.id}" title="Review">Review</a>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>

		</c:when>
		<c:when test="${posts.size() == 0}">
			<p>Good stuff, no flagged posts!</p>
		</c:when>
	</c:choose>
</body>
</html>