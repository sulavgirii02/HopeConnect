<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - HopeConnect</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">
    <div class="page-header">
        <h1>My Profile</h1>
        <p>Manage your personal and household information</p>
    </div>

    <c:if test="${not empty sessionScope.flash_success}">
        <div class="alert alert-success" role="alert">
            <c:out value="${sessionScope.flash_success}" />
        </div>
        <c:remove var="flash_success" scope="session" />
    </c:if>
    <c:if test="${not empty requestScope.error}">
        <div class="alert alert-error" role="alert">
            <c:out value="${requestScope.error}" />
        </div>
    </c:if>

    <div class="card card-wide center">
        <form method="post" action="${pageContext.request.contextPath}/profile" class="stack">
            <h3>Personal Information</h3>
            <div class="form-row">
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input id="fullName" class="form-control" value="<c:out value="${sessionScope.user.fullName}" />" disabled>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input id="email" class="form-control" value="<c:out value="${sessionScope.user.email}" />" disabled>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth</label>
                    <input id="dateOfBirth" class="form-control" type="date" name="dateOfBirth" value="${requestScope.profile.dateOfBirth}">
                </div>
                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select id="gender" class="form-control" name="gender">
                        <option value="">Select</option>
                        <option value="male" ${requestScope.profile.gender == 'male' ? 'selected' : ''}>Male</option>
                        <option value="female" ${requestScope.profile.gender == 'female' ? 'selected' : ''}>Female</option>
                        <option value="other" ${requestScope.profile.gender == 'other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>
            </div>

            <h3 style="margin-top:18px;">Contact & Address</h3>
            <div class="form-group">
                <label for="addressLine1">Address Line 1</label>
                <input id="addressLine1" class="form-control" name="addressLine1" value="<c:out value="${requestScope.profile.addressLine1}" />">
            </div>
            <div class="form-group">
                <label for="addressLine2">Address Line 2</label>
                <input id="addressLine2" class="form-control" name="addressLine2" value="<c:out value="${requestScope.profile.addressLine2}" />">
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="city">City</label>
                    <input id="city" class="form-control" name="city" value="<c:out value="${requestScope.profile.city}" />">
                </div>
                <div class="form-group">
                    <label for="state">State</label>
                    <input id="state" class="form-control" name="state" value="<c:out value="${requestScope.profile.state}" />">
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="postalCode">Postal Code</label>
                    <input id="postalCode" class="form-control" name="postalCode" value="<c:out value="${requestScope.profile.postalCode}" />">
                </div>
                <div class="form-group">
                    <label for="country">Country</label>
                    <input id="country" class="form-control" name="country" value="<c:out value="${requestScope.profile.country}" />">
                </div>
            </div>

            <h3 style="margin-top:18px;">Household & Income</h3>
            <div class="form-row">
                <div class="form-group">
                    <label for="householdSize">Household Size</label>
                    <input id="householdSize" class="form-control" type="number" min="1" max="50" name="householdSize" value="${requestScope.profile.householdSize}">
                </div>
                <div class="form-group">
                    <label for="monthlyIncome">Monthly Income</label>
                    <input id="monthlyIncome" class="form-control" type="number" step="0.01" min="0" name="monthlyIncome" value="${requestScope.profile.monthlyIncome}">
                </div>
            </div>
            <div class="form-group">
                <label for="preferredCategory">Preferred Aid Category</label>
                <input id="preferredCategory" class="form-control" name="preferredCategory" value="<c:out value="${requestScope.profile.preferredCategory}" />" placeholder="e.g. food, housing, medical">
            </div>
            <div class="form-group">
                <label for="additionalInfo">Additional Information</label>
                <textarea id="additionalInfo" class="form-control" name="additionalInfo" rows="3"><c:out value="${requestScope.profile.additionalInfo}" /></textarea>
            </div>

            <div class="form-actions">
                <button class="btn btn-primary" type="submit">Save Profile</button>
                <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</main>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
