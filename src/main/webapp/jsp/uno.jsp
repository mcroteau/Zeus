<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<style type="text/css">


p{
    line-height:1.4em;
}

</style>

<c:if test="${not empty message}">
    <div class="span12">
        <div class="alert alert-info">${message}</div>
    </div>
</c:if>

<c:if test="${not empty error}">
    <div>
        <div class="alert alert-danger">${error}</div>
    </div>
</c:if>

<div id="uno-content">


    <h2 style="font-size:32px; font-family:Roboto-Bold !important; line-height:1.3em;">Zeus.</h2>

    <p class="thin">Like. Share. Obey!</p>

    <p style="line-height:1.4em;">Want to see Zeus in action? You can sign up a new account or view as a guest and start sharing!.

    <a href="${pageContext.request.contextPath}/account/guest" class="href-dotted-zeus">Go here!</a></p>

    <a href="${pageContext.request.contextPath}/account/guest" class="button light large" style="display:block;margin-top:40px;margin-bottom:10px;">Check it Out !</a>

    <a href="${pageContext.request.contextPath}/signup" class="button retro large" style="display:block;margin-bottom:70px;">Sign Up !</a>

    <p id="get_code">Want to add like & share to your web page? <br/><a href="${pageContext.request.contextPath}/get_code" class="href-dotted-zeus">Get Code</a></p>

</div>

<script type="text/javascript">
var songs = [
    "${pageContext.request.contextPath}/media/samples/Operate.mp3",
    "${pageContext.request.contextPath}/media/samples/WhereEverIMayRoam.mp3",
    "${pageContext.request.contextPath}/media/samples/GinAndJuice.mp3"
]
//var audioPlayer = document.getElementById("player")
//var index = getRandomInt(0, songs.length -1)
//audioPlayer.src = songs[index]

function getRandomInt(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min;
}

</script>