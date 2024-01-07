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
      <c:if test="${sessionScope.account.name == sessionScope.group.accountName}">
        <a href="group-approve?id=${sessionScope.group.id}">
          Approve post
        </a>
      </c:if>

      <button></button>
    </div>
  </div>
  <div class="main-content">
    <section class="group-members">
      <h2>Waiting to be approved:</h2>
      <!-- Display group members here -->
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

            <button type="submit" style="width: 100%;background-color: #007bff;color: black;border: none;">
              Approve
            </button>
          </form>
        </c:forEach>
      </c:if>

    </section>
  </div>
</div>
</body>

</html>
