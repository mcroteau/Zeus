<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="xyz.ioc.model.Account" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>

<!doctype html>
<html>
<head>
	<title>Zeus : Like. Share. Obey!</title>
	
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



<!--/// beginning of scripts ///-->

<script type="text/template" id="chat-session-header-template">
    <h2 id="chat-session-header" class="retro" data-id="{{recipientId}}"><img src="${pageContext.request.contextPath}/{{recipientImageUri}}" id="chat-header-img" data-id="{{recipientId}}"/></h2>
    <span id="close-chat-session" class="yella">x</span>
</script>


<script type="text/template" id="chat-session-template">
{{#.}}
    <div class="chat-content-container">
        <p class="chat-who"><span class="from">{{sender}}</span><span class="time-ago">{{timeAgo}}</span></p>
        <p class="chat-content">{{content}}</p>
    </div>
{{/.}}
</script>


<script type="text/template" id="friends-chat-template">
{{#.}}
    <tr class="friend-wrapper">
        <td><a href="javascript:" class="lightf chat-session-launcher {{#messages}}unread-messages{{/messages}}" data-id="{{friendId}}" {{#messages}}data-messages="true"{{/messages}}>{{name}}</a></td>
        <td>
            <img src="${pageContext.request.contextPath}/{{imageUri}}" class="chat-session-launcher" data-id="{{friendId}}" {{#messages}}data-messages="true"{{/messages}}/>
            <span class="online-indicator {{#isOnline}}online{{/isOnline}}"></span>
        </td>
    </tr>
{{/.}}
</script>


<script type="text/template" id="posts-template">
{{#.}}
<div class="feed-content-container" id="post-{{id}}">
{{^hidden}}
    {{^flagged}}
        {{#shared}}
            <div class="shared-post-container">

                <a href="javascript:" class="shared-post-whois" data-id="{{sharedAccountId}}">
                    <img src="${pageContext.request.contextPath}/{{sharedImageUri}}" style="width:40px; height:40px;"/>
                </a>

                <div class="shared-account-container">
                    <span class="shared-account">{{sharedAccount}}</span>
                    <span class="shared-time-ago">{{timeSharedAgo}}</span>
                </div>

                <span class="shared-descriptor">Shared</span>

                <br class="clear"/>

                <div class="shared-comment-container">
                    <span class="shared-comment">{{sharedComment}}</span>
                </div>
            </div>
        {{/shared}}

        {{#shared}}
            <div class="feed-content shared">
        {{/shared}}
        {{^shared}}
            <div class="feed-content">
        {{/shared}}
            <div class="post-content">

                {{#imageFileUris}}
                     <div class="image-post-container">
                        <a href="${pageContext.request.contextPath}/{{.}}" target="_blank">
                            <img src="${pageContext.request.contextPath}/{{.}}" style="width:100%"/>
                        </a>
                     </div>
                {{/imageFileUris}}

                {{#musicFileUris.length}}
                        <p class="music-message">Use Google Chrome to download.</p>
                {{/musicFileUris.length}}

                {{#musicFileUris}}
                     <div class="music-post-container">
                        <audio controls src="${pageContext.request.contextPath}/{{.}}" class="audio-music"></audio>
                     </div>
                {{/musicFileUris}}

                {{#videoFileUri}}
                     <video controls src="${pageContext.request.contextPath}/{{videoFileUri}}" style="width:100%"></video>
                {{/videoFileUri}}

                {{#content}}
                    <div style="white-space: pre-line" class="post-comment">{{{content}}}</div>
                {{/content}}

            </div>

            <div class="post-meta">
                <a href="javascript:" class="post-whois" data-id="{{accountId}}">
                    <img src="${pageContext.request.contextPath}/{{imageUri}}" style="height:40px; width:40px;"/>
                </a>
                <span class="posted-by" data-id="{{accountId}}">{{name}}</span><br/>
                <span class="post-date">{{timeAgo}}</span>
                <br class="clear"/>
            </div>

            <div class="content-actions">

                 <div class="like-container float-right">
                        <span class="actions-count like-container-{{id}}" id="likes-{{id}}">{{likes}}</span> <span class="actions-label">Likes</span>
                    {{#liked}}
                        <a href="javascript:" class="like-button like-{{id}} liked" data-id="{{id}}">&pi;</a>
                    {{/liked}}
                    {{^liked}}
                        <a href="javascript:" class="like-button like-{{id}}" data-id="{{id}}">&pi;</a>
                    {{/liked}}
                </div>


                <div class="share-container float-left">
                    {{#shared}}
                        <span class="actions-count" id="shares-{{postShareId}}">{{shares}} <span class="actions-label">Shares</span></span>
                        <a href="javascript:" class="share-shared-button" data-id="{{postShareId}}" data-post-id="{{id}}">&Delta;</a>
                    {{/shared}}
                    {{^shared}}
                        <span class="actions-count" id="shares-{{id}}">{{shares}} <span class="actions-label">Shares</span></span>
                        <a href="javascript:" class="share-button" data-id="{{id}}">&Delta;</a>
                    {{/shared}}
                </div>

                <br class="clear"/>

            </div>

            <div class="post-separators">
                <div class="post-sep post-sep-uno"></div>
                <div class="post-sep post-sep-dos"></div>
                <div class="post-sep post-sep-tres"></div>
            </div>

            {{#shared}}
            <div class="share-comment-container" id="share-shared-comment-{{postShareId}}" style="display:none">
            {{/shared}}
            {{^shared}}
            <div class="share-comment-container" id="share-comment-{{id}}" style="display:none">
            {{/shared}}

                {{#shared}}
                    <form id="share-shared-post-form-{{postShareId}}">
                {{/shared}}
                {{^shared}}
                    <form id="share-post-form-{{id}}">
                {{/shared}}
                    <span class="share-comment-header" style="font-family:Roboto-Light !important;color:#617078">Share Post</span>
                    <textarea name="comment"></textarea>

                    {{#shared}}
                        <a href="javascript:" class="button retro small light-shadow float-right share-post-button" data-id="{{postShareId}}" data-post-id="{{id}}" data-shared="true">Share Post</a>
                    {{/shared}}
                    {{^shared}}
                        <a href="javascript:" class="button retro small light-shadow float-right share-post-button" data-id="{{id}}" data-post-id="{{id}}">Share Post</a>
                    {{/shared}}
                    <br class="clear"/>
                </form>
            </div>

            {{#commentsOrShareComments}}
                <div class="comments-container" style="margin-left:62px;">
            {{/commentsOrShareComments}}
            {{^commentsOrShareComments}}
                <div class="comments-container" style="margin-left:62px;">
            {{/commentsOrShareComments}}
                {{#commentsOrShareComments}}
                    <div class="comments-header-spacer"></div>
                {{/commentsOrShareComments}}
                {{#comments}}
                    <div class="post-comment-wrapper">

                        <div class="post-comment-meta">
                            <a href="javascript:" class="post-comment-whois left-float" data-id="{{accountId}}">
                                <img src="${pageContext.request.contextPath}/{{accountImageUri}}" style="height:30px; width:30px;"/>
                            </a>
                            <div class="left-float" style="margin-left:10px;width:79%;">
                                <p>
                                    <a href="javascript:" class="post-comment-whois" data-id="{{accountId}}" style="text-decoration:none; color:#222; font-family:Roboto-Bold !important">
                                        {{accountName}}
                                    </a>
                                    <span class="comment-comment">{{comment}}</span>
                                    {{#commentDeletable}}
                                        <a href="javascript:" class="comment-delete href-dotted" data-id="{{commentId}}" style="display:none">Delete</a>
                                    {{/commentDeletable}}
                                </p>
                            </div>
                            <br class="clear"/>
                        </div>
                    </div>
                {{/comments}}

                {{#shareComments}}
                    <div class="post-comment-wrapper">

                        <div class="post-comment-meta">
                            <a href="javascript:" class="post-comment-whois left-float" data-id="{{accountId}}">
                                <img src="${pageContext.request.contextPath}/{{accountImageUri}}" style="height:30px; width:30px;"/>
                            </a>
                            <div class="left-float" style="margin-left:10px;width:79%;">
                                <p>
                                    <a href="javascript:" class="post-comment-whois" data-id="{{accountId}}" style="text-decoration:none; color:#222; font-family:Roboto-Bold !important">
                                        {{accountName}}
                                    </a>
                                    <span class="comment-comment">{{comment}}</span>
                                    {{#commentDeletable}}
                                        <a href="javascript:" class="comment-delete href-dotted" data-id="{{commentId}}" style="display:none">Delete</a>
                                    {{/commentDeletable}}
                                </p>
                            </div>
                            <br class="clear"/>
                        </div>
                    </div>
                {{/shareComments}}

                <div class="comment-container" class="clear">

                    {{#shared}}
                        <form id="comment-shared-form-{{postShareId}}" class="comment-shared-form" data-id="{{postShareId}}">
                            <span class="comments-header information">Comment</span>
                            <input type="text" name="comment" id="comment-shared-{{postShareId}}" class="comment-shared-input" value=""/>
                            <span class="enter" style="display:none">Press Enter</span>
                        </form>
                    {{/shared}}
                    {{^shared}}
                        <form id="comment-form-{{id}}" class="comment-form" data-id="{{id}}">
                            <span class="comments-header information">Comment</span>
                            <input type="text" name="comment" id="comment-{{id}}" value="" class="comment-input"/>
                            <span class="enter" style="display:none">Press Enter</span>
                        </form>
                    {{/shared}}

                </div>

                <br class="clear"/>
            </div>
        </div>

        {{#shared}}
            <div class="post-admin-actions-wrapper">&Xi;
                <div class="post-admin-actions-inner" style="position:relative;">
                    <div class="post-admin-actions">
                        {{#deletable}}
                            <a href="javascript:" class="delete-post-share" data-id="{{postShareId}}">Delete Post</a>
                        {{/deletable}}
                        <a href="javascript:" class="flag-post" data-id="{{id}}" data-shared="true">Flag Post</a>
                        <a href="javascript:" class="hide-post" data-id="{{id}}">Hide Post</a>
                    </div>
                </div>
            </div>
        {{/shared}}
        {{^shared}}
            <div class="post-admin-actions-wrapper">&Xi;
                <div class="post-admin-actions-inner" style="position:relative;">
                    <div class="post-admin-actions">
                        {{#deletable}}
                            <a href="javascript:" class="delete-post" data-id="{{id}}">Delete Post</a>
                        {{/deletable}}
                        <a href="javascript:" class="flag-post" data-id="{{id}}" data-shared="false">Flag Post</a>
                        <a href="javascript:" class="hide-post" data-id="{{id}}">Hide Post</a>
                    </div>
                </div>
            </div>
        {{/shared}}
    {{/flagged}}
{{/hidden}}
</div>
{{/.}}
</script>



<script type="text/javascript">

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


    function openSessionWebsockets(event){

        disconnect()
        recipientId = event.target.getAttribute("data-id")

        var socket = new SockJS("http://localhost:8080${pageContext.request.contextPath}/chat");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function(frame) {
            stompClient.subscribe('/topic/messages', function(messageOutput) {

                var data = JSON.parse(messageOutput.body)
                renderMustache(chatSessionTemplate, chatSessionDiv, data.messages).then(function(){
                    chatSessionDiv.scrollTop = chatSessionDiv.scrollHeight
                    chatText.value = ''

                    var chatSessionHeaderWrapper = document.getElementById("chat-session-header-wrapper")
                    var chatSessionHeaderTemplate = document.getElementById("chat-session-header-template")

                    chatSessionHeaderWrapper.innerHTML = ''
                    renderMustache(chatSessionHeaderTemplate, chatSessionHeaderWrapper, data).then(applyHandlersOpenSessionWindow(event)).catch(error)
                });
            });

            if(!initialized){
                pingServer(recipientId)
                initialized = true
            }
        });
    }

    function pingServer(recipientId){
        stompClient.send("/app/chat", {}, JSON.stringify({'senderId':senderId , recipientId: recipientId }));
    }


    function disconnectWebsockets() {
        if(stompClient != null)
            stompClient.disconnect();

        if(initialized)
            initialized = false;

        console.log("Disconnected");
    }










    var dataPolling = {}
    var searchInterval = {}

    var pageProcessing = document.getElementById("page-processing")

    var posts = new Posts("${pageContext.request.contextPath}")
    var profile = new Profile("${pageContext.request.contextPath}")
    var search = new Search("${pageContext.request.contextPath}")
    var friendInvites = new Invites("${pageContext.request.contextPath}")


    var topbar = document.getElementById("top-outer-container")
    var overlay = document.getElementById("processing-overlay")

    var feedPage = document.getElementById("feed-page"),
        profilePage = document.getElementById("profile-page"),
    	musicUploadPage = document.getElementById("music-upload-page"),
    	searchPage = document.getElementById("search-page"),
    	aboutPage = document.getElementById("about-page"),
    	donatePage = document.getElementById("donate-page"),
    	friendInvitesPage = document.getElementById("friend-invites-page")

    var leafs = document.getElementsByClassName("leaf")
    var leafsArr = Array.from(leafs)

    var mainContainerDiv = document.getElementById("content-container")
    var layoutContainerDiv = document.getElementById("layout-container")


    var profileActionsHref = document.getElementById("profile-actions-href")
    var profileActionsDiv = document.getElementById("profile-picture-actions-container")
    var profileHref = document.getElementById("profile-href")
    var messagesHref = document.getElementById("messages-href")
    var invitesCount = document.getElementById("invites-total")
    var baseNotificationsCount = document.getElementById("base-notifications-count")
    var messagesHref = document.getElementById("messages-href")
    var unreadMessages = document.getElementById("latest-messages-total")
    var latestFeedTotal = document.getElementById("latest-feed-total")


    profileActionsHref.addEventListener("click", dispatchProfileActionsClickedEvent)

    messagesHref.addEventListener("click", openFriends)

    profileHref.addEventListener("click", function(){
        var id = profileHref.getAttribute("data-id")
        profile.displayProfile(id)
    })

    var siteRefs = document.getElementsByClassName("page-ref")
    var siteRefsArr = Array.from(siteRefs)

    siteRefsArr.forEach(function(ref, index){
        ref.addEventListener("click", function(){
            var destination = ref.getAttribute("data-ref")
            switch(destination){
                case "about" :
                    showAboutPage()
                    break;
                case "donate" :
                    showDonatePage();
                    break;
                default :
                    break;
            }
        })
    })

    function showAboutPage(){
        shiftDownLeafs(aboutPage)
        sessionStorage.setItem("zeus-current", "about")
        aboutPage.style.display = "block"
        aboutPage.style.opacity = 1
        pageProcessing.style.display = "none"
        adjustMainContainerHeight(aboutPage)
    }

    function showDonatePage(){
        shiftDownLeafs(donatePage)
        sessionStorage.setItem("zeus-current", "donate")
        pageProcessing.style.display = "none"
        donatePage.style.opacity = 1
        adjustMainContainerHeight(donatePage)
    }

    function clearIntervals(){
        clearInterval(searchInterval)
        clearInterval(dataPolling)
        if(dataPolling == 0)startApplicationPolling()

        dataPolling = 0;
        searchInterval = 0;
    }

    var invitesHref = document.getElementById("invites-href")
    invitesHref.addEventListener("click", friendInvites.dispatchTransitionInvitesPageEvent)

    document.getElementsByTagName("body")[0].addEventListener("click", function(event){

        var element = event.target
        var id = element.getAttribute("id")

        if(id != "profile-actions-href" &&
            id != "profile-ref-image"){
            profileActionsDiv.setAttribute("data-opened", "false")
            profileActionsDiv.style.display = "none"
        }

        if(!element.classList.contains("chat-session-launcher")){
            chatPopup.style.height = "37px"
            chatPopup.style.overflow = "initial"
            setChatLaunched(false)
        }

        posts.notificationsOuterDiv.setAttribute("data-opened", false)
        posts.notificationsOuterDiv.style.display = "none"
    })


    function getRandomArbitrary(min, max) {
        return Math.random() * (max - min) + min;
    }


    function startApplicationPolling(){

        dataPolling = setInterval(function(){

            var id = profileActionsDiv.getAttribute("data-id")
            var uri = "${pageContext.request.contextPath}/profile/data"
            req.http(uri).then(function(request){
                var data = JSON.parse(request.responseText)
                renderLatestPosts(data)
                updateGlobalNotificationsCount(data)
                posts.updateNotificationsCount(data.notificationsCount)
            })


        }, 4201);
    }

    function updateGlobalNotificationsCount(data){
        unreadMessages.innerHTML = data.messagesCount;
        invitesCount.innerHTML = data.invitationsCount;
        baseNotificationsCount.innerHTML = data.invitationsCount + data.messagesCount
        if(data.invitationsCount > 0 || data.messagesCount > 0){
            baseNotificationsCount.style.display = "block"
        }
    }


    function dispatchProfileActionsClickedEvent(event){
        event.preventDefault()

        var opened = profileActionsDiv.getAttribute("data-opened")

        if(opened == "true"){
            profileActionsDiv.setAttribute("data-opened", "false")
            profileActionsDiv.style.display = "none"
        }
        if(!opened || opened == "false"){
            var id = profileActionsDiv.getAttribute("data-id")
            profileActionsDiv.setAttribute("data-opened", "true")
            profileActionsDiv.style.display = "block"
        }
    }


    function renderLatestPosts(data){
    
        if(data.latestPosts && data.latestPosts.length > 0){

            latestFeedTotal.innerHTML = data.latestPosts.length

            var names = ""
            data.latestPosts.forEach(function(value, index){
                names += value.name
                if(index != data.length -1){
                    names += ", "
                }
            })

            var message = names + " just posted a new post"

            if(names != ""){
                var somepeoplePosted = document.getElementById("people-posted")
                somepeoplePosted.style.display = "block"
                posts.latestPostsDiv.innerHTML = message
            }
        }else{
            latestFeedTotal.innerHTML = 'Zeus'
        }
    }


    function renderMustache(template, container, data){
        return new Promise(function(resolve, reject){
            var html = Mustache.render(template.innerHTML, data)
            container.innerHTML = html;
            resolve();
        });
    }

    function showProcessing(){
        pageProcessing.style.display = "block"
        pageProcessing.style.zIndex = 10
    }



    function slimShift(leaf){
        initializeProgressBar()

        posts.latestPosts.style.display = "none"
        posts.feedContainerDiv.innerHTML = "";
        posts.uploadMessageContainer.style.display = "none"

        pageProcessing.style.display = "block";

        leafsArr.forEach(function(lf, index){
             lf.style.zIndex = -1
             lf.style.opacity = 0
        })
        leaf.style.zIndex = 13

        window.scroll({
            top: 0,
            left: 0,
            behavior: 'smooth'
        });
    }


    function shiftDownLeafs(leaf){
        initializeProgressBar()
        topbar.style.display = "none"

        posts.latestPosts.style.display = "none"
        posts.feedContainerDiv.innerHTML = "";
        posts.uploadMessageContainer.style.display = "none"

        pageProcessing.style.display = "block";

        document.body.classList.remove("love")

        leafsArr.forEach(function(lf, index){
             lf.style.zIndex = -1
             lf.style.opacity = 0
        })
        leaf.style.zIndex = 13

        window.scroll({
            top: 0,
            left: 0,
            behavior: 'smooth'
        });
    }

    function shiftLeaf(leaf){
        setTimeout(function(){
            topbar.style.display = "block"
            pageProcessing.style.display = "none";
            leaf.style.display = "block"
            leaf.style.opacity = 1;
            adjustMainContainerHeight(leaf)
        }, 301)
    }


    function adjustMainContainerHeight(leaf){
        var height = leaf.clientHeight;
        height += 10
        layoutContainerDiv.setAttribute("style", "height:" + height + "px")
        mainContainerDiv.setAttribute("style", "height:" + height + "px")
    }


    function getElementsByClassNameArray(className){
        var elements = document.getElementsByClassName(className)
        var elementsArr = Array.from(elements)
        return elementsArr;
    }


    function error(e){
        console.log("error", e)
        sessionStorage.clear()
    }

    function errorTransition(e){
        sessionStorage.clear()
        //window.location = window.location.href
    }



    var lovePopup = document.getElementById("love")
    var closeLove = document.getElementById("close-love")

    var tm = 0
    var welcomeAudio = {};

    function displayLove(){

        if(sessionStorage.getItem("loved") == "" ||
            !sessionStorage.getItem("loved")){

            lovePopup.style.display = "block"

            //Fixed : requires user interaction before playing
            welcomeAudio = new Audio()
            welcomeAudio.src = "${pageContext.request.contextPath}/media/samples/Encore.mp3";

            var playPromise = welcomeAudio.play().catch( () => { welcomeAudio.play(); } );

            sessionStorage.setItem("loved", "loved")
        }

        closeLove.addEventListener("click", closeLovePopup)
        lovePopup.addEventListener("click", closeLovePopup)
    }

    function closeLovePopup(){
        lovePopup.style.display = "none"
        if(tm && !isEmptyObj(tm)){
            clearTimeout(tm)
        }
        welcomeAudio.pause();
    }


    function redirectAuthentication(){
        window.location = window.location.href
    }



    function isEmptyObj(obj){
        return Object.keys(obj).length === 0 && obj.constructor === Object
    }


    var paramsArr = []
    var currentPage = sessionStorage.getItem("zeus-current")
    var params = sessionStorage.getItem("zeus-params")
    var searchParams = sessionStorage.getItem("zeus-search")

    try{
        paramsArr = JSON.parse(params)
    }catch(error){ }

    console.log("current", currentPage)

    switch(currentPage){
        case 'profile':
            profile.displayProfile(paramsArr)
            break;
        case 'invites':
            friendInvites.transitionInvitesPage(paramsArr)
            break;
        case 'search':
            search.transition(searchParams)
            break;
        case 'posts':
            posts.transition()
            break;
        default:
            posts.transition()
            break;
    }

    initializeProgressBar()
    startApplicationPolling()

    var value = Cookies.get("Parakeet")
    console.log("value", value)
    if(value != null){
        var cookie = Cookies.set("Parakeet", value, { sameSite : "Lax" });
        console.log(cookie);
    }

</script>


<script async src="https://www.googletagmanager.com/gtag/js?id=UA-40862316-16"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-40862316-16');
</script>

</body>
</html>

