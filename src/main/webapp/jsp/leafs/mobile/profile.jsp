<style type="text/css">
#profile-outer-container{
    margin-top:2px;
}
#profile-profile-details{
    float:left;
    height:190px;
    margin-left:30px;
    border:solid 0px #ddd;
}
#profile-profile-image{
    height:250px;
    width:250px;
    float:left;
}
#profile-profile-name{
    word-wrap:break-word;
    font-size:62px;
    width:fit-content;
    margin-top:0px;
}
#profile-playlist-rows-container{
    padding:10px 0px;
}
#profile-playlist th{
    padding:0px 0px 0px 20px;
}
#profile-playlist-rows-container tr{
    height:31px;
}
#profile-playlist-rows-container td{
    height:31px;
    font-size:19px;
    font-family:Roboto-Thin !important;
    padding:0px 0px 0px 20px;
}

#profile-actions-container{
    position:absolute;
    top:30px;
    right:0px;
}

#profile-left-container{
    width:417px;
    float:left;
    margin-top:30px;
}

.profile-friend-container{
    width:95px;
    margin:10px 20px 10px 0px;
    float:left;
}

.profile-friend-container img{
    height:75px;
    width:75px;
    padding:5px;
    background:#353535;
}
.profile-friend-container a{
    text-decoration:none;
}
.profile-friend-name{
    font-size:11px;
}
#profile-posts-container{
    float:right;
    width:461px;
}
#profile-profile-image{
    padding: 5px;
    background: #C0CDD0;
    border: solid 1px #C0CDD0;
}
h2.yella{
    width:fit-content !important;
}
#profile-stats-container h2{
    font-size:32px;
    text-align:center;
    font-family:Roboto-Bold !important;
}
#profile-stats-container{
    margin:30px 0px 70px 0px;
}
.profile-stat{
    width:33%;
    display:inline-block;
    float:left;
    margin-bottom:40px;
}
.profile-stat .information{
    display:block;
    text-align:center;
}
.profile-like{
    margin-right:7px;
    display:inline-block;
}

.zeus-like{
    height:13px;
    width:13px;
    fill:#000;
}
</style>

<div id="profile-outer-container">

    <div id="profile-container"></div>

    <div id="profile-posts-container">
        <div id="profile-posts"></div>
    </div>

    <div id="profile-left-container">

        <div id="profile-friends-outer-container" style="display:none">
            <h2 class="gray no-border yella">Friends</h2>
            <div id="profile-friends-container"></div>
            <br class="clear"/>
        </div>

    </div>

</div>

<br class="clear"/>

<a href="javascript:" id="block-profile-btn" class="button sky small" data-id="" data-blocked="false">Block Person</a>
<p class="information">Blocking an individual means <br/>
he/she will not be able to see your profile and you will <br/>
not show up in search results for them</p>

<script type="text/template" id="profile-template">
    {{^isOwnersAccount}}
    <div id="profile-actions-container">

        {{#isFriend}}
            <a href="javascript:" class="button sky small" data-id="{{id}}" data-action="remove" id="profile-friend-button">Unfriend</a>
        {{/isFriend}}
        {{^isFriend}}
            <a href="javascript:" class="button retro small" data-id="{{id}}" data-action="invite" id="profile-friend-button">Send Friend Request</a>
        {{/isFriend}}

        <br class="clear"/>
    </div>
    {{/isOwnersAccount}}

    <br class="clear"/>

    <img src="${pageContext.request.contextPath}/{{imageUri}}" id="profile-profile-image"/>
    <div id="profile-profile-details">
        <h1 id="profile-profile-name">{{name}}</h1>
        <h3 class="light" style="width:fit-content;">{{age}} {{location}}</h3>

        {{#isOwnersAccount}}
            <span class="tiny" style="display:block;margin:20px auto 10px auto;text-align:center;">Only you can see # of likes and profile views data</span>
        {{/isOwnersAccount}}

        {{^isOwnersAccount}}
            {{^liked}}
                <a href="javascript:" class="button yella small profile-like" id="profile-like-button" data-id="{{id}}" data-liked="false">
                    Like &nbsp;
                    <svg class="zeus-like">
                        <use xlink:href="#zeus" />
                    </svg>
                </a>
                <span id="love-it" style="display:none">
                    <svg class="zeus-like">
                        <use xlink:href="#zeus" />
                    </svg>
                    <span>Love you!</span>
                </span>
            {{/liked}}
            {{#liked}}
                <a href="javascript:" class="button sky small profile-like" id="profile-like-button" data-id="{{id}}" data-liked="true">
                    Liked!
                </a>
                <span id="love-it">
                    <svg class="zeus-like">
                        <use xlink:href="#zeus" />
                    </svg>
                    <span>Love you!</span>
                </span>
            {{/liked}}
        {{/isOwnersAccount}}

        {{#isOwnersAccount}}
            <span style="font-size:21px; display:inline-block;"><span style="font-size:21px;" id="num-likes">{{likes}}</span> Likes</span>
        {{/isOwnersAccount}}
        <br class="clear"/>
    </div>

    {{#isOwnersAccount}}
        <a href="${pageContext.request.contextPath}/account/edit/{{id}}" class="href-dotted right-float">Edit</a>
    {{/isOwnersAccount}}

    <br class="clear"/>


    {{#isOwnersAccount}}

        <div id="profile-stats-container" style="width:100%">

            <br class="clear"/>

            <div class="profile-stat">
                <h2 id="profile-stat-uno"></h2>
                <span class="information">All Time</span>
                <span class="information">Visits</span>
            </div>

            <div class="profile-stat">
                <h2 id="profile-stat-dos"></h2>
                <span class="information">Previous Month</span>
                <span class="information">Visits</span>
            </div>

            <div class="profile-stat">
                <h2 id="profile-stat-tres"></h2>
                <span class="information">Previous Week</span>
                <span class="information">Visits</span>
            </div>

            <br class="clear"/>

            <div id="chart" width="890" height="400"></div>

            <a href="http://apexcharts.com" class="tiny" style="color:#212121; text-decoration:none;" target="_blank">Apex.</a>
        </div>

    {{/isOwnersAccount}}


</script>



<script type="text/template" id="profile-friends-template">
{{#.}}
    <a href="javascript:" class="profile-friend-ref" data-id="{{friendId}}">
        <div class="profile-friend-container">
            <img src="${pageContext.request.contextPath}/{{imageUri}}"/>
            <span class="profile-friend-name">{{name}}</span>
        </div>
    </a>
{{/.}}
</script>