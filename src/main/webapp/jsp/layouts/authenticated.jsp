<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="xyz.ioc.model.Account" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!doctype html>
<html>
<head>
	<title>Zeus : Like. Share. Kneel. Obey!</title>
	
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png?v=<%=System.currentTimeMillis()%>">

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/apexcharts/apexcharts.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/js/lib/apexcharts/apexcharts.css"/>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/anchorme.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/Request.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/WebForm.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/progressbar.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/js.cookies.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/mustache.js"></script>

	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css?v=<%=System.currentTimeMillis()%>"/>

    <script type="text/javascript">

        var progressBar = {}

        function getRandomInt(min, max) {
            min = Math.ceil(min);
            max = Math.floor(max);
            return Math.floor(Math.random() * (max - min + 1)) + min;
        }

        var songs = [];

        var playing;

        var track = 0;
        var nextSong = 0;
        var songDisplay = 1;
        var musicPlayer = null;


        var req = new Request("${pageContext.request.contextPath}");
        var web = new WebForm();

        function initializeProgressBar(){

            if(progressBar && progressBar != "" && (Object.entries(progressBar).length != 0 && progressBar.constructor === Object) ){
                progressBar.destroy()
            }

            if(isEmptyObj(progressBar)){

                progressBar = new ProgressBar.Line('#progress-bar-top', {
                    color: '#2b2b34',
                    easing: 'easeInOut'
                });

                progressBar.animate(1, function(){
                    progressBar.destroy()
                    progressBar = {}
                });
            }

        }

    </script>

	<decorator:head />

</head>
<body>



	<div id="progress-bar-top" style="display:block"></div>

	<div id="processing-overlay">
	    <img src="${pageContext.request.contextPath}/images/processing-dos.gif" style="height:50px; width:50px;margin-top:200px;"/>
	</div>

	<div id="layout-container">

		<div id="top-outer-container">

            <div id="logo-container" style="position:absolute;">
                <a href="javascript:" id="logo-logo">
                    <svg id="zeus-logo">
                        <use xlink:href="#zeus" />
                    </svg>
                    <span id="latest-feed-total" class="notifications-count" style="display:inline-block; position:absolute;bottom:3px;left:54px;">Zeus</span></a>
            </div>

			<div id="top-inner-container">

                <div id="search-label">Search:</div>

                <div id="search-container" class="float-left" style="z-index:100">
                    <input type="text" class="search-input" id="search-box" placeholder=""/>
				</div>

				<br class="clear"/>

                <div id="page-processing">
	                <img src="${pageContext.request.contextPath}/images/processing-dos.gif" style="height:50px; width:50px; position:absolute; right:140px; top:3px;"/>
                    <span class="information" id="processing-message"></span>
                </div>

			</div>

            <div id="navigation-container" class="float-right">
                <a href="javascript:" id="profile-actions-href" style="margin-right:37px;">
                    <img src="${pageContext.request.contextPath}/${sessionScope.imageUri}" id="profile-ref-image" style="z-index:1"/>
                    <span id="base-notifications-count">0</span>
                </a>

                <div id="profile-picture-actions-container" style="display:none;text-align:left;" data-id="${sessionScope.account.id}" class="global-shadow">
                    <a href="javascript:" id="profile-href"  class="profile-popup-action" data-id="${sessionScope.account.id}"><span class="space"></span> Profile</a>
                    <a href="javascript:" id="messages-href"  class="profile-popup-action" data-id="${sessionScope.account.id}"><span id="latest-messages-total" class="space">0</span> Unread</a>
                    <a href="javascript:" id="invites-href"  class="profile-popup-action" data-id="${sessionScope.account.id}"><span id="invites-total" class="space">0</span> Invites</a>
                    <a href="${pageContext.request.contextPath}/signout" class="profile-popup-action" ><span class="space"></span> Logout</a>
                </div>


            </div>

		</div>

		<div id="content-container">

            <decorator:body />

		</div>

        <br class="clear"/>

	</div>

    <br class="clear"/>
    <br class="clear"/>


    <div id="chat-session-outer-wrapper" class="global-shadow">
        <div id="chat-inner-wrapper">
            <div id="chat-session-header-wrapper"></div>
            <div id="chat-session"></div>
            <form id="chat-session-frm">
                <textarea id="chat-text" placeholder="Begin chat..." name="content"></textarea>
            </form>
        </div>
    </div>


    <div id="chat-launcher-popup" class="global-shadow chat-launcher" data-launched="false" >
        <div id="chat-header">
            <h2 id="friends-launcher" data-launched="false" class="chat-launcher">Messages</h2>
        </div>
        <div id="friends-wrapper-container">
            <table id="friends-wrapper"></table>
        </div>
    </div>


    <div id="site-refs" style="text-align:center;margin-top:191px;">
        <a href="${pageContext.request.contextPath}/get_code" class="page-ref href-dotted" >Get Code</a>
        <a href="javascript:" class="page-ref href-dotted" data-ref="about">About</a>
        <a href="${pageContext.request.contextPath}/invite" class="href-dotted" id="invite-people">Invite</a>
    </div>

    <p style="text-align:center;"><a href="mailto:support@zeus.social" style="color:#2b2b2c" class="href-dotted">support@zeus.social</a>

        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 134 134" id="zeus">
            <path d="M49 1L21 88L57 88L42 134L84 134L113 47L92 47L79 47L75 47L91 1L49 1Z" />
        </svg>
    </p>

    <style>
        #zeus{
            height:20px;
            width:20px;
        }
    </style>

    <div style="text-align:center;margin:20px auto 560px auto">
        <p style="text-align: center; font-size:12px;">&copy; 2020 Zeus</p>
        <a href="http://tomcat.apache.org/" target="_blank" class="information">Powered by<br/>Tomcat</a>
    </div>



<script>

    var stompClient = null;
    var initialized = false;

    var messagePoll = 0;

    var recipientId = null
    var senderId = "${sessionScope.account.id}";

    var chatPopup = document.getElementById("chat-launcher-popup")

    var chatSessionOuterWrapper = document.getElementById("chat-session-outer-wrapper")

    var launchers = document.getElementsByClassName("chat-launcher")
    var launchersArr = Array.from(launchers)
    launchersArr.forEach(function(launcher){
        launcher.addEventListener("click", openFriendsDispatchedEvent)
    });

    var chatSessionDiv = document.getElementById("chat-session");
    var chatSessionTemplate = document.getElementById("chat-session-template")
    chatSessionDiv.scrollTop = chatSessionDiv.scrollHeight;


    var chatText = document.getElementById("chat-text")
    chatText.addEventListener("keypress", function(event){
        var target = event.target
        if(event.key === "Enter") {
            var uri = "${pageContext.request.contextPath}/message/send/" + recipientId;
            var form = document.getElementById("chat-session-frm")
            web.publish(uri, form).then(function(){
                retrieveMessages();
                chatText.value = ''
            }).catch(error)
            //stompClient.send("/app/chat", {}, JSON.stringify({ senderId :senderId , recipientId: recipientId, content: chatText.value }));
        }
    })

    function openFriendsDispatchedEvent(event){

        if(event.target.classList.contains("chat-session-launcher")){
            return false;
        }

        var opened = event.target.getAttribute("data-launched")
        if(opened == "false"){
            openFriends()
        }else{
            chatPopup.style.height = "37px"
            setChatLaunched("false")
        }
    }


    function openFriends(){
        var template = document.getElementById("friends-chat-template")
        var container = document.getElementById("friends-wrapper")

        var uri = "${pageContext.request.contextPath}/friends/${sessionScope.account.id}"
        req.http(uri, "get").then(function(response){
            var data = JSON.parse(response.responseText)
            renderMustache(template, container, data).then(function(){
                chatPopup.style.height = "310px"
                chatPopup.style.overflow = "scroll"
                setChatLaunched("true")
                bindClickEventHandlers()
            })
        })
    }

    function openSession(event){
        recipientId = event.target.getAttribute("data-id")
        disconnectSession()
        retrieveMessages()
        initiateMessagePolling()
    }

    function retrieveMessages(){
        var uri = "${pageContext.request.contextPath}/messages/" + recipientId
        req.http(uri, "get").then(function(request){
            var outputMessage = JSON.parse(request.responseText)
            renderMustache(chatSessionTemplate, chatSessionDiv, outputMessage.messages).then(function(){
                if(!initialized){
                    chatSessionDiv.scrollTop = chatSessionDiv.scrollHeight
                    initialized = true
                }
                var chatSessionHeaderWrapper = document.getElementById("chat-session-header-wrapper")
                var chatSessionHeaderTemplate = document.getElementById("chat-session-header-template")

                chatSessionHeaderWrapper.innerHTML = ''
                renderMustache(chatSessionHeaderTemplate, chatSessionHeaderWrapper, outputMessage).then(applyHandlersOpenSessionWindow(event)).catch(error)
            });


        }).catch(error);
    }

    function initiateMessagePolling(){
        messagePoll = setInterval(retrieveMessages, 2100)
    }

    function disconnectSession(){
        clearInterval(messagePoll)
        messagePoll = 0
    }


    function applyHandlersOpenSessionWindow(event){
        return function(){
            console.info("applyHandlersOpenSessionWindow...")

            openChatSessionWindow()
            removeChatHeaderClickEvents()
            bindChatHeaderClickEvents()
            applyChatHyperlinks()
            updateFriendsListUi(event)
        }
    }

    function openChatSessionWindow(){
        chatSessionOuterWrapper.style.bottom = "0px"
        chatSessionOuterWrapper.style.display = "block"
    }


    function removeChatHeaderClickEvents(){
        var chatSessionHeader = document.getElementById("chat-session-header")
        chatSessionHeader.removeEventListener("click", function(){});
        var closeChatSession = document.getElementById("close-chat-session")
        closeChatSession.removeEventListener("click", function(){});
    }

    function bindChatHeaderClickEvents(){
        var chatSessionHeader = document.getElementById("chat-session-header")
        chatSessionHeader.addEventListener("click", dispatchChatSessionHeaderEvent);


        var closeChatSession = document.getElementById("close-chat-session")
        closeChatSession.addEventListener("click", dispatchCloseSessionEvent)
    }

    function updateFriendsListUi(event){
        var uri = "${pageContext.request.contextPath}/messages/read/" + recipientId;
        req.http(uri, "get").then(function(){
            event.target.classList.remove("unread-messages")
        }).catch(error)
    }

    function dispatchCloseSessionEvent(event){
        chatSessionOuterWrapper.style.display = "none"
        disconnectSession()
    }

    function dispatchChatSessionHeaderEvent(event){
        var id = event.target.getAttribute("data-id")
        profile.displayProfile(id)
    }

    function bindClickEventHandlers(){
        var chatSessionLaunchers = document.getElementsByClassName("chat-session-launcher")
        var chatSessionLaunchersArr = Array.from(chatSessionLaunchers)
        chatSessionLaunchersArr.forEach(function(sessionLauncher){
            sessionLauncher.addEventListener("click", openSession)
        });
    }

    function setChatLaunched(launched){
        launchersArr.forEach(function(launcher){
            launcher.setAttribute("data-launched", launched)
        })
    }


    function applyChatHyperlinks(){
        var chatContent = document.getElementsByClassName("chat-content")
        var chatContentArr = Array.from(chatContent)

        chatContentArr.forEach(function(content, index){
            var d = {
                input: content.innerHTML,
                options: {
                    attributes: {
                        target: "_blank",
                        class: "href-dotted"
                    },
                }
            }
            const hyperP = anchorme(d)
            content.innerHTML = hyperP
        })
    }


</script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/Base.js"></script>



<script async src="https://www.googletagmanager.com/gtag/js?id=UA-40862316-16"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-40862316-16');
</script>

</body>
</html>

