<html>
    <head>
        <title>Zeus : Like. Share. Obey!</title>

        <meta name="viewport" content="width=device-width, initial-scale=1">

        <script type="text/javascript" src="${pageContext.request.contextPath}/js/request.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/web_form.js"></script>

	    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css?v=<%=System.currentTimeMillis()%>">

    </head>

    <body>

        <style>
            h1{
                font-size:23px !important;
            }
            #like-button{
                background:#FDFE01;
            }
            #zeus-like{
                height:30px;
                width:30px;
                fill:#000;
                clear:both;
            }
        </style>

        <p>${uri}</p>
        
        <div id="like-resource-container" style="padding:20px;margin-top:3px;text-align:left">
            <form action="${pageContext.request.contextPath}/resource/like" id="like-form" method="post">
                <input type="hidden" name="uri" value="${uri}">
                <a href="javascript:" id="like-button" class="like-button" style="margin-left:30px;margin-top:20px;font-family:Roboto-Medium !important; font-size:92px !important;padding:7px 14px;">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 134 134" id="zeus-like">
                    <path d="M49 1L21 88L57 88L42 134L84 134L113 47L92 47L79 47L75 47L91 1L49 1Z" />
                </svg>
                </a><span class="information">Like</span>
            </form>
        </div>


        <p style="text-align:center;margin:10px auto;font-weight:200;">or</p>

        <div id="share-resource-container" style="padding:0px 20px;position:relative;width:370px;text-align:left;">
            <h1>Share</h1>
            <form action="${pageContext.request.contextPath}/resource/share" method="post">
                <input type="hidden" name="uri" value="${uri}">
                <textarea name="comment" id="voice" class="resource-text" placeholder="Write about ${uri}" style="width:352px;height:251px;border-radius: 4px;-moz-border-radius: 4px;-webkit-border-radius: 4px;"></textarea>
                <input type="submit" class="button retro post-button share-button" value="&Sigma; Share" style="position:absolute;bottom:10px;right:58px;">
            </form>
        </div>



        <script>
            var likeFrm = document.getElementById("like-form")
            var likeBtn = document.getElementById("like-button")
            likeBtn.addEventListener("click", function(event){
                event.preventDefault()
                likeFrm.submit()
            });
        </script>
    </body>

</html>