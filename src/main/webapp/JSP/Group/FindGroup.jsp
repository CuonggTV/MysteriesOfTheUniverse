<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
  <title>Mysteries Of The Universe</title>
  <link rel="stylesheet" href="./css/friend.css">
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
      <a href="create-group">
        Create group
      </a>
      <a href="my-group">
        My Group
      </a>
      <a href="find-group">
        Find Group
      </a>
      <button></button>
    </div>
  </div>


  <div class="main-content">
    <h2>Find Group</h2>
    <c:if test = "${requestScope.groupList != null}">
      <c:forEach items="${requestScope.groupList}" var="group">
        <form class="friend-container" method="post">
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
          <div class="right">
            <button type="submit" name="groupId" value="${group.id}">Join</button>
          </div>
        </form>
      </c:forEach>
    </c:if>
  </div>
</div>
</body>

</html>