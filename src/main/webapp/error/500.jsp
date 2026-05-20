<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Server Error | HopeConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
    <div class="container error-page">
        <div class="card" style="padding:48px;text-align:center;max-width:480px;">
            <div class="error-code">500</div>
            <h2>Something Went Wrong</h2>
            <p>We encountered an unexpected error. Please try again later or contact our support team for assistance.</p>
            <div class="actions" style="justify-content:center;">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Go Home</a>
                <a href="${pageContext.request.contextPath}/contact" class="btn btn-secondary">Contact Support</a>
            </div>
        </div>
    </div>
</body>
</html>
