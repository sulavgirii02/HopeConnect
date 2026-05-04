<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <section class="hero">
        <h1>About HopeConnect</h1>
        <p>HopeConnect is a simple welfare management system built to support secure account access and organized admin management for public support programs.</p>
    </section>

    <section class="grid mt-4">
        <article class="card">
            <h2>Our Focus</h2>
            <p class="mt-2">It focuses on secure registration, login, session handling, role-based access, and admin-side program management.</p>
        </article>
        <article class="card">
            <h2>For Users</h2>
            <p class="mt-2">Users can create an account, log in securely, and view published programs. </p>
        </article>
        <article class="card">
            <h2>For Admins</h2>
            <p class="mt-2">Admins can manage programs, review applications, and manage users through protected admin pages.</p>
        </article>
    </section>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
