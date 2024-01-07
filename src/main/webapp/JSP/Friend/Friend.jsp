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
        <form class="left-sidebar" action="friend">
            <div class="imp-links">
                <button type="submit" name="pageAction" value=" ">
                    My Friend
                </button>
                <button type="submit" name="pageAction" value="friendRequest">
                    Friend Request
                </button>
                <button type="submit" name="pageAction" value="myFriendRequest">
                    My Friend Request
                </button>
                <button type="submit" name="pageAction" value="findFriend">
                    Find Friend
                </button>
            </div>
        </form>


        <div class="main-content">
            <h2>${sessionScope.title}</h2>
            <form action="friend" method="post">
                <div class="find-friend" >
                    <input type="text" name="friendSearch" placeholder="Search for friend">
                    <button type="submit" name="action" value="friendSearch">
                        Find
                    </button>
                </div>
            </form>

            <c:if test = "${sessionScope.friendList != null}">
                <c:forEach items="${sessionScope.friendList}" var="friend" varStatus="friendListStatus">
                    <form class="friend-container" action="friend" method="post">
                        <input type="hidden" name="index" value="${friendListStatus.index}">

                        <img src="${pageContext.request.contextPath}/${friend.avatarName}" style="width: 100px;height: 100px;border-radius: 50%">

                        <div class="left">
                            <a href="profile?id=${friend.id}">
                                <img src="../../images/avatar/profile-pic.png" alt="">
                                <h3>${friend.name}</h3>
                            </a>

                        </div>
                        <div class="center">
                            <h4>Introduction</h4>
                            <p>${friend.introduction}</p>
                        </div>
                        <div class="right">
                            <c:choose>
                                <c:when test="${sessionScope.title eq 'Friend Request'}">
                                    <button name="action" value="acceptRequest" type="submit">Yes</button>
                                    <button name="action" value="refuseRequest" type="submit" style="background-color: red;">No</button>
                                </c:when>
                                <c:when test="${sessionScope.title eq 'My Friend Request'}">
                                    <button style="background-color: gray; color: white" disabled>Waiting</button>
                                </c:when>
                                <c:when test="${sessionScope.title eq 'Find Friend'}">
                                    <button name="action" value="makeFriend" type="submit">Make friend</button>
                                </c:when>
                                <c:otherwise>
                                    <button name="action" value="deleteFriendship" type="submit" style="background-color: red;">Unfriended</button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>
                </c:forEach>
            </c:if>
        </div>
    </div>
</body>

</html>