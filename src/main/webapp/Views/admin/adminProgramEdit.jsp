<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Program - HopeConnect</title>
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
      <h1>Edit Program</h1>
      <p>Update welfare aid program details</p>
    </div>

    <div class="card card-wide">
      <form method="post" action="${pageContext.request.contextPath}/admin/programs" class="stack">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="id" value="${requestScope.editProgram.id}">

        <div class="form-row">
          <div class="form-group">
            <label for="title">Program Title</label>
            <input id="title" class="form-control" name="title" value="<c:out value="${requestScope.editProgram.title}" />" required>
          </div>
          <div class="form-group">
            <label for="slug">URL Slug</label>
            <input id="slug" class="form-control" name="slug" value="<c:out value="${requestScope.editProgram.slug}" />" required>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="category">Category</label>
            <input id="category" class="form-control" name="category" value="<c:out value="${requestScope.editProgram.category}" />" required>
          </div>
          <div class="form-group">
            <label for="eligibility">Eligibility</label>
            <input id="eligibility" class="form-control" name="eligibility" value="<c:out value="${requestScope.editProgram.eligibility}" />" required>
          </div>
        </div>
        <div class="form-group">
          <label for="description">Description</label>
          <textarea id="description" class="form-control" name="description" rows="4"><c:out value="${requestScope.editProgram.description}" /></textarea>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="capacity">Capacity</label>
            <input id="capacity" class="form-control" type="number" min="0" name="capacity" value="${requestScope.editProgram.capacity}">
          </div>
          <div class="form-group">
            <label for="programStatus">Program Status</label>
            <select id="programStatus" class="form-control" name="programStatus">
              <option value="open" ${requestScope.editProgram.programStatus == 'open' ? 'selected' : ''}>Open</option>
              <option value="closed" ${requestScope.editProgram.programStatus == 'closed' ? 'selected' : ''}>Closed</option>
              <option value="archived" ${requestScope.editProgram.programStatus == 'archived' ? 'selected' : ''}>Archived</option>
            </select>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="startDate">Start Date</label>
            <input id="startDate" class="form-control" type="date" name="startDate" value="${requestScope.editProgram.startDate}">
          </div>
          <div class="form-group">
            <label for="endDate">End Date</label>
            <input id="endDate" class="form-control" type="date" name="endDate" value="${requestScope.editProgram.endDate}">
          </div>
        </div>
        <div class="form-group">
          <label for="published">Publish Status</label>
          <select id="published" class="form-control" name="published">
            <option value="1" ${requestScope.editProgram.published ? 'selected' : ''}>Published (Visible to Users)</option>
            <option value="0" ${not requestScope.editProgram.published ? 'selected' : ''}>Draft (Hidden from Users)</option>
          </select>
        </div>
        <div class="form-actions">
          <button class="btn btn-primary" type="submit">Save Changes</button>
          <a href="${pageContext.request.contextPath}/admin/programs" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </main>
</div>
</div>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
