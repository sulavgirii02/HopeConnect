<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Apply - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <c:if test="${empty requestScope.program}">
      <div class="error-page">
        <div class="error-code">404</div>
        <h2>Program Not Found</h2>
        <p>This aid program does not exist or is no longer available.</p>
        <a href="${pageContext.request.contextPath}/programs" class="btn btn-primary">Browse Programs</a>
      </div>
    </c:if>

    <c:if test="${not empty requestScope.program and (requestScope.program.programStatus != 'open' or (requestScope.program.remainingCapacity != null and requestScope.program.remainingCapacity <= 0))}">
      <div class="alert alert-warning" style="margin-bottom: 24px;">
        <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="flex-shrink:0;">
          <path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/>
        </svg>
        <strong>Applications Closed:</strong> This program is no longer accepting applications.
        <a href="${pageContext.request.contextPath}/programs" style="margin-left: 8px;">View other programs →</a>
      </div>
    </c:if>

    <c:if test="${not empty requestScope.program}">
    <div class="card card-wide center" style="margin-bottom:24px;">
        <div style="display:flex;align-items:center;gap:12px;margin-bottom:12px;">
            <span class="badge badge-info"><c:out value="${requestScope.program.category}" /></span>
            <h2 style="margin:0;"><c:out value="${requestScope.program.title}" /></h2>
        </div>
        <p class="muted" style="margin-bottom:8px;"><strong style="color:var(--color-gray-dark);">Eligibility:</strong> <c:out value="${requestScope.program.eligibility}" /></p>
        <p class="muted"><c:out value="${requestScope.program.description}" /></p>
    </div>

    <div class="card card-wide center">
        <h3 style="margin-bottom:18px;">Submit Your Application</h3>

        <c:if test="${not empty requestScope.alreadyApplied}">
            <div class="alert alert-warning" style="margin-bottom:18px;">You have already applied to this program.</div>
        </c:if>
        <c:if test="${not empty requestScope.error and empty requestScope.alreadyApplied}">
            <div class="alert alert-error" style="margin-bottom:18px;"><c:out value="${requestScope.error}" /></div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/apply" class="stack">
            <input type="hidden" name="programId" value="${requestScope.program.id}">
            <div class="form-group">
                <label for="notes">Additional Notes (optional)</label>
                <textarea id="notes" name="notes" class="form-control" rows="4" placeholder="Any additional information to support your application..."></textarea>
            </div>
            <div class="form-group" style="margin-top:8px;">
                <label style="display:flex;align-items:center;gap:8px;font-weight:500;cursor:pointer;">
                    <input type="checkbox" required style="width:auto;accent-color:var(--color-primary);">
                    I declare that all information provided is true and accurate.
                </label>
            </div>
            <button class="btn btn-primary" type="submit"
                    <c:if test="${not empty requestScope.alreadyApplied or requestScope.program.programStatus != 'open' or (requestScope.program.remainingCapacity != null and requestScope.program.remainingCapacity <= 0)}">disabled</c:if>>
                <c:choose>
                    <c:when test="${not empty requestScope.alreadyApplied}">Already Applied</c:when>
                    <c:otherwise>Submit Application</c:otherwise>
                </c:choose>
            </button>
        </form>
    </div>
    </c:if>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
