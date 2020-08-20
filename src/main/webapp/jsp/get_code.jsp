<div style="padding:20px;">

    <style>
        textarea{
            border:solid 1px #c7c7c7;
        }
        #copy-message{
            display:none;
        }
    </style>

    <a href="/b" class="href-dotted">Home</a>

    <h1>Get Code</h1>

    <span class="information" style="position:absolute; top:100; right:359px;">Generates</span>

    <div id="zeus-actions-wrapper" data-uri="{{NOT READY, ENTER URL}}" style="position:absolute; top:100px;right:200px;">
        <div id="zeus-inner-wrapper">
            <a href="javascript:" id="launch-zeus"><img src="https://zeus.social/b/images/zeus-launcher.png"/></a>
            <span id="zeus-likes"><span id="likes-span"></span> likes</span>
            <span id="zeus-shares"><span id="shares-span"></span> shares</span>
            <span id="zeus">Zeus</span>
        </div>
    </div>
    <script type="text/javascript" src="https://www.zeus.social/b/js/Resource.js"></script>


    <div style="float:left;margin-bottom:20px;">
        <span>Enter Web Address</span><br/>
        <input type="text" name="web" id="web-address" value="" placeholder="http://sap.com" style="width:500px">
        <a href="javascript:" class="button retro" style="margin-bottom:5px;display:block;text-align:center;" id="generate-code">Generate Code</a>
    </div>
    <br class="clear"/>

    <p class="yella" id="copy-message">Copy &amp; paste code below to your website</p>

    <textarea style="width:100%;height:349px;" id="code-wrapper" disabled="disabled">
<div id="zeus-actions-wrapper" data-uri="{{uri}}">
    <div id="zeus-inner-wrapper">
        <a href="javascript:" id="launch-zeus"><img src="http://www.zeus.social/b/images/zeus-launcher.png"/></a>
        <span id="zeus-likes"><span id="likes-span"></span> likes</span>
        <span id="zeus-shares"><span id="shares-span"></span> shares</span>
        <span id="zeus">Zeus</span>
    </div>
</div>
<script type="text/javascript" src="https://www.zeus.social/b/js/Resource.js"></script>
    </textarea>

</div>

<script>
    var webAddress = document.getElementById("web-address")
    var code = document.getElementById("code-wrapper")
    var zeusActions = document.getElementById("zeus-actions-wrapper")
    var generateButton = document.getElementById("generate-code")
    var copyMessage = document.getElementById("copy-message")
    var zeusActions = document.getElementById("zeus-actions-wrapper")

    generateButton.addEventListener("click", checkGenerateCode);

    function checkGenerateCode(){
        if(validUri(webAddress.value)){
            var value = webAddress.value
            var output = code.value.replace("{{uri}}", value)
            code.value = output
            copyMessage.style.display = "block"
            zeusActions.setAttribute("data-uri", value)
            console.log(zeusActions, zeusActions.getAttribute("data-uri"))
        }else{
            alert("enter valid web address")
        }
    }

    function validUri(str) {
      var pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
        '(\\#[-a-z\\d_]*)?$','i'); // fragment locator
      return !!pattern.test(str);
    }

</script>