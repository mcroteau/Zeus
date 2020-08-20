var ResourceRequest = function(){

    var http = function(uri, method){

        var request = new XMLHttpRequest();
        request.onprogress = updateRequestProgress;

        return new Promise(function(resolve, reject){
            request.onreadystatechange = function () {

                if (request.readyState !== 4) return;
                if (request.status >= 200 && request.status < 300) {
                    resolve(request);
                } else {
                    reject({
                        status: request.status,
                        statusText: request.statusText
                    });
                }
            };
            request.open(method ? method : 'get', uri, true);
            request.send();
        });
    }

    var updateRequestProgress = function(arg){
    }

    return {
        http: http
    };
}

var popup = {}

var actionsWrapper = document.getElementById("zeus-actions-wrapper")
var likesSpan = document.getElementById("likes-span")
var sharesSpan = document.getElementById("shares-span")

addActionStyles()

var req = new ResourceRequest()
var uri = actionsWrapper.getAttribute("data-uri")

var likesUri = "https://www.zeus.social/b/resource/likes?uri=" + encodeURIComponent(uri);
var sharesUri = "https://www.zeus.social/b/resource/shares?uri=" + encodeURIComponent(uri);

var likesUriDev = "/b/resource/likes?uri=" + encodeURIComponent(uri);
var sharesUriDev = "/b/resource/shares?uri=" + encodeURIComponent(uri);

req.http(likesUri).then(updateLikes).catch(error)
req.http(sharesUri).then(updateShares).catch(error)

var launcher = document.getElementById("launch-zeus")

launcher.addEventListener("click", function(){

    var uri = actionsWrapper.getAttribute("data-uri")

    var src = "https://www.zeus.social/b/resource?uri=" + encodeURIComponent(uri)
    var srcDev = "http://localhost:8080/b/resource?uri=" + encodeURIComponent(uri)

    var height = 575;
    var width = 437
    var top = (screen.height - height) / 4;
    var left = (screen.width - width) / 2;

    popup = window.open("", "ZeusResourceAction", 'top=100 left=' + left + ' width=437 height=575')
    popup.document.write('<iframe width="' + width + '" height="' + height + '"  allowTransparency="true" frameborder="0" scrolling="yes" style="width:100%;" src="'+ src +'" type="text/javascript"></iframe>');

});

function updateLikes(request){
    var data = JSON.parse(request.responseText)
    console.log(data)
    likesSpan.innerHTML = data.likes
}

function updateShares(request){
    var data = JSON.parse(request.responseText)
    sharesSpan.innerHTML = data.shares
}

function addActionStyles(){

    var css =  '#launch-zeus:hover{ color: #617078 !important }' +
               '#zeus-actions-wrapper{ text-align:left; display:inline-block; line-height:1.0em; width:140px; height:57px; }' +
               '#zeus-inner-wrapper{ position:relative;width:140px; height:57px; }' + 
               '#launch-zeus{ color:#2cafed;font-size:39px;font-weight:bold;text-decoration:none; }' + 
               '#zeus-likes{ position:absolute; left:49px; top:0px; font-size:12px; font-weight:normal }' +
               '#zeus-shares{ position:absolute; left:49px; top:20px; font-size:12px; font-weight:normal  }' +
               '#likes-span { font-size:12px; font-weight:normal;}' +
               '#shares-span { font-size:12px; font-weight:normal }' +
               '#zeus { font-size:11px; margin-left:8px; display:block;clear:both; font-weight:normal;}';

    var style = document.createElement('style');

    if (style.styleSheet) {
        style.styleSheet.cssText = css;
    } else {
        style.appendChild(document.createTextNode(css));
    }

    document.getElementsByTagName('head')[0].appendChild(style);
}

var timer = setInterval(function () {
    if (popup.closed) {
        clearInterval(timer);
        window.location.reload();
    }
}, 1000);

function error(){
    console.log(error)
}







