<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div id="invite-people-form">

	<c:if test="${not empty message}">
		<div class="span12">
			<div class="notify">${message}</div>
		</div>
	</c:if>

	<c:if test="${not empty error}">
		<div>
			<div class="notify">${error}</div>
		</div>
	</c:if>


	<div class="float-left">

        <form action="${pageContext.request.contextPath}/invite" class="pure-form pure-form-stacked" method="post">

            <h1>Invite <br/> People</h1>

            <br class="clear"/>
            <label for="issue">Email</label>
            <textarea name="emails" placeholder="Enter emails separated by comma..." style="width:100%; height:123px;"></textarea>
            <br class="clear"/>
            <br/>
            <div class="buttons-container">
                <a href="${pageContext.request.contextPath}/">Cancel</a>
                <input type="submit" class="button retro" id="invite" value="Invite People"/>
            </div>

        </form>
    </div>

    <br class="clear"/>

</div>


