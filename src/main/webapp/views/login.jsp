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

<style>
.auth-page { display: flex; align-items: center; justify-content: center; min-height: calc(100vh - 160px); padding: 40px 20px; }
.auth-card { max-width: 400px; width: 100%; }
.auth-card h2 { margin-bottom: 4px; }
.auth-card .subtitle { margin-bottom: 24px; }
.register-link { margin-top: 20px; padding-top: 20px; border-top: 1px solid var(--color-gray-light); text-align: center; color: var(--color-gray-mid); font-size: 14px; }
.register-link a { color: var(--color-primary); font-weight: 600; text-decoration: none; }
.register-link a:hover { text-decoration: underline; }
@media (max-width: 768px) {
  .auth-page { min-height: auto; padding: 24px 16px; }
}
</style>

<main class="auth-page">
    <div class="container auth-card">
        <div class="card">
            <h2>Welcome Back</h2>
            <p class="subtitle muted">Sign in to your HopeConnect account</p>
            
            <c:if test="${not empty requestScope.error}">
                <div class="error-text" style="margin-bottom: 16px; padding: 10px; background: var(--color-red-ghost); border-left: 3px solid var(--color-red); border-radius: var(--radius-sm);">
                    <c:out value="${requestScope.error}" />
                </div>
            </c:if>
            
            <c:if test="${not empty sessionScope.flash_success}">
                <div class="success-text" style="margin-bottom: 16px; padding: 10px; background: var(--color-primary-tint); border-left: 3px solid var(--color-primary); border-radius: var(--radius-sm);">
                    <c:out value="${sessionScope.flash_success}" />
                </div>
                <c:remove var="flash_success" scope="session" />
            </c:if>
            
            <form method="post" action="${pageContext.request.contextPath}/login" class="stack">
                <div class="form-group">
                    <label for="email" style="font-weight: 600; color: var(--color-gray-dark);">Email Address</label>
                    <input id="email" name="email" type="email" class="form-control" placeholder="your@email.com" value="<c:out value='${requestScope.formEmail}' />" required>
                </div>
                <div class="form-group">
                    <label for="password" style="font-weight: 600; color: var(--color-gray-dark);">Password</label>
                    <input id="password" name="password" type="password" class="form-control" placeholder="••••••••" required>
                </div>
                <button class="btn btn-primary full" type="submit" style="margin-top: 8px;">Sign In</button>
            </form>
            
            <div class="register-link">
                Don't have an account? <a href="${pageContext.request.contextPath}/register">Create one here</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
