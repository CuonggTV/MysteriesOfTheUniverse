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
      <a href="edit-profile">
        Edit Profile
      </a>
      <a href="change-password">
        Change password
      </a>
      <button></button>
    </div>
  </div>
  <div class="main-container">
    <div class="profile-image">
      <img src="${pageContext.request.contextPath}/images/avatar/default.png" alt="image">
      <button></button>
    </div>



    <div class="profile-container">

      <form class="profile-details" action="change-password" method="post">
        <h1>Change Password</h1>
        <c:if test="${requestScope.updateStatus != null}">
          <c:if test="${requestScope.updateStatus == true}">
            <p style="color: green">Update success!</p>
          </c:if>
        </c:if>

        <label for="oldPass">Old password:</label>
        <input type="password" id="oldPass" name="oldPass">
        <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
          <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
            <c:if test="${error.name eq 'inputOldPass'}" var="errorIsInConfirmPassword">
              <p style="color: red">${error.message}</p>
            </c:if>
          </c:forEach>
        </c:if>


        <label for="newPass">New Password:</label>
        <input type="password" id="newPass" name="newPass">
        <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
          <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
            <c:if test="${error.name eq 'newPass'}" var="errorIsInConfirmPassword">
              <p style="color: red">${error.message}</p>
            </c:if>
          </c:forEach>
        </c:if>

        <label for="confirmPass">Confirm Password:</label>
        <input type="password" id="confirmPass" name="confirmPass">
        <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
          <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
            <c:if test="${error.name eq 'confirmPass'}" var="errorIsInConfirmPassword">
              <p style="color: red">${error.message}</p>
            </c:if>
          </c:forEach>
        </c:if>

        <input type="submit" value="Update">
      </form>
    </div>

  </div>
</div>

</body>

</html>
