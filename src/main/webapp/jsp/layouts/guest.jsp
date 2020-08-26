<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ page import="io.github.mcroteau.Parakeet" %>
<%@ page import="xyz.ioc.common.BeanLookup" %>

<html>
<head>
    <title>Zeus : Like. Love. Obey!</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png?v=<%=System.currentTimeMillis()%>">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css?v=<%=System.currentTimeMillis()%>">
</head>
<body>

    <style type="text/css">

      @media screen and (max-width: 590px) {
            #guest-content-container{
                width:100%;
                margin:0px auto 0px auto;
            }
            #guest-content-right{
                float:none;
                margin:30px !important;
            }
            #zeus-logo-container{
                display:none !important;
            }
            #content-mobile{
                display:block !important;
            }
        }

        body{
            background:#fff;
        }

        #guest-content-right{
            float:left;
            margin-left:100px;
            margin-top:24px;
            width:300px;
        }

        #guest-content-container{
            margin:0px auto 0px auto;
            width:760px; 
            border:solid 0px #ddd;
        }

        #zeus-logo-container{
            float:left;
            margin-top:71px;
            position:relative;
            width:339px;
        }

        #zeus-logo-deg{
            color:#617078 !important;
            margin-top:-181px;
            margin-left:-20px;
        }

        #logo-logo{
            padding:60px;
            background:#FDFE00;
            font-family:Roboto-Medium !important;
            border-radius:60px;
        }

        #zeus-logo-container h1{
            font-size:72px;
            font-family:Roboto-Bold !important;
            margin-left:8px;
        }

        #zeus-logo-container p{
            font-family:Roboto;
        }

        #guest-header{
            text-align:center;
            margin-bottom:29px;
        }

        h1{
            color:#000;
            text-align:center;
        }

        h2{
            font-size:32px;
            font-family:Roboto-Bold !important;
            line-height:1.3em;
        }

        p{
            font-family:Roboto !important;
        }
        a{
            font-family:Roboto-Medium !important;
        }

        #zeus{
            height:192px;
            fill:#43a7fb;
            fill:#000;
        }
    </style>


    <div id="guest-content-container">


        <div id="zeus-logo-container">
            <a href="${pageContext.request.contextPath}/uno" id="logo-logo" style="font-size:321px;text-decoration:none;line-height:0.9em;">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 134 134" id="zeus">
                    <path d="M49 1L21 88L57 88L42 134L84 134L113 47L92 47L79 47L75 47L91 1L49 1Z" />
                </svg>
            </a>
            <h1>Zeus</h1>
            <p style="text-align:center">Like. Share. Obey!</p>
            <br class="clear"/>
        </div>

        <div id="guest-content-right">

            <div id="guest-header">
                <%
                    BeanLookup beanLookup = new BeanLookup();
                    Parakeet parakeet = (Parakeet) beanLookup.get("parakeet");
                %>

                <%if(parakeet.isAuthenticated()){%>

                    Welcome <strong>${sessionScope.account.nameUsername}</strong>!
                    &nbsp;|&nbsp;
                    <a href="${pageContext.request.contextPath}/signout" class="href-dotted">Signout<a>
                    <br/>
                    <br/>
                    <a href="${pageContext.request.contextPath}/" class="href-dotted">Home<a>&nbsp;|&nbsp;
                    <a href="${pageContext.request.contextPath}/account/edit/${sessionScope.account.id}" class="href-dotted">Edit Profile<a>

                <%}else{%>
                    <a href="${pageContext.request.contextPath}/?uri=${uri}" class="href-dotted">Signin</a>
                    &nbsp;
                    <a href="${pageContext.request.contextPath}/signup?uri=${uri}" class="href-dotted">Signup!</a>
                <%}%>

            </div>

            <decorator:body />
        </div>

        <br class="clear"/>
    </div>


    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-40862316-16"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', 'UA-40862316-16');
    </script>

</body>
</html>