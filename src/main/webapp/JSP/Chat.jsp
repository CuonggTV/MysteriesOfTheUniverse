<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Chat Page</title>
    <link rel="stylesheet" href="./css/style.css">
    <link rel="stylesheet" href="./css/chat.css">
    <style>
        .find{
            display: flex;
        }
        .find input{
            padding: 5px;
        }
        .find button{
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

<div class="chat-container">
    <div class="left-sidebar">
        <!-- Friend Heading -->
        <c:if test="${sessionScope.group != true}">
            <h2>Friends</h2>
        </c:if>
        <c:if test="${sessionScope.group == true}">
            <h2>Groups</h2>
        </c:if>

        <form method="post">
            <div class="find">
                <input type="text" name="chatSearch" placeholder="Search for friend">
                <button type="submit" name="action" value="chatSearch">
                    Find
                </button>
            </div>
        </form>
        <form action="chat" class="friend-list">
            <c:if test="${sessionScope.chatList!=null}">
                <c:forEach items="${sessionScope.chatList}" var="chat">
                    <a href="${requestScope.directLink}?id=${chat.id}" class="friend">
                        <c:if test="${sessionScope.group != true}">
                            <img src="${pageContext.request.contextPath}/${chat.avatarName}" alt="Avatar" class="avatar">
                        </c:if>
                        <span class="friend-name">${chat.name}</span>
                    </a>
                </c:forEach>
            </c:if>
            <!-- Add more friends as needed -->
        </form>
    </div>
    <c:if test="${not empty sessionScope.chatList}">
        <div class="main-content">
            <h2>Message</h2>
            <div style="display: flex">
                <c:if test="${sessionScope.group != true}">
                    <img src="${pageContext.request.contextPath}/${sessionScope.chatObject.avatarName}" alt="Avatar" class="avatar">
                </c:if>
                <span class="friend-name">${sessionScope.chatObject.name}</span>
            </div>

            <!-- Messages Display -->
            <form action="${requestScope.directLink}" class="message-container" method="post">
                <!-- Message Threads -->
                <div class="message-thread">
                    <c:if test="${sessionScope.messages!=null}">
                        <c:forEach items="${sessionScope.messages}" var="message">
                            <c:if test="${sessionScope.account.id == message.accountSent}">
                                <div class="message right" style="

                                        margin: 10px;
                                        max-width: 70%;

                                        border-radius: 5px;
                                        padding: 8px;


                                        align-self: flex-end;
                                        background-color: #d3d3d3;

                                ">${message.details}</div>
                            </c:if>
                            <c:if test="${sessionScope.account.id == message.accountReceived}">
                                <div class="message left" style="

                                    margin: 10px;
                                    max-width: 70%;
                                    border-radius: 5px;
                                    padding: 8px;
                                    align-self: flex-start;
                                 background-color: #e6e6e6;

                            ">${message.details}</div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <!-- More messages can be added here -->
                </div>
                <c:if test="${requestScope.errors!=null}">
                    <c:forEach items="${requestScope.errors}" var="error">
                        <p style="color: red">${error.message}</p>
                    </c:forEach>
                </c:if>
                <!-- Message Input -->
                <div class="message-input" style="
    padding-top: 0;
    margin: 10px;

    /* Additional margin */
    display: flex;
    align-items: center;
    position: sticky;
    bottom: 0;
    background-color: #fff;
">
                    <input type="text" placeholder="Type your message..." name="message" style="width: calc(100% - 70px);
    padding: 8px;
    border-radius: 5px;
    border: 1px solid #ccc;
    margin-right: 10px;">
                    <button type="submit" name="action" value="sendMessage" style="padding: 8px 15px;
    border: none;
    border-radius: 5px;
    background-color: #4caf50;
    color: white;
    cursor: pointer;">Send</button>
                </div>

            </form>
        </div>
    </c:if>

</div>
</body>

</html>