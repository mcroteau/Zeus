<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>Accounts</title>
</head>

<body>

	<h1>Accounts</h1>

	
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
		<c:when test="${accounts.size() > 0}">
					
			<div class="span12">
    		
				<div class="btn-toolbar">
					<div class="btn-group">
    		
						<%  int total = Integer.parseInt(request.getAttribute("total").toString());
							int resultsPerPage = Integer.parseInt(request.getAttribute("resultsPerPage").toString());
							int activePage = Integer.parseInt(request.getAttribute("activePage").toString());
							
							int currentPage = 1;
						    for(int m = 0; m < total; m++){ 
								if(m % resultsPerPage == 0){%>
									<%if(activePage == currentPage){%>
										<a href="${pageContext.request.contextPath}/admin/account/list?offset=<%=m%>&max=<%=resultsPerPage%>&page=<%=currentPage%>" class="btn active"><%=currentPage%></a>
									<%}else{%>
										<a href="${pageContext.request.contextPath}/admin/account/list?offset=<%=m%>&max=<%=resultsPerPage%>&page=<%=currentPage%>" class="btn"><%=currentPage%></a>
									<%}%>
									
								<%	
									currentPage++;
								}%>
						<%}%>
    		
					</div>
				</div>
				
					
				<table class="table table-condensed">
					<thead>
						<tr>
							<th>Id</th>
							<th>Name</th>
							<th>Username</th>
							<th>Status</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="account" items="${accounts}">
							<tr>
								<td>${account.id}</td>
								<td>${account.name}</td>
								<td>${account.username}</td>
								<td>
	                                <c:if test="${account.disabled}">
	                                    <span class="beauty-light">Disabled</span>
	                                </c:if>
	                         	</td>

								<td><a href="${pageContext.request.contextPath}/account/edit/${account.id}" title="Edit" class="button sky">Edit</a>
							</tr>									
						</c:forEach>
					</tbody>
				</table>
				
			</div>
			
		</c:when>
		<c:when test="${accounts.size() == 0}">
			<p>No accounts created yet.</p>
		</c:when>
	</c:choose>
</body>
</html>