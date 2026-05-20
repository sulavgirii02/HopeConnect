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

<main class="auth-page auth-page-tall">
    <div class="auth-card auth-card-register">
        <div class="card">
            <div class="text-center" style="margin-bottom:24px;">
                <h2>Create Your Account</h2>
                <p class="subtitle muted">Join HopeConnect and access welfare programs</p>
            </div>

            <c:if test="${not empty requestScope.error}">
                <div class="alert alert-error" style="margin-bottom:18px;">
                    <c:out value="${requestScope.error}" />
                </div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/register" class="stack">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input id="fullName" name="fullName" type="text" class="form-control" placeholder="John Doe" value="${requestScope.formFullName}" required>
                    <c:if test="${not empty requestScope.errorFullName}">
                        <div class="error-text"><c:out value="${requestScope.errorFullName}" /></div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input id="email" name="email" type="email" class="form-control" placeholder="your@email.com" value="${requestScope.formEmail}" required>
                    <c:if test="${not empty requestScope.errorEmail}">
                        <div class="error-text"><c:out value="${requestScope.errorEmail}" /></div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input id="phone" name="phone" type="text" class="form-control" placeholder="+977 98XXXXXXXX" value="${requestScope.formPhone}" required>
                    <c:if test="${not empty requestScope.errorPhone}">
                        <div class="error-text"><c:out value="${requestScope.errorPhone}" /></div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="age">Age</label>
                    <input id="age" name="age" type="number" class="form-control" placeholder="25" value="${requestScope.formAge}" min="1" max="120" required>
                    <c:if test="${not empty requestScope.errorAge}">
                        <div class="error-text"><c:out value="${requestScope.errorAge}" /></div>
                    </c:if>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input id="password" name="password" type="password" class="form-control" placeholder="Min 8 characters" required>
                        <c:if test="${not empty requestScope.errorPassword}">
                            <div class="error-text"><c:out value="${requestScope.errorPassword}" /></div>
                        </c:if>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input id="confirmPassword" name="confirmPassword" type="password" class="form-control" placeholder="Repeat password" required>
                        <c:if test="${not empty requestScope.errorConfirmPassword}">
                            <div class="error-text"><c:out value="${requestScope.errorConfirmPassword}" /></div>
                        </c:if>
                    </div>
                </div>

                <button class="btn btn-primary full" type="submit" style="margin-top:8px;">Create Account</button>
            </form>

            <div class="auth-link">
                Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in here</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
