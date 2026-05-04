<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Applications - HopeConnect</title>
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
.app-row-form { display: flex; gap: 8px; align-items: center; flex-wrap: wrap; }
.app-row-form button { flex: 0 1 auto; padding: 8px 14px; font-size: 0.85rem; }
.app-row-form input { flex: 1; min-width: 120px; max-width: 180px; }
@media (max-width: 1100px) {
  .admin-layout { flex-direction: column; }
  .admin-sidebar { width: 100%; height: auto; position: relative; top: 0; border-right: none; border-bottom: 1px solid var(--color-gray-light); padding: 16px 0; display: flex; flex-wrap: wrap; gap: 0; }
  .admin-sidebar h3 { flex-basis: 100%; padding: 0 16px; margin: 0; }
  .admin-sidebar a { flex: 1 1 150px; border-bottom: 1px solid var(--color-gray-light); border-left: none; padding: 12px 16px; }
  .admin-content { padding: 20px; }
  .app-row-form { flex-direction: column; align-items: stretch; }
  .app-row-form button, .app-row-form input { width: 100%; max-width: none; }
  .admin-content .table { min-width: 520px; }
}
</style>

<div class="admin-layout">
  <aside class="admin-sidebar">
    <h3>Navigation</h3>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/programs">Programs</a>
    <a href="${pageContext.request.contextPath}/admin/applications" class="active">Applications</a>
    <a href="${pageContext.request.contextPath}/admin/users">Users</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Review Applications</h1>
      <p>Approve or reject user applications for welfare programs</p>
    </div>

    <div class="section">
      <h2 class="section-title">Pending & Historical Applications</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>User</th>
                <th>Program</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.applications}">
                  <c:forEach var="a" items="${requestScope.applications}">
                    <tr>
                      <td><strong>#<c:out value="${a.id}" /></strong></td>
                      <td>
                        <div><c:out value="${a.userName}" /></div>
                        <div style="font-size: 0.85rem; color: var(--color-gray-mid);"><c:out value="${a.userEmail}" /></div>
                      </td>
                      <td><c:out value="${a.programTitle}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${a.status == 'approved'}">
                            <span class="badge badge-approved">Approved</span>
                          </c:when>
                          <c:when test="${a.status == 'rejected'}">
                            <span class="badge badge-rejected">Rejected</span>
                          </c:when>
                          <c:otherwise>
                            <span class="badge badge-pending">Pending</span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/applications" class="app-row-form">
                          <input type="hidden" name="id" value="${a.id}">
                          <button class="btn btn-primary" name="action" value="approve" type="submit">Approve</button>
                          <button class="btn btn-danger" name="action" value="reject" type="submit">Reject</button>
                          <input type="text" name="reason" class="form-control" placeholder="Reason (optional)" style="padding: 8px 10px; font-size: 0.85rem;">
                        </form>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="5" style="text-align: center; padding: 30px; color: var(--color-gray-mid);">No applications found.</td></tr>
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

