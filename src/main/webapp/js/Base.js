
var dataPolling = {}
var searchInterval = {}

var pageProcessing = document.getElementById("page-processing")

var posts = new Posts("/b")
var profile = new Profile("/b")
var search = new Search("/b")
var friendInvites = new Invites("/b")


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

if(messagesHref != null) {
    messagesHref.addEventListener("click", openFriends)
}

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

    try {
        if (!element.classList.contains("chat-session-launcher")) {
            chatPopup.style.height = "37px"
            chatPopup.style.overflow = "initial"
            setChatLaunched(false)
        }
    }catch(e){}

    posts.notificationsOuterDiv.setAttribute("data-opened", false)
    posts.notificationsOuterDiv.style.display = "none"
})


function getRandomArbitrary(min, max) {
    return Math.random() * (max - min) + min;
}


function startApplicationPolling(){

    dataPolling = setInterval(function(){

        var id = profileActionsDiv.getAttribute("data-id")
        var uri = "/b/profile/data"
        req.http(uri).then(function(request){
            var data = JSON.parse(request.responseText)
            renderLatestPosts(data)
            updateGlobalNotificationsCount(data)
            posts.updateNotificationsCount(data.notificationsCount)
        })


    }, 4201);
}

function updateGlobalNotificationsCount(data){
    if(unreadMessages != null) {
        unreadMessages.innerHTML = data.messagesCount;
    }
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
        welcomeAudio.src = "/b/media/samples/Encore.mp3";

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
