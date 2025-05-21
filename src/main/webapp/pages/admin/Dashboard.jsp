<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f5f5f5; padding: 20px; }
        .section { margin-bottom: 30px; }
        .card {
            display: flex;
            align-items: center;
            background: white;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin-bottom: 15px;
        }
        .cover { width: 80px; height: 120px; object-fit: cover; border-radius: 5px; margin-right: 20px; }
        .info { flex-grow: 1; }
        .title { font-size: 18px; font-weight: bold; margin-bottom: 5px; }
        .detail { margin: 2px 0; color: #555; }
        h2 { border-bottom: 2px solid #ddd; padding-bottom: 5px; }
    </style>
</head>
<body>
<aside class="sidebar">
              <nav>
                <ul>
                  <li><img src="image/LibXTransparent.png"></li>
                  <li><a href="#">Dashboard</a></li>
                  <li><a href="${pageContext.request.contextPath}/AdminFetchAllBooksController">Books</a></li>
                  <li><a href="#">Members</a></li>
                  <li><a href="#">Report</a></li>
                </ul>
              </nav>
</aside>
<h1>Library Dashboard</h1>

<!-- MOST BORROWED BOOK -->
<div class="section">
    <h2>Most Borrowed Book</h2>
    <c:forEach var="book" items="${mostBorrowedBook}">
        <div class="card">
            <img class="cover" src="${book.cover_img_path}" alt="Book Cover">
            <div class="info">
                <div class="title">${book.title}</div>
                <div class="detail">Author: ${book.author}</div>
                <div class="detail">Genre: ${book.genre}</div>
                <div class="detail">Borrowed: ${book.borrow_count} times</div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- LEAST BORROWED BOOK -->
<div class="section">
    <h2>Least Borrowed Book</h2>
    <c:forEach var="book" items="${leastBorrowedBook}">
        <div class="card">
            <img class="cover" src="${book.cover_img_path}" alt="Book Cover">
            <div class="info">
                <div class="title">${book.title}</div>
                <div class="detail">Author: ${book.author}</div>
                <div class="detail">Genre: ${book.genre}</div>
                <div class="detail">Borrowed: ${book.borrow_count} times</div>
            </div>
        </div>
    </c:forEach>
</div>

<!-- MOST BORROWED GENRE -->
<div class="section">
    <h2>Most Borrowed Genre</h2>
    <div class="card">
        <div class="info">
            <div class="title">Genre: ${mostBorrowedGenre.genre}</div>
            <div class="detail">Borrowed: ${mostBorrowedGenre.borrow_count} times</div>
        </div>
    </div>
</div>

<!-- GENDER BASED STATS -->
<div class="section">
    <h2>Male Readers</h2>
    <div class="card">
        <img class="cover" src="${mostBookMale.cover_img_path}" alt="Book Cover">
        <div class="info">
            <div class="title">Top Book: ${mostBookMale.title}</div>
            <div class="detail">Author: ${mostBookMale.author}</div>
            <div class="detail">Genre: ${mostBookMale.genre}</div>
            <div class="detail">Borrowed: ${mostBookMale.borrow_count} times</div>
        </div>
    </div>
    <div class="card">
        <div class="info">
            <div class="title">Top Genre: ${mostGenreMale.genre}</div>
            <div class="detail">Borrowed: ${mostGenreMale.borrow_count} times</div>
        </div>
    </div>
</div>

<div class="section">
    <h2>Female Readers</h2>
    <div class="card">
        <img class="cover" src="${mostBookFemale.cover_img_path}" alt="Book Cover">
        <div class="info">
            <div class="title">Top Book: ${mostBookFemale.title}</div>
            <div class="detail">Author: ${mostBookFemale.author}</div>
            <div class="detail">Genre: ${mostBookFemale.genre}</div>
            <div class="detail">Borrowed: ${mostBookFemale.borrow_count} times</div>
        </div>
    </div>
    <div class="card">
        <div class="info">
            <div class="title">Top Genre: ${mostGenreFemale.genre}</div>
            <div class="detail">Borrowed: ${mostGenreFemale.borrow_count} times</div>
        </div>
    </div>
</div>

</body>
</html>
