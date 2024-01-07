<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Mysteries Of The Universe</title>
    <link rel="stylesheet" href="./css/style.css">
    <style>
        /* Example CSS styles for the comment section */
        .comment-section {
            margin-top: 20px;
        }

        .comment {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .comment img {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            margin-right: 10px;
        }

        .comment-content {
            background-color: #f2f2f2;
            padding: 10px;
            border-radius: 10px;
        }

        .comment-user {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .comment-input {
            display: flex;
            margin-top: 10px;
        }

        .comment-input input[type="text"] {
            flex: 1;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .comment-input button {
            padding: 8px 15px;
            margin-left: 5px;
            border: none;
            border-radius: 5px;
            background-color: #3498db;
            color: white;
            cursor: pointer;
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
                <img src="images/inbox.png">
            </li>
            <li>
                <img src="images/notification.png">
            </li>
        </ul>
        <div class="settings-links">
            <a href="Logout">
                <img src="images/logout.png" class="logout">
            </a>
        </div>
    </div>
</nav>

<c:if test="${sessionScope.postList != null}" var="postListIsNotNull">
    <c:forEach items="${sessionScope.postList}" var="post" varStatus="postListStatus">
        <form class="post-container" action="post" method="post">
            <div class="post-row">
                <div class="user-profile">
                    <a href="friend-profile?id=${post.accountId}">
                        <img src="images/profile-pic.png">
                        <div>
                            <p>${post.username}</p>
                            <span>${post.timeSent}</span>
                        </div>
                    </a>

                </div>
                <a href="#">
                    <i class="fa fa-ellipsis-v"></i>
                </a>
            </div>

            <p class="post-text">
                    ${post.details}
            </p>
            <img src="${post.imageName}" alt="post_image" class="post-img">
            <div class="post-row">
                <div class="activity-icons">
                    <input type="hidden" name="postId" value="${post.id}">
                    <button type="submit" name="action" value="like">
                        <img src="images/like-blue.png">
                            ${post.likes}
                    </button>
                    <button type="submit" name="action" value="comment">
                        <img src="images/comments.png">
                        19
                    </button>
                    <div>
                        <img src="images/share.png">
                        120
                    </div>
                </div>
                <div class="post-profile-icon">
                    <img src="images/profile-pic.png">
                    <i class="fas fa fa-caret-down"></i>
                </div>
            </div>

            <c:if test="${requestScope.}">

            </c:if>
            <!-- Actual comment section -->
            <div class="comment-section">
                <!-- Fake comments for demonstration -->
                <div class="comment">
                    <img src="images/profile-pic.png" alt="user-profile-pic">
                    <div class="comment-content">
                        <p class="comment-user">John Doe</p>
                        <p class="comment-text">This is a fake comment!</p>
                    </div>
                </div>

                <div class="comment">
                    <img src="../images/avatar/profile-pic.png" alt="user-profile-pic">
                    <div class="comment-content">
                        <p class="comment-user">Jane Smith</p>
                        <p class="comment-text">Here's another fake comment.</p>
                    </div>
                </div>
                <!-- More fake comments can be added -->

                <!-- Your actual comment input field and button -->
                <div class="comment-input">
                    <input type="hidden" name="postId" value="${post.id}">
                    <input type="text" name="comment" placeholder="Add a comment">
                    <button type="submit" name="action" value="comment">Post</button>
                </div>
            </div>
        </form>
    </c:forEach>
</c:if>


</body>
</html>
