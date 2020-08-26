<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    #signup-button-old{
    	-webkit-animation-name: zeus; /* Safari 4.0 - 8.0 */
        -webkit-animation-duration: 23s; /* Safari 4.0 - 8.0 */
        animation-name: zeus;
        animation-iteration-count: infinite;
        animation-duration: 23s;
    }
    .pure-form-stacked input[type="text"],
    .pure-form-stacked input[type="email"],
    .pure-form-stacked input[type="password"]{
        margin:0.35em 0;
    }

</style>

<div id="registration-form-container" style="padding-top:20px;">

    <c:if test="${not empty message}">
        <div class="notify alert-info">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="notify alert-danger">${error}</div>
    </c:if>


    <form action="${pageContext.request.contextPath}/register" modelAttribute="account" method="post" enctype="multipart/form-data" autocomplete="false" class="pure-form pure-form-stacked" id="registration-form">
        <fieldset>

            <h1 style="font-family:Roboto-Bold !important;font-size:23px;margin-bottom:20px;">Signup!</h1>
            <p>Create your account and begin sharing with friends &amp; family.</p>
            <input type="hidden" name="uri" value="${uri}"/>

            <input id="name" type="text" placeholder="Name" name="name" style="width:100%">

            <input id="username" type="email" placeholder="Email Address" name="username" style="width:100%;">

            <input id="password" type="password" placeholder="Password &#9679;&#9679;&#9679;" name="password" style="width:100%">

        </fieldset>
    </form>


    <div style="margin-top:41px;text-align:right">
        <input type="submit" class="button zeus" id="signup-button" value="Signup!" style="width:100%;"/>

        <p style="text-align: left; margin-top:30px;">Are you already one of God's delicate flowers?</p>

        <a href="${pageContext.request.contextPath}/" type="submit" class="button retro" id="signin-button">Signin!</a>
    </div>

</div>


<script>
    var email = document.getElementById("username")
    var password = document.getElementById("password")
    var form = document.getElementById("registration-form")
    var signupButton = document.getElementById("signup-button")
    var processing = false

    signupButton.addEventListener("click", function(event){
        event.preventDefault();
        if(!processing){
            processing = true;
            form.submit();
        }
    })

    setTimeout(function(){
        password.value = ""
        email.value = ""
    }, 1000)

</script>
