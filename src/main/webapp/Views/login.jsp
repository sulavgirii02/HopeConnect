<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="auth-page auth-page-tall">
    <div class="auth-card auth-card-login">
        <div class="card">
            <div class="text-center" style="margin-bottom:24px;">
                <h2>Welcome Back</h2>
                <p class="subtitle muted">Sign in to your HopeConnect account</p>
            </div>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-error" style="margin-bottom:18px;">
                    <c:out value="${requestScope.error}" />
                </div>
            </c:if>

            <c:if test="${not empty sessionScope.flash_success}">
                <div class="alert alert-success" style="margin-bottom:18px;">
                    <c:out value="${sessionScope.flash_success}" />
                </div>
                <c:remove var="flash_success" scope="session" />
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/login" class="stack">
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input id="email" name="email" type="email" class="form-control" placeholder="your@email.com" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input id="password" name="password" type="password" class="form-control" placeholder="Enter your password" required>
                </div>
                <button class="btn btn-primary full" type="submit" style="margin-top:8px;">Sign In</button>
            </form>

            <div class="auth-link">
                Don't have an account? <a href="${pageContext.request.contextPath}/register">Create one here</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
