<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Login Form</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
  <style>body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    background-color: #f4f4f4;
  }

  .login-container {
    background-color: #fff;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  .login-form {
    display: flex;
    flex-direction: column;
  }

  .login-form h2 {
    text-align: center;
    margin-bottom: 20px;
  }

  .input-group {
    margin-bottom: 15px;
  }

  label {
    font-weight: bold;
  }

  input[type="text"],
  input[type="password"] {
    padding: 8px;
    width: 100%;
    border-radius: 3px;
    border: 1px solid #ccc;
  }

  button {
    padding: 10px;
    border: none;
    border-radius: 3px;
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
  }

  button:hover {
    background-color: #0056b3;
  }
  </style>
</head>

<body>
  <div class="login-container">
    <form class="login-form" method="post">
      <h2>Login</h2>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
            <p style="color: red">${error.message}</p>
        </c:forEach>
      </c:if>
      <div class="input-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" >
      </div>
      <div class="input-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" >
      </div>
      <button type="submit">Login</button>
    </form>
  </div>
</body>

</html>