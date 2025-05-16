<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<html>
<head>
    <title>Manage Borrow</title>
    <script src="https://kit.fontawesome.com/9acb07b9b1.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ManageBorrow.css">
    <script>
        window.onload = function() {
            var currentDate = new Date();
            var nextDay = new Date(currentDate);
            nextDay.setDate(currentDate.getDate() + 1);
            
            var formattedDate = nextDay.toISOString().split('T')[0]; // YYYY-MM-DD format
            
            // Set dueDate field to the next day
            document.getElementsByName('dueDate')[0].min = formattedDate;
            document.getElementsByName('dueDate')[0].value = formattedDate;
        };
    </script>
</head>
<body>

<div class="container">

    <h2>Manage Borrowing</h2>

    <!-- Success or Error Messages -->
    <c:if test="${param.success == 'true'}">
        <div class="success">Book borrowed successfully!</div>
    </c:if>
    <c:if test="${param.error == 'true'}">
        <div class="error">Error borrowing the book. Please try again.</div>
    </c:if>
    <c:if test="${param.error == 'already_borrowed'}">
        <div class="error">You have already borrowed this book!</div>
    </c:if>

    <!-- Search Form -->
    <div class="search-box">
        <form method="get" action="${pageContext.request.contextPath}/ManageBorrowController">
            <label>Search User (by name or email)</label>
            <input type="text" name="userQuery" value="${param.userQuery}" placeholder="Enter username or email" />

            <label>Search Book (by title or author)</label>
            <input type="text" name="bookQuery" value="${param.bookQuery}" placeholder="Enter title or author" />

            <input type="submit" value="Search" />
        </form>
    </div>

    <!-- Borrow Form -->
    <div class="form-box">
        <form action="${pageContext.request.contextPath}/ManageBorrowController" method="POST" >
            <label>Select User</label>
            <select name="userId" required size="5">
                <option value="">-- Choose a user --</option>
                <c:forEach var="user" items="${users}">
                    <option value="${user.user_id}">${user.username} (${user.email})</option>
                </c:forEach>
                <c:if test="${empty users}">
                    <option>No users found for your search.</option>
                </c:if>
            </select>

            <label>Select Book</label>
            <select name="bookId" required size="5">
                <option value="">-- Choose a book --</option>
                <c:forEach var="book" items="${books}">
                    <option value="${book.book_id}">
                        ${book.title} by ${book.author} (${book.available_copies} available)
                    </option>
                </c:forEach>
                <c:if test="${empty books}">
                    <option>No books found for your search.</option>
                </c:if>
            </select>

            <label>Borrow Date</label>
            <input type="date" name="borrowDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" readonly />

            <label>Due Date</label>
            <input type="date" name="dueDate" required />

            <input type="submit" value="Borrow Book" />
        </form>
    </div>

</div>

</body>

</html>
