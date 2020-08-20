
var Posts = function(context){

    var public = new Object(null)
    var _context = context

    var phrases = [
        "And she conceived and bore to Zeus, who delights in the thunderbolt...",
        "Zeus, the father of the Olympic Gods, turned mid-day into night, hiding the light of the dazzling Sun;"
    ]


    var emptyHtml = '<div id="empty-feed"><p class="light" style="padding-right:70px;">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur... <br/><span class="yella">all of this means you need to share something!</span></p></div>'

    var voice = document.getElementById("voice"),
        postsRefreshBtn = document.getElementById("logo-logo"),
        shareButton = document.getElementById("share-button");

    var latestPostsDiv = document.getElementById("latest-posts")
    var latestPosts = document.getElementById("people-posted")

    var refreshPosts = document.getElementById("refresh-posts")
    refreshPosts.addEventListener("click", refreshFeed)

    var uploadContents = document.getElementsByClassName("file-upload-content")
    var uploadContentsArr = Array.from(uploadContents)

    var uploadMessageContainer = document.getElementById("upload-message-container")


    var shareFileButtons = document.getElementsByClassName("share-file-button")
    var shareFileButtonsArr = Array.from(shareFileButtons)

    shareFileButtonsArr.forEach(function(shareFileButton, index){
        shareFileButton.addEventListener("click", dispatchShareFileButtonEvent)
    });

    var notificationsHref = document.getElementById("notifications-href")
    var notificationsCount = document.getElementById("notifications-count")
    var notificationsOuterDiv = document.getElementById("notifications-outer-container")
    var notificationsContainer = document.getElementById("notifications-container")
    var notificationsTemplate = document.getElementById("notifications-template")
    var clearNotifications = document.getElementById("clear-notifications")
    var postsFellowsFemsContainer = document.getElementById("posts-fellows-fems-outer-container")

    var feedContainerDiv = document.getElementById("feed")

    notificationsHref.addEventListener("click", function(event){
        event.preventDefault()
        var opened = notificationsOuterDiv.getAttribute("data-opened")

        if(opened == "true"){
            notificationsOuterDiv.setAttribute("data-opened", "false")
            notificationsOuterDiv.style.display = "none"
        }
        if(!opened || opened == "false"){
            var uri = _context + "/notifications"
            req.http(uri).then(updateNotificationsDiv).catch(error)
        }
    })

    function updateNotificationsDiv(request){
        var data = JSON.parse(request.responseText)
        var count = data.length
        updateNotificationsCount(count)
        if(data.length > 0){
        notificationsContainer.innerHtml = ""
            renderMustache(notificationsTemplate, notificationsContainer, data).then(function(){
                notificationsOuterDiv.setAttribute("data-opened", "true")
                notificationsOuterDiv.style.display = "block"
            });
        }
        else{
            notificationsContainer.innerHTML = '<p style="background:#fff472">No new notifications</p>'
            notificationsOuterDiv.setAttribute("data-opened", "true")
            notificationsOuterDiv.style.display = "block"
        }
    }

    clearNotifications.addEventListener("click", function(event){
        event.preventDefault();
        var uri = _context + "/notifications/clear"
        req.http(uri, "delete").then(resetNotifications).catch(error)
    })

    function resetNotifications(request){
        notificationsCount.innerHTML = 0
        notificationsContainer.innerHTML = '<p style="background:#FBF35A">No new notifications</p>'
    }


    function dispatchShareFileButtonEvent(event){
        event.preventDefault()
        var shareFileButton = event.target
        var type = shareFileButton.getAttribute("data-type")
        var message = shareFileButton.getAttribute("data-message")
        var uploadForm = document.getElementById(type + "-upload-form")
        var uploadContent = document.getElementById(type + "-upload-content")
        uploadContent.value = voice.value

        var uri = _context + "/post/share"
        web.publish(uri, uploadForm).then(validateUploadRefresh(message)).catch(failedUpload(message))
    }


    var fileUploadInputs = document.getElementsByClassName("file-upload-input");
    var fileUploadInputsArr = Array.from(fileUploadInputs)

    fileUploadInputsArr.forEach(function(uploadInput, index){
        uploadInput.removeEventListener("change", function(){
            var type = uploadInput.getAttribute("data-upload")
            var uploadButton = document.getElementById(type + "-upload-button")
            uploadButton.removeEventListener("click", function(event){})
        })
        uploadInput.addEventListener("change", function(event){

            shareFileButtonsArr.forEach(function(shareButton, index){
                shareButton.style.display = "none"
            })

            var type = uploadInput.getAttribute("data-upload")
            var message = ""
            switch(type){
                case "image":
                    console.log("image selected...")
                    message = "What about these images make them special? (Optional)"
                    break;
                case "music":
                    message = "Tell us about the selected tunes.. (Optional)"
                    break
                case "video":
                    message = "We love it, give us some background? (Optional)"
                    break
                default:
                    message = ""
                    break
            }
            voice.setAttribute("placeholder", message)
            var uploadButton = document.getElementById(type + "-upload-button")
            uploadButton.style.display = "block"
            shareButton.style.display = "none"

        });
    })


    function validateUploadRefresh(message){
        return function(request){
            var data = JSON.parse(request.responseText)

            //TODO: make sexy jexi
            if(data.error){
                alert(message)
            }
            else{
                fileUploadInputsArr.forEach(function(uploadInput, index){
                    uploadInput.removeEventListener("change", function(){
                        var type = uploadInput.getAttribute("data-upload")
                        var uploadButton = document.getElementById(type + "-upload-button")
                        uploadButton.removeEventListener("click", function(event){})
                    })
                })
                retrievePosts()
            }
        }
    }


    function failedUpload(message){
        return function(request){
            clearPreviousFormData()
            uploadMessageContainer.innerHTML = message
        }
    }


    function removeDeletePostButtonsHander(){
        var deletePostButtons = document.getElementsByClassName("delete-post")
        var deletePostButtonsArr = Array.from(deletePostButtons)

        deletePostButtonsArr.forEach(function(button, index){
            button.unbind()
        })
    }


    function alertUserRedisplayFeed(request){
        var data = JSON.parse(request.responseText)

        if(data.error){
            alert("You do not have permission to remove this post")
        }

        if(data.success){
            alert("Post has been successfully removed!")
        }

        retrievePosts()
        overlay.style.display = "none"
    }

    function alertUserHideRedisplayFeed(request){
        var data = JSON.parse(request.responseText)

        if(data.error){
            alert("Something went wrong... apologies.")
        }
        retrievePosts()
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

        retrievePosts()
        overlay.style.display = "none"
    }

    function alertUserDeleteCommentRedisplayFeed(request){
        var data = JSON.parse(request.responseText)
        if(data.error){
            alert("You do not have permission to remove this comment")
        }else{
            alert("Comment has been successfully deleted")
        }
        retrievePosts()
    }

    var shareForm = document.getElementById("share-form")
    shareButton.addEventListener("click", propogatePeople)
    postsRefreshBtn.addEventListener("click", refreshFeed);


    function retrievePosts(){
        pageProcessing.style.display = "block";
        uploadMessageContainer.style.display = "none"
        latestPosts.style.display = "none"
        var uri = _context + "/posts";
        req.http(uri).then(renderPosts).catch(error);
    }


    function refreshFeed(event){
        shiftDownLeafs(feedPage)
        document.body.classList.add("love")
        var uri = _context + "/posts";
        req.http(uri).then(renderPosts).catch(error);
    }

    function renderPosts(request){
        search.searchBox.value = ""
        var data = JSON.parse(request.responseText)
        if(data.length > 0){
            var feedTemplate = document.getElementById("posts-template")
            renderMustache(feedTemplate, feedContainerDiv, data).then(function(){
                hideNoPosts()
                hyperlinkHrefContent()
                addColorsFeedContent()
                checkSetPostEventListeners()
                addEventListenersProfiles()
                renderLatest(request)
                postsFellowsFemsContainer.style.display = "block"
                shiftLeaf(feedPage)
            })


        }else{
            feedContainerDiv.innerHTML = emptyHtml
            postsFellowsFemsContainer.style.display = "none"
            adjustMainContainerHeight(feedPage)
            shiftLeaf(feedPage)
        }


        setShareBox()
        clearPreviousFormData()

        sessionStorage.setItem("zeus-current", "posts")
    }

    var processingMessage = document.getElementById("processing-message")

    function hideNoPosts(){
        var emptyFeedInfo = document.getElementById("empty-feed-info")
        if(emptyFeedInfo){
            emptyFeedInfo.style.display = "none";
        }
    }
/**
    function sexijexi(){
        var imgs = document.images,
            len = imgs.length,
            imageCounter = 0;

        [].forEach.call( imgs, function( img ) {
            if(img.complete)
                incrementImageCounter()
            else
              img.addEventListener( 'load', incrementImageCounter, false );
        } );

        function incrementImageCounter() {
            imageCounter++;
            processingMessage.innerHTML = "loading web stuff..."
        }

        var audioCounter = 0
        var audios = document.getElementsByClassName("audio-music")
        var audiosArr = Array.from(audios)
        var audiosLen = audiosArr.length


        if(audiosArr.lenth == 0 && imageCounter === len ){
            processingMessage.innerHTML = "Loading stuff..."
            adjustMainContainerHeight(feedPage)
            shiftDownLeafs(feedPage)
        }else{

            if(audiosArr.length > 0){
                audiosArr.forEach(function(audio, index){
                    audio.onloadeddata = function(){
                        incrementAudioCounter()
                    }
                })

                function incrementAudioCounter() {
                    audioCounter++;;
                    processingMessage.innerHTML = "loading more web stuff..."
                    if ( audioCounter === audiosLen ) {
                         processingMessage.innerHTML = ".."
                        setTimeout(function(){
                            adjustMainContainerHeight(feedPage)
                            shiftDownLeafs(feedPage)
                        }, 1000)
                    }
                }
            }else{
                setTimeout(function(){
                    processingMessage.innerHTML = ".."
                }, 900)
                setTimeout(function(){
                    adjustMainContainerHeight(feedPage)
                    shiftDownLeafs(feedPage)
                }, 1000)
            }
        }
    }
    **/


    function hyperlinkHrefContent(){
        var ps = document.getElementsByClassName("post-comment")
        var pArr = Array.from(ps)

        pArr.forEach(function(p, index){
            var d = {
                input: p.innerHTML,
                options: {
                    attributes: {
                        target: "_blank",
                        class: "href-dotted"
                    },
                }
            }
            const hyperP = anchorme(d)
            p.innerHTML = hyperP
        })
    }


    function renderLatest(request){

        var data = JSON.parse(request.responseText)

        var postsfellowsFemsTemplate = document.getElementById("posts-fellows-fems-template")
        var postsfellowsFemsContainer = document.getElementById("posts-fellows-fems-container")

        var consolidatedData = []
        var lookup = {}

        data.forEach(function(obj, index){
            if(lookup[obj.accountId] != null &&
                    lookup[obj.accountId].count != null){
                lookup[obj.accountId].count++
            }

            if(lookup[obj.accountId] == null){
                lookup[obj.accountId] = obj
                lookup[obj.accountId].count = 1
            }
        })

        for (var key in lookup) {
            if (lookup.hasOwnProperty(key)) {
                consolidatedData.push(lookup[key])
            }
        }

        renderMustache(postsfellowsFemsTemplate, postsfellowsFemsContainer, consolidatedData).then(function(){
            var profileRefs = document.getElementsByClassName("profile-ref")
            var profileRefsArr = Array.from(profileRefs)

            profileRefsArr.forEach(function(ref, index){
                ref.addEventListener("click", function(){
                    var id = ref.getAttribute("data-id")
                    profile.displayProfile(id)
                });
            })
        });
    }


    function addEventListenersProfiles(){
        var profileRefs = document.getElementsByClassName("post-whois")
        var profileRefsArr = Array.from(profileRefs)

        profileRefsArr.forEach(function(ref, index){
            ref.addEventListener("click", function(event){
                var id = ref.getAttribute("data-id")
                profile.displayProfile(id)
            })
        })

        var shareProfileRefs = document.getElementsByClassName("shared-post-whois")
        var shareProfileRefsArr = Array.from(shareProfileRefs)

        shareProfileRefsArr.forEach(function(ref, index){
            ref.addEventListener("click", function(event){
                event.preventDefault()
                var id = ref.getAttribute("data-id")
                profile.displayProfile(id)
            })
        })

        var commentProfileRefs = document.getElementsByClassName("post-comment-whois")
        var commentProfileRefsArr = Array.from(commentProfileRefs)

        commentProfileRefsArr.forEach(function(ref, index){
            ref.addEventListener("click", function(event){
                var id = ref.getAttribute("data-id")
                profile.displayProfile(id)
            })
        })

        var profileRefs = document.getElementsByClassName("posted-by")
        var profileRefsArr = Array.from(profileRefs)

        profileRefsArr.forEach(function(ref, index){
            ref.addEventListener("click", function(event){
                var id = ref.getAttribute("data-id")
                profile.displayProfile(id)
            })
        })
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


    function dispatchSharePostEvent(event){
        event.preventDefault()
        var id = this.getAttribute("data-id")
        var postId = this.getAttribute("data-post-id")
        var shared = this.getAttribute("data-shared")
        var uri = _context + "/post/share_post/" + postId

        var form = document.getElementById("share-post-form-" + id)
        if(shared === "true")form = document.getElementById("share-shared-post-form-" + id)
        web.publish(uri, form).then(retrievePosts).catch(error)
    }


    function dispatchDeleteCommentEvent(event){
        console.log("dispatch delete comment > ")
        var id = this.getAttribute("data-id")
        var uri = _context + "/post/delete_comment/" + id
        req.http(uri, "delete").then(alertUserDeleteCommentRedisplayFeed)
    }

    function dispatchDeleteShareCommentEvent(event){
        var id = this.getAttribute("data-id")
        var uri = _context + "/post_share/delete_comment/" + id
        req.http(uri, "delete").then(alertUserDeleteCommentRedisplayFeed)
    }


    function dispatchCommentPostEvent(event) {
        var target = event.target
        if(event.key === "Enter") event.preventDefault();
        if(target.classList.contains("comment-input") &&
                    event.key === "Enter") {
            var id = this.getAttribute("data-id")
            var uri = _context + "/post/comment/"+ id
            web.publish(uri, this).then(retrievePosts)
        }
    }


    function dispatchCommentSharedPostEvent(event) {
        var target = event.target
        if(event.key === "Enter") event.preventDefault();
        if(target.classList.contains("comment-shared-input") &&
                    event.key === "Enter") {
            var id = this.getAttribute("data-id")
            var uri = _context + "/post_share/comment/"+ id
            web.publish(uri, this).then(retrievePosts)
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

        if(commentContainer.style.display != "block"){
            commentContainer.style.display = "block";
        }
        else{
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
            req.http(uri, "delete").then(alertUserRedisplayFeed)
        }
    }

    function dispatchDeletePostShareEvent(event){
        event.preventDefault()
        var confirmed = confirm("Are you sure you want to delete this shared post?")
        if(confirmed){
            var button = event.target
            var id = button.getAttribute("data-id")
            var uri = _context + "/post/unshare/" + id
            req.http(uri, "delete").then(alertUserRedisplayFeed)
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



    function propogatePeople(event){

        if(voice.value && voice.value.length >= 3){
            var uri = _context + "/post/share";
            web.publish(uri, shareForm).then(retrievePosts).then(clearForm)
        }
    }


    function addColorsFeedContent(){

        var posts = document.getElementsByClassName("feed-content")
        var postsArr = Array.from(posts)

        postsArr.forEach(function(post, index){
            var color = getRandomInt(0, 1000)
            var className = "maroon"

            if(color % 2 == 0 && index != 0){
                className = "maroon"
            }
            if(color % 3 == 0 && index != 0){
                className = "blue"
            }
            if(color % 4 == 0 && index != 0){
                className = "sky"
            }
            if(color % 5 == 0 && index != 0){
                className = "green"
            }
            post.classList.add(className)
        })
    }


    function clearPreviousFormData(){
        voice.value = ""
        uploadMessageContainer.innerHTML = ""
        shareButton.style.display = "block"

        uploadContentsArr.forEach(function(value, index){
            value.value = ""
        });

        shareFileButtonsArr.forEach(function(value, index){
            value.style.display = "none"
        });

        fileUploadInputsArr.forEach(function(value, index){
            value.value = ""
        });
    }


    function clearForm(){
        voice.value = ""
    }


    function setShareBox(){
        voice.setAttribute("placeholder", phrases[getRandomInt(0, phrases.length -1)])
    }

    function transition(){
        shiftDownLeafs(feedPage)
        document.body.classList.add("love")
        var uri = _context + "/posts";
        req.http(uri).then(renderPosts).catch(errorTransition);
    }

    function updateNotificationsCount(count){
        notificationsCount.innerHTML = count
    }

    return {
        transition : transition,
        addColorsFeedContent : addColorsFeedContent,
        checkSetPostEventListeners : checkSetPostEventListeners,
        addEventListenersProfiles : addEventListenersProfiles,
        hyperlinkHrefContent : hyperlinkHrefContent,
        updateNotificationsCount : updateNotificationsCount,
        uploadMessageContainer : uploadMessageContainer,
        notificationsOuterDiv: notificationsOuterDiv,
        feedContainerDiv : feedContainerDiv,
        latestPostsDiv : latestPostsDiv,
        latestPosts : latestPosts,

    }

}