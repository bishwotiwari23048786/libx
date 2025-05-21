<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Add Book - Admin Panel | LibX</title>
  <script src="https://kit.fontawesome.com/9acb07b9b1.js"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AddBook.css" />
</head>
<body>
  <!-- Navbar -->
  <div class="navbar">
    <div class="logo">
      <a href="home.html"><img src="${pageContext.request.contextPath}/images/LibXTransparent.png" alt="LibX Logo" /></a>
    </div>
    
    <!-- Hamburger Icon (Hidden on Large Screens) -->
    <div class="hamburger" id="hamburger">
      <i class="fas fa-bars"></i>
    </div>
    
    <!-- Navbar Links (Hidden on Small Screens) -->
    <ul class="nav-links" id="navLinks">
      <li><a href="${pageContext.request.contextPath}/pages/admin/AddBook.jsp">Add Book</a></li>
      <li><a href="${pageContext.request.contextPath}/pages/user/Books.jsp">View Books</a></li>
      <li><a href="#">Logout</a></li>
    </ul>
  </div>

  <main>
    <section class="form-section">
      <h1>Add a New Book</h1>

      <c:if test="${not empty sessionScope.success}">
        <div class="success-message" style="color: green;">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
      </c:if>

      <c:if test="${not empty sessionScope.error}">
        <div class="error-message" style="color: red;">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
      </c:if>

      <form action="${pageContext.request.contextPath}/AddBookController" method="POST" class="book-form" enctype="multipart/form-data">
        <label>Title:</label>
        <input type="text" name="title" required />

        <label>Author:</label>
        <input type="text" name="author" required />

        <label>Genre:</label>
        <select name="genre" required style="padding: 10px; border: 1px solid #ccc; border-radius: 8px;">
          <option value="" disabled selected>Select Genre</option>
          <option value="Fiction">Fiction</option>
          <option value="Non-Fiction">Non-Fiction</option>
          <option value="Science">Science</option>
          <option value="History">History</option>
        </select>

        <label>ISBN:</label>
        <input type="text" name="isbn" required />

        <label>Publisher:</label>
        <input type="text" name="publisher" required />

        <label>Publish Year:</label>
        <input type="number" name="publishYear" required min="1500" max="2025" />

        <label>Total Copies:</label>
        <input type="number" name="totalCopies" required min="1" />

        <label for="coverImage">Book Cover:</label>
        <input type="file" name="coverImage" accept="image/*" required />

        <button type="submit">Add Book</button>
      </form>
    </section>
  </main>

  <script>
    // Toggle the navbar links visibility
    document.querySelector('.hamburger').addEventListener('click', function () {
  		document.querySelector('.nav-links').classList.toggle('active');
	});

  </script>
</body>
</html>
