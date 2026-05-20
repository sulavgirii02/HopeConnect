<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/theme.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout.css">

<header class="nav site-header" role="banner">
    <div class="nav-container nav-inner">
        <a class="brand" href="${pageContext.request.contextPath}/dashboard">
            <span class="brand-icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5" stroke-linecap="round" stroke-linejoin="round"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"/></svg>
            </span>
            HopeConnect
        </a>
        <button class="nav-toggle" type="button" aria-label="Toggle navigation" aria-expanded="false">
            <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round"><line x1="3" y1="6" x2="21" y2="6"/><line x1="3" y1="12" x2="21" y2="12"/><line x1="3" y1="18" x2="21" y2="18"/></svg>
        </button>
        <ul class="nav-links main-nav" id="navLinks">
            <c:choose>
                <%-- Admin navbar only --%>
                <c:when test="${not empty sessionScope.user and sessionScope.user.role == 'admin'}">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard" id="nav-admin">Admin Panel</a></li>
                    <li><a href="${pageContext.request.contextPath}/notifications" id="nav-notifications">
                        Notifications
                        <c:if test="${sessionScope.unreadCount > 0}">
                            <span class="badge-count"><c:out value="${sessionScope.unreadCount}"/></span>
                        </c:if>
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="btn-nav-logout" id="nav-logout">Logout</a></li>
                </c:when>

                <%-- Normal logged-in user navbar --%>
                <c:when test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/home" id="nav-home">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/programs" id="nav-programs">Programs</a></li>
                    <li><a href="${pageContext.request.contextPath}/about" id="nav-about">About</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact" id="nav-contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/user-dashboard" id="nav-dashboard">Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/my-applications" id="nav-applications">My Applications</a></li>
                    <li><a href="${pageContext.request.contextPath}/wishlist" id="nav-wishlist">Wishlist</a></li>
                    <li><a href="${pageContext.request.contextPath}/notifications" id="nav-notifications">
                        Notifications
                        <c:if test="${sessionScope.unreadCount > 0}">
                            <span class="badge-count"><c:out value="${sessionScope.unreadCount}"/></span>
                        </c:if>
                    </a></li>
                    <li><a href="${pageContext.request.contextPath}/logout" class="btn-nav-logout" id="nav-logout">Logout</a></li>
                </c:when>

                <%-- Guest navbar --%>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/home" id="nav-home">Home</a></li>
                    <li><a href="${pageContext.request.contextPath}/programs" id="nav-programs">Programs</a></li>
                    <li><a href="${pageContext.request.contextPath}/about" id="nav-about">About</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact" id="nav-contact">Contact</a></li>
                    <li><a href="${pageContext.request.contextPath}/login" id="nav-login">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register" id="nav-register">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</header>

<c:if test="${not empty sessionScope.flashMessage}">
    <div class="flash-message flash-${sessionScope.flashType}" role="alert">
        <span>
            <c:choose>
                <c:when test="${sessionScope.flashType == 'success'}">&#10003;</c:when>
                <c:when test="${sessionScope.flashType == 'error'}">&#10005;</c:when>
                <c:when test="${sessionScope.flashType == 'warning'}">!</c:when>
                <c:otherwise>i</c:otherwise>
            </c:choose>
        </span>
        <p><c:out value="${sessionScope.flashMessage}" /></p>
    </div>
    <c:remove var="flashMessage" scope="session" />
    <c:remove var="flashType" scope="session" />
</c:if>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        var toggle = document.querySelector(".nav-toggle");
        var links = document.getElementById("navLinks");
        if (toggle && links) {
            toggle.addEventListener("click", function () {
                links.classList.toggle("open");
                toggle.setAttribute("aria-expanded", links.classList.contains("open") ? "true" : "false");
            });
        }
    });
</script>