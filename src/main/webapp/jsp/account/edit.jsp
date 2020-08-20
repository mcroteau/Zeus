<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="xyz.ioc.model.Account"%>
<%@ page import="io.github.mcroteau.Parakeet" %>
<%@ page import="xyz.ioc.common.BeanLookup" %>
<%@ page import="xyz.ioc.common.Constants" %>

<div id="edit-account-form">

	<style type="text/css">
        @media screen and (max-width: 590px) {
            body{
                padding:20px !important;
            }
            #guest-content-container{
                width:100% !important;
            }
            input[type="text"],
            input[type="password"]{
                width:80% !important;
            }
            #guest-content-right{
                padding:0px;
            }
        }
	    #guest-content-container{
	        width:590px;
	    }
	    #zeus-logo-container{
            display:none;
	    }
	    #guest-content-right{
	        float:none;
	        margin:0px auto !important;
	        width:100% !important;
	        text-align:left !important;
	    }
	    #guest-header{
	    }
		#profile-image{
		    text-align:center;
		}
		#profile-image img{
			height:201px;
			width:201px;
			border-radius: 201px;
			-moz-border-radius: 203px;
			-webkit-border-radius: 203px;
		}
        #edit-actions-container{
            margin:30px auto 30px auto;
        }
        #edit-actions-container a{
            display:inline-block;
            margin-top:30px;
        }
        #delete-account-container{
            margin:0px auto 10px auto;
        }
        #suspend-actions-container{
            margin:0px auto 200px auto;
        }
        label{
            font-size:19px;
            display:block;
            color:#2b2b34;
            margin:10px auto 0px auto;
        }
        input[type="text"],
        input[type="password"]{
            width:100% !important;
        }
        .button,
        .retro,
        input[type="submit"]{
            width:100%;
            display:block;
            -webkit-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
            -moz-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
            box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
        }
        .button:hover,
        .retro:hover,
        .yella:hover{
            -webkit-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
            -moz-box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
            box-shadow: 0px 1px 7px 0px rgba(0,0,0,0) !important;
        }
        h2{
            margin-top:20px;
        }
        .notify{
            line-height:1.4em;
        }
	</style>

	<c:if test="${account.disabled}">
	    <div class="notify">This account has been suspended or disabled due to activity that is either deemed
	    inappropriate or abusive. You will either have to wait 24 hours before someone unlocks your account or appeal
        to have this account reopened.
	    <%
	        Account account = (Account) request.getAttribute("account");
	        Date suspendedDate = null;
	        String revokeDate = null;
	        if(account.isDisabled()){
	            String date = String.valueOf(account.getDateDisabled());
	            suspendedDate = new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(date);
	            Calendar cal = Calendar.getInstance();
                cal.setTime(suspendedDate);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                SimpleDateFormat formatter = new SimpleDateFormat("dd MMM @ hh:mm");
                revokeDate = formatter.format(cal.getTime());
	        }
        %>
	        <br/>
	        <span class="beauty-light">Suspended until : <%=revokeDate%></span>
        </div>
	</c:if>

	<c:if test="${not empty error}">
	    <div class="notify">${error}</div>
	</c:if>


	<div id="edit-user-container">

        <form action="${pageContext.request.contextPath}/account/update/${account.id}" class="pure-form pure-form-stacked" modelAttribute="account" method="post" enctype="multipart/form-data">


            <div id="profile-image">
                <img src="${pageContext.request.contextPath}/${account.imageUri}"/>

                <span class="information" style="display:block;margin:20px auto;">250 x 250</span>

                <input type="file" name="image" style="display:block; margin-left:30px;"/>
            </div>


            <h2>${account.username}</h2>

            <input type="hidden" name="id" value="${account.id}"/>
            <input type="hidden" name="imageUri" value="${account.imageUri}"/>


            <label for="name">Name</label>
            <input type="text" name="name" id="name" placeholder="" value="${account.name}">

            <label for="name">Age</label>
            <input type="text" name="age" id="name" placeholder="" value="${account.age}">

            <label for="name">Location</label>
            <input type="text" name="location" id="name" placeholder="Boston, MA" value="${account.location}">


            <div id="edit-actions-container" style="width:100%;">
                <input type="submit" class="button retro" id="update" value="Update" style="width:100% !important;"/>
            </div>
        </form>



        <%
            BeanLookup beanLookup = new BeanLookup();
            Parakeet parakeet = (Parakeet) beanLookup.get("parakeet");
        %>
        <%if(parakeet.hasRole(Constants.ROLE_ADMIN)){%>

            <form action="${pageContext.request.contextPath}/account/delete/${account.id}" method="post">
                <div id="delete-account-container" style="width:100%;">
                    <input type="submit" class="button beauty-light" id="disable" value="Disable Account" style="width:100% !important;"/>
                </div>
            </form>

            <c:if test="${!account.disabled}">
                <form action="${pageContext.request.contextPath}/account/suspend/${account.id}" method="post">
                    <div id="suspend-actions-container" style="width:100%;">
                        <input type="submit" class="button beauty-light" id="suspend" value="Suspend Account" style="width:100% !important;"/>
                    </div>
                </form>
            </c:if>

            <c:if test="${account.disabled}">
                <form action="${pageContext.request.contextPath}/account/renew/${account.id}" method="post">
                    <div id="suspend-actions-container" style="width:100%;">
                        <input type="submit" class="button yella" id="revoke" value="Revoke Suspension" style="width:100% !important;"/>
                    </div>
                </form>
            </c:if>
        <%}%>
    </div>
</div>
	
		
