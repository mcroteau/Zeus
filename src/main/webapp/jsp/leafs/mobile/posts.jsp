<style>

    .file-upload-container{
        float:right;
    }

</style>


<div id="notifications-outer-container" class="main-shadow" style="display:none">

    <a href="javascript:" id="clear-notifications" class="right-float" style="margin-bottom:10px; margin-right:10px;" data-id="${sessionScope.account.id}">Clear</a>
    <br class="clear"/>

    <div id="notifications-container">
        <p>No new notifications</p>
    </div>
</div>


<div id="posts-express-it-container">

    <div id="post-right" style="position:relative">

        <a href="javascript:" class="navigation-href float-right" id="notifications-href" style="margin-top:5px;margin-bottom:9px;">N<span id="notifications-count">0</span></a>

        <span id="express-it-label" class="information left-float">Write something...</span>

        <br class="clear"/>

        <form modelAttribute="post" method="post" id="share-form" enctype="multipart/form-data">
            <textarea placeholder="And lightening struck..." id="voice" name="content"></textarea>
        </form>

        <br class="clear"/>

        <div id="actions-container" class="float-right">

            <div id="image-upload-post-container" class="file-upload-container">
                <form id="image-upload-form">
                    <a href="javascript:" class="button sky small upload-button" id="image-button">Images</a>
                    <input type="hidden" name="content" id="image-upload-content" class="file-upload-content"/>
                    <input type="file" name="imageFiles" id="image-file-select-input" class="file-upload-input" data-upload="image" multiple="multiple" data-message="Image must be Gif, PNG, or JPG"/>
                </form>
            </div>

            <!--
            <div id="music-upload-post-container" class="file-upload-container">
                <form id="music-upload-form">
                    <a href="javascript:" class="button sky small upload-button" id="music-button">Music</a>
                    <input type="hidden" name="content" id="music-upload-content" class="file-upload-content"/>
                    <input type="file" name="musicFiles" id="music-file-select-input" class="file-upload-input" data-upload="music" multiple="multiple" data-message="Music must be MP3"/>
                </form>
            </div>
            -->

            <div id="video-upload-post-container" class="file-upload-container">
                <form id="video-upload-form">
                    <a href="javascript:" class="button light small upload-button" id="video-button">Video</a>
                    <input type="hidden" name="content" id="video-upload-content" class="file-upload-content"/>
                    <input type="file" name="videoFile" id="video-file-select-input" class="file-upload-input" data-upload="video" data-message="Video must be MP4"/>
                </form>
            </div>

        </div>


        <span id="upload-message-container" style="z-index:9;padding:10px;background:#fff;position:absolute; bottom:-9px; right:195px; display:none"></span>

        <input type="button" class="post-button" id="share-button" value="Share!"/>
        <input type="button" class="post-button share-file-button" id="image-upload-button" value="Share Image(s)" style="display:none" data-type="image" data-message="Image must be Gif, PNG, or JPG"/>
        <input type="button" class="post-button share-file-button" id="music-upload-button" value="Share Music" style="display:none" data-type="music" data-message="Music must be MP3"/>
        <input type="button" class="post-button share-file-button" id="video-upload-button" value="Share Video" style="display:none" data-type="video" data-message="Video must be MP4"/>

    </div>

    <br class="clear" id="cleared"/>

</div>

<div id="news-feed" style="height:auto;">

    <h2 style="margin-left:2%;font-family:Roboto !important; margin-bottom:4px">Latest Posts</h2>

	<div id="people-posted" class="information" style="display:none;font-size:13px;"><span id="latest-posts" style="color:#000 !important;"></span> <a href="javascript:" id="refresh-posts">refresh &#8635;</a>.</div>
	<div id="new">Refresh to see new posts...</div>

	<div id="feed"></div>

    <br class="clear"/>
</div>


<br class="clear"/>

<div id="posts-fellows-fems-outer-container" style="margin-top:26px;display:none;float:right;width:inherit;text-align:left;">
    <h2 style="margin-left:2%;font-family:Roboto !important; margin-bottom:4px">Who's posted:</h2>

    <div id="posts-fellows-fems-container"></div>
</div>

<br class="clear"/>
<div class="clear" style="margin-bottom:130px;"></div>



<script type="text/html" id="notifications-template">
{{#.}}
    <div class="notification">
        <a href="#post-{{postId}}">{{name}}
            {{#liked}}
                liked your post.
            {{/liked}}
            {{#shared}}
                shared your post.
            {{/shared}}
            {{#commented}}
                commented on your post.
            {{/commented}}
        </a>
    </div>
{{/.}}
</script>



<script type="text/template" id="posts-fellows-fems-template">
{{#.}}
    <span class="profile-post-container" data-title="{{count}} New Posts">
        <a href="javascript:" data-id="{{accountId}}" class="profile-ref"><img src="${pageContext.request.contextPath}/{{imageUri}}" style="width:46px; height:46px; margin:5px;" class="profile-image-posted main-shadow" alt="{{name}}" title="{{name}}"/></a>
        <span class="activity-status {{status}}"></span>
        <span class="tooltiptext">{{name}}<br/> {{count}} New Posts</span>
    </span>
{{/.}}
</script>
