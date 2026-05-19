<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Users - HopeConnect</title>
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
    <a href="${pageContext.request.contextPath}/admin/users" class="active">Users</a>
    <a href="${pageContext.request.contextPath}/admin/messages">Messages</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Manage Users</h1>
      <p>Verify and manage HopeConnect user accounts</p>
    </div>

    <c:if test="${not empty sessionScope.flashMessage}">
      <c:choose>
        <c:when test="${sessionScope.flashType == 'success'}">
          <div class="alert alert-success" role="alert">
            <c:out value="${sessionScope.flashMessage}" />
          </div>
        </c:when>
        <c:when test="${sessionScope.flashType == 'error'}">
          <div class="alert alert-error" role="alert">
            <c:out value="${sessionScope.flashMessage}" />
          </div>
        </c:when>
        <c:otherwise>
          <div class="alert alert-info" role="alert">
            <c:out value="${sessionScope.flashMessage}" />
          </div>
        </c:otherwise>
      </c:choose>
      <c:remove var="flashMessage" scope="session" />
      <c:remove var="flashType" scope="session" />
    </c:if>

    <div class="section">
      <h2 class="section-title">All Users</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead><tr><th>ID</th><th>Full Name</th><th>Email</th><th>Phone</th><th>Role</th><th>Status</th><th>Created</th><th>Actions</th></tr></thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.users}">
                  <c:forEach var="u" items="${requestScope.users}">
                    <tr>
                      <td><strong>#<c:out value="${u.id}" /></strong></td>
                      <td><c:out value="${u.fullName}" /></td>
                      <td><c:out value="${u.email}" /></td>
                      <td><c:out value="${u.phone}" /></td>
                      <td><c:out value="${u.role}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${u.status == 'active'}"><span class="badge badge-approved">Active</span></c:when>
                          <c:when test="${u.status == 'pending'}"><span class="badge badge-pending">Pending Approval</span></c:when>
                          <c:when test="${u.status == 'suspended'}"><span class="badge badge-rejected">Suspended</span></c:when>
                          <c:when test="${u.status == 'deactivated'}"><span class="badge badge-rejected">Deactivated</span></c:when>
                          <c:otherwise><c:out value="${u.status}" /></c:otherwise>
                        </c:choose>
                      </td>
                      <td><c:out value="${u.createdAt}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${sessionScope.user.id == u.id}">
                            <span class="muted">Current admin</span>
                          </c:when>
                          <c:when test="${u.role == 'admin'}">
                            <span class="muted">Protected admin</span>
                          </c:when>
                          <c:otherwise>
                            <div class="form-actions" style="gap:6px;">
                              <c:if test="${u.status == 'pending'}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/users">
                                  <input type="hidden" name="userId" value="${u.id}">
                                  <input type="hidden" name="action" value="approve">
                                  <button class="btn btn-primary btn-sm" type="submit">Approve</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/admin/users">
                                  <input type="hidden" name="userId" value="${u.id}">
                                  <input type="hidden" name="action" value="deactivate">
                                  <button class="btn btn-danger btn-sm" type="submit">Deactivate</button>
                                </form>
                              </c:if>
                              <c:if test="${u.status == 'active'}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/users">
                                  <input type="hidden" name="userId" value="${u.id}">
                                  <input type="hidden" name="action" value="suspend">
                                  <button class="btn btn-secondary btn-sm" type="submit">Suspend</button>
                                </form>
                                <form method="post" action="${pageContext.request.contextPath}/admin/users">
                                  <input type="hidden" name="userId" value="${u.id}">
                                  <input type="hidden" name="action" value="deactivate">
                                  <button class="btn btn-danger btn-sm" type="submit">Deactivate</button>
                                </form>
                              </c:if>
                              <c:if test="${u.status == 'suspended' || u.status == 'deactivated'}">
                                <form method="post" action="${pageContext.request.contextPath}/admin/users">
                                  <input type="hidden" name="userId" value="${u.id}">
                                  <input type="hidden" name="action" value="reactivate">
                                  <button class="btn btn-primary btn-sm" type="submit">Reactivate</button>
                                </form>
                              </c:if>
                            </div>
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td colspan="8">
                      <div class="empty-state" style="padding: 40px 20px;">
                        <span class="empty-state-icon">
                          <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="#94A3B8" stroke-width="1.5">
                            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
                            <polyline points="14 2 14 8 20 8"/>
                          </svg>
                        </span>
                        <h3>No records found</h3>
                        <p>Data will appear here once available.</p>
                      </div>
                    </td>
                  </tr>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </main>
</div>
</div>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
