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
    <div class="card card-wide center" style="padding:36px 40px;margin-bottom:32px;">
        <h1 style="font-size:1.8rem;margin-bottom:12px;">About HopeConnect</h1>
        <p class="muted" style="font-size:1.05rem;line-height:1.7;max-width:720px;">
            HopeConnect is a centralized digital welfare management platform designed to bridge the gap between disadvantaged families and government welfare programs. Our mission is to make welfare access simple, dignified, and transparent for every citizen.
        </p>
    </div>

    <div class="section-header" style="margin-bottom:28px;">
        <h2>Why HopeConnect Was Created</h2>
    </div>
    <div class="grid" style="margin-bottom:40px;">
        <div class="card" style="padding:24px;">
            <h4 style="color:var(--color-primary-darkest);margin-bottom:8px;">Improving Welfare Access</h4>
            <p class="muted" style="font-size:0.92rem;line-height:1.6;">Many families are unaware of available programs or face barriers applying. HopeConnect brings all programs into one searchable, accessible platform.</p>
        </div>
        <div class="card" style="padding:24px;">
            <h4 style="color:var(--color-primary-darkest);margin-bottom:8px;">Reducing Corruption</h4>
            <p class="muted" style="font-size:0.92rem;line-height:1.6;">With transparent tracking, quota management, and audit logs, HopeConnect ensures fair distribution and accountability at every step.</p>
        </div>
        <div class="card" style="padding:24px;">
            <h4 style="color:var(--color-primary-darkest);margin-bottom:8px;">Supporting Communities</h4>
            <p class="muted" style="font-size:0.92rem;line-height:1.6;">Rural citizens can apply online without repeated office visits, saving time and travel costs while receiving equal access to support.</p>
        </div>
    </div>

    <h2 class="section-title" style="text-align:center;margin-bottom:24px;">How It Works</h2>
    <div class="grid mt-2" style="margin-bottom:40px;">
        <div class="card" style="text-align:center;padding:24px;">
            <h4>1. Discover</h4>
            <p class="muted">Search programs that match your needs and eligibility criteria.</p>
        </div>
        <div class="card" style="text-align:center;padding:24px;">
            <h4>2. Apply</h4>
            <p class="muted">Submit applications with a guided, simple form.</p>
        </div>
        <div class="card" style="text-align:center;padding:24px;">
            <h4>3. Track</h4>
            <p class="muted">Follow your application status and receive updates.</p>
        </div>
    </div>

    <h2 class="section-title" style="text-align:center;margin-bottom:24px;">Our Team</h2>
    <div class="card card-wide center" style="text-align:center;padding:40px;">
        <p class="muted">We are a dedicated group of students building impact-driven technology to improve welfare distribution in our communities.</p>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>