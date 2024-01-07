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
    <div class="main-container" style="display: flex">
        
        <div class="profile-container" >

            <form class="profile-details" method="post" enctype="multipart/form-data">
                <h1>User Profile</h1>
                <c:if test="${requestScope.updateStatus != null}">
                    <c:if test="${requestScope.updateStatus == true}">
                        <p style="color: green">Update success!</p>
                    </c:if>
                </c:if>

                <%-- Avatar --%>
                <img src="${pageContext.request.contextPath}/${requestScope.account.avatarName}"
                     alt="${requestScope.account.avatarName}"
                style="width: 100%">
                <label for="image_input">Image:</label>
                <input type="file" id="image_input" name="image" placeholder="Upload image" alt="">

                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'avatar'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="${requestScope.account.username}">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'username'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>


                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${requestScope.account.name}">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'name'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${requestScope.account.email}">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'email'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label for="phone">Phone Number:</label>
                <input type="tel" id="phone" name="phone" value="${requestScope.account.phoneNumber}">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'phoneNumber'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob" value="${requestScope.account.dateOfBirth}">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'dateOfBirth'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label>Date Created:</label>
                <p>${requestScope.account.dateCreated}</p>

                <label for="intro">Introduction:</label>
                <textarea id="intro" rows="4" name="intro" value="${requestScope.account.introduction}"></textarea>
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'introduction'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label for="interest">Interest:</label>
                <input type="text" id="interest" name="interest" value="${requestScope.account.interest}">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'interest'}" var="errorIsInConfirmPassword">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>


                <button type="submit" >Update</button>
            </form>
        </div>

    </div>
</div>

</body>

</html>
