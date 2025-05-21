<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Books - LibX</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Books.css" />
</head>
<body>
  <header class="navbar">
    <div class="logo">
      <a href="${pageContext.request.contextPath}/pages/user/Home.jsp">
        <img src="${pageContext.request.contextPath}/images/LibXTransparent.png" alt="LibX Logo" />
      </a>
    </div>
	<nav>
    	<ul class="nav-links">
        	<li><a href="${pageContext.request.contextPath}/pages/member/Home.jsp">Home</a></li>
        	<li><a href="${pageContext.request.contextPath}/FetchAllBooksController">Books</a></li>
        	<li><a href="#">About Us</a></li>
        	<li><a href="${pageContext.request.contextPath}/UserProfileController">Profile</a></li>
    	</ul>
	</nav>
  </header>

  <main>
    <section class="books-section">
      <h1>Books in the Library</h1>

      <!-- Filter/Search Form -->
      <form action="${pageContext.request.contextPath}/FetchBookController" method="get">
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

          <button type="submit">Apply Filters</button>
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
