<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found | HopeConnect</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">
</head>
<body>
    <div class="container error-page">
        <div class="card" style="padding:48px;text-align:center;max-width:480px;">
            <div class="error-code">404</div>
            <h2>Page Not Found</h2>
            <p>The page you are looking for doesn't exist or has been moved. Please check the URL or go back to the homepage.</p>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Return Home</a>
        </div>
    </div>
</body>
</html>
