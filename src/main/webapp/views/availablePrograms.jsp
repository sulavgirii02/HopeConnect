<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Available Programs - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="card mb-4">
        <h1>Available Programs</h1>
        <p class="muted mt-2">Published programs are visible here for users.</p>
    </div>

    <div class="grid">
        <c:choose>
            <c:when test="${not empty requestScope.programs}">
                <c:forEach var="p" items="${requestScope.programs}">
                    <div class="card">
                        <h3><c:out value="${p.title}" /></h3>
                        <p class="muted mt-1"><c:out value="${p.category}" /></p>
                        <p class="mt-2"><c:out value="${p.description}" /></p>
                        <p class="mt-2"><strong>Eligibility:</strong> <c:out value="${p.eligibility}" /></p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="card">
                    <p class="muted">No published programs are available right now.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
