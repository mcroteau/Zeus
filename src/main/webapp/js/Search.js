
var Search = function(context){

   var _context = context

   var searchMusicPlayer = new Audio()

    var resultsTotal = document.getElementById("results-total"),
        resultsCount = document.getElementById("results-count");

    var searchButton = document.getElementById("search-button");
    var searchBox = document.getElementById("search-box");

    var nextPageButton = document.getElementById("next-page-button"),
        previousPageButton = document.getElementById("previous-page-button");

    sessionStorage.getItem("zeus-search") ? searchBox.value = sessionStorage.getItem("oa-search") : searchBox.value = ""
    //searchButton.addEventListener("click", dispatchSearchAccounts);
    searchBox.addEventListener("keypress", dispatchSearchEnterAccounts);

    function transition(q){
        searchBox.style.display = "block"
        searchBox.value = q
        searchAccounts()
    }

    function dispatchSearchEnterAccounts(event){
        var target = event.target
        if(target.classList.contains("search-input") &&
                    event.key === "Enter") {

            searchAccounts()
        }else{
            var notification = document.getElementById("press-enter")
        }
    }

    function dispatchSearchAccounts(event){
        searchAccounts()
    }

    function searchAccounts(){
        slimShift(searchPage)
        loading.style.display = "block";

        var noResults = document.getElementById("no-results")
        noResults.style.display = "none"

        var searchResultsContainer = document.getElementById("search-results-container")
        searchResultsContainer.style.display = "none";
        var uri = _context + "/search?q=" + searchBox.value;
        req.http(uri).then(renderSearchResults).catch(failedSearch);
    }

   function renderSearchResults(request){
        var data = JSON.parse(request.responseText);

        console.log('blocked', data)

        var searchAccountsResultsContainer = document.getElementById("search-accounts-results-container")
        var searchResultsContainer = document.getElementById("search-results-container")
        var accountsResultsTable = document.getElementById("accounts-results-table")

        if(data.accounts && data.accounts.length > 0){

            var searchResultsTemplate = document.getElementById("account-results-template")
            var searchResultsRowsContainer = document.getElementById("accounts-search-results-container")
            renderMustache(searchResultsTemplate, searchResultsRowsContainer, data.accounts).then(function(){


                checkDisplayNavigation(data)
                initializeSearchResults()

                sessionStorage.setItem("zeus-current", "search")
                sessionStorage.setItem("zeus-search", searchBox.value)
                document.body.classList.remove("background")
                document.body.classList.add("background-add")
                searchResultsContainer.style.display = "block"
                searchAccountsResultsContainer.style.display = "block"
            })

            loading.style.display = "none"
            shiftLeaf(searchPage)

        }else{
            searchAccountsResultsContainer.style.display = "none"
            loading.style.display = "none"
            shiftLeaf(searchPage)
        }

        if(data.error){
            sessionStorage.clear()
            window.location = window.location.href
        }

        if(data.accounts.length == 0){
            var messy = searchBox.value
            var query = messy.trim()
            var noResults = document.getElementById("no-results")
            noResults.innerHTML = "No results for " + query + " found. Give it another go!"
            noResults.style.display = "block"
        }

    }



    function prependMustache(template, container, data){
        return new Promise(function(resolve, reject){
            var html = Mustache.render(template.innerHTML, data)
            var div = document.createElement('div');
            div.innerHTML = html
            container.prepend(div);
            resolve();
        });
    }


    function initializeSearchResults(){
        var profileHrefs = document.getElementsByClassName("profile-href");
        var profileHrefsArr = Array.from(profileHrefs)

        profileHrefsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchProfileClickEvent)
        });

        var inviteButtons = document.getElementsByClassName("friend-invite")
        var inviteButtonsArr = Array.from(inviteButtons)

        inviteButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchFriendRequestEvent)
        })
    }

    function dispatchProfileClickEvent(event){
        var button = event.target
        var id = button.getAttribute("data-id")
        profile.displayProfile(id)
    }

    function dispatchFriendRequestEvent(event){
        var button = event.target
        var id = button.getAttribute("data-id")
        var elementId = button.getAttribute("id")

        var uri = _context + "/friend/invite/" + id
        req.http(uri, "post").then(updateInviteStatus(elementId, id, button)).catch(failedSearch)
    }

    function updateInviteStatus(elementId, id, button){
        return function(request){
            var data = JSON.parse(request.responseText)
            if(data && data.success){
                var html = "<span class='invited'>Invite Sent</span>"
                var actionsTd = document.getElementById("actions-" + id)
                //actionsTd.innerHTML = html
                button.innerHTML = "+ Resend Invite"
                button.classList.remove("retro")
                button.classList.add("beauty-light")
                var newButton = document.getElementById(elementId)
                newButton.addEventListener("click", dispatchFriendRequestEvent)
            }
            overlay.style.display = "none"
        }
    }


    function checkDisplayNavigation(data){
        /**resultsTotal.innerHTML = data.count**/
    }

    function setOffset(offset, step, count){
        offset+= step;
        if(offset > count)offset = 0
    }

    function hideById(id){
        var searchResultsContainer = document.getElementById(id)
        searchResultsContainer.style.display = "none"
    }

    function failedSearch(error){
        sessionStorage.clear()
        //window.location = window.location.href
    }

    function clearSearchBox(){
        searchBox.value = ""
    }

    return {
        searchBox : searchBox,
        transition : transition,
        dispatchSearchAccounts : dispatchSearchAccounts
    }

}
