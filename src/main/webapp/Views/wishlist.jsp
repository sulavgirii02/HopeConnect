<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Wishlist - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="page-header">
        <h1>My Wishlist</h1>
        <p>Programs you have saved for later</p>
    </div>

    <div class="grid wishlist-grid">
        <c:choose>
            <c:when test="${not empty requestScope.wishlistPrograms}">
                <c:forEach var="p" items="${requestScope.wishlistPrograms}">
                    <div class="card program-card">

                        <span class="badge badge-info"
                              style="align-self:flex-start; margin-bottom:12px;">
                            <c:out value="${p.category}"/>
                        </span>

                        <h3><c:out value="${p.title}"/></h3>

                        <p><c:out value="${p.description}"/></p>

                        <div class="meta" style="margin-bottom:14px;">
                            <strong style="color:var(--color-gray-dark);">
                                Eligibility:
                            </strong>
                            <c:out value="${p.eligibility}"/>
                        </div>

                        <div class="card-footer"
                             style="display:flex; gap:8px; margin-top:auto; padding-top:16px;">

                            <a class="btn btn-primary"
                               style="flex:1; text-align:center;"
                               href="${pageContext.request.contextPath}/apply?programId=${p.id}">
                                Apply Now
                            </a>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/wishlist"
                                  style="margin:0; display:flex;">

                                <input type="hidden" name="programId" value="${p.id}">
                                <input type="hidden" name="action" value="remove">

                                <button type="submit"
                                        class="btn btn-secondary"
                                        title="Remove from Wishlist"
                                        style="font-size:1.1rem; padding:10px 14px;
                                               color:var(--color-primary);
                                               border-color:var(--color-primary);">
                                    &#9829;
                                </button>
                            </form>

                        </div>
                    </div>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <div class="wishlist-empty-wrap">
                    <div class="empty-state wishlist-empty-state" style="padding:60px 20px;">
                        <span class="empty-state-icon">&#9825;</span>

                        <h3>Your wishlist is empty</h3>

                        <p>
                            Browse programs and click the heart icon to save them here.
                        </p>

                        <a href="${pageContext.request.contextPath}/programs"
                           class="btn btn-primary empty-state-action">
                            Explore Programs
                        </a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
