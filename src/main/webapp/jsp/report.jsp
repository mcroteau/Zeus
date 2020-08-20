<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="report-issue-form">

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

        <form action="${pageContext.request.contextPath}/issues/report" class="pure-form pure-form-stacked" method="post">

            <br class="clear"/>
            <label for="email">Email</label>
            <input type="email" name="email" id="name" placeholder="Email Address" value="">

            <br class="clear"/>
            <label for="issue">Tell us in a few sentences what happened...</label>
            <textarea name="issue" placeholder="What happened?" style="width:100%; height:123px;"></textarea>


            <div class="buttons-container">
                <a href="${pageContext.request.contextPath}/"></a>
                <input type="submit" class="button retro" id="update" value="Report Issue"/>
            </div>

        </form>
    </div>
</div>


