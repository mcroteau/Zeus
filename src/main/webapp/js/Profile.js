
var Profile = function(context){

    var _id
    var _context = context

    var blockButton = document.getElementById("block-profile-btn");
    blockButton.addEventListener("click", dispatchBlockPersonClicked)


    var profileMusicPlayer = new Audio();
    var profileOuterContainer = document.getElementById("profile-page")
    var profileFriendsOuterContainer = document.getElementById("profile-friends-outer-container");


    function profileClicked(event){
        var target = event.target
        var id = target.getAttribute("data-id")
        displayProfile(id)
        _id = id
    }

    function refreshProfile(){
        displayProfile(_id)
    }

    function displayProfile(id){
        shiftDownLeafs(profilePage)

        var uri = _context + "/profile/" + id;
        req.http(uri).then(renderProfile(id)).catch(errorTransition);

        _id = id
    }

    function renderPosts(request){
        var data = JSON.parse(request.responseText)

        if(data.length > 0){
            var postsTemplate = document.getElementById("posts-template")
            var postsContainer = document.getElementById("profile-posts")

            renderMustache(postsTemplate, postsContainer, data).then(function(){
                posts.addColorsFeedContent()
                posts.addEventListenersProfiles()
                posts.hyperlinkHrefContent();
                checkSetPostEventListeners()//TODO:abstractit

               shiftLeaf(profilePage)

            });
        }else{
            shiftLeaf(profilePage)
        }
    }


    function checkSetPostEventListeners(){

        var likeButtons = document.getElementsByClassName("like-button")
        var likeButtonsArr = Array.from(likeButtons)
        likeButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchLikePostEvent)
        })

        var shareButtons = document.getElementsByClassName("share-button")
        var shareButtonsArr = Array.from(shareButtons)
        shareButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchRenderSharePostEvent)
        })

        var shareSharedButtons = document.getElementsByClassName("share-shared-button")
        var shareSharedButtonsArr = Array.from(shareSharedButtons)
        shareSharedButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchRenderShareSharedPostEvent)
        })

        var deletePostButtonsArr = getDeleteButtonsArr()
        deletePostButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchDeletePostEvent)
        })

        var deletePostButtonsArr = getDeletePostShareButtonsArr()
        deletePostButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchDeletePostShareEvent)
        })


        var commentsForms = document.getElementsByClassName("comment-form")
        var commentsFormsArr = Array.from(commentsForms)
        commentsFormsArr.forEach(function(form, index){
            form.addEventListener("keypress", dispatchCommentPostEvent);
        })

        var commentsSharedForms = document.getElementsByClassName("comment-shared-form")
        var commentsSharedFormsArr = Array.from(commentsSharedForms)
        commentsSharedFormsArr.forEach(function(form, index){
            form.addEventListener("keypress", dispatchCommentSharedPostEvent);
        })

        var deleteCommentRefs = document.getElementsByClassName("comment-delete")
        var deleteCommentRefsArr = Array.from(deleteCommentRefs)
        deleteCommentRefsArr.forEach(function(ref, index){
            ref.addEventListener("click", dispatchDeleteCommentEvent)
        })


        var deleteShareCommentRefs = document.getElementsByClassName("comment-delete")
        var deleteShareCommentRefsArr = Array.from(deleteShareCommentRefs)
        deleteShareCommentRefsArr.forEach(function(ref, index){
            ref.addEventListener("click", dispatchDeleteShareCommentEvent)
        })

        var sharePostButtons = document.getElementsByClassName("share-post-button")
        var sharePostButtonsArr = Array.from(sharePostButtons)
        sharePostButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchSharePostEvent)
        })



        var flagPostButtons = document.getElementsByClassName("flag-post")
        var flagPostButtonsArr = Array.from(flagPostButtons)
        flagPostButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchFlagPostEvent)
        })


        var hidePostButtons = document.getElementsByClassName("hide-post")
        var hidePostButtonsArr = Array.from(hidePostButtons)
        hidePostButtonsArr.forEach(function(button, index){
            button.addEventListener("click", dispatchHidePostEvent)
        })


    }


    function dispatchBlockPersonClicked(event){
        var message = "Are you sure you would like to block this person. He/She will not be able to see your profile. Your profile wont be searchable for this person as well."

        var button = event.target;
        var blocked = button.getAttribute("data-blocked")

        if(blocked == "false"){
            if(confirm(message)){
                performBlockProfileReq(button, blocked)
            }
        }else{
            performBlockProfileReq(button, blocked)
        }
    }

    function performBlockProfileReq(button, blocked){
        var id = button.getAttribute("data-id")
        var uri = _context + "/profile/block/" + id
        req.http(uri, "post").then(function(req){
            var data = JSON.parse(req.responseText)

            if(data.error){
                alert(data.error);
            }else{
                if(blocked == "false"){
                    button.setAttribute("data-blocked", true)
                    button.innerHTML = "Blocked!"
                }else{
                    button.setAttribute("data-blocked", false)
                    button.innerHTML = "Block Person"
                }
            }
        })
    }


    function dispatchSharePostEvent(event){
        event.preventDefault()
        var id = this.getAttribute("data-id")
        var postId = this.getAttribute("data-post-id")
        var shared = this.getAttribute("data-shared")
        var uri = _context + "/post/share_post/" + postId

        var form = document.getElementById("share-post-form-" + id)
        if(shared === "true")form = document.getElementById("share-shared-post-form-" + id)
        web.publish(uri, form).then(posts.transition).catch(error)
    }


    function dispatchDeleteCommentEvent(event){
        var id = this.getAttribute("data-id")
        var uri = _context + "/post/delete_comment/" + id
        req.http(uri, "delete").then(alertUserDeleteCommentRedisplayProfile)
    }

    function dispatchDeleteShareCommentEvent(event){
        var id = this.getAttribute("data-id")
        var uri = _context + "/post_share/delete_comment/" + id
        req.http(uri, "delete").then(alertUserDeleteCommentRedisplayProfile)
    }


    function dispatchCommentPostEvent(event) {
        var target = event.target
        if(event.key === "Enter") event.preventDefault();
        if(target.classList.contains("comment-input") &&
                    event.key === "Enter") {
            var id = this.getAttribute("data-id")
            var uri = _context + "/post/comment/"+ id
            web.publish(uri, this).then(refreshProfile)
        }
    }


    function dispatchCommentSharedPostEvent(event) {
        var target = event.target
        if(event.key === "Enter") event.preventDefault();
        if(target.classList.contains("comment-shared-input") &&
                    event.key === "Enter") {
            var id = this.getAttribute("data-id")
            var uri = _context + "/post_share/comment/"+ id
            web.publish(uri, this).then(refreshProfile)
        }
    }


    function dispatchLikePostEvent(event){
        var button = event.target
        var id = button.getAttribute("data-id")
        var uri = _context + "/post/like/"+ id;

        req.http(uri, "post").then(function(request){
            var data = JSON.parse(request.responseText)
            if(data.success){
                var likeButtons = document.getElementsByClassName("like-" + data.id)
                var likeButtonsArr = Array.from(likeButtons)
                likeButtonsArr.forEach(function(button, index){
                    if(data.action === "added"){
                        button.classList.add("liked")
                    }else{
                        button.classList.remove("liked")
                    }
                })

                var likeContainers = document.getElementsByClassName("like-container-" + data.id)
                var likeContainersArr = Array.from(likeContainers)

                likeContainersArr.forEach(function(span, index){
                    span.innerHTML = data.likes
                })
                overlay.style.display = "none"
            }
        })
    }

    function dispatchRenderSharePostEvent(event){
        var button = event.target
        var id = button.getAttribute("data-id")
        var commentContainer = document.getElementById("share-comment-" + id)
        console.log(id, commentContainer, "'", commentContainer.style.display, "'")
        if(commentContainer.style.display == "none"){
            console.log("display none")
            commentContainer.style.display = "block";
        }
        else{
            console.log("display block")
            commentContainer.style.display = "none";
        }
    }

    function dispatchRenderShareSharedPostEvent(event){
        var button = event.target
        var id = button.getAttribute("data-id")
        var postId = button.getAttribute("data-post-id")

        var commentContainer = document.getElementById("share-shared-comment-" + id)

        if(commentContainer.style.display != "block"){
            commentContainer.style.display = "block";
        }
        else{
            commentContainer.style.display = "none";
        }
    }

    function dispatchDeletePostEvent(event){
        event.preventDefault()
        var confirmed = confirm("Are you sure you want to delete this post?")
        if(confirmed){
            var button = event.target
            var id = button.getAttribute("data-id")
            var uri = _context + "/post/remove/" + id
            console.log("delete post", uri)
            req.http(uri, "delete").then(alertUserRefreshProfile)
        }
    }

    function dispatchDeletePostShareEvent(event){
        event.preventDefault()
        var confirmed = confirm("Are you sure you want to delete this shared post?")
        if(confirmed){
            var button = event.target
            var id = button.getAttribute("data-id")
            var uri = _context + "/post/unshare/" + id
            req.http(uri, "delete").then(alertUserRefreshProfile)
        }
    }



   function dispatchFlagPostEvent(event){
        event.preventDefault()
        var confirmed = confirm("Are you sure you want to flag this post as inappropriate?")
        if(confirmed){
            var button = event.target
            var id = button.getAttribute("data-id")
            var shared = button.getAttribute("data-shared")
            var uri = _context + "/post/flag/" + id + "/" + shared
            req.http(uri, "post").then(alertUserFlagRedisplayFeed)
        }
    }

   function dispatchHidePostEvent(event){
        event.preventDefault()
        var confirmed = confirm("Are you sure you want to hide this post, once done, cannot be undone.")
        if(confirmed){
            var button = event.target
            var id = button.getAttribute("data-id")
            var shared = button.getAttribute("data-shared")
            var uri = _context + "/post/hide/" + id
            req.http(uri, "post").then(alertUserHideRedisplayFeed)
        }
   }

   function alertUserHideRedisplayFeed(request){
        var data = JSON.parse(request.responseText)

        if(data.error){
            alert("Something went wrong... apologies.")
        }
        refreshProfile()
        overlay.style.display = "none"
   }

   function alertUserFlagRedisplayFeed(request){
        var data = JSON.parse(request.responseText)

        if(data.error){
            alert("You must be logged in... apologies.")
        }

        if(data.success){
            alert("Post has been successfully flagged!")
        }

        refreshProfile()
        overlay.style.display = "none"
   }

    function getDeleteButtonsArr(){
        var deletePostButtons = document.getElementsByClassName("delete-post")
        var deletePostButtonsArr = Array.from(deletePostButtons)
        return deletePostButtonsArr;
    }

    function getDeletePostShareButtonsArr(){
        var deletePostButtons = document.getElementsByClassName("delete-post-share")
        var deletePostButtonsArr = Array.from(deletePostButtons)
        return deletePostButtonsArr;
    }

    function getLikeButtonsArr(){
        var likeButtons = document.getElementsByClassName("like-button")
        var likeButtonsArr = Array.from(likeButtons)
        return likeButtonsArr;
    }


    function getShareButtonsArr(){
        var shareButtons = document.getElementsByClassName("share-button")
        var shareButtonsArr = Array.from(shareButtons)
        return shareButtonsArr;
    }

    function alertUserDeleteCommentRedisplayProfile(){
        alert("Successfully deleted comment..")
        refreshProfile()
        overlay.style.display = "none"
    }


    function alertUserRefreshProfile(){
        alert("Successfully deleted Post.")
        refreshProfile()
        overlay.style.display = "none"
    }


    function renderProfile(id){
        return function(request){

            var data = JSON.parse(request.responseText);
            var profileTemplate = document.getElementById("profile-template")
            var profileContainer = document.getElementById("profile-container")


            renderMustache(profileTemplate, profileContainer, data.profile).then(function(){
                sessionStorage.setItem("zeus-current", "profile")
                sessionStorage.setItem("zeus-params", JSON.stringify([id]))

                var nameH1 = document.getElementById("profile-profile-name")
                var nameFormatted = ""

                data.profile.name.split(" ").forEach(function(name, index){
                    nameFormatted+= name + "<br/>"
                });
                nameH1.innerHTML = nameFormatted
                setBlockButtonAttrs(data)
                addEventListeners(data)
                renderFriends(data)
                setupGraph(data)
            });
        }
    }


    function setBlockButtonAttrs(data){

        blockButton.setAttribute("data-id", data.profile.id);
        if(data.profile.blocked){
            blockButton.setAttribute("data-blocked", true)
            blockButton.innerHTML = "Blocked!"
        }else{
            blockButton.setAttribute("data-blocked", false)
            blockButton.innerHTML = "Block"
        }
    }


    function setupGraph(data){
        if(data.profile.isOwnersAccount){
            req.http(_context + "/profile/data/views").then(function(request){
                var data = JSON.parse(request.responseText);

                var options = {
                      series: [{
                      name: 'Views',
                      data: data.counts
                    }],
                      chart: {
                      height: 350,
                      type: 'bar',
                    },
                    fill: {
    //                  colors: ['#FDFE00']
                        colors: ['#5AB2FF']
    //                    colors: ['#c62f69']
    //                    colors: ['#3b90db']
                    },
                    plotOptions: {
                      bar: {
                        dataLabels: {
                          position: 'top', // top, center, bottom
                        },
                      }
                    },
                    dataLabels: {
                      enabled: true,
                      formatter: function (val) {
                        return val;
                      },
                      offsetY: -20,
                      style: {
                        fontSize: '10px',
                        colors: ["#304758"]
                      }
                    },

                    xaxis: {
                      categories: data.labels,
                      position: 'none',
                      axisBorder: {
                        show: false
                      },
                      axisTicks: {
                        show: false
                      },
                      crosshairs: {
                        fill: {
                          type: 'gradient',
                          gradient: {
                            colorFrom: '#efefef',
                            colorTo: '#efefef',
                            stops: [0, 100],
                            opacityFrom: 0.4,
                            opacityTo: 0.5,
                          }
                        }
                      },
                      tooltip: {
                        enabled: false,
                      }
                    },
                    yaxis: {
                      axisBorder: {
                        show: false
                      },
                      axisTicks: {
                        show: false,
                      },
                      labels: {
                        show: true,
                        formatter: function (val) {
                          return val;
                        }
                      }

                    },
                    title: {
                      text: '# of Monthly Views',
                      floating: true,
                      offsetY: 330,
                      align: 'center',
                      style: {
                        color: '#444'
                      }
                    },
                    grid: {
                      row: {
                        colors: ['#fff', '#f8f8f8', '#fff']
                      },
                      column: {
                        colors: ['#fff', '#f8f8f8', '#fff']
                      }
                    }
                };

                var chart = new ApexCharts(document.querySelector("#chart"), options);
                chart.render();

                var uno = document.getElementById("profile-stat-tres")
                var dos = document.getElementById("profile-stat-dos")
                var tres = document.getElementById("profile-stat-uno")

                uno.innerHTML = data.week
                dos.innerHTML = data.month
                tres.innerHTML = data.all
            });
        }
    }


    function addEventListeners(data){


        if(!data.profile.isOwnersAccount){

            var friendButton = document.getElementById("profile-friend-button")
            friendButton.addEventListener("click", function(){
                var action = friendButton.getAttribute("data-action")
                var id = friendButton.getAttribute("data-id")
                var uri = _context + "/friend/" + action + "/" + id;
                req.http(uri, "post").then(updateButton(friendButton, action)).catch(error)
            })

            var profileLikeButton = document.getElementById("profile-like-button")
            profileLikeButton.addEventListener("click", function(){

                var confirmationText = "They are going to love you for this. Like data is private."
                var liked = profileLikeButton.getAttribute("liked")

                var numLikesSpan = document.getElementById("num-likes")
                var lighteningBolt = document.getElementById("love-it")
                var id = profileLikeButton.getAttribute("data-id")
                var uri = _context + "/profile/like/" + id;

                if(liked != "true"){
                    req.http(uri, "post").then(function(request){

                        var data = JSON.parse(request.responseText)
                        if(numLikesSpan != null)
                            numLikesSpan.innerHTML = data.likes

                        profileLikeButton.innerHTML = "Liked!"
                        profileLikeButton.setAttribute("liked", true)
                        profileLikeButton.classList.replace("yella", "sky")
                        lighteningBolt.style.display = "inline-block"
                    }).catch(error)
                }else{
                    req.http(uri, "post").then(function(request){

                        var data = JSON.parse(request.responseText)
                        if(numLikesSpan != null)
                            numLikesSpan.innerHTML = data.likes

                        profileLikeButton.innerHTML = 'Like '+
                                                '<svg class="zeus-like">' +
                                                    '<use xlink:href="#zeus" />' +
                                                '</svg>'
                        profileLikeButton.setAttribute("liked", false)
                        profileLikeButton.classList.replace("sky", "yella")
                        lighteningBolt.style.display = "none";

                    }).catch(error)

                }
            })

        }
    }


    function updateButton(button, action){
        return function(request){
            if(action == "invite"){
                button.setAttribute("data-action", "remove")
                button.classList.replace("retro", "sky")
                button.innerHTML = "Unfriend"
            }else{
                button.setAttribute("data-action", "invite")
                button.classList.replace("sky", "retro")
                button.innerHTML = "Send Friend Request"
            }
            overlay.style.display = "none";
        }
    }


    function renderFriends(data){
        console.log(data.friends)

        var profileFriendsContainer = document.getElementById("profile-friends-container")

        if(data.friends.length > 0){

            var profileFriendsTemplate = document.getElementById("profile-friends-template")
            var profileFriendsContainer = document.getElementById("profile-friends-container")

            renderMustache(profileFriendsTemplate, profileFriendsContainer, data.friends).then(function(){
                var profileRefs = document.getElementsByClassName("profile-friend-ref")
                var profileRefsArr = Array.from(profileRefs)

                profileRefsArr.forEach(function(ref, index){
                    ref.addEventListener("click", function(event){
                        var id = ref.getAttribute("data-id")
                        displayProfile(id)
                    });
                })

                profileFriendsOuterContainer.style.display = "block";
                retrieveProfilePosts()
            });

        }else{
            profileFriendsOuterContainer.style.display = "none";
            retrieveProfilePosts()
        }
    }


    function retrieveProfilePosts(){
        var postsUri = _context + "/posts/" + _id;
        req.http(postsUri).then(renderPosts).catch(error)
    }

    return {
        displayProfile : displayProfile
    }

}