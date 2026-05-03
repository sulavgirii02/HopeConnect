<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>403 - Forbidden</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .container { min-height: 60vh; display:flex; align-items:center; justify-content:center; }
        .card { text-align:center; padding:40px; }
    </style>
</head>
<body>
<div class="container">
    <div class="card">
        <h1>403</h1>
        <h2>Access Denied</h2>
        <p>You do not have permission to view this page.</p>
        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">Home</a>
    </div>
</div>
</body>
</html>