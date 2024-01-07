<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <title>Registration Form</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 50%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        h2 {
            text-align: center;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            font-weight: bold;
        }

        input[type="text"],
        input[type="password"],
        input[type="email"],
        input[type="tel"],
        input[type="date"],
        button {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }

        button {
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        #confirmPassword {
            margin-bottom: 15px;
        }
    </style>

</head>

<body>
  <div class="container">
    <form id="registerForm" method="post">
      <h2>Registration Form</h2>
      <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" >
      </div>
<%--      USERNAME ERROR--%>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
            <c:if test="${error.name eq 'username'}" var="errorIsInUsername">
              <p style="color: red">${error.message}</p>
            </c:if>
        </c:forEach>
      </c:if>

      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" >
      </div>
<%--      PASSWORD ERROR--%>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
          <c:if test="${error.name eq 'password'}" var="errorIsInPassword">
            <p style="color: red">${error.message}</p>
          </c:if>
        </c:forEach>
      </c:if>


      <div class="form-group">
        <label for="confirmPassword">Confirm Password:</label>
        <input type="password" id="confirmPassword" name="confirmPassword" >
      </div>
<%--      CONFIRM PASSWORD ERROR--%>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
          <c:if test="${error.name eq 'confirmPassword'}" var="errorIsInConfirmPassword">
            <p style="color: red">${error.message}</p>
          </c:if>
        </c:forEach>
      </c:if>


      <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" >
      </div>
<%--      NAME ERROR--%>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
          <c:if test="${error.name eq 'name'}" var="errorIsInName">
            <p style="color: red">${error.message}</p>
          </c:if>
        </c:forEach>
      </c:if>


      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" >
      </div>
<%--  EMAIL ERROR--%>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
          <c:if test="${error.name eq 'email'}" var="errorIsInEmail">
            <p style="color: red">${error.message}</p>
          </c:if>
        </c:forEach>
      </c:if>


      <div class="form-group">
        <label for="phoneNumber">Phone Number:</label>
        <input type="tel" id="phoneNumber" name="phoneNumber" >
      </div>
      <%--  PHONENUMBER ERROR--%>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
          <c:if test="${error.name eq 'phoneNumber'}" var="errorIsInphoneNumber">
            <p style="color: red">${error.message}</p>
          </c:if>
        </c:forEach>
      </c:if>


      <div class="form-group">
        <label for="dob">Date of Birth:</label>
        <input type="date" id="dob" name="dob" >
      </div>
      <c:if test="${requestScope.errors != null}" var="errorsAreNotNull">
        <c:forEach items="${requestScope.errors}" var="error" varStatus="errorStatus">
          <c:if test="${error.name eq 'dateOfBirth'}" var="errorIsDateOfBirth">
            <p style="color: red">${error.message}</p>
          </c:if>
        </c:forEach>
      </c:if>


        <a href="Login">Login?</a>
      <div class="form-group">
        <button type="submit">Register</button>
      </div>
    </form>
  </div>
</body>

</html>