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
    #zeus{
        height:20px;
        width:20px;
        fill:#fff;
    }
</style>

<div id="registration-form-container">

    <c:if test="${not empty message}">
        <div class="notify alert-info">${message}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="notify alert-danger">${error}</div>
    </c:if>



    

    <form action="${pageContext.request.contextPath}/register" modelAttribute="account" method="post" enctype="multipart/form-data" autocomplete="false" class="pure-form pure-form-stacked" id="registration-form">
        <fieldset>

            <p class="thin" style="line-height:1.3em;">Why should there be one group to reign social media, when there is 
<!--                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 134 134" id="zeus">
                    <path d="M49 1L21 88L57 88L42 134L84 134L113 47L92 47L79 47L75 47L91 1L49 1Z" />
                </svg>-->
                <span style="font-family:Roboto-Bold !important; line-height:1.3em;margin:20px auto;">Zeus. </span>
                
            </p>

            <span style="font-family:Roboto-Bold !important;font-size:23px;">Signup</span>

            <p style="line-height:1.3em">Start sharing with friends.</p>

            <input type="hidden" name="uri" value="${uri}"/>

            <input id="name" type="text" placeholder="Name" name="name">

            <input id="username" type="email" placeholder="Email Address" name="username" style="width:100%;">

            <input id="password" type="password" placeholder="Password &#9679;&#9679;&#9679;" name="password">

        </fieldset>
    </form>


    <div style="margin-top:41px;text-align:right">
        <input type="submit" class="button retro" id="signup-button" value="Signup" style="width:100%;"/>
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
