var Invites = function(context){

    var _id = {}
    var _context = context


    var friendInvitesContainer = document.getElementById("friend-invites-container")
    var noFriendInvites = document.getElementById("no-friend-invites")
    var friendInviteRowsContainer = document.getElementById("friend-invite-rows-container")
    var friendInvitesRowTemplate = document.getElementById("friend-invites-row")


    function dispatchTransitionInvitesPageEvent(event){
        var target = event.target
        var id = target.getAttribute("data-id")
        transitionInvitesPage(id)
    }


    function transitionInvitesPage(id){
        shiftDownLeafs(friendInvitesPage)
        var uri = _context + "/friend/invitations"
        req.http(uri).then(renderPendingInvites).catch(error)
        _id = id
    }

    function renderPendingInvites(request){
        var data = JSON.parse(request.responseText)

        if(data && data.length > 0){

            noFriendInvites.style.display = "none"

            renderMustache(friendInvitesRowTemplate, friendInviteRowsContainer, data).then(function(){
                friendInvitesContainer.style.display = "block"
                applyEventHandlers()
                sessionStorage.setItem("zeus-current", "invites")
                shiftLeaf(friendInvitesPage)
            });

        }
        else{
            friendInvitesContainer.style.display = "none"
            noFriendInvites.style.display = "block"
            sessionStorage.setItem("zeus-current", "invites")
            shiftLeaf(friendInvitesPage)
        }

        sessionStorage.setItem("zeus-current", "invites")
        sessionStorage.setItem("zeus-params", JSON.stringify([_id]))

    }

    function applyEventHandlers(){
        var acceptButtons = document.getElementsByClassName("accept-button")
        var acceptButtonsArr = Array.from(acceptButtons)

        acceptButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchAcceptEvent)
        })

        var ignoreButtons = document.getElementsByClassName("ignore-button")
        var ignoreButtonsArr = Array.from(ignoreButtons)

        ignoreButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchIgnoreEvent)
        })

        var friendInvitesProfileHrefs = document.getElementsByClassName("friend-invite-profile-href")
        var friendInvitesProfileHrefArr = Array.from(friendInvitesProfileHrefs)

        friendInvitesProfileHrefArr.forEach(function(href, index){
            var id = href.getAttribute("data-id")
            href.addEventListener("click", dispatchDisplayProfileEvent)
        })

    }

    function dispatchDisplayProfileEvent(event){
        var id = this.getAttribute("data-id")
        profile.displayProfile(id)
    }

    function dispatchAcceptEvent(event){
        var target = event.target
        var id = target.getAttribute("data-id")
        var uri = _context + "/friend/accept/" + id;
        req.http(uri, "post").then(acceptFriendInviteRow(id))
    }

    function dispatchIgnoreEvent(event){
        var target = event.target
        var id = target.getAttribute("data-id")
        var uri = _context + "/friend/ignore/" + id;
        req.http(uri, "post").then(ignoreFriendInviteRow(id))
    }

    function acceptFriendInviteRow(id){
        return function(request){
            var data = JSON.parse(request.responseText)
            if(data && data.success){
                var actionsTd = document.getElementById("invite-actions-" + id)
                actionsTd.innerHTML = "Accepted";
            }
            else{
                alert("Something went wrong. Please contact us")
            }

            overlay.style.display = "none"
        }
    }

    function ignoreFriendInviteRow(id){
        return function(request){
            var data = JSON.parse(request.responseText)
            if(data && data.success){
                var row = document.getElementById("friend-invite-row-" + id)
                row.style.display = "none";
            }
            else{
                alert("Something went wrong. Please contact us")
            }
        }
    }


    return {
        transitionInvitesPage : transitionInvitesPage,
        dispatchTransitionInvitesPageEvent : dispatchTransitionInvitesPageEvent
    }

}