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
            background-image:url("/b/images/zeus-home.jpg");
            background-position:0px 0px;
            background-color:#fff;
            background-position:0px 0px;
        }

        #guest-content-right{
            float:right;
            color:#fff !important;
            width:300px;
            padding:30px;
            margin-right:49px;
            margin-top:24px;
            text-align:left;
            background:rgba(0,0,0, 0.97);
            background:rgba(67, 167, 251, 1);
            background: linear-gradient(-31deg, rgba(67,136,251,1) 0%, rgba(67,179,251,1) 100%);
            background:rgba(0,0,0, 0.97);

            border-radius: 7px !important;
            -moz-border-radius: 7px !important;
            -webkit-border-radius: 7px !important;

            -webkit-box-shadow: 1px 3px 12px 0px rgba(0,0,0,.17)
            -moz-box-shadow: 1px 3px 12px 0px rgba(0,0,0,.17);
            box-shadow: 1px 3px 12px 0px rgba(0,0,0,.17);
        }

        #guest-content-right p,
        #guest-content-right h2{
            color:#fff;
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
    </style>

    <div id="guest-content-container">



        <div id="guest-content-right">

            <div style="text-align:center;margin-bottom:29px;">

                <%
                    BeanLookup beanLookup = new BeanLookup();
                    Parakeet parakeet = (Parakeet) beanLookup.get("parakeet");
                %>

                <%
                    try{

                    if(parakeet.isAuthenticated()){%>

                    Welcome <strong>${sessionScope.account.nameUsername}</strong>!
                    &nbsp;|&nbsp;
                    <a href="${pageContext.request.contextPath}/signout" class="href-dotted-zeus">Signout</a>
                    <br/>
                    <br/>
                    <a href="${pageContext.request.contextPath}/" class="href-dotted-zeus">Home<a>&nbsp;|&nbsp;
                    <a href="${pageContext.request.contextPath}/account/edit/${sessionScope.account.id}" class="href-dotted-cau">Edit Profile<a>

                <%}else{%>
                    <a href="${pageContext.request.contextPath}/signin?uri=${uri}" class="href-dotted-zeus">Signin</a>&nbsp;
                    <a href="${pageContext.request.contextPath}/signup?uri=${uri}" class="href-dotted-zeus">Signup!</a>
                <%}
                }catch(Exception e){
                    e.printStackTrace();
                }
                %>
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