<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>

<%@ page import="io.github.mcroteau.Parakeet" %>
<%@ page import="xyz.ioc.common.BeanLookup" %>

<html>
<head>
    <title>Zeus : Like. Share. Obey!</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png?v=<%=System.currentTimeMillis()%>">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css?v=<%=System.currentTimeMillis()%>">

    <script data-ad-client="ca-pub-4887712095140583" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

</head>
<body>

    <style type="text/css">
        body{
            /**background-image:url("/b/images/zeus-home.jpg");**/
            background-position:0px 0px;
            background-color:#fcfcfc;
            background-position:0px 0px;
        }

        #guest-content-left{
            float:left;
        }

        #guest-content-right{
            float:right;
            color:#2b2b34 !important;
            width:300px;
            padding:0px 30px 30px 30px;
            margin-right:49px;
            margin-top:14px;
            text-align:left;
            background:rgba(67, 167, 251, 1);
            background: linear-gradient(-31deg, rgba(67,136,251,1) 0%, rgba(67,179,251,1) 100%);
            background:rgba(255,255,255, 1);

            border-radius: 7px !important;
            -moz-border-radius: 7px !important;
            -webkit-border-radius: 7px !important;

            -webkit-box-shadow: 1px 3px 12px 0px rgba(0,0,0,.17)
            -moz-box-shadow: 1px 3px 12px 0px rgba(0,0,0,.17);
            box-shadow: 1px 3px 12px 0px rgba(0,0,0,.17);
        }

        #guest-content-right p,
        #guest-content-right h2{
            color:#2b2b34;
        }

        #guest-content-right .light{
            color:#2b2b34;
        }

        #guest-content-container{
            margin:0px auto 0px auto;
            border:solid 0px #ddd;
            width:100%;
        }

        #zeus-logo-container{
            float:left;
            margin-top:71px;
            position:relative;
            width:239px;
        }

        #zeus-logo-deg{
            color:#617078 !important;
            margin-top:-181px;
            margin-left:-20px;
        }

        #logo-logo{
            color:#617078;
            font-family:Roboto-Bold !important;
        }

        #zeus-logo-container h1{
            font-size:72px;
            font-family:Roboto-Black !important;
            margin-left:8px;
        }

        #zeus-logo-container p{
            font-family:'RobotoBold';
        }

        #inspectasexajexa{
            top:3px;
            left:30px;
            position:absolute;
            height:40px;
            width:50px;
            cursor:pointer;
            cursor:hand;
        }

        h2{
            font-size:32px;
            font-family:Roboto-Bold !important;
            line-height:1.3em;
        }

        .button{
            -webkit-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0);
            -moz-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0);
            box-shadow: 0px 1px 7px 0px rgba(0,0,0,0);
        }
        .button:hover,
        .retro:hover,
        .yella:hover{
            -webkit-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
            -moz-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
            box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
        }
        input[type="button"],
        .button,
        .retro,
        .yella{
            width:80%;
            text-align:center;
        }
        p{
            line-height:1.4em;
            font-family:Roboto !important;
        }

        a{
            font-family:Roboto !important;
        }
        @media screen and (max-width: 590px) {
            #guest-content-container{
                width:100%;
                margin:0px auto 0px auto;
            }
            #guest-content-right{
                float:none;
                width:80%;
                height:100%;
                margin:0px auto;
            }


            #get_code{
                display:none;
            }
        }

        #zeus{
            width:43px;
        }
    </style>

    <div class="zeus" style="height:3px;position: absolute;z-index:-1;top:0px;left:0px;right:0px;"></div>

    <div id="guest-content-container">

        <div id="guest-content-left" style="padding:23px 50px; width:52%;">
            <span style="display:inline-block;background:#FDFE03; padding:53px;">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 134 134" id="zeus">
                    <path d="M49 1L21 88L57 88L42 134L84 134L113 47L92 47L79 47L75 47L91 1L49 1Z" />
                </svg>
            </span>
<%--            <img src="/b/icons/Assets.xcassets/AppIcon.appiconset/180.png" style="border:solid 1px #fff;">--%>
            <p style="font-weight: normal; font-size:32px;font-family:Georgia !important;">Like. Share. Obey!</p>

    <p style="font-family:Roboto-Light;">How much is Facebook Worth? <strong>$527 billion!</strong> While I am a fan
    of Harvard and the talent that it produces, there is a definitive pipeline of success.</p>

            <p>My name is Mike Croteau, and this is my promise as the sole developer
                of Zeus, <strong>89% of what Zeus earns will go towards children without homes,
                    and military veterans.</strong> Currently I am only accepting donations
                on <a href="https://www.patreon.com/user?u=35004351" class="href-dotted" target="_blank">Patreon.</a></p>

            <p>Here is what you get as a member of Zeus, a promise that I will not sell your data,
                a promise of 100% transparency as I offer the code for Free. You can view the source
                code anytime by browsing to <a href="https://github.com/mcroteau/Zeus" class="href-dotted" target="_blank">https://github.com/mcroteau/Zeus</a></p>

            <p>Why should there be one group to reign social media, when there is <strong>Zeus!</strong></p>

        </div>

        <%
            BeanLookup beanLookup = new BeanLookup();
            Parakeet parakeet = (Parakeet) beanLookup.get("parakeet");
        %>

        <% if(!parakeet.isAuthenticated()){%>

            <div id="guest-content-right">
                <decorator:body />
            </div>

        <%}%>

        <% if(parakeet.isAuthenticated()){%>
            <div style="float:right;margin:30px 30px 0px 0px;">
                Welcome back <strong>${sessionScope.account.nameUsername}</strong> my child!
                &nbsp;|&nbsp;
                <a href="${pageContext.request.contextPath}/signout" class="href-dotted-zeus">Signout</a>
                <br/>
                <br/>
                <a href="${pageContext.request.contextPath}/" class="href-dotted-zeus">Home<a>&nbsp;|&nbsp;
                    <a href="${pageContext.request.contextPath}/account/edit/${sessionScope.account.id}" class="href-dotted-zeus">Edit Profile<a>
            </div>

        <%}%>

        <br class="clear"/>
    </div>

    <div style="margin-bottom:167px;"></div>

<%--    <div style="background:#1b1b1b;height:130px;"></div>--%>

    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-40862316-16"></script>
    <script>
      window.dataLayer = window.dataLayer || [];
      function gtag(){dataLayer.push(arguments);}
      gtag('js', new Date());

      gtag('config', 'UA-40862316-16');
    </script>

</body>
</html>