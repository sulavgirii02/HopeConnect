<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Applications - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="page-header">
        <h1>My Applications</h1>
        <p>Track the status of your welfare program applications</p>
    </div>

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
                                        <h3>No Applications Yet</h3>
                                        <p><a href="${pageContext.request.contextPath}/programs">Browse available programs</a> and submit your first application</p>
                                    </div>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
