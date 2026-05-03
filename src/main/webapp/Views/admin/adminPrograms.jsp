<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Programs - HopeConnect</title>
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
.admin-content { flex: 1 1 auto; min-height: 0; min-width: 0; width: 100%; max-width: none; margin: 0; padding: 36px 40px; }
.admin-content .table { min-width: 720px; }
.admin-header { margin-bottom: 30px; }
.admin-header h1 { margin-bottom: 6px; }
.admin-header p { color: var(--color-gray-mid); font-size: 14px; }
.section { margin-bottom: 30px; }
.section-title { font-size: 1.1rem; font-weight: 700; margin-bottom: 16px; color: var(--color-primary-darkest); }
.programs-card { padding: 0; overflow: hidden; }
.programs-card .table-wrap { width: 100%; overflow-x: auto; }
.programs-card .table { border: 0; min-width: 760px; }
.programs-card .table th,
.programs-card .table td { padding: 16px; vertical-align: middle; }
.programs-card .table th:nth-child(1),
.programs-card .table td:nth-child(1) { width: 80px; }
.programs-card .table th:nth-child(3),
.programs-card .table td:nth-child(3) { width: 150px; }
.programs-card .table th:nth-child(4),
.programs-card .table td:nth-child(4) { width: 150px; }
.programs-card .table th:nth-child(5),
.programs-card .table td:nth-child(5) { width: 300px; }
.program-actions { display: flex; flex-wrap: wrap; gap: 8px; align-items: center; }
.program-actions form { margin: 0; }
.program-actions .btn { min-width: 74px; padding: 8px 14px; font-size: 0.85rem; white-space: nowrap; }
@media (max-width: 1100px) {
  .admin-layout { flex-direction: column; }
  .admin-sidebar { width: 100%; height: auto; position: relative; top: 0; border-right: none; border-bottom: 1px solid var(--color-gray-light); padding: 16px 0; display: flex; flex-wrap: wrap; gap: 0; }
  .admin-sidebar h3 { flex-basis: 100%; padding: 0 16px; margin: 0; }
  .admin-sidebar a { flex: 1 1 150px; border-bottom: 1px solid var(--color-gray-light); border-left: none; padding: 12px 16px; }
  .admin-content { padding: 22px 16px; }
  .admin-content .table { min-width: 720px; }
  .programs-card .table th,
  .programs-card .table td { padding: 14px; }
}
</style>

