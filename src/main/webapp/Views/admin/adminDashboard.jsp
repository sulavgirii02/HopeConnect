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

<div class="container page admin-page">
  <div class="admin-layout">
    <aside class="admin-sidebar">
      <h3>Navigation</h3>
      <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a>
      <a href="${pageContext.request.contextPath}/admin/programs">Programs</a>
      <a href="${pageContext.request.contextPath}/admin/applications">Applications</a>
      <a href="${pageContext.request.contextPath}/admin/users">Users</a>
      <a href="${pageContext.request.contextPath}/admin/messages">Messages</a>
      <h3>Account</h3>
      <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </aside>

    <main class="admin-content">
      <div class="admin-header">
        <h1>Admin Dashboard</h1>
        <p>Overview of HopeConnect welfare program management</p>
      </div>

      <div class="stat-grid">
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/>
            <path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.totalUsers}" default="0" /></div>
          <div class="stat-label">Total Users</div>
          <div class="stat-desc">Registered accounts</div>
        </div>
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
            <polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.totalApplicationsThisMonth}" default="0" /></div>
          <div class="stat-label">Applications This Month</div>
          <div class="stat-desc">Submitted in current period</div>
        </div>
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <polyline points="20 6 9 17 4 12"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.approvalRatio}" default="0%" /></div>
          <div class="stat-label">Approval Rate</div>
          <div class="stat-desc">Approved vs total processed</div>
        </div>
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/>
            <polyline points="14 2 14 8 20 8"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.pendingApplicationCount}" default="0" /></div>
          <div class="stat-label">Pending Requests</div>
          <div class="stat-desc">Applications awaiting review</div>
        </div>
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <path d="M21 15a4 4 0 0 1-4 4H8l-5 3V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4z"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.newMessageCount}" default="0" /></div>
          <div class="stat-label">New Messages</div>
          <div class="stat-desc">Customer inquiries waiting</div>
        </div>
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <rect x="3" y="4" width="18" height="16" rx="2"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.totalPrograms}" default="0" /></div>
          <div class="stat-label">Total Programs</div>
          <div class="stat-desc"><c:out value="${requestScope.publishedPrograms}" default="0" /> published</div>
        </div>
        <div class="stat-card">
        <span class="stat-icon">
          <svg width="26" height="26" viewBox="0 0 24 24" fill="none" stroke="var(--color-primary)" stroke-width="2">
            <circle cx="12" cy="12" r="9"/>
          </svg>
        </span>
          <div class="stat-value"><c:out value="${requestScope.openPrograms}" default="0" /></div>
          <div class="stat-label">Open Programs</div>
          <div class="stat-desc"><c:out value="${requestScope.closedPrograms}" default="0" /> closed/full</div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">Recent Notifications</h2>
        <div class="card">
          <div class="table-wrap">
            <table class="table">
              <thead><tr><th>Time</th><th>Title</th><th>Type</th><th>Status</th></tr></thead>
              <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.recentNotifications}">
                  <c:forEach var="n" items="${requestScope.recentNotifications}">
                    <tr>
                      <td class="muted" style="font-size:0.85rem;"><c:out value="${n.createdAt}" /></td>
                      <td><c:out value="${n.title}" /></td>
                      <td><c:out value="${n.type}" /></td>
                      <td><c:out value="${n.read ? 'Read' : 'Unread'}" /></td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="4" class="text-center muted" style="padding:24px;">No notifications yet.</td></tr>
                </c:otherwise>
              </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">Customer Notifications</h2>
        <div class="card">
          <div class="table-wrap">
            <table class="table">
              <thead><tr><th>Time</th><th>Customer</th><th>Notification</th><th>Status</th><th>Action</th></tr></thead>
              <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.customerNotifications}">
                  <c:forEach var="n" items="${requestScope.customerNotifications}">
                    <tr>
                      <td class="muted" style="font-size:0.85rem;"><c:out value="${n.createdAt}" /></td>
                      <td><c:out value="${n.customerName}" /></td>
                      <td><c:out value="${n.summary}" /></td>
                      <td>
                        <c:choose>
                          <c:when test="${n.status == 'pending' or n.status == 'new'}"><span class="badge badge-pending"><c:out value="${n.status}" /></span></c:when>
                          <c:when test="${n.status == 'approved' or n.status == 'responded'}"><span class="badge badge-approved"><c:out value="${n.status}" /></span></c:when>
                          <c:otherwise><span class="badge badge-rejected"><c:out value="${n.status}" /></span></c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${n.type == 'application'}">
                            <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/admin/applications">Review</a>
                          </c:when>
                          <c:otherwise>
                            <a class="btn btn-secondary btn-sm" href="${pageContext.request.contextPath}/admin/messages?view=${n.entityId}">Open</a>
                          </c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="5" class="text-center muted" style="padding:24px;">No customer activity yet.</td></tr>
                </c:otherwise>
              </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">Applications per Program</h2>
        <div class="card" style="padding: 24px 28px;">
          <c:choose>
            <c:when test="${not empty requestScope.topPrograms}">
              <div style="display: flex; flex-direction: column; gap: 14px;">
                <c:forEach var="p" items="${requestScope.topPrograms}">
                  <div>
                    <div style="display: flex; justify-content: space-between; margin-bottom: 6px; font-size: 0.9rem;">
                      <span style="font-weight: 600; color: var(--color-gray-dark);"><c:out value="${p.title}"/></span>
                      <span style="color: var(--color-primary-dark); font-weight: 700;"><c:out value="${p.count}"/> applications</span>
                    </div>
                    <div style="background: var(--color-gray-light); border-radius: 6px; height: 10px; overflow: hidden;">
                      <div style="background: var(--color-primary); height: 10px; border-radius: 6px;
                              width: ${(p.count * 100) / requestScope.maxProgramCount}%;
                              transition: width 0.6s ease;"></div>
                    </div>
                  </div>
                </c:forEach>
              </div>
            </c:when>
            <c:otherwise>
              <div class="empty-state">
                <span class="empty-state-icon">📊</span>
                <p>No program data yet.</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <!-- Approval Ratio Donut (CSS only) -->
      <div class="section">
        <h2 class="section-title">Approval Ratio</h2>
        <div class="card" style="display: flex; align-items: center; gap: 32px; padding: 24px 28px;">
          <div style="position: relative; width: 100px; height: 100px; flex-shrink: 0;">
            <svg viewBox="0 0 36 36" width="100" height="100" style="transform: rotate(-90deg);">
              <circle cx="18" cy="18" r="15.9" fill="none" stroke="var(--color-gray-light)" stroke-width="3.8"/>
              <circle cx="18" cy="18" r="15.9" fill="none" stroke="var(--color-primary)" stroke-width="3.8"
                      stroke-dasharray="${requestScope.approvalRatioNum} ${100 - requestScope.approvalRatioNum}"
                      stroke-linecap="round"/>
            </svg>
            <div style="position: absolute; inset: 0; display: flex; align-items: center; justify-content: center;
               font-size: 1.1rem; font-weight: 700; color: var(--color-primary-dark);">
              <c:out value="${requestScope.approvalRatio}"/>
            </div>
          </div>
          <div>
            <div style="font-size: 0.9rem; color: var(--color-gray-mid); margin-bottom: 8px;">Applications reviewed this period</div>
            <div style="display: flex; gap: 20px; font-size: 0.9rem;">
              <span><span style="color: var(--color-success); font-weight: 700;">●</span> Approved</span>
              <span><span style="color: var(--color-red); font-weight: 700;">●</span> Rejected</span>
              <span><span style="color: var(--color-amber); font-weight: 700;">●</span> Pending</span>
            </div>
          </div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">Recent Applications</h2>
        <div class="card">
          <div class="table-wrap">
            <table class="table">
              <thead><tr><th>ID</th><th>User</th><th>Program</th><th>Status</th></tr></thead>
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
                          <c:when test="${a.status == 'pending'}"><span class="badge badge-pending">Pending</span></c:when>
                          <c:when test="${a.status == 'approved'}"><span class="badge badge-approved">Approved</span></c:when>
                          <c:when test="${a.status == 'rejected'}"><span class="badge badge-rejected">Rejected</span></c:when>
                          <c:otherwise><c:out value="${a.status}" /></c:otherwise>
                        </c:choose>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="4" class="text-center muted" style="padding:24px;">No recent applications.</td></tr>
                </c:otherwise>
              </c:choose>
              </tbody>
            </table>
          </div>
        </div>
      </div>

      <div class="section">
        <h2 class="section-title">Recent System Activity</h2>
        <div class="card">
          <div class="table-wrap">
            <table class="table">
              <thead>
              <tr>
                <th>Time</th>
                <th>Admin ID</th>
                <th>Action</th>
                <th>Entity</th>
                <th>Description</th>
              </tr>
              </thead>
              <tbody>
              <c:choose>
                <c:when test="${not empty requestScope.recentLogs}">
                  <c:forEach var="log" items="${requestScope.recentLogs}">
                    <tr>
                      <td class="muted" style="font-size:0.85rem;"><c:out value="${log.createdAt}"/></td>
                      <td><c:out value="${log.actorId}"/></td>
                      <td><span class="badge badge-info"><c:out value="${log.action}"/></span></td>
                      <td><c:out value="${log.entityType}"/> #<c:out value="${log.entityId}"/></td>
                      <td><c:out value="${log.details}"/></td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr><td colspan="5" class="text-center muted" style="padding:24px;">No audit entries yet.</td></tr>
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