<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <title>Mysteries Of The Universe</title>
    <link rel="stylesheet" href="./css/friend.css">
    <style>
        input[type="text"],
        input[type="datetime-local"],
        textarea {
            display: block;
            margin-bottom: 15px;
            width: 100%;
        }

        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }

        button:hover {
            background-color: #45a049;
        }

        .find-friend{
            display: flex;
        }
        .find-friend input{
            padding: 5px;
        }
        .find-friend button{
            width: 20%;
            height: 30px;
        }
    </style>
</head>

<body>
<nav>
    <div class="nav-left">
        <a href="home">
            <img src="../../images/logo.png" class="logo">
        </a>
    </div>
    <div class="nav-right">
        <ul>
            <li>
                <img src="../../images/inbox.png">
            </li>
            <li>
                <img src="../../images/notification.png">
            </li>
        </ul>
        <div class="settings-links">
            <a href="Logout">
                <img src="../../images/logout.png" class="logout">
            </a>
        </div>
    </div>

</nav>

<div class="container">
    <div class="main-content">
        <h2>Notification</h2>
        <c:if test="${requestScope.removeSuccess == true}">
            <p style="color: green">Remove success</p>
        </c:if>

        <c:if test = "${sessionScope.notificationList != null}">
            <c:forEach items="${sessionScope.notificationList}" var="noti" varStatus="notificationListStatus">
                <form class="friend-container" method="post">
                    <input type="hidden" name="index" value="${notificationListStatus.index}">
                    <div class="left">
                        <a href="${noti.url}">
                                ${noti.details}
                        </a>
                    </div>

                    <div class="left">
                        <a href="profile?id=${noti.accountSentId}">
                            <img src="${pageContext.request.contextPath}/${noti.avatarName}" alt="" style="
                                width: 100px;
                                height: 100px;
                                border-radius: 50%;
                            ">
                            <h3>${noti.accountSentName}</h3>
                        </a>

                    </div>


                    <div class="right">
                        <button type="submit" name="action" value="delete"  style="background-color: red;">Remove</button>
                    </div>
                </form>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>

</html>