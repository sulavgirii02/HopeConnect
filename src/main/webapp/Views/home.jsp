<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="HopeConnect - A trusted digital welfare platform connecting families to aid programs with transparency and dignity.">
    <title>HopeConnect - Welfare Support Platform</title>
</head>
<body>
<jsp:include page="/views/partials/header.jsp" />

<main class="container page">

    <!-- ===== Hero Section ===== -->
    <section class="hero" id="hero">
        <div class="hero-text">
            <h1>Connecting People to Welfare Support, Fairly and Transparently</h1>
            <p>HopeConnect helps families discover welfare programs, submit applications, and track progress in one trusted place. Access support with dignity.</p>
            <div class="hero-actions">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/dashboard">Go to Dashboard</a>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/programs">Browse Programs</a>
                    </c:when>
                    <c:otherwise>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Sign In</a>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/register">Create Account</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <div class="hero-visual">
            <div class="hero-preview-card">
                <div class="preview-header">
                    <span class="preview-title">Family Support Program</span>
                    <span class="badge badge-approved">Open</span>
                </div>
                <div class="preview-meta">
                    <div class="preview-row"><span>Category</span><strong>Emergency Aid</strong></div>
                    <div class="preview-row"><span>Quota Remaining</span><strong>45</strong></div>
                    <div class="preview-row"><span>Deadline</span><strong>25 June 2026</strong></div>
                </div>
                <a class="btn btn-primary btn-sm full" href="${pageContext.request.contextPath}/programs">Apply Now</a>
                <div class="hero-tracker">
                    <div class="tracker-step done"><span class="tracker-dot">&#10003;</span><span>Submitted</span></div>
                    <div class="tracker-line done"></div>
                    <div class="tracker-step done"><span class="tracker-dot">&#10003;</span><span>Verified</span></div>
                    <div class="tracker-line"></div>
                    <div class="tracker-step active"><span class="tracker-dot">3</span><span>Review</span></div>
                    <div class="tracker-line"></div>
                    <div class="tracker-step"><span class="tracker-dot">4</span><span>Approved</span></div>
                </div>
            </div>
        </div>
    </section>

    <!-- ===== Emergency Alert Banner ===== -->
    <div class="emergency-banner" id="emergency-alert">
        <span class="banner-icon">
            <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#F97316" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86L1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/><line x1="12" y1="9" x2="12" y2="13"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>
        </span>
        <span class="banner-text"><strong>Emergency Notice:</strong> Food relief applications are open until 25 June. Apply before the deadline closes.</span>
        <a class="btn btn-sm btn-secondary" href="${pageContext.request.contextPath}/programs" style="flex-shrink:0;">View Programs</a>
    </div>

    <!-- ===== Statistics Section ===== -->
    <section class="stats-grid" id="statistics">
        <div class="stat-card">
            <span class="stat-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><rect x="2" y="3" width="20" height="14" rx="2"/><line x1="8" y1="21" x2="16" y2="21"/><line x1="12" y1="17" x2="12" y2="21"/></svg></span>
            <div class="stat-value"><c:out value="${requestScope.statActivePrograms != null ? requestScope.statActivePrograms : '24'}" /></div>
            <div class="stat-label">Active Programs</div>
            <div class="stat-desc">Currently available</div>
        </div>
        <div class="stat-card">
            <span class="stat-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/><line x1="16" y1="13" x2="8" y2="13"/><line x1="16" y1="17" x2="8" y2="17"/></svg></span>
            <div class="stat-value"><c:out value="${requestScope.statApplicationsProcessed != null ? requestScope.statApplicationsProcessed : '1,250'}" /></div>
            <div class="stat-label">Applications Processed</div>
            <div class="stat-desc">This quarter</div>
        </div>
        <div class="stat-card">
            <span class="stat-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg></span>
            <div class="stat-value"><c:out value="${requestScope.statFamiliesSupported != null ? requestScope.statFamiliesSupported : '860'}" /></div>
            <div class="stat-label">Families Supported</div>
            <div class="stat-desc">Across all programs</div>
        </div>
        <div class="stat-card">
            <span class="stat-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg></span>
            <div class="stat-value"><c:out value="${requestScope.statPartnerNGOs != null ? requestScope.statPartnerNGOs : '18'}" /></div>
            <div class="stat-label">Partner NGOs</div>
            <div class="stat-desc">Working together</div>
        </div>
    </section>

    <!-- ===== How It Works ===== -->
    <section class="section" id="how-it-works">
        <div class="section-header">
            <h2>How It Works</h2>
            <p>Getting welfare support is simple and transparent with HopeConnect</p>
        </div>
        <div class="steps-grid">
            <div class="card step-card">
                <span class="step-number">1</span>
                <span class="step-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="8.5" cy="7" r="4"/><line x1="20" y1="8" x2="20" y2="14"/><line x1="23" y1="11" x2="17" y2="11"/></svg></span>
                <h4>Register</h4>
                <p>Create your free HopeConnect account securely</p>
            </div>
            <div class="card step-card">
                <span class="step-number">2</span>
                <span class="step-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg></span>
                <h4>Complete Profile</h4>
                <p>Add family details and required documents</p>
            </div>
            <div class="card step-card">
                <span class="step-number">3</span>
                <span class="step-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><circle cx="11" cy="11" r="8"/><line x1="21" y1="21" x2="16.65" y2="16.65"/></svg></span>
                <h4>Discover Programs</h4>
                <p>Browse programs matching your eligibility</p>
            </div>
            <div class="card step-card">
                <span class="step-number">4</span>
                <span class="step-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"/><path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"/></svg></span>
                <h4>Apply Online</h4>
                <p>Submit applications with guided forms</p>
            </div>
            <div class="card step-card">
                <span class="step-number">5</span>
                <span class="step-icon"><svg width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><polyline points="22 12 18 12 15 21 9 3 6 12 2 12"/></svg></span>
                <h4>Track Status</h4>
                <p>Monitor progress with real-time updates</p>
            </div>
        </div>
    </section>

    <!-- ===== Featured Aid Programs ===== -->
    <section class="section" id="featured-programs">
        <div class="section-header">
            <h2>Featured Aid Programs</h2>
            <p>Explore currently available welfare assistance programs</p>
        </div>
        <div class="grid mt-2">
            <c:choose>
                <c:when test="${not empty requestScope.featuredPrograms}">
                    <c:forEach var="p" items="${requestScope.featuredPrograms}">
                        <div class="card program-card">
                            <span class="badge badge-info" style="align-self:flex-start;margin-bottom:12px;"><c:out value="${p.category}" /></span>
                            <h4><c:out value="${p.title}" /></h4>
                            <p><c:out value="${p.description}" /></p>
                            <div class="meta" style="margin-bottom:14px;">
                                <strong style="color:var(--color-gray-dark);">Eligibility:</strong> <c:out value="${p.eligibility}" />
                            </div>
                            <div class="card-footer">
                                <a class="btn btn-primary full" href="${pageContext.request.contextPath}/apply?programId=${p.id}">Apply Now</a>
                            </div>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <!-- Static fallback cards when no dynamic data -->
                    <div class="card program-card">
                        <span class="badge badge-info" style="align-self:flex-start;margin-bottom:12px;">Education</span>
                        <h4>Education Support</h4>
                        <p>Financial assistance for low-income students to continue their studies and access educational resources.</p>
                        <div class="meta"><strong style="color:var(--color-gray-dark);">Deadline:</strong> 20 June &middot; <strong>Quota:</strong> 35 remaining</div>
                        <div class="card-footer"><a class="btn btn-secondary full" href="${pageContext.request.contextPath}/programs">View Details</a></div>
                    </div>
                    <div class="card program-card">
                        <span class="badge badge-info" style="align-self:flex-start;margin-bottom:12px;">Health</span>
                        <h4>Health Assistance</h4>
                        <p>Medical support coverage for families who cannot afford healthcare and treatment costs.</p>
                        <div class="meta"><strong style="color:var(--color-gray-dark);">Deadline:</strong> 30 June &middot; <strong>Quota:</strong> 20 remaining</div>
                        <div class="card-footer"><a class="btn btn-primary full" href="${pageContext.request.contextPath}/programs">Apply Now</a></div>
                    </div>
                    <div class="card program-card">
                        <span class="badge badge-info" style="align-self:flex-start;margin-bottom:12px;">Food</span>
                        <h4>Food Relief</h4>
                        <p>Emergency food packages and nutritional support for vulnerable families and communities.</p>
                        <div class="meta"><strong style="color:var(--color-gray-dark);">Deadline:</strong> 25 June &middot; <strong>Quota:</strong> 50 remaining</div>
                        <div class="card-footer"><a class="btn btn-secondary full" href="${pageContext.request.contextPath}/programs">View Details</a></div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </section>
</main>

<!-- ===== Why HopeConnect (Alt background section) ===== -->
<section class="section-alt" id="why-hopeconnect">
    <div class="section-inner">
        <div class="section-header">
            <h2>Why HopeConnect</h2>
            <p>Built on the principles of fairness, transparency, and accessibility</p>
        </div>
        <div class="value-grid">
            <div class="card value-card">
                <span class="value-icon"><svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg></span>
                <h4>Transparent Process</h4>
                <p>Users can track their application progress at every stage, from submission to approval, ensuring complete visibility.</p>
            </div>
            <div class="card value-card">
                <span class="value-icon"><svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg></span>
                <h4>Fair Distribution</h4>
                <p>Aid allocation is managed through verification and quota control, ensuring resources reach those who need them most.</p>
            </div>
            <div class="card value-card">
                <span class="value-icon"><svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="#0F5D49" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg></span>
                <h4>Accessible Support</h4>
                <p>Rural and disadvantaged citizens can apply online without visiting offices repeatedly, saving time and costs.</p>
            </div>
        </div>
    </div>
</section>

<jsp:include page="/views/partials/footer.jsp" />
</body>
</html>