<div class="admin-layout">
  <aside class="admin-sidebar">
    <h3>Navigation</h3>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/programs" class="active">Programs</a>
    <a href="${pageContext.request.contextPath}/admin/applications">Applications</a>
    <a href="${pageContext.request.contextPath}/admin/users">Users</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Manage Programs</h1>
      <p>Create, edit, and manage welfare aid programs</p>
    </div>

    <c:if test="${not empty sessionScope.flash_success}">
      <div class="success-text" style="margin-bottom: 16px; padding: 10px; background: var(--color-primary-tint); border-left: 3px solid var(--color-primary); border-radius: var(--radius-sm);">
        <c:out value="${sessionScope.flash_success}" />
      </div>
      <c:remove var="flash_success" scope="session" />
    </c:if>

    <c:if test="${not empty sessionScope.flash_error}">
      <div class="error-text" style="margin-bottom: 16px; padding: 10px; background: var(--color-red-ghost); border-left: 3px solid var(--color-red); border-radius: var(--radius-sm);">
        <c:out value="${sessionScope.flash_error}" />
      </div>
      <c:remove var="flash_error" scope="session" />
    </c:if>

    <c:if test="${not empty requestScope.error}">
      <div class="error-text" style="margin-bottom: 16px; padding: 10px; background: var(--color-red-ghost); border-left: 3px solid var(--color-red); border-radius: var(--radius-sm);">
        <c:out value="${requestScope.error}" />
      </div>
    </c:if>

    <!-- Create New Program Section -->
    <div class="section">
      <h2 class="section-title">Add New Program</h2>
      <div class="card programs-card">
        <form method="post" action="${pageContext.request.contextPath}/admin/programs" class="stack">
          <input type="hidden" name="action" value="create">
          
          <div class="form-row">
            <div class="form-group">
              <label for="title" style="font-weight: 600; color: var(--color-gray-dark);">Program Title</label>
              <input id="title" class="form-control" name="title" placeholder="e.g., Senior Citizens Allowance" required>
            </div>
            <div class="form-group">
              <label for="slug" style="font-weight: 600; color: var(--color-gray-dark);">URL Slug</label>
              <input id="slug" class="form-control" name="slug" pattern="[a-z0-9]+(-[a-z0-9]+)*" placeholder="e.g., senior-allowance" required>
            </div>
          </div>
          
          <div class="form-row">
            <div class="form-group">
              <label for="category" style="font-weight: 600; color: var(--color-gray-dark);">Category</label>
              <input id="category" class="form-control" name="category" placeholder="e.g., Healthcare, Education" required>
            </div>
            <div class="form-group">
              <label for="eligibility" style="font-weight: 600; color: var(--color-gray-dark);">Eligibility</label>
              <input id="eligibility" class="form-control" name="eligibility" placeholder="e.g., Age 60+" required>
            </div>
          </div>
          
          <div class="form-group">
            <label for="description" style="font-weight: 600; color: var(--color-gray-dark);">Description</label>
            <textarea id="description" class="form-control" name="description" rows="4" placeholder="Program details and benefits..." required></textarea>
          </div>
          
          <div class="form-group">
            <label for="published" style="font-weight: 600; color: var(--color-gray-dark);">Publish Status</label>
            <select id="published" class="form-control" name="published">
              <option value="1">Publish Now (Visible to Users)</option>
              <option value="0">Save as Draft (Hidden from Users)</option>
            </select>
          </div>
          
          <button class="btn btn-primary" type="submit">Create Program</button>
        </form>
      </div>
    </div>

    <c:if test="${not empty requestScope.editProgram}">
      <div class="section">
        <h2 class="section-title">Edit Program</h2>
        <div class="card">
          <form method="post" action="${pageContext.request.contextPath}/admin/programs" class="stack">
            <input type="hidden" name="id" value="${requestScope.editProgram.id}">

            <div class="form-row">
              <div class="form-group">
                <label for="edit-title" style="font-weight: 600; color: var(--color-gray-dark);">Program Title</label>
                <input id="edit-title" class="form-control" name="title" value="<c:out value='${requestScope.editProgram.title}' />" required>
              </div>
              <div class="form-group">
                <label for="edit-slug" style="font-weight: 600; color: var(--color-gray-dark);">URL Slug</label>
                <input id="edit-slug" class="form-control" name="slug" value="<c:out value='${requestScope.editProgram.slug}' />" pattern="[a-z0-9]+(-[a-z0-9]+)*" required>
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label for="edit-category" style="font-weight: 600; color: var(--color-gray-dark);">Category</label>
                <input id="edit-category" class="form-control" name="category" value="<c:out value='${requestScope.editProgram.category}' />" required>
              </div>
              <div class="form-group">
                <label for="edit-eligibility" style="font-weight: 600; color: var(--color-gray-dark);">Eligibility</label>
                <input id="edit-eligibility" class="form-control" name="eligibility" value="<c:out value='${requestScope.editProgram.eligibility}' />" required>
              </div>
            </div>

            <div class="form-group">
              <label for="edit-description" style="font-weight: 600; color: var(--color-gray-dark);">Description</label>
              <textarea id="edit-description" class="form-control" name="description" rows="4" required><c:out value="${requestScope.editProgram.description}" /></textarea>
            </div>

            <div class="form-group">
              <label for="edit-published" style="font-weight: 600; color: var(--color-gray-dark);">Status</label>
              <select id="edit-published" class="form-control" name="published">
                <c:choose>
                  <c:when test="${requestScope.editProgram.published}">
                    <option value="1" selected>Published</option>
                    <option value="0">Draft</option>
                  </c:when>
                  <c:otherwise>
                    <option value="1">Published</option>
                    <option value="0" selected>Draft</option>
                  </c:otherwise>
                </c:choose>
              </select>
            </div>

            <div class="form-actions">
              <button class="btn btn-primary" name="action" value="update" type="submit">Update Program</button>
              <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/programs">Cancel</a>
            </div>
          </form>
        </div>
      </div>
    </c:if>

    <!-- Programs List Section -->
    <div class="section">
      <h2 class="section-title">Programs</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Details</th>
                <th>Category</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.programs}">
                  <c:forEach var="p" items="${requestScope.programs}">
                    <tr>
                      <td><c:out value="${p.id}" /></td>
                      <td>
                        <strong><c:out value="${p.title}" /></strong>
                        <div class="muted"><c:out value="${p.slug}" /></div>
                      </td>
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
                      <td>
                        <div class="program-actions">
                          <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/programs?editId=${p.id}">Edit</a>
                          <form method="post" action="${pageContext.request.contextPath}/admin/programs">
                            <input type="hidden" name="id" value="${p.id}">
                            <input type="hidden" name="published" value="${p.published ? 0 : 1}">
                            <button class="btn btn-secondary" name="action" value="toggleStatus" type="submit">
                              <c:out value="${p.published ? 'Unpublish' : 'Publish'}" />
                            </button>
                          </form>
                          <form method="post" action="${pageContext.request.contextPath}/admin/programs">
                            <input type="hidden" name="id" value="${p.id}">
                            <button class="btn btn-danger" name="action" value="delete" type="submit">Delete</button>
                          </form>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="5" style="text-align: center; padding: 30px; color: var(--color-gray-mid);">No programs found. Create your first program above.</td></tr>
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

