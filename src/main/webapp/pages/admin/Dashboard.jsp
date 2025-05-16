<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <script src="https://kit.fontawesome.com/9acb07b9b1.js"></script>
  <title>Admin Dashboard</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/DashboardStyle.css">
</head>
<body>
  <div class="container">
    <div class="dashboard">
      <aside class="sidebar">
        <nav>
          <ul>
            <li><img src="${pageContext.request.contextPath}/images/LibXTransparent.png" alt="Logo"></li>
            <li><a href="${pageContext.request.contextPath}/pages/admin/Dashboard.jsp">Dashboard</a></li>
            <li><a href="#">Books</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/admin/AddBook.jsp">Add Books</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/admin/ManageBorrow.jsp">Manage Borrows</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/admin/ManageReturn.jsp">Manage Return</a></li>
            <li><a href="#">Members</a></li>
            <li><a href="#">Report</a></li>
          </ul>
        </nav>
      </aside>

      <main class="main-content">
        <header>
          <h1>Welcome, ${sessionScope.user.username}!</h1>
        </header>
        <section class="dashboard-widgets">
          <div class="widget">Total Books: ____</div>
          <div class="widget">Books Issued: ____</div>
          <div class="widget">Overdue: ____</div>
        </section>
      </main>
    </div>
  </div>
</body>
</html>
