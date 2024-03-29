<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Create Post</title>
    <link rel="stylesheet" href="./css/style.css">
    <link rel="stylesheet" href="./css/createPost.css">

    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;

            height: 100vh;
            background-color: #f7f7f7;
        }
        .container{
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .post-form-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 400px;
        }

        h2 {
            margin-top: 0;
            text-align: center;
        }

        label,
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

        .find-group{
            display: flex;
        }
        .find-group input{
            padding: 5px;
        }
        .find-group button{
            width: 20%;
            height: 30px;
        }
        /* ... (previous CSS remains unchanged) */

        .group-selection-scroll {
            width: 100%;
            max-height: 150px;
            /* Adjust the height as needed */
            overflow-y: auto;
            border: 1px solid #ccc;
            border-radius: 4px;
            padding: 5px;
            margin-bottom: 15px;
        }

        .scrollable-radio {
            /* Ensure the content doesn't overflow horizontally */
            white-space: nowrap;
        }

        /* Styling for radio buttons and labels */
        input[type="radio"],
        label {
            display: block;
            margin-bottom: 8px;
        }

        /* ... (previous CSS remains unchanged) */

        .visibility-options {
            display: flex;
            flex-wrap: wrap;
        }

        .radio-label {
            display: flex;
            align-items: center;
            flex: 1 0 25%;
            margin-bottom: 10px;
        }

        .radio-label input[type="radio"] {
            margin-right: 5px;
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
    <div class="post-form-container">
        <h2>Create Post</h2>
        <c:if test="${requestScope.success == true}">
            <p style="color:green;">Create post success</p>
        </c:if>
        <div id="postForm">

            <label>Select Visibility:</label>
            <form class="visibility-options" action="create-post" method="post" enctype='multipart/form-data'>

                    <div class="radio-label">
                        <input type="radio" id="public" name="visibility" value="public"
                               <c:if test="${sessionScope.visibility eq 'public'}">
                                checked
                                </c:if>
                        >
                        <label for="public">Public</label>
                    </div>

                    <div class="radio-label">
                        <input type="radio" id="private" name="visibility" value="private"
                        <c:if test="${sessionScope.visibility eq 'private'}">
                               checked
                        </c:if>
                        >
                        <label for="private">Private</label>
                    </div>

                    <div class="radio-label">
                        <input type="radio" id="groups" name="visibility" value="group"
                        <c:if test="${sessionScope.visibility eq 'group'}">
                               checked
                        </c:if>
                        >
                        <label for="groups">Group</label>
                    </div>
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'visibility'}" >
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <button type="submit" name="action" value="updateRadioBox">
                    Update radio box
                </button>
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'groupId'}" >
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

            <c:if test="${sessionScope.isGroup == true}">
                <label>Search Group:</label>
                    <div class="find-group" >
                        <input type="text" name="groupSearch" placeholder="Search for groups">
                        <button type="submit" name="action" value="findGroups">
                            Find
                        </button>
                    </div>

                    <div class="group-selection-scroll">
                            <div class="scrollable-radio" id="groupContainer">
                                <!-- Radio buttons for groups will be dynamically added here -->
                                <c:if test="${sessionScope.groupList!=null}">
                                    <c:forEach items="${sessionScope.groupList}" var="group">
                                        <div class="radio-label">
                                            <input type="radio" id="${group.id}" name="groupId" value="${group.id}" >
                                            <label for="${group.id}">${group.name}</label>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </div>
                    </div>

            </c:if>



                <label for="details">Details:</label>
                <textarea id="details" name="details" rows="4" >${sessionScope.details}</textarea>

                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'details'}" >
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <label for="image_input">Image:</label>
                <input type="file" id="image_input" name="image" placeholder="Upload image" alt="">
                <div id="display_image">
                <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
                    <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
                        <c:if test="${error.name eq 'image'}" >
                            <p style="color: red">${error.message}</p>
                        </c:if>
                    </c:forEach>
                </c:if>

                <button type="submit" name="action" value="submitAll">
                    Submit
                </button>
            </form>

        </div>
    </div>

</div>
<script src="../Javascript/imageOutput.js"></script>
</body>

</html>