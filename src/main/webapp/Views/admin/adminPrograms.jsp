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

<div class="container page admin-page">
<div class="admin-layout">
  <aside class="admin-sidebar">
    <h3>Navigation</h3>
    <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/programs" class="active">Programs</a>
    <a href="${pageContext.request.contextPath}/admin/applications">Applications</a>
    <a href="${pageContext.request.contextPath}/admin/users">Users</a>
    <a href="${pageContext.request.contextPath}/admin/messages">Messages</a>
    <h3>Account</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </aside>

  <main class="admin-content">
    <div class="admin-header">
      <h1>Manage Programs</h1>
      <p>Create, edit, and manage welfare aid programs</p>
    </div>

    <div class="section">
      <h2 class="section-title">Add New Program</h2>
      <div class="card">
        <form method="post" action="${pageContext.request.contextPath}/admin/programs" class="stack">
          <input type="hidden" name="action" value="create">
          <div class="form-row">
            <div class="form-group">
              <label for="title">Program Title</label>
              <input id="title" class="form-control" name="title" placeholder="e.g., Senior Citizens Allowance" required>
            </div>
            <div class="form-group">
              <label for="slug">URL Slug</label>
              <input id="slug" class="form-control" name="slug" placeholder="e.g., senior-allowance" required>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="category">Category</label>
              <input id="category" class="form-control" name="category" placeholder="e.g., Healthcare, Education" required>
            </div>
            <div class="form-group">
              <label for="eligibility">Eligibility</label>
              <input id="eligibility" class="form-control" name="eligibility" placeholder="e.g., Age 60+" required>
            </div>
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" class="form-control" name="description" rows="4" placeholder="Program details and benefits..."></textarea>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="capacity">Capacity</label>
              <input id="capacity" class="form-control" type="number" min="0" name="capacity" required>
            </div>
            <div class="form-group">
              <label for="programStatus">Program Status</label>
              <select id="programStatus" class="form-control" name="programStatus">
                <option value="open">Open</option>
                <option value="closed">Closed</option>
                <option value="archived">Archived</option>
              </select>
            </div>
          </div>
          <div class="form-row">
            <div class="form-group">
              <label for="startDate">Start Date</label>
              <input id="startDate" class="form-control" type="date" name="startDate">
            </div>
            <div class="form-group">
              <label for="endDate">End Date</label>
              <input id="endDate" class="form-control" type="date" name="endDate">
            </div>
          </div>
          <div class="form-group">
            <label for="published">Publish Status</label>
            <select id="published" class="form-control" name="published">
              <option value="1">Publish Now (Visible to Users)</option>
              <option value="0">Save as Draft (Hidden from Users)</option>
            </select>
          </div>
          <button class="btn btn-primary" type="submit">Create Program</button>
        </form>
      </div>
    </div>

    <div class="section">
      <h2 class="section-title">All Programs</h2>
      <div class="card">
        <div class="table-wrap">
          <table class="table">
            <thead><tr><th>ID</th><th>Title</th><th>Category</th><th>Capacity</th><th>Remaining</th><th>Program Status</th><th>Publish</th><th>End Date</th><th>Actions</th></tr></thead>
            <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.programs}">
                  <c:forEach var="p" items="${requestScope.programs}">
                    <tr>
                      <td><c:out value="${p.id}" /></td>
                      <td><c:out value="${p.title}" /></td>
                      <td><c:out value="${p.category}" /></td>
                      <td><c:out value="${p.capacity}" /></td>
                      <td><c:out value="${p.remainingCapacity}" /></td>
                      <td><c:out value="${p.programStatus}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${p.published}"><span class="badge badge-approved">Published</span></c:when>
                          <c:otherwise><span class="badge badge-pending">Draft</span></c:otherwise>
                        </c:choose>
                      </td>
                      <td><c:out value="${p.endDate}" /></td>
                      <td>
                        <form method="post" action="${pageContext.request.contextPath}/admin/programs" class="form-actions" style="gap:6px;">
                          <input type="hidden" name="id" value="${p.id}">
                          <input type="hidden" name="published" value="${p.published ? 0 : 1}">
                          <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/admin/programs?edit=${p.id}">Edit</a>
                          <button class="btn btn-secondary btn-sm" name="action" value="toggleStatus" type="submit"><c:out value="${p.published ? 'Unpublish' : 'Publish'}" /></button>
                          <button class="btn btn-danger btn-sm" name="action" value="delete" type="submit">Delete</button>
                        </form>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td colspan="9">
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
