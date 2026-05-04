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

<main class="container page admin-page" style="display: flex; flex-direction: column; flex: 1 1 auto;">
<style>
.admin-layout { display: flex; gap: 0; flex: 1 1 auto; min-height: calc(100vh - 58px); margin: 0; width: 100%; }
.admin-sidebar {
  flex: 0 0 260px;
  width: 260px;
  background: var(--color-primary-ghost);
  border-right: 1px solid var(--color-gray-light);
  padding: 20px 0;
  position: sticky;
  top: 58px;
  min-height: calc(100vh - 58px);
  max-height: calc(100vh - 58px);
  overflow-y: auto;
}
.admin-sidebar h3 { padding: 0 16px; font-size: 0.85rem; font-weight: 700; color: var(--color-primary-dark); text-transform: uppercase; margin-bottom: 12px; margin-top: 16px; }
.admin-sidebar h3:first-child { margin-top: 0; }
.admin-sidebar a {
  display: block;
  padding: 10px 16px;
  color: var(--color-gray-dark);
  text-decoration: none;
  font-size: 0.95rem;
  transition: var(--transition);
  border-left: 3px solid transparent;
}
.admin-sidebar a:hover { background: var(--color-white); color: var(--color-primary-dark); border-left-color: var(--color-primary); }
.admin-sidebar a.active { background: var(--color-white); color: var(--color-primary-dark); border-left-color: var(--color-primary); font-weight: 600; }
.admin-content { flex: 1 1 auto; min-height: 0; min-width: 0; max-width: 1180px; margin: 0 auto; padding: 40px 48px; }
.admin-content .table { min-width: 520px; }
.admin-header { margin-bottom: 30px; }
.admin-header h1 { margin-bottom: 6px; }
.admin-header p { color: var(--color-gray-mid); font-size: 14px; }
.section { margin-bottom: 30px; }
.section-title { font-size: 1.1rem; font-weight: 700; margin-bottom: 16px; color: var(--color-primary-darkest); }
.user-status-badge { display: inline-block; }
.status-active { color: var(--color-primary-dark); }
.status-suspended { color: var(--color-red); }
.status-deleted { color: var(--color-gray-mid); }
@media (max-width: 1100px) {
  .admin-layout { flex-direction: column; }
  .admin-sidebar { width: 100%; height: auto; position: relative; top: 0; border-right: none; border-bottom: 1px solid var(--color-gray-light); padding: 16px 0; display: flex; flex-wrap: wrap; gap: 0; }
  .admin-sidebar h3 { flex-basis: 100%; padding: 0 16px; margin: 0; }
  .admin-sidebar a { flex: 1 1 150px; border-bottom: 1px solid var(--color-gray-light); border-left: none; padding: 12px 16px; }
  .admin-content { padding: 20px; }
  .admin-content .table { min-width: 520px; }
}
</style>

<div class="admin-layout">
  <aside class="admin-sidebar">
    <h3>Navigation</h3>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/programs">Programs</a>
    <a href="${pageContext.request.contextPath}/admin/applications">Applications</a>
    <a href="${pageContext.request.contextPath}/admin/users" class="active">Users</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Manage Users</h1>
      <p>Verify and manage HopeConnect user accounts</p>
    </div>

    <div class="section">
      <h2 class="section-title">All Users</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Email</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.users}">
                  <c:forEach var="u" items="${requestScope.users}">
                    <tr>
                      <td><strong>#<c:out value="${u.id}" /></strong></td>
                      <td><c:out value="${u.fullName}" /></td>
                      <td>
                        <div><c:out value="${u.email}" /></div>
                      </td>
                      <td>
                        <span class="user-status-badge status-${u.status}">
                          <c:choose>
                            <c:when test="${u.status == 'active'}">
                              <span class="badge badge-approved">Active</span>
                            </c:when>
                            <c:when test="${u.status == 'suspended'}">
                              <span class="badge badge-rejected">Suspended</span>
                            </c:when>
                            <c:otherwise>
                              <c:out value="${u.status}" />
                            </c:otherwise>
                          </c:choose>
                        </span>
                      </td>
                      <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/users" class="form-actions" style="gap: 6px;">
                          <input type="hidden" name="id" value="${u.id}">
                          <button class="btn btn-secondary" name="action" value="verify" type="submit" style="font-size: 0.85rem; padding: 8px 14px;">Verify</button>
                          <button class="btn btn-danger" name="action" value="deactivate" type="submit" style="font-size: 0.85rem; padding: 8px 14px;">Deactivate</button>
                        </form>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="5" style="text-align: center; padding: 30px; color: var(--color-gray-mid);">No users found.</td></tr>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </main>
</div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>

