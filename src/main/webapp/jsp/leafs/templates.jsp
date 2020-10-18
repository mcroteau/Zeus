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
                        <a href="javascript:" class="share-shared-button" data-id="{{postShareId}}" data-post-id="{{id}}">&Delta;&deg;</a>
                        {{/shared}}
                        {{^shared}}
                        <span class="actions-count" id="shares-{{id}}">{{shares}} <span class="actions-label">Shares</span></span>
                        <a href="javascript:" class="share-button" data-id="{{id}}">&Delta;&deg;</a>
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