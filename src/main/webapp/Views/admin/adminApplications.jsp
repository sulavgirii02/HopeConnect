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

<div class="container page admin-page">
  <div class="admin-layout">
    <aside class="admin-sidebar">
      <h3>Navigation</h3>
      <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
      <a href="${pageContext.request.contextPath}/admin/programs">Programs</a>
      <a href="${pageContext.request.contextPath}/admin/applications" class="active">Applications</a>
      <a href="${pageContext.request.contextPath}/admin/users">Users</a>
      <a href="${pageContext.request.contextPath}/admin/messages">Messages</a>
      <h3>Account</h3>
      <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </aside>

    <main class="admin-content">
      <div class="admin-header">
        <h1>Review Applications</h1>
        <p>Approve or reject user applications for welfare programs</p>
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
        <h2 class="section-title">Pending & Historical Applications</h2>
        <form method="get" action="${pageContext.request.contextPath}/admin/applications" class="filter-bar">
          <div class="filter-group">
            <label class="filter-label" for="status">Status</label>
            <select id="status" name="status" class="filter-select">
              <option value="all" ${requestScope.selectedStatus == 'all' ? 'selected' : ''}>All Applications</option>
              <option value="pending" ${requestScope.selectedStatus == 'pending' ? 'selected' : ''}>Pending</option>
              <option value="approved" ${requestScope.selectedStatus == 'approved' ? 'selected' : ''}>Approved</option>
              <option value="rejected" ${requestScope.selectedStatus == 'rejected' ? 'selected' : ''}>Rejected</option>
            </select>
          </div>
          <div class="filter-group">
            <label class="filter-label" for="programId">Program</label>
            <select id="programId" name="programId" class="filter-select">
              <option value="">All Programs</option>
              <c:forEach var="p" items="${requestScope.programs}">
                <option value="${p.id}" ${requestScope.selectedProgramId == p.id ? 'selected' : ''}><c:out value="${p.title}" /></option>
              </c:forEach>
            </select>
          </div>
          <div class="filter-actions">
            <button class="btn btn-primary" type="submit">Filter</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/admin/applications">Clear</a>
          </div>
        </form>
        <div class="card">
          <div class="table-wrap">
            <table class="table">
              <thead><tr><th>ID</th><th>User</th><th>Program</th><th>Status</th><th>Actions</th></tr></thead>
              <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.applications}">
                  <c:forEach var="a" items="${requestScope.applications}">
                    <tr>
                      <td><strong>#<c:out value="${a.id}" /></strong></td>
                      <td>
                        <div><c:out value="${a.userName}" /></div>
                        <div class="muted" style="font-size:0.85rem;"><c:out value="${a.userEmail}" /></div>
                      </td>
                      <td><c:out value="${a.programTitle}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${a.status == 'approved'}"><span class="badge badge-approved">Approved</span></c:when>
                          <c:when test="${a.status == 'rejected'}"><span class="badge badge-rejected">Rejected</span></c:when>
                          <c:otherwise><span class="badge badge-pending">Pending</span></c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${a.status == 'pending'}">
                            <form method="post" action="${pageContext.request.contextPath}/admin/applications" class="app-row-form">
                              <input type="hidden" name="id" value="${a.id}">
                              <button class="btn btn-primary btn-sm" name="action" value="approve" type="submit">Approve</button>
                              <button class="btn btn-danger btn-sm" name="action" value="reject" type="submit">Reject</button>
                              <input type="text" name="reason" class="form-control" placeholder="Reason (optional)" style="padding:8px 10px;font-size:0.85rem;">
                            </form>
                          </c:when>
                          <c:otherwise>
                            <span style="color:#94A3B8; font-size:0.9rem;">No actions available</span>
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td colspan="5">
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
        <c:if test="${requestScope.totalPages > 1}">
          <div class="pagination">
            <a class="pagination-link ${requestScope.currentPage == 1 ? 'disabled' : ''}" href="${pageContext.request.contextPath}/admin/applications?page=${requestScope.currentPage - 1}&status=${requestScope.selectedStatus}&programId=${requestScope.selectedProgramId}">Previous</a>
            <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
              <a class="pagination-link ${requestScope.currentPage == i ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/applications?page=${i}&status=${requestScope.selectedStatus}&programId=${requestScope.selectedProgramId}">${i}</a>
            </c:forEach>
            <a class="pagination-link ${requestScope.currentPage == requestScope.totalPages ? 'disabled' : ''}" href="${pageContext.request.contextPath}/admin/applications?page=${requestScope.currentPage + 1}&status=${requestScope.selectedStatus}&programId=${requestScope.selectedProgramId}">Next</a>
          </div>
        </c:if>
      </div>
    </main>
  </div>
</div>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>