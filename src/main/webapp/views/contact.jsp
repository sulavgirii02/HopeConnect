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
    <div class="page-center">
        <section class="card card-wide">
            <h1>Contact Us</h1>
            <p class="muted mt-2">Send a message to the HopeConnect team. All fields are validated before the message is saved.</p>

            <c:if test="${not empty requestScope.success}">
                <div class="success-box mt-3"><c:out value="${requestScope.success}" /></div>
            </c:if>
            <c:if test="${not empty requestScope.error}">
                <div class="error-box mt-3"><c:out value="${requestScope.error}" /></div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/contact" class="stack mt-4">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input id="name" class="form-control" name="name" value="<c:out value='${requestScope.formName}' />" required>
                    <c:if test="${not empty requestScope.errorName}"><div class="error-text"><c:out value="${requestScope.errorName}" /></div></c:if>
                </div>
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input id="email" class="form-control" type="email" name="email" value="<c:out value='${requestScope.formEmail}' />" required>
                    <c:if test="${not empty requestScope.errorEmail}"><div class="error-text"><c:out value="${requestScope.errorEmail}" /></div></c:if>
                </div>
                <div class="form-group">
                    <label for="subject">Subject</label>
                    <input id="subject" class="form-control" name="subject" maxlength="255" value="<c:out value='${requestScope.formSubject}' />" required>
                    <c:if test="${not empty requestScope.errorSubject}"><div class="error-text"><c:out value="${requestScope.errorSubject}" /></div></c:if>
                </div>
                <div class="form-group">
                    <label for="message">Message</label>
                    <textarea id="message" class="form-control" name="message" rows="6" maxlength="2000" required><c:out value="${requestScope.formMessage}" /></textarea>
                    <c:if test="${not empty requestScope.errorMessage}"><div class="error-text"><c:out value="${requestScope.errorMessage}" /></div></c:if>
                </div>
                <button class="btn btn-primary" type="submit">Send Message</button>
            </form>
        </section>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
