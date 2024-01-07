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
  <div class="left-sidebar">
    <div class="imp-links">
      <a href="group?id=${requestScope.group.id}">
        Group
      </a>
      <a href="members?id=${requestScope.group.id}">
        Member
      </a>
      <c:if test="${requestScope.account.name == requestScope.group.accountName}">
        <a href="group-approve?id=${requestScope.group.id}">
          Approve post
        </a>
      </c:if>
    </div>
  </div>
  <div class="main-content">
    <h2>Group Member</h2>
    <form method="post">
      <div class="find-friend" >
        <input type="text" name="memberSearch" placeholder="Search for friend">
        <button type="submit" name="action" value="search">
          Find
        </button>
      </div>
    </form>

    <c:if test = "${sessionScope.memberList != null}">
      <c:forEach items="${sessionScope.memberList}" var="member" varStatus="memberListStatus">
        <form class="friend-container" method="post">
          <input type="hidden" name="index" value="${memberListStatus.index}">

          <div class="left">
            <a href="profile?id=${member.id}">
              <img src="../../images/avatar/profile-pic.png" alt="">
              <h3>${member.name}</h3>
            </a>

          </div>
          <div class="center">
            <h4>Introduction</h4>
            <p>${member.introduction}</p>
          </div>
          <div class="right">
            <c:if test="${requestScope.account.name == requestScope.group.accountName}">
              <button type="submit" name="action" value="remove" style="background-color: red;color:white;">
                Remove
              </button>
            </c:if>
          </div>
        </form>
      </c:forEach>
    </c:if>
  </div>
</div>
</body>

</html>