<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Admin - Books - LibX</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminBooks.css" />
</head>
<body>
  <header class="navbar">
    <div class="logo">
      <a href="${pageContext.request.contextPath}/pages/admin/Home.jsp">
        <img src="${pageContext.request.contextPath}/images/LibXTransparent.png" alt="LibX Logo" />
      </a>
    </div>
    <nav>
      <ul class="nav-links">
        <li><a href="${pageContext.request.contextPath}/pages/admin/Home.jsp">Home</a></li>
        <li><a href="${pageContext.request.contextPath}/AdminBooksController">Books</a></li>
        <li><a href="#">About Us</a></li>
        <li><a href="${pageContext.request.contextPath}/AdminProfileController">Profile</a></li>
      </ul>
    </nav>
  </header>

  <main>
    <section class="books-section">
      <h1>Manage Books in the Library</h1>

      <!-- Search / Filter Form -->
      <form action="${pageContext.request.contextPath}/AdminFetchBookController" method="get">
        <div class="filters">
          <select name="genre">
            <option value="">Select Genre</option>
            <option value="Fiction">Fiction</option>
            <option value="Non-Fiction">Non-Fiction</option>
            <option value="Science">Science</option>
            <option value="History">History</option>
          </select>

          <input type="text" name="author" placeholder="Author" />

          <select name="availability">
            <option value="">Availability</option>
            <option value="Available">Available</option>
            <option value="Checked Out">Checked Out</option>
          </select>

          <input type="text" name="title" placeholder="Title" />

          <button type="submit">Search</button>
        </div>
      </form>

      <!-- Book Cards -->
      <div class="card-container" id="booksGrid">
        <c:choose>
          <c:when test="${not empty books}">
            <c:forEach var="book" items="${books}">
              <div class="card">
                <img class="book-cover" src="${pageContext.request.contextPath}/${book.cover_img_path}" alt="Cover Image" />
                <div class="book-info">
                  <h3>${book.title}</h3>
                  <p><strong>Author:</strong> ${book.author}</p>
                  <p><strong>Genre:</strong> ${book.genre}</p>
                  <p><strong>Publish Year:</strong> ${book.publish_year}</p>
                  <p><strong>Available Copies:</strong> ${book.available_copies}</p>

                  <!-- Admin Controls -->
                  <form action="${pageContext.request.contextPath}/AddToStockController" method="post" class="stock-form">
                    <input type="hidden" name="book_id" value="${book.book_id}" />
                    <label for="quantity-${book.book_id}">Quantity:</label>
                    <input type="number" id="quantity-${book.book_id}" name="quantity" min="1" max="100" required/>
                    <button type="submit">Add</button>
                  </form>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="no-books">
              <p>No books found.</p>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </section>
  </main>
</body>
</html>
