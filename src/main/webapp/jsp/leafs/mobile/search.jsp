<style type="text/css">
#accounts-results-table{
    margin-bottom:71px;
}

#music-results-table,
#accounts-results-table{
    width:100% !important;
}

#music-results-table td,
#music-results-table td a,
#accounts-results-table td,
#accounts-results-table td a{
    font-size:23px !important;
    font-family:Roboto-Light !important;
}
#music-results-table td,
#accounts-results-table td{
    padding:10px 0px;
    vertical-align:middle;
}
#accounts-results-table img{
    border-radius:32px;
    width:65px;
}
#search-results-table td a{
    text-decoration:none;
    color:#12ADFD;
}

h1.gray span{
    color:inherit;
}
.invite-sent-span{
    font-size:13px;
}
</style>


<p id="loading" class="information">People...</p>

<div id="search-results-container" style="display:none">

    <div id="search-accounts-results-container">

        <h1 class="no-border" style="margin-bottom:40px;">Results</h1>

        <table id="accounts-results-table">
            <thead class="render-desktop">
                <tr>
                    <th></th>
                    <th>Name</th>
                    <th>Location</th>
                    <th></th>
                </tr>
            </thead>
            <tbody id="accounts-search-results-container"></tbody>
        </table>
    </div>

</div>

<p id="no-results" style="display:none;margin-top:71px;">No results found...</p>


<script type="text/template" id="account-results-template">
    {{#.}}
        {{^hidden}}
            <tr>
                <td><a href="javascript:" class="profile-href" data-id="{{id}}"><img src="${pageContext.request.contextPath}/{{imageUri}}" class="profile-href search-profile-image" data-id="{{id}}"/></a></td>
                <td>
                    <a href="javascript:" class="profile-href href-dotted" data-id="{{id}}">{{name}}
                        {{^isFriend}}
                            {{#invited}}
                                <span class="invite-sent-span">(Invite sent)</span>
                            {{/invited}}
                        {{/isFriend}}
                    </a>
                </td>
                <td class="render-desktop">{{location}}</td>
                <td class="align-right" id="actions-{{id}}">
                {{^isOwnersAccount}}
                    {{#isFriend}}
                        <span class="is-friend information">Connected</span>
                    {{/isFriend}}
                    {{^isFriend}}
                        {{#invited}}
                            <button class="button beauty-light small friend-invite" data-id="{{id}}" id="friend-request-{{id}}">+ Resend Invite</button>
                        {{/invited}}
                        {{^invited}}
                            <button class="button retro small friend-invite" data-id="{{id}}" id="friend-request-{{id}}">+ Friend</button>
                        {{/invited}}
                    {{/isFriend}}
                {{/isOwnersAccount}}
            </tr>
        {{/hidden}}
    {{/.}}
</script>


<script type="text/template" id="music-search-results-template">
{{#.}}
    <tr>
        <td><a href="javascript:" data-src="{{uri}}" data-playing="false" class="play-searched-music-button button retro music-player-action" style="text-decoration:none;">&triangleright;</a></td>
        <td>{{title}}</td>
        <td>{{artist}}</td>
        <td>{{duration}}</td>
        <td class="align-right">
            <a href="javascript:" class="button sky button-poquito add-music-button" data-id="{{id}}">+</a>
        </td>
    </tr>
{{/.}}
</script>

