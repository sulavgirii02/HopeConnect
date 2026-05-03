<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />
<main class="container page">
    <div class="card text-center">
        <h2>500 - Something Went Wrong</h2>
        <p>We encountered an unexpected error. Please try again later.</p>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">Go Home</a>
    </div>
</main>
<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>