<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Dashboard - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="user-page-header">
        <h1>Welcome, <c:out value="${sessionScope.user.fullName}" /></h1>
        <p>Manage your applications and explore welfare programs</p>
        <div class="action-grid">
            <a class="action-card" href="${pageContext.request.contextPath}/profile">
                <div><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align:middle;margin-right:4px;"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg> My Profile</div>
            </a>
            <a class="action-card" href="${pageContext.request.contextPath}/programs">
                <div><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align:middle;margin-right:4px;"><rect x="2" y="3" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg> Explore Programs</div>
            </a>
            <a class="action-card" href="${pageContext.request.contextPath}/my-applications">
                <div><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align:middle;margin-right:4px;"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg> My Applications</div>
            </a>
            <a class="action-card" href="${pageContext.request.contextPath}/notifications">
                <div><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align:middle;margin-right:4px;"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg> Notifications
                <c:if test="${requestScope.unreadCount > 0}">
                    <span class="badge-count" style="margin-left:6px;"><c:out value="${requestScope.unreadCount}" /></span>
                </c:if>
                </div>
            </a>
            <a class="action-card" href="${pageContext.request.contextPath}/wishlist">
                <div><svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="vertical-align:middle;margin-right:4px;"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg> My Wishlist</div>
            </a>
        </div>
    </div>

    <c:if test="${not empty requestScope.recommendedPrograms}">
    <div class="section" style="margin-top: 28px;">
        <div style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 18px;">
            <h2 class="section-title" style="margin-bottom: 0;">
                ✨ Recommended for You
            </h2>
            <a href="${pageContext.request.contextPath}/programs" class="btn btn-secondary btn-sm">View All</a>
        </div>
        <div class="grid">
            <c:forEach var="p" items="${requestScope.recommendedPrograms}">
                <div class="card program-card">
                    <span class="badge badge-info" style="align-self:flex-start; margin-bottom:12px;">
                        <c:out value="${p.category}"/>
                    </span>
                    <h4><c:out value="${p.title}"/></h4>
                    <p><c:out value="${p.description}"/></p>
                    <div class="meta" style="margin-bottom:14px;">
                        <strong style="color:var(--color-gray-dark);">Eligibility:</strong>
                        <c:out value="${p.eligibility}"/>
                    </div>
                    <div class="card-footer">
                        <a class="btn btn-primary full" href="${pageContext.request.contextPath}/apply?programId=${p.id}">
                            Apply Now
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    </c:if>
    <c:if test="${empty requestScope.hasProfile}">
      <div class="alert alert-info" style="margin-top:18px;">
        Complete your profile to get better recommendations.
      </div>
    </c:if>
        <h2 class="section-title">Recent Applications</h2>
        <div class="card">
            <div class="table-wrap">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Application ID</th>
                            <th>Program</th>
                            <th>Status</th>
                            <th>Applied On</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty requestScope.applications}">
                                <c:forEach var="a" items="${requestScope.applications}">
                                    <tr>
                                        <td><strong>#<c:out value="${a.id}" /></strong></td>
                                        <td><c:out value="${a.programTitle}" /></td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${a.status == 'approved'}"><span class="badge badge-approved">Approved</span></c:when>
                                                <c:when test="${a.status == 'rejected'}"><span class="badge badge-rejected">Rejected</span></c:when>
                                                <c:otherwise><span class="badge badge-pending">Pending Review</span></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><c:out value="${a.appliedAt}" /></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4">
                                        <div class="empty-state">
                                            <span class="empty-state-icon"><svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#94A3B8" stroke-width="1.5"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg></span>
                                            <p>No applications yet. <a href="${pageContext.request.contextPath}/programs">Start exploring programs</a></p>
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

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
