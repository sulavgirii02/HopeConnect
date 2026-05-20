<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Messages - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<div class="container page admin-page">
<div class="admin-layout">
  <aside class="admin-sidebar">
    <h3>Navigation</h3>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/programs">Programs</a>
    <a href="${pageContext.request.contextPath}/admin/applications">Applications</a>
    <a href="${pageContext.request.contextPath}/admin/users">Users</a>
    <a href="${pageContext.request.contextPath}/admin/messages" class="active">Messages</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Contact Messages</h1>
      <p>Manage inquiries from users and visitors</p>
    </div>

    <c:if test="${not empty sessionScope.flash_success}">
      <div class="alert alert-success" role="alert"><c:out value="${sessionScope.flash_success}" /></div>
      <c:remove var="flash_success" scope="session" />
    </c:if>

    <c:choose>
      <c:when test="${not empty requestScope.viewMessage}">
        <div class="card card-wide">
          <div style="display:flex;justify-content:space-between;align-items:flex-start;">
            <div>
              <h2><c:out value="${requestScope.viewMessage.subject}" /></h2>
              <p><strong>From:</strong> <c:out value="${requestScope.viewMessage.name}" /> &lt;<c:out value="${requestScope.viewMessage.email}" />&gt;</p>
              <c:if test="${not empty requestScope.viewMessage.phone}">
                <p><strong>Phone:</strong> <c:out value="${requestScope.viewMessage.phone}" /></p>
              </c:if>
              <p><strong>Status:</strong>
                <c:choose>
                  <c:when test="${requestScope.viewMessage.status == 'new'}"><span class="badge badge-pending">New</span></c:when>
                  <c:when test="${requestScope.viewMessage.status == 'responded'}"><span class="badge badge-approved">Responded</span></c:when>
                  <c:otherwise><span class="badge badge-rejected">Closed</span></c:otherwise>
                </c:choose>
              </p>
              <p><strong>Received:</strong> <c:out value="${requestScope.viewMessage.createdAt}" /></p>
            </div>
            <div style="display:flex;gap:8px;">
              <form method="post" action="${pageContext.request.contextPath}/admin/messages">
                <input type="hidden" name="id" value="${requestScope.viewMessage.id}" />
                <c:if test="${requestScope.viewMessage.status != 'responded'}">
                  <button class="btn btn-secondary btn-sm" name="action" value="markResponded" type="submit">Mark Responded</button>
                </c:if>
                <c:if test="${requestScope.viewMessage.status != 'closed'}">
                  <button class="btn btn-danger btn-sm" name="action" value="markClosed" type="submit">Close</button>
                </c:if>
              </form>
            </div>
          </div>
          <hr style="margin:18px 0;">
          <div style="white-space:pre-wrap;line-height:1.7;"><c:out value="${requestScope.viewMessage.message}" /></div>
          <div style="margin-top:18px;">
            <a href="${pageContext.request.contextPath}/admin/messages" class="btn btn-secondary">&larr; Back to all messages</a>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <div class="card card-wide">
          <div class="table-wrap">
            <table class="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>From</th>
                  <th>Subject</th>
                  <th>Status</th>
                  <th>Received</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="m" items="${requestScope.messages}">
                  <tr>
                    <td><strong>#<c:out value="${m.id}" /></strong></td>
                    <td><c:out value="${m.name}" /> &lt;<c:out value="${m.email}" />&gt;</td>
                    <td><c:out value="${m.subject}" /></td>
                    <td>
                      <c:choose>
                        <c:when test="${m.status == 'new'}"><span class="badge badge-pending">New</span></c:when>
                        <c:when test="${m.status == 'responded'}"><span class="badge badge-approved">Responded</span></c:when>
                        <c:otherwise><span class="badge badge-rejected">Closed</span></c:otherwise>
                      </c:choose>
                    </td>
                    <td><c:out value="${m.createdAt}" /></td>
                    <td><a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/admin/messages?view=${m.id}">View</a></td>
                  </tr>
                </c:forEach>
                <c:if test="${empty requestScope.messages}">
                  <tr><td colspan="6"><div class="empty-state"><p>No messages received yet.</p></div></td></tr>
                </c:if>
              </tbody>
            </table>
          </div>
        </div>
      </c:otherwise>
    </c:choose>
  </main>
</div>
</div>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
