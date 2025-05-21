<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <script src="https://kit.fontawesome.com/9acb07b9b1.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/UserProfile.css">
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
        		<li><a href="${pageContext.request.contextPath}/pages/user/Home.jsp">Home</a></li>
        		<li><a href="${pageContext.request.contextPath}/pages/user/SearchBooks.jsp">Search Books</a></li>
        		<li><a href="${pageContext.request.contextPath}/FetchAllBooksController">All Books</a></li>
        		<li><a href="#">About Us</a></li>
        		<li><a href="${pageContext.request.contextPath}/UserProfileController">Profile</a></li>
    		</ul>
		</nav>
  </header>

<div class="container mt-5">
    <h2>User Profile</h2>

    <div class="mb-3"><strong>Username:</strong> ${sessionScope.user.username}</div>
	<div class="mb-3"><strong>Email:</strong> ${sessionScope.user.email}</div>
	<div class="mb-3"><strong>Role:</strong> ${sessionScope.user.role}</div>
	
	<c:if test="${sessionScope.user.role == 'member'}">
	    <h4 class="mt-4">Currently Borrowed Books</h4>
	    <c:if test="${empty currentBorrows}">
	        <p>No current borrows.</p>
	    </c:if>
	    <c:forEach var="b" items="${currentBorrows}">
	        <div class="border p-2 mb-2">
	            <strong>Title:</strong> ${b['title']}<br>
	            <strong>Borrowed On:</strong> ${b['borrow_date']}<br>
	            <strong>Due Date:</strong> ${b['due_date']}
	        </div>
	    </c:forEach>
	
	    <h4 class="mt-4 text-danger">Overdue Books</h4>
	    <c:if test="${empty overdueBorrows}">
	        <p>No overdue books.</p>
	    </c:if>
	    <c:forEach var="b" items="${overdueBorrows}">
	        <div class="border p-2 mb-2 bg-warning">
	            <strong>Title:</strong> ${b['title']}<br>
	            <strong>Borrowed On:</strong> ${b['borrow_date']}<br>
	            <strong>Due Date:</strong> ${b['due_date']}
	        </div>
	    </c:forEach>
	</c:if>

</div>
</body>
</html>
