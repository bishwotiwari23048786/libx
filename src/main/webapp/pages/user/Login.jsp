<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Login</title>
  <style>
    body {
      font-family: poppins, sans-serif;
      min-width: 100vh;
      margin: 0;
      padding: 0;
    }

    .login_nav_bar {
      width: 100%;
      margin: 0 auto;
      padding-top: 10px;
      padding-left: 135px;
      background-color: #6E94F5;
    }

    .login_logo {
      top: 50px;
      left: 20px;
      padding: 10px 20px;
      border-radius: 10px;
    }

    .login_logo img {
      width: 200px;
    }

    .login_container {
      width: 85%;
      display: flex;
      flex-direction: column;
      align-items: center;
      align-content: center;
      justify-content: center;
      margin: 0 auto;
      margin-top: 100px;
    }

    .login_main_container {
      text-align: center;
      padding-left: 20px;
      padding-right: 20px;
      padding-bottom: 20px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    form {
      width: 60%;
      text-align: left;
      font-size: 16px;
      font-weight: bold;
      margin-bottom: 0;
    }

    input[type="email"],
    input[type="password"] {
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
      	max-height: 50px;o
	  	overflow: auto;
    }
  </style>
</head>

<body>
  <header class="login_nav_bar">
    <div class="login_logo">
      <a href="Login.jsp">
        <img src="${pageContext.request.contextPath}/images/LibXTransparent.png" alt="Logo">
      </a>
    </div>
  </header>
  <div class="login_container">
    <div class="login_main_container">
      <h1>Sign in to your account</h1>

      <!-- Display Error Message (if any) -->
      <c:if test="${not empty errorMessage}">
        <div class="error-message">${errorMessage}</div>
      </c:if>
      
      <!-- Display Success Message (if any) -->
      <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
      </c:if>

      <form action="${pageContext.request.contextPath}/LoginController" method="POST">
        <label>Email</label>
        <input type="email" name="email" placeholder="Email" required />
        <label>Password</label>
        <input type="password" name="password" placeholder="Password" required />
        <button type="submit">Log in</button>
      </form>

      <div class="link">
        Don’t have an account? <a href="${pageContext.request.contextPath}/pages/member/Registration.jsp" style="color: rgb(64, 111, 252);">Create one</a>
      </div>
    </div>
	<div class="footer">
	  By clicking &quot;Log in&quot; you agree to the Terms of use &amp; Privacy policy
	</div>
  </div>
</body>
</html>
