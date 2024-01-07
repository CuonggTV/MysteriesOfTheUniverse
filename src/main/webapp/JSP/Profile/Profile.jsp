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
      <a href="profile?id=${requestScope.account.id}">
        Profile
      </a>
      <a href="profile-post?id=${requestScope.account.id}">
        Post
      </a>
      <button></button>
    </div>
  </div>
  <div class="main-container">
    <div class="profile-container">

      <img src="${pageContext.request.contextPath}/${requestScope.account.avatarName}" style="width: 150px;height: 150px;">

      <form class="profile-details" method="post">
        <h1>User Profile</h1>

        <h3>Username:</h3>
        <p id="username">${requestScope.account.username}</p>

        <h3>Name:</h3>
        <p id="name">${requestScope.account.name}</p>

        <h3>Email:</h3>
        <p id="email">${requestScope.account.email}</p>

        <h3>Phone Number:</h3>
        <p id="phone">${requestScope.account.phoneNumber}</p>

        <h3>Date of Birth:</h3>
        <p id="dob">${requestScope.account.dateOfBirth}</p>

        <h3>Date Created:</h3>
        <p>${requestScope.account.dateCreated}</p>

        <h3>Introduction:</h3>
        <p id="intro">${requestScope.account.introduction}</p>

        <h3>Interest:</h3>
        <p id="interest"> ${requestScope.account.interest}</p>
      </form>


      <c:if test="${requestScope.accountCookie.id == requestScope.account.id}">
        <form action="edit-profile">
          <button type="submit">Edit Profile</button>
        </form>
      </c:if>

    </div>

  </div>
</div>

</body>

</html>
