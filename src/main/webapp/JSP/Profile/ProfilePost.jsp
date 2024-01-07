<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: PC
  Date: 12/24/2023
  Time: 10:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Mysteries Of The Universe</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="./css/comment.css">
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
    <div class="left-sidebar">
        <div class="imp-links">
            <a href="profile?id=${sessionScope.accountProfile.id}">
                Profile
            </a>
            <a href="profile-post?id=${sessionScope.accountProfile.id}">
                Post
            </a>
        </div>
    </div>
    <div class="main-container">
        <c:if test="${sessionScope.postList != null}" var="postListIsNotNull">
            <c:forEach items="${sessionScope.postList}" var="post" varStatus="postListStatus">
                <form class="post-container" method="post">
                    <input type="hidden" name="index" value="${postListStatus.index}">

                    <input type="hidden" name="index" value="${pageScope.postListStatus.index}">

                    <div class="post-row" style="display: flex; justify-content: space-between">
                        <div class="user-profile">
                            <a href="profile?id=${post.accountId}">
                                <img src="${pageContext.request.contextPath}/${post.avatarName}" alt="">
                                <div>
                                    <p>${post.username}</p>
                                    <span>${post.timeSent}</span>
                                </div>
                            </a>
                        </div>
                        <div class="icon">
                            <c:if test="${post.favorite == false}">
                                <button type="submit" name="action" value="setFavorite">
                                    <img src="images/saveFavorite.png">
                                </button>
                            </c:if>

                            <c:if test="${post.favorite == true}">
                                <button type="submit" name="action" value="removeFavorite">
                                    <img src="images/deleteBookMark.png">
                                </button>
                            </c:if>


                            <c:if test="${post.accountId == sessionScope.account.id }">
                                <button type="submit" name="action" value="editPost">
                                    <img src="images/editPost.png">
                                </button>
                            </c:if>
                        </div>
                    </div>

                    <p class="post-text">
                            ${post.details}
                    </p>
                    <c:if test="${not empty post.imageName}">
                        <img src="${pageContext.request.contextPath}/${post.imageName}" alt="${post.imageName}" class="post-img">
                    </c:if>


                    <div class="post-row">
                        <div class="activity-icons">
                            <input type="hidden" name="postId" value="${post.id}">
                            <button type="submit" name="action" value="like">
                                <c:if test="${post.isLiked == true}">
                                    <img src="images/like-blue.png">
                                </c:if>
                                <c:if test="${post.isLiked == false}">
                                    <img src="images/like.png">
                                </c:if>
                                    ${post.likes}
                            </button>
                            <button type="submit" name="action" value="comment">
                                <img src="images/comments.png">
                                    ${post.totalComment}
                            </button>
                        </div>
                    </div>

                    <c:if test="${post.isClickComment == true}">

                        <!-- Actual comment section -->
                        <div class="comment-section">
                            <!-- Fake comments for demonstration -->
                            <c:if test="${post.commentList != null}">
                                <c:forEach items="${post.commentList}" var="comment">
                                    <div class="comment">
                                        <img src="${pageContext.request.contextPath}/${comment.avatarPath}" alt="user-profile-pic">
                                        <div class="comment-content">
                                            <p class="comment-user">${comment.accountName}</p>
                                            <p class="comment-text">${comment.details}</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>

                            <!-- Your actual comment input field and button -->
                            <div class="comment-input">
                                <input type="hidden" name="postId" value="${post.id}">
                                <input type="text" name="comment" placeholder="Add a comment">
                                <button type="submit" name="action" value="sendComment">Post</button>
                            </div>

                            <c:if test="${requestScope.errors!=null}">
                                <c:forEach items="${requestScope.errors}" var="error">
                                    <p style="color: red">${error.message}</p>
                                </c:forEach>
                            </c:if>
                        </div>
                    </c:if>
                </form>



            </c:forEach>
        </c:if>
    </div>
</div>

</body>

</html>
