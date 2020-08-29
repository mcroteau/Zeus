<!-- Home Sweet Home -->

<div id="feed-page" class="leaf">
    <%@ include file="/jsp/leafs/mobile/posts.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Posts.js?v=<%=System.currentTimeMillis()%>"></script>
</div>

<div id="profile-page" class="leaf">
    <%@ include file="/jsp/leafs/mobile/profile.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Profile.js?v=<%=System.currentTimeMillis()%>"></script>
</div>

<div id="search-page" class="leaf">
    <%@ include file="/jsp/leafs/mobile/search.jsp" %>
    <br class="clear"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Search.js?v=<%=System.currentTimeMillis()%>"></script>
</div>

<div id="friend-invites-page" class="leaf">
    <%@ include file="/jsp/leafs/mobile/invitations.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Invitations.js?v=<%=System.currentTimeMillis()%>"></script>
</div>


<%@ include file="/jsp/leafs/templates.jsp" %>

<br class="clear"/>