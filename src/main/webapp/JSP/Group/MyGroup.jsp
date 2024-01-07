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
        }

        button:hover {
            background-color: #45a049;
        }

        .find-group{
            display: flex;
        }
        .find-group input{
            padding: 5px;
        }
        .find-group button{
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
    <form class="left-sidebar">
        <div class="imp-links">
            <button type="submit" name="pageAction" value="myGroup">
                My Group
            </button>
            <button type="submit" name="pageAction" value="createGroup">
                Create Group
            </button>
            <button type="submit" name="pageAction" value="seeJoinedGroup">
                See Joined Group
            </button>
            <button type="submit" name="pageAction" value="findGroup">
                Find Group
            </button>
        </div>
    </form>


    <div class="main-content">
        <h2>${sessionScope.title}</h2>
        <form action="my-group" method="post">
            <div class="find-group" >
                <input type="text" name="groupSearch" placeholder="Search for friend">
                <button type="submit" name="action" value="groupSearch">
                    Find
                </button>
            </div>
        </form>



                <c:if test = "${sessionScope.groupList != null}">
                    <c:forEach items="${sessionScope.groupList}" var="group" varStatus="groupListStatus">
                        <form class="friend-container" action="my-group" method="post">
                            <input type="hidden" name="index" value="${groupListStatus.index}">
                            <div class="left">
                                <a href="group?id=${group.id}">
                                    <h3>${group.name}</h3>
                                </a>
                            </div>
                            <div class="center">
                                <h4>Owned By</h4>
                                <p>${group.accountName}</p>
                            </div>
                            <div class="center">
                                <h4>Details</h4>
                                <p>${group.details}</p>
                            </div>
                            <c:choose>
                                <c:when test="${sessionScope.title eq 'Find Group'}">
                                    <button type="submit" name="action" value="join">Join</button>
                                </c:when>
                                <c:when test="${sessionScope.title eq 'See Joined Group'}">
                                    <button type="submit" name="action" value="leave" style="background-color: red">Leave</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" name="action" value="edit">Edit group</button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </c:forEach>
                </c:if>
    </div>
</div>
</body>

</html>