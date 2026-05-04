<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<style>
.auth-page { display: flex; align-items: center; justify-content: center; min-height: calc(100vh - 160px); padding: 40px 20px; }
.auth-card { max-width: 500px; width: 100%; }
.auth-card h2 { margin-bottom: 8px; }
.auth-card .subtitle { margin-bottom: 24px; color: var(--color-gray-mid); font-size: 14px; }
.login-link { margin-top: 20px; padding-top: 20px; border-top: 1px solid var(--color-gray-light); text-align: center; color: var(--color-gray-mid); font-size: 14px; }
.login-link a { color: var(--color-primary); font-weight: 600; text-decoration: none; }
.login-link a:hover { text-decoration: underline; }
@media (max-width: 768px) {
  .auth-page { min-height: auto; padding: 24px 16px; }
}
</style>

<main class="auth-page">
    <div class="container auth-card">
        <div class="card">
            <h2>Create Your Account</h2>
            <p class="subtitle">Create your HopeConnect account for this milestone</p>
            
            <c:if test="${not empty requestScope.error}">
                <div class="error-text" style="margin-bottom: 16px; padding: 10px; background: var(--color-red-ghost); border-left: 3px solid var(--color-red); border-radius: var(--radius-sm);">
                    <c:out value="${requestScope.error}" />
                </div>
            </c:if>
            
            <form method="post" action="${pageContext.request.contextPath}/register" class="stack">
                <div class="form-group">
                    <label for="fullName" style="font-weight: 600; color: var(--color-gray-dark);">Full Name</label>
                    <input id="fullName" name="fullName" type="text" class="form-control" placeholder="John Doe" value="${requestScope.formFullName}" required>
                    <c:if test="${not empty requestScope.errorFullName}">
                        <div class="error-text"><c:out value="${requestScope.errorFullName}" /></div>
                    </c:if>
                </div>
                
                <div class="form-group">
                    <label for="email" style="font-weight: 600; color: var(--color-gray-dark);">Email Address</label>
                    <input id="email" name="email" type="email" class="form-control" placeholder="your@email.com" value="${requestScope.formEmail}" required>
                    <c:if test="${not empty requestScope.errorEmail}">
                        <div class="error-text"><c:out value="${requestScope.errorEmail}" /></div>
                    </c:if>
                </div>
                
                <div class="form-group">
                    <label for="phone" style="font-weight: 600; color: var(--color-gray-dark);">Phone Number</label>
                    <input id="phone" name="phone" type="text" class="form-control" placeholder="+1 (555) 123-4567" value="${requestScope.formPhone}" required>
                    <c:if test="${not empty requestScope.errorPhone}">
                        <div class="error-text"><c:out value="${requestScope.errorPhone}" /></div>
                    </c:if>
                </div>
                
                <div class="form-group">
                    <label for="age" style="font-weight: 600; color: var(--color-gray-dark);">Age</label>
                    <input id="age" name="age" type="number" class="form-control" placeholder="25" value="${requestScope.formAge}" min="1" max="120" required>
                    <c:if test="${not empty requestScope.errorAge}">
                        <div class="error-text"><c:out value="${requestScope.errorAge}" /></div>
                    </c:if>
                </div>
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="password" style="font-weight: 600; color: var(--color-gray-dark);">Password</label>
                        <input id="password" name="password" type="password" class="form-control" placeholder="••••••••" required>
                        <c:if test="${not empty requestScope.errorPassword}">
                            <div class="error-text"><c:out value="${requestScope.errorPassword}" /></div>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword" style="font-weight: 600; color: var(--color-gray-dark);">Confirm Password</label>
                        <input id="confirmPassword" name="confirmPassword" type="password" class="form-control" placeholder="••••••••" required>
                        <c:if test="${not empty requestScope.errorConfirmPassword}">
                            <div class="error-text"><c:out value="${requestScope.errorConfirmPassword}" /></div>
                        </c:if>
                    </div>
                </div>
                
                <button class="btn btn-primary full" type="submit" style="margin-top: 8px;">Create Account</button>
            </form>
            
            <div class="login-link">
                Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in here</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
