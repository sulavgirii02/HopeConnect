<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HopeConnect - Home</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <section class="hero">
        <h1>HopeConnect</h1>
        <p> HopeConnect is a simple welfare management system built to support secure account access and organized admin management for public support programs.</p>
        <div class="actions">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">Go to Dashboard</a>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:when>
                <c:otherwise>
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Login</a>
                    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/register">Register</a>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
