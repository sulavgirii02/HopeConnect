<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - HopeConnect</title>
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
.stat-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 18px; margin-bottom: 30px; }
.stat-card { background: var(--color-white); border: 1px solid var(--color-gray-light); border-radius: var(--radius-lg); padding: 20px; box-shadow: var(--shadow-sm); }
.stat-card h3 { font-size: 0.9rem; color: var(--color-gray-mid); font-weight: 600; margin-bottom: 12px; text-transform: uppercase; letter-spacing: 0.4px; }
.stat-card .value { font-size: 2.2rem; font-weight: 700; color: var(--color-primary-dark); font-family: var(--font-heading); }
.section { margin-bottom: 30px; }
.section-title { font-size: 1.3rem; font-weight: 700; margin-bottom: 16px; color: var(--color-primary-darkest); }
@media (max-width: 1100px) {
  .admin-layout { flex-direction: column; }
  .admin-sidebar { width: 100%; height: auto; position: relative; top: 0; border-right: none; border-bottom: 1px solid var(--color-gray-light); padding: 16px 0; display: flex; flex-wrap: wrap; gap: 0; }
  .admin-sidebar h3 { flex-basis: 100%; padding: 0 16px; margin: 0; }
  .admin-sidebar a { flex: 1 1 150px; border-bottom: 1px solid var(--color-gray-light); border-left: none; padding: 12px 16px; }
  .admin-content { padding: 20px; }
  .admin-layout { gap: 0; }
  .admin-content .table { min-width: 520px; }
}
</style>

<div class="admin-layout">
  <aside class="admin-sidebar">
    <h3>Navigation</h3>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/programs">Programs</a>
    <a href="${pageContext.request.contextPath}/admin/applications">Applications</a>
    <a href="${pageContext.request.contextPath}/admin/users">Users</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Admin Dashboard</h1>
      <p>Overview of HopeConnect welfare program management</p>
    </div>

    <!-- Key Metrics -->
    <div class="stat-grid">
      <div class="stat-card">
        <h3>Total Users</h3>
        <div class="value"><c:out value="${requestScope.totalUsers}" default="0" /></div>
      </div>
      <div class="stat-card">
        <h3>Total Programs</h3>
        <div class="value"><c:out value="${requestScope.totalPrograms}" default="0" /></div>
      </div>
      <div class="stat-card">
        <h3>Published Programs</h3>
        <div class="value"><c:out value="${requestScope.publishedPrograms}" default="0" /></div>
      </div>
      <div class="stat-card">
        <h3>This Month's Applications</h3>
        <div class="value"><c:out value="${requestScope.totalApplicationsThisMonth}" default="0" /></div>
      </div>
      <div class="stat-card">
        <h3>Approval Ratio</h3>
        <div class="value"><c:out value="${requestScope.approvalRatio}" default="0" /></div>
      </div>
    </div>

    <div class="section">
      <h2 class="section-title">Recent Programs</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Program Name</th>
                <th>Category</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.recentPrograms}">
                  <c:forEach var="p" items="${requestScope.recentPrograms}">
                    <tr>
                      <td><c:out value="${p.id}" /></td>
                      <td><c:out value="${p.title}" /></td>
                      <td><c:out value="${p.category}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${p.published}">
                            <span class="badge badge-approved">Published</span>
                          </c:when>
                          <c:otherwise>
                            <span class="badge badge-pending">Draft</span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="4" style="text-align: center; color: var(--color-gray-mid);">No programs yet.</td></tr>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </div>
    </div>

    <div class="section">
      <h2 class="section-title">Recent Applications</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead>
              <tr>
                <th>Application ID</th>
                <th>User</th>
                <th>Program</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.recentApplications}">
                  <c:forEach var="a" items="${requestScope.recentApplications}">
                    <tr>
                      <td><c:out value="${a.id}" /></td>
                      <td><c:out value="${a.userId}" /></td>
                      <td><c:out value="${a.programId}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${a.status == 'approved'}"><span class="badge badge-approved">Approved</span></c:when>
                          <c:when test="${a.status == 'rejected'}"><span class="badge badge-rejected">Rejected</span></c:when>
                          <c:otherwise><span class="badge badge-pending">Pending</span></c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="4" style="text-align: center; color: var(--color-gray-mid);">No recent applications.</td></tr>
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

