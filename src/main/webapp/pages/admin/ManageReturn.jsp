<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Manage Borrow</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ManageReturn.css">
</head>
<body>

<div class="container">

    <h2>Manage Returns</h2>
    
    <c:if test="${param.success == 'true'}">
    	<div class="success">Book(s) returned successfully!</div>
	</c:if>
	<c:if test="${param.error == 'true'}">
    	<div class="error">Error returning book(s). Please try again.</div>
	</c:if>

    <!-- Search Form -->
	<div class="form-box">
	    <form method="get" action="${pageContext.request.contextPath}/ManageReturnController">
	        <label>Search Borrow Record (by username or email)</label>
	        <input type="text" name="userQuery" value="${param.userQuery}" placeholder="Enter username or email" />
	        <input type="submit" value="Search" />
	    </form>
	</div>
	
	<!-- Borrow Form -->
    <div class="form-box">
        <form action="${pageContext.request.contextPath}/ManageReturnController" method="POST" >
            <label>Select Borrow Record</label>
            <select name="borrowIds" required size="5">
		    	<option value="">-- Choose a borrow record --</option>
			    <c:forEach var="rec" items="${records}">
				    <option value="${rec['borrowId']}">
				        ${rec['username']} (${rec['email']}) - ${rec['bookTitle']} by ${rec['author']}			        
				    </option>
				</c:forEach>

			    <c:if test="${empty records}">
			        <option>No borrow records found for your search.</option>
			    </c:if>
			</select>
			
			<label>Overdue Days</label><br>
			<ul style="list-style-type: none; padding-left: 0;">
			    <c:forEach var="rec" items="${records}">
			        <li>
			            ${rec.username} (${rec.bookTitle}): 
			            <c:if test="${rec.overdueDays > 0}">
			                <span style="color: red;">${rec.overdueDays} day(s) overdue</span>
			            </c:if>
			            <c:if test="${rec.overdueDays <= 0}">
			                <span style="color: green;">No overdue days</span>
			            </c:if>
			        </li><br><br>
			    </c:forEach>
			</ul>
            <label>Return Date</label>
	        <input type="date" name="returnDate" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" readonly required />
	
	        <input type="submit" value="Return Selected Books" />
        </form>
    </div>
</div>

</body>
</html>
