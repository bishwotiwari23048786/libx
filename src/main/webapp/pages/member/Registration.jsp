<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>  
  <title>Register</title>
  <style>
    body {
      font-family: poppins, sans-serif;
      min-width: 100vh;
      margin: 0;
      padding: 0;
    }

    .register_nav_bar{
      width: 100%;
      margin: 0 auto;
      padding-top: 10px;
      padding-left: 135px;
      background-color: #6E94F5;
    }

    .register_logo{
      top: 50px;
      left: 20px;
      padding: 10px 20px;
      border-radius: 10px;
    }

    .register_logo img{
      width: 200px;
    }

    .register_container{
      width: 85%;
      display: flex;
      flex-direction: column;
      align-items: center;
      align-content: center;
      justify-content: center;
      margin: 0 auto;
      margin-top: 100px;
    }

    .register_main_container {
      width: 43%;
      text-align: center;
      padding-left: 20px;
      padding-right: 20px;
      padding-bottom: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    form{
      width: 60%;
      text-align: left;
      font-size: 16px;
      font-weight: bold;  
      margin-bottom: 0;
    }

    input[type="text"],
    input[type="email"],
    input[type="password"],
    select {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      border-radius: 5px;
      border: 1px solid #ccc;
    }

    .link {
      font-size: 12px;
      margin-top: 10px;
    }

    button {
      width: 100%;
      padding: 10px;
      background: #3168f4;
      color: #ffffff;
      border: none;
      border-radius: 10px;
      font-weight: bold;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    button:hover {
      background-color: #4b8ef5;
    }

    .footer {
      position: absolute;
      bottom: 20px;
      left: 50%;
      transform: translateX(-50%);
      font-size: 10px;
      text-align: center;
    }

    .error-message {
      color: red;
      font-size: 14px;
      margin-top: 10px;
      max-height: 50px;
	  overflow: auto;
    }

    .success-message {
      color: green;
      font-size: 14px;
      margin-top: 10px;
      max-height: 50px;
	  overflow: auto;
    }
  </style>
</head>

<body>
  <header class="register_nav_bar">
    <div class="register_logo">
      <a href="Register.jsp">
        <img src="${pageContext.request.contextPath}/images/LibXTransparent.png" alt="Logo">
      </a>
    </div>
  </header>
  <div class="register_container">
    <div class="register_main_container">
      <h1>Create your account</h1>

      <!-- Display Error Message (if any) -->
      <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
      </c:if>
      
      <!-- Display Success Message (if any) -->
      <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/RegistrationController" method="POST">
        <label>Username</label>
        <input type="text" name="username" placeholder="Username" required />

        <label>Email</label>
        <input type="email" name="email" placeholder="something@gmail.com" required />

        <label>Password</label>
        <input type="password" name="password" placeholder="Password" required />

        <label>Age</label>
        <input type="text" name="age" placeholder="Enter your age" required />

        <label>Gender</label>
        <select name="gender" required>
          <option value="" disabled selected>Select your gender</option>
          <option value="Male">Male</option>
          <option value="Female">Female</option>
          <option value="Other">Other</option>
        </select>

        <button type="submit">Create account</button>
      </form>
      
      <div class="link">
        Already have an account? <a href="${pageContext.request.contextPath}/pages/user/Login.jsp" style="color: rgb(64, 111, 252);">Log in</a>
      </div>
    </div>
    <div class="footer">
      By clicking &quot;Create account&quot; you agree to the Terms of use &amp; Privacy policy
    </div>
  </div>
</body>
</html>
