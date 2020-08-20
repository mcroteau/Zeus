<style type="text/css">
#invites-results-table{
    margin-bottom:71px;
}

#invites-results-table{
    width:100% !important;
}

#invites-results-table td,
#invites-results-table td a{
    font-size:23px !important;
    font-family:Roboto-Light !important;
}

#invites-results-table td{
    padding:5px 0px;
}

#invites-results-table td a{
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


<div id="friend-invites-container" style="display:none">

    <h1 class="no-border" style="margin-bottom:40px;">Friend Invites <span id="results-total" class="float-right"></span></h1>

    <br class="clear"/>

    <table class="table" id="invites-results-table">
        <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Age</th>
                <th>City</th>
                <th></th>
            </tr>
        </thead>
        <tbody id="friend-invite-rows-container"></tbody>
    </table>

</div>

<p id="no-friend-invites" style="display:none; margin-top:40px;" class="notify">No friend pending friend invites... have no fear, invites are on their way</p>

<script type="text/template" id="friend-invites-row">
    {{#.}}
        {{^isOwnersAccount}}
            <tr id="friend-invite-row-{{inviteeId}}">
                <td>{{inviteeId}}</td>
                <!--<td>{{invitedId}}</td>-->
                <td>
                    <a href="javascript:" class="friend-invite-profile-href href-dotted" data-id="{{invitedId}}">{{name}}</a>
                </td>
                <td>{{age}}</td>
                <td>{{location}}</td>
                <td class="align-right" id="invite-actions-{{inviteeId}}">
                    <button class="button sky ignore-button small" data-id="{{inviteeId}}">Ignore</button>
                    <button class="button retro accept-button small" data-id="{{inviteeId}}">+ Accept</button>
                </td>
            </tr>
        {{/isOwnersAccount}}
    {{/.}}
</script>
