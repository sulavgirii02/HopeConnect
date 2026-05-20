<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notifications - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
  <div class="content-narrow">
    <div class="page-header">
        <h1>Notifications</h1>
        <p>Stay updated on your applications and account activity</p>
    </div>

    <c:if test="${requestScope.unreadCount > 0}">
        <form method="post" action="${pageContext.request.contextPath}/notifications" style="margin-bottom:18px;">
            <input type="hidden" name="action" value="markAllRead">
            <button class="btn btn-secondary btn-sm" type="submit">Mark all as read</button>
        </form>
    </c:if>

    <c:choose>
        <c:when test="${not empty requestScope.notifications}">
            <div class="notification-list">
            <c:forEach var="n" items="${requestScope.notifications}">
                <div class="notification-card ${n.read ? 'is-read' : 'is-unread'}">
                    <h3><c:out value="${n.title}" /></h3>
                    <p><c:out value="${n.message}" /></p>
                    <p class="muted notification-meta" style="font-size:0.85rem;">
                        <span class="badge badge-info"><c:out value="${n.type}" /></span>
                        &nbsp; <c:out value="${n.createdAt}" />
                        &nbsp; <c:out value="${n.read ? 'Read' : 'Unread'}" />
                    </p>
                    <c:if test="${not n.read}">
                        <form method="post" action="${pageContext.request.contextPath}/notifications" class="actions">
                            <input type="hidden" name="action" value="markRead">
                            <input type="hidden" name="id" value="${n.id}">
                            <button class="btn btn-secondary btn-sm" type="submit">Mark as Read</button>
                        </form>
                    </c:if>
                </div>
            </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="empty-state">
                <span class="empty-state-icon"><svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#94A3B8" stroke-width="1.5"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"/><path d="M13.73 21a2 2 0 0 1-3.46 0"/></svg></span>
                <h3>No Notifications Yet</h3>
                <p>Updates about your applications and account activity will appear here.</p>
            </div>
        </c:otherwise>
    </c:choose>
  </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
