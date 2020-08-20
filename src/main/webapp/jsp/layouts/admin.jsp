<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>

<html>
<head>
    <title>Zeus : Like. Share. Obey!</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://fonts.googleapis.com/css?family=Roboto:200,300,400,500,600,700&display=swap" rel="stylesheet">

    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/images/favicon.png?v=<%=System.currentTimeMillis()%>">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/application.css?v=<%=System.currentTimeMillis()%>">

    <style>
        body{
            text-align:center;
        }
        #content-wrapper{
            width:960px;
            text-align:left;
            margin:10px auto 120px 10px;
        }
    </style>

</head>
<body>

    <div id="content-wrapper">
        <div id="navigation">
            <a href="${pageContext.request.contextPath}/accounts" class="href-dotted">Accounts</a>&nbsp;
            <a href="${pageContext.request.contextPath}/posts/flagged" class="href-dotted">Flagged</a>
        </div>
        <decorator:body />
    </div>

</body>
</html>