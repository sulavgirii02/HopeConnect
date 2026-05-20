<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="page-header text-center" style="margin-bottom:36px;">
        <h1>Contact & Support</h1>
        <p>We're here to help. Reach out with any questions about welfare programs or your applications.</p>
    </div>

    <div style="display:flex;gap:28px;flex-wrap:wrap;max-width:900px;margin:0 auto;">
        <!-- Contact Form -->
        <div class="card" style="flex:1 1 400px;">
            <h3 style="margin-bottom:16px;">Send a Message</h3>
            <p class="muted" style="margin-bottom:20px;font-size:0.9rem;">We usually respond within 1-2 business days.</p>
            <form method="post" action="${pageContext.request.contextPath}/contact" class="stack">
                <div class="form-group">
                    <label for="name">Name</label>
                    <input id="name" name="name" class="form-control" value="${requestScope.formName}" required>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" name="email" type="email" class="form-control" value="${requestScope.formEmail}" required>
                </div>
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <input id="subject" name="subject" class="form-control" value="${requestScope.formSubject}" required>
                </div>
                <div class="form-group">
                    <label for="message">Message</label>
                    <textarea id="message" name="message" class="form-control" rows="4" required><c:out value="${requestScope.formMessage}" /></textarea>
                </div>
                <c:if test="${not empty requestScope.error}">
                    <div class="alert alert-error"><c:out value="${requestScope.error}" /></div>
                </c:if>
                <c:if test="${not empty requestScope.success}">
                    <div class="alert alert-success"><c:out value="${requestScope.success}" /></div>
                </c:if>
                <button class="btn btn-primary" type="submit">Send Message</button>
            </form>
        </div>

        <!-- Support Info -->
        <div style="flex:1 1 260px;display:flex;flex-direction:column;gap:16px;">
            <div class="card">
                <h4 style="margin-bottom:8px;">Helpdesk</h4>
                <p class="muted" style="font-size:0.9rem;margin-bottom:4px;">Phone: +977-01-XXXXXXX</p>
                <p class="muted" style="font-size:0.9rem;margin-bottom:4px;">Email: support@hopeconnect.org</p>
                <p class="muted" style="font-size:0.9rem;">Hours: Sun-Fri, 9:00 AM - 5:00 PM</p>
            </div>
            <div class="card">
                <h4 style="margin-bottom:8px;">Office Address</h4>
                <p class="muted" style="font-size:0.9rem;">HopeConnect Welfare Office<br>Kathmandu, Nepal</p>
            </div>
            <div class="card">
                <h4 style="margin-bottom:8px;">Quick Help</h4>
                <p class="muted" style="font-size:0.9rem;margin-bottom:8px;">Common questions about applications, eligibility, and tracking.</p>
                <a href="${pageContext.request.contextPath}/about" class="btn btn-secondary btn-sm">Learn More</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
