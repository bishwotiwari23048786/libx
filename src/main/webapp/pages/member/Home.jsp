<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Library Management</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/HomeStyle.css" />
</head>

<body>
  <header class="navbar">
        <div class="logo">
            <a href="Home.jsp">
                <img src="${pageContext.request.contextPath}/images/LibXTransparent.png">
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

  <main class="main-content">
    <section class="left-section">
      <h1>Welcome to the LibX</h1>
      <p>"Browse available books in our library from anywhere. Check what's on the shelves before your next visit."</p>
      <a href="SearchBooks.jsp" class="cta-button">Explore Books</a>
    </section>
    <section class="right-section">
      <img src="${pageContext.request.contextPath}/images/BooksTransparent.png" alt="books image" />
    </section>
  </main>
</body>
</html>