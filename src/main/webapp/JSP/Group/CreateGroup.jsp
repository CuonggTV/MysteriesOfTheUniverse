<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Group Form</title>

    <link rel="stylesheet" href="./css/style.css">
    <style>
        /* Basic CSS for styling the form */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
        }

        form {
            margin: 0 auto;
        }

        label,
        input,
        textarea {
            display: block;
            width: 100%;
            margin-bottom: 10px;
        }

        input[type="submit"] {
            padding: 8px 15px;
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
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
    <div class="main-content">
        <form method="post">
            <h2 style="text-align: center;">Group Form</h2>


            <c:if test="${requestScope.success != null}">
                <p style="color: green">${requestScope.success}</p>
            </c:if>
            <div class="approve" style="display: inline-block; vertical-align: middle;">
                <p>Need to approve post? </p>
                <div class="radio-label">
                    <input type="radio" id="yes" name="approve" value="yes"
                    <c:if test="${sessionScope.approve == true}">
                           checked
                    </c:if>
                    >
                    <label for="yes">Yes</label>
                </div>

                <div class="radio-label">
                    <input type="radio" id="no" name="approve" value="no"
                    <c:if test="${sessionScope.approve == false}">
                           checked
                    </c:if>
                    >
                    <label for="no">No</label>
                </div>
                <c:if test="${requestScope.errors != null}">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'approve'}">
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>

            <label for="groupName">Group name:</label>
            <input type="text" id="groupName" name="groupName" value="${sessionScope.groupName}">
            <c:if test="${requestScope.errors != null}">
                <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                    <c:if test="${error.name eq 'groupName'}" var="errorIsInConfirmPassword">
                        <p style="color: red">${error.message}</p>
                    </c:if>
                </c:forEach>
            </c:if>

            <label for="details">Details:</label>
            <input id="details" name="details" value="${sessionScope.details}" >
            <c:if test="${requestScope.errors != null}">
                <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                    <c:if test="${error.name eq 'details'}" var="errorIsInConfirmPassword">
                        <p style="color: red">${error.message}</p>
                    </c:if>
                </c:forEach>
            </c:if>

            <button type="submit" name="action" value="createGroup">Submit</button>
        </form>

    </div>
</div>
</body>

</html>
</html>