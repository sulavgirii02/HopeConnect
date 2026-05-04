<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HopeConnect - Welfare Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <c:redirect url="${pageContext.request.contextPath}/dashboard" />
    </c:when>
    <c:otherwise>
        <c:redirect url="${pageContext.request.contextPath}/login" />
    </c:otherwise>
</c:choose>
</body>
</html>