<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Explore Programs - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="programs-header">
        <h1>Explore Welfare Programs</h1>
        <p>Discover and apply for available assistance programs</p>
    </div>

    <form method="get" action="${pageContext.request.contextPath}/programs" class="filter-bar program-filter-card">
            <div class="filter-group">
                <label class="filter-label" for="keyword">Search keyword</label>
                <input id="keyword" type="text" name="keyword" class="form-control filter-input" placeholder="Search title, description, eligibility..." value="${requestScope.keyword}">
            </div>
            <div class="filter-group">
                <label for="category">Category</label>
                <select id="category" name="category" class="filter-select">
                    <option value="all" ${requestScope.selectedCategory == 'all' ? 'selected' : ''}>All Categories</option>
                    <option value="Education" ${requestScope.selectedCategory == 'Education' ? 'selected' : ''}>Education</option>
                    <option value="Health" ${requestScope.selectedCategory == 'Health' ? 'selected' : ''}>Health</option>
                    <option value="Food" ${requestScope.selectedCategory == 'Food' ? 'selected' : ''}>Food</option>
                    <option value="Housing" ${requestScope.selectedCategory == 'Housing' ? 'selected' : ''}>Housing</option>
                    <option value="Emergency" ${requestScope.selectedCategory == 'Emergency' ? 'selected' : ''}>Emergency</option>
                    <option value="Family Support" ${requestScope.selectedCategory == 'Family Support' ? 'selected' : ''}>Family Support</option>
                </select>
            </div>
            <div class="filter-group">
                <label class="filter-label" for="status">Availability</label>
                <select id="status" name="status" class="filter-select">
                    <option value="available" ${requestScope.selectedStatus == 'available' ? 'selected' : ''}>Available Programs</option>
                    <option value="open" ${requestScope.selectedStatus == 'open' ? 'selected' : ''}>Open Programs</option>
                    <option value="closed" ${requestScope.selectedStatus == 'closed' ? 'selected' : ''}>Full / Closed Programs</option>
                </select>
            </div>
            <div class="filter-actions">
                <button class="btn btn-primary" type="submit">Search Programs</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/programs">Clear</a>
            </div>
    </form>

    <div class="grid program-grid ${requestScope.programs.size() % 4 == 2 ? 'program-grid-tail-two' : ''}">
        <c:choose>
            <c:when test="${not empty requestScope.programs}">
                <c:forEach var="p" items="${requestScope.programs}">
                    <div class="card program-card">
                        <h3><c:out value="${p.title}" /></h3>
                        <div class="meta-row">
                            <span class="badge badge-info"><c:out value="${p.category}" /></span>
                        </div>
                        <p><c:out value="${p.description}" /></p>
                        <div class="meta">
                            <strong style="color:var(--color-gray-dark);">Eligibility:</strong> <c:out value="${p.eligibility}" />
                        </div>
                        <div class="meta">
                            <strong style="color:var(--color-gray-dark);">Quota:</strong>
                            <c:out value="${p.remainingCapacity}" /> / <c:out value="${p.capacity}" />
                        </div>
                        <div class="card-footer" style="display:flex; gap:8px; margin-top:auto; padding-top:16px;">
                            <c:choose>
                                <c:when test="${p.remainingCapacity != null and p.remainingCapacity <= 0}">
                                    <span class="btn btn-secondary" style="flex:1; text-align:center;">Quota Full</span>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-primary" style="flex:1; text-align:center;" href="${pageContext.request.contextPath}/apply?programId=${p.id}">Apply Now</a>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <c:set var="alreadySaved" value="${not empty wishlistedIds and wishlistedIds.contains(p.id)}"/>
                                    <form method="post" action="${pageContext.request.contextPath}/wishlist" style="margin:0; display:flex;">
                                        <input type="hidden" name="programId" value="${p.id}">
                                        <input type="hidden" name="action" value="${alreadySaved ? 'remove' : 'add'}">
                                        <button type="submit" class="btn btn-secondary" title="${alreadySaved ? 'Remove from Wishlist' : 'Save to Wishlist'}" style="font-size:1.1rem; padding:10px 14px; ${alreadySaved ? 'color:var(--color-primary); border-color:var(--color-primary);' : ''}">${alreadySaved ? '&#9829;' : '&#9825;'}</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/login" class="btn btn-secondary" title="Sign in to save programs" style="font-size:1.1rem; padding:10px 14px;">&#9825;</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="empty-state" style="flex:1 1 100%;">
                    <span class="empty-state-icon"><svg width="40" height="40" viewBox="0 0 24 24" fill="none" stroke="#94A3B8" stroke-width="1.5"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg></span>
                    <h3>No Programs Found</h3>
                    <p>Try adjusting your search filters or <a href="${pageContext.request.contextPath}/programs">clear filters</a></p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <c:if test="${requestScope.totalPages > 1}">
      <div class="pagination pagination-wrap">
        <a class="pagination-link ${requestScope.currentPage == 1 ? 'disabled' : ''}" href="${pageContext.request.contextPath}/programs?page=${requestScope.currentPage - 1}&keyword=${requestScope.keyword}&category=${requestScope.selectedCategory}&status=${requestScope.selectedStatus}">Previous</a>
        <c:forEach begin="1" end="${requestScope.totalPages}" var="i">
          <a class="pagination-link ${requestScope.currentPage == i ? 'active' : ''}" href="${pageContext.request.contextPath}/programs?page=${i}&keyword=${requestScope.keyword}&category=${requestScope.selectedCategory}&status=${requestScope.selectedStatus}">${i}</a>
        </c:forEach>
        <a class="pagination-link ${requestScope.currentPage == requestScope.totalPages ? 'disabled' : ''}" href="${pageContext.request.contextPath}/programs?page=${requestScope.currentPage + 1}&keyword=${requestScope.keyword}&category=${requestScope.selectedCategory}&status=${requestScope.selectedStatus}">Next</a>
      </div>
    </c:if>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
