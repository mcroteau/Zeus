<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>

<div id="uno-content">

    <c:if test="${not empty sessionScope.message}">
        <div class="span12">
            <p>${sessionScope.message}</p>
        </div>
    </c:if>


    <h2 style="margin-bottom:20px;">Signin</h2>

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

    <p style="margin-top:30px;">Want to see Zeus in action? You can view as a guest and start sharing!.

    <a href="${pageContext.request.contextPath}/account/guest" class="href-dotted-zeus">Go here!</a></p>

    <a href="${pageContext.request.contextPath}/signup" class="button zeus large" style="display:block;">Sign Up !</a>

    <a href="${pageContext.request.contextPath}/account/guest" class="button light large" style="display:block;margin-top:10px;margin-bottom:70px;">Check it Out !</a>

    <p id="get_code">Want to add like & share to your web page? <br/><a href="${pageContext.request.contextPath}/get_code" class="href-dotted-zeus">Get Code</a></p>

</div>
