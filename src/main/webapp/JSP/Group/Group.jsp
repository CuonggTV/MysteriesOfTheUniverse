<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Group Home</title>
  <style>
    /* styles.css */

    /* Basic reset and styles */
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
    }

    main {
      max-width: 800px;
      margin: 20px auto;
      padding: 0 20px;
    }

    .group-info,
    .group-members {
      margin-bottom: 30px;
    }

    .group-details {
      border: 1px solid #ccc;
      padding: 10px;
    }

    .group-details h3 {
      margin-top: 0;
    }

    .group-details p {
      margin-bottom: 8px;
    }

    /* Styling for the list of group members */
    .group-members ul {
      list-style: none;
      padding: 0;
    }

    .group-members li {
      margin-bottom: 5px;
    }
  </style>
  <link rel="stylesheet" href="./css/style.css">
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
      <a href="group?id=${sessionScope.group.id}">
        Group
      </a>
      <a href="members?id=${sessionScope.group.id}">
        Member
      </a>
      <c:if test="${sessionScope.account.id == sessionScope.group.accountOwner}">
        <a href="group-approve?id=${sessionScope.group.id}">
          Approve post
        </a>
      </c:if>

      <button></button>
    </div>
  </div>
  <div class="main-content">
  <section class="group-info">
    <h2>${sessionScope.group.name}</h2>
    <!-- Display group information here -->
    <div class="group-details">
      <h3>Owned by: ${sessionScope.group.accountName}</h3>
      <p>Details: ${sessionScope.group.details}</p>
      <p>Created on: ${sessionScope.group.dateCreated}</p>
      <!-- You can add more details as needed -->
    </div>
  </section>

  <section class="group-members">
    <h2>Group Post</h2>
    <!-- Display group post here -->
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
  </section>
  </div>
</div>
</body>

</html>