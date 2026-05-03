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

<main class="container page user-dashboard">
    <section class="dashboard-hero">
        <div>
            <p class="eyebrow">User Dashboard</p>
            <h1>Welcome, <c:out value="${sessionScope.user.fullName}" /></h1>
            <p class="muted mt-2">Your account is ready. You can view published programs here.</p>
        </div>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/available-programs">View Programs</a>
    </section>

    <section class="program-panel">
        <div class="section-heading">
            <div>
                <h2>Available Programs</h2>
                <p class="muted">Published by admin and visible to registered users.</p>
            </div>
        </div>

        <div class="program-grid">
            <c:choose>
                <c:when test="${not empty requestScope.programs}">
                    <c:forEach var="p" items="${requestScope.programs}">
                        <article class="program-card">
                            <div>
                                <span class="program-category"><c:out value="${p.category}" /></span>
                                <h3><c:out value="${p.title}" /></h3>
                                <p class="muted"><c:out value="${p.description}" /></p>
                            </div>
                            <div class="program-meta">
                                <strong>Eligibility</strong>
                                <span><c:out value="${p.eligibility}" /></span>
                            </div>
                        </article>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <h3>No programs available</h3>
                        <p class="muted">Published programs will appear here when an admin adds them.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
