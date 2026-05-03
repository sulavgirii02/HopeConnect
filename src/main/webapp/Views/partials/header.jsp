<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<style>
@import url('https://fonts.googleapis.com/css2?family=Nunito:wght@400;600;700&family=Playfair+Display:wght@600;700&display=swap');
:root {
  --color-primary: #1A6B5A;
  --color-primary-dark: #0F6E56;
  --color-primary-darkest: #0F3D2E;
  --color-primary-light: #5DCAA5;
  --color-primary-tint: #D4F0E6;
  --color-primary-ghost: #EAF6F2;
  --color-blue: #185FA5;
  --color-blue-ghost: #E6F1FB;
  --color-blue-tint: #B5D4F4;
  --color-amber: #BA7517;
  --color-amber-ghost: #FAEEDA;
  --color-amber-tint: #FAC775;
  --color-red: #E24B4A;
  --color-red-ghost: #FCEBEB;
  --color-red-tint: #F7C1C1;
  --color-gray-dark: #444441;
  --color-gray-mid: #888780;
  --color-gray-light: #D3D1C7;
  --color-gray-ghost: #F1EFE8;
  --color-page-bg: #F7FAF9;
  --color-white: #FFFFFF;
  --font-heading: 'Playfair Display', Georgia, serif;
  --font-body: 'Nunito', sans-serif;
  --radius-sm: 6px;
  --radius-md: 10px;
  --radius-lg: 16px;
  --radius-xl: 24px;
  --shadow-sm: 0 1px 4px rgba(15,61,46,0.07);
  --shadow-md: 0 4px 16px rgba(15,61,46,0.10);
  --transition: 0.2s ease;
}

* {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
}

html, body { height: 100%; }
body {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: var(--font-body);
  background: var(--color-page-bg);
  color: var(--color-gray-dark);
  font-size: 15px;
  line-height: 1.6;
  overflow-x: hidden;
}

h1, h2 {
  font-family: var(--font-heading);
  color: var(--color-primary-darkest);
}

h3, h4 {
  font-family: var(--font-body);
  color: var(--color-gray-dark);
}

.muted { color: var(--color-gray-mid); }

.page { padding: 30px 0; }
.page-center { display: flex; justify-content: center; }
.card-narrow { max-width: 560px; width: 100%; }
.card-tight { max-width: 460px; width: 100%; }
.card-wide { max-width: 900px; width: 100%; }
.card-center { margin: 0 auto; }
.mt-2 { margin-top: 12px; }
.mt-3 { margin-top: 18px; }
.mt-4 { margin-top: 24px; }
.mb-3 { margin-bottom: 18px; }
.mb-4 { margin-bottom: 24px; }
.text-center { text-align: center; }
.stack { display: flex; flex-direction: column; gap: 12px; }
.actions { display: flex; gap: 8px; flex-wrap: wrap; }
.content-grid { display: grid; grid-template-columns: minmax(0, 1.5fr) minmax(280px, 0.8fr); gap: 24px; align-items: start; }
.hero { 
  padding: 60px 40px; 
  background: linear-gradient(135deg, var(--color-primary-ghost) 0%, var(--color-blue-ghost) 50%, var(--color-white) 100%);
  border: none;
  border-radius: var(--radius-xl);
  box-shadow: 0 8px 32px rgba(26,107,90,0.12);
  position: relative;
  overflow: hidden;
}
.hero::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -10%;
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(93,202,165,0.1) 0%, transparent 70%);
  border-radius: 50%;
}
.hero::after {
  content: '';
  position: absolute;
  bottom: -30%;
  left: -5%;
  width: 300px;
  height: 300px;
  background: radial-gradient(circle, rgba(24,95,165,0.08) 0%, transparent 70%);
  border-radius: 50%;
}
.hero > * { position: relative; z-index: 1; }
.hero h1 { 
  font-size: 2.8rem; 
  margin-bottom: 16px;
  font-weight: 700;
  letter-spacing: -0.5px;
  background: linear-gradient(135deg, var(--color-primary-dark) 0%, var(--color-primary-darkest) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}
.hero p { 
  color: var(--color-gray-mid);
  font-size: 1.05rem;
  line-height: 1.6;
  margin-bottom: 28px;
  max-width: 600px;
}
.hero .btn { 
  margin-right: 12px;
  margin-bottom: 12px;
  transition: all var(--transition);
}
.hero .btn-primary {
  box-shadow: 0 4px 12px rgba(26,107,90,0.25);
}
.hero .btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(26,107,90,0.35);
}
.hero .btn-secondary {
  background: var(--color-white);
}
.hero .btn-secondary:hover {
  background: var(--color-white);
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(26,107,90,0.15);
}
.section-title { margin-top: 24px; }
.card-link { text-decoration: none; color: var(--ink); }
.badge-count { background: var(--color-primary); color: var(--color-white); padding: 2px 8px; border-radius: 999px; font-size: 0.75rem; }
.max-460 { max-width: 460px; }
.max-560 { max-width: 560px; }
.max-720 { max-width: 720px; }
.center { margin: 0 auto; }
.mt-1 { margin-top: 6px; }
.mb-1 { margin-bottom: 6px; }
.mb-2 { margin-bottom: 12px; }
.align-end { align-self: flex-end; }
.full { width: 100%; }
.max-460 { max-width: 460px; }
.max-560 { max-width: 560px; }
.max-720 { max-width: 720px; }
.center { margin: 0 auto; }
.mt-1 { margin-top: 6px; }
.mb-1 { margin-bottom: 6px; }
.mb-2 { margin-bottom: 12px; }
.align-end { align-self: flex-end; }
.full { width: 100%; }

.container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 0 20px;
}

/* Ensure main page content grows to push footer to the bottom when content is short */
main.container.page { flex: 1 1 auto; }

.admin-page.container {
  max-width: none;
  padding: 0;
  margin: 0;
}

.admin-page .admin-layout {
  width: 100%;
  min-height: calc(100vh - 58px);
}

.admin-page .admin-sidebar {
  flex: 0 0 260px;
  width: 260px;
  min-height: calc(100vh - 58px);
}

.admin-page .admin-content {
  flex: 1 1 auto;
  min-width: 0;
  width: 100%;
  max-width: none;
  margin: 0;
}

.nav {
  background: var(--color-primary);
  position: sticky;
  top: 0;
  z-index: 100;
  box-shadow: 0 2px 10px rgba(15,61,46,0.12);
}

.nav-inner {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 14px 0;
}

.nav .container {
  max-width: none;
  padding-left: 32px;
  padding-right: 32px;
}

.brand {
  font-family: var(--font-heading);
  font-size: 1.25rem;
  font-weight: 700;
  color: var(--color-white);
  letter-spacing: 0.4px;
  text-decoration: none;
  white-space: nowrap;
}

.brand:hover { color: var(--color-white); text-decoration: none; }

.nav-links {
    display: flex;
    gap: 8px;
    list-style: none;
    flex-wrap: wrap;
    justify-content: flex-end;
}

.nav-links a {
  color: rgba(255,255,255,0.75);
  text-decoration: none;
  font-weight: 600;
  font-size: 0.9rem;
  padding: 8px 10px;
  border-radius: var(--radius-sm);
  transition: var(--transition);
  display: block;
}

.nav-links a:hover {
  color: var(--color-white);
  text-decoration: none;
  background: rgba(255,255,255,0.12);
}

.nav-toggle {
  display: none;
  background: transparent;
  border: 1px solid rgba(255,255,255,0.4);
  color: var(--color-white);
  padding: 6px 10px;
  border-radius: var(--radius-sm);
}

.badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
}

.badge-pending { background: var(--color-amber-ghost); color: var(--color-amber); }
.badge-approved { background: var(--color-primary-tint); color: var(--color-primary-dark); }
.badge-rejected { background: var(--color-red-ghost); color: var(--color-red); }

.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 10px 22px;
  border: none;
  border-radius: var(--radius-md);
  cursor: pointer;
  font-weight: 600;
  text-decoration: none;
  transition: all var(--transition);
  gap: 6px;
  font-size: 0.95rem;
}

.btn-primary { 
  background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-primary-dark) 100%);
  color: var(--color-white);
  box-shadow: 0 4px 12px rgba(26,107,90,0.25);
}
.btn-primary:hover { 
  background: linear-gradient(135deg, var(--color-primary-dark) 0%, var(--color-primary-darkest) 100%);
  box-shadow: 0 6px 20px rgba(26,107,90,0.35);
  transform: translateY(-2px);
}

.btn-secondary { 
  background: var(--color-white);
  border: 2px solid var(--color-primary);
  color: var(--color-primary);
  box-shadow: 0 2px 8px rgba(26,107,90,0.1);
}
.btn-secondary:hover { 
  background: var(--color-primary-ghost);
  border-color: var(--color-primary-dark);
  color: var(--color-primary-dark);
  box-shadow: 0 4px 12px rgba(26,107,90,0.15);
  transform: translateY(-2px);
}

.btn-danger { 
  background: linear-gradient(135deg, var(--color-red) 0%, #d4383a 100%);
  color: var(--color-white);
  box-shadow: 0 4px 12px rgba(226,75,74,0.25);
}
.btn-danger:hover { 
  background: linear-gradient(135deg, #d4383a 0%, #c0302f 100%);
  box-shadow: 0 6px 20px rgba(226,75,74,0.35);
  transform: translateY(-2px);
}

.card {
  background: var(--color-white);
  border: 1px solid var(--color-gray-light);
  border-radius: var(--radius-lg);
  padding: 20px 22px;
  box-shadow: var(--shadow-sm);
  transition: var(--transition);
}

.card:hover { box-shadow: var(--shadow-md); }

.grid {
    display: flex;
    flex-wrap: wrap;
    gap: 18px;
}

.grid > .card {
    flex: 1 1 280px;
}

.table {
  width: 100%;
  border-collapse: collapse;
  background: var(--color-white);
  border: 1px solid var(--color-gray-light);
  border-radius: var(--radius-lg);
  overflow: hidden;
}

.table th, .table td {
    text-align: left;
    padding: 12px;
    border-bottom: 1px solid var(--line);
    font-size: 0.95rem;
}

.table th {
  background: var(--color-primary-ghost);
  color: var(--color-primary-dark);
  font-weight: 700;
  font-size: 13px;
}

.table tr:nth-child(even) { background: var(--color-gray-ghost); }

.form-control {
  width: 100%;
  padding: 10px 14px;
  border: 1.5px solid var(--color-gray-light);
  border-radius: var(--radius-md);
  background: var(--color-white);
  font-family: var(--font-body);
  font-size: 15px;
}

.form-control:focus {
  border-color: var(--color-primary);
  outline: none;
  box-shadow: 0 0 0 3px rgba(26,107,90,0.12);
}

.form-group {
    display: flex;
    flex-direction: column;
    gap: 6px;
}

.form-row {
    display: flex;
    gap: 16px;
    flex-wrap: wrap;
}

.form-row .form-group {
    flex: 1 1 220px;
}

.form-actions { display: flex; gap: 10px; flex-wrap: wrap; }
.table-wrap { overflow-x: auto; }
.table { min-width: 640px; }

.error-text { color: var(--color-red); font-size: 0.85rem; }
.success-text { color: var(--color-primary-dark); font-size: 0.9rem; }
.error-box { color: var(--color-red); background: var(--color-red-ghost); border-left: 3px solid var(--color-red); border-radius: var(--radius-sm); padding: 10px; }
.success-box { color: var(--color-primary-dark); background: var(--color-primary-tint); border-left: 3px solid var(--color-primary); border-radius: var(--radius-sm); padding: 10px; }

.user-dashboard {
  max-width: 1120px;
}

.dashboard-hero {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  background: var(--color-white);
  border: 1px solid var(--color-gray-light);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
  padding: 28px 32px;
  margin-bottom: 24px;
}

.eyebrow {
  color: var(--color-primary-dark);
  font-size: 0.8rem;
  font-weight: 800;
  letter-spacing: 0.04em;
  text-transform: uppercase;
  margin-bottom: 6px;
}

.program-panel {
  background: var(--color-white);
  border: 1px solid var(--color-gray-light);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
  padding: 28px 32px 32px;
}

.section-heading {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  gap: 16px;
  margin-bottom: 20px;
}

.program-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
  gap: 16px;
}

.program-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 18px;
  min-height: 190px;
  border: 1px solid var(--color-gray-light);
  border-radius: var(--radius-md);
  background: var(--color-page-bg);
  padding: 18px;
}

.program-card h3 {
  color: var(--color-primary-darkest);
  margin: 8px 0 6px;
  overflow-wrap: anywhere;
}

.program-category {
  display: inline-flex;
  width: fit-content;
  max-width: 100%;
  background: var(--color-primary-ghost);
  color: var(--color-primary-dark);
  border-radius: 999px;
  padding: 4px 10px;
  font-size: 0.78rem;
  font-weight: 700;
  overflow-wrap: anywhere;
}

.program-meta {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  border-top: 1px solid var(--color-gray-light);
  padding-top: 12px;
}

.program-meta span {
  text-align: right;
  overflow-wrap: anywhere;
}

.empty-state {
  grid-column: 1 / -1;
  border: 1px dashed var(--color-gray-light);
  border-radius: var(--radius-md);
  padding: 28px;
  text-align: center;
  background: var(--color-page-bg);
}

.footer {
  padding: 30px 0;
  margin-top: 40px;
  text-align: center;
  color: var(--color-white);
  background: var(--color-primary-darkest);
}

@media (max-width: 1100px) {
  .admin-layout { flex-direction: column !important; }
  .admin-sidebar {
    width: 100% !important;
    position: static !important;
    max-height: none !important;
    border-right: none !important;
    border-bottom: 1px solid var(--color-gray-light) !important;
    display: flex !important;
    flex-wrap: wrap !important;
    padding: 12px !important;
    gap: 8px !important;
  }
  .admin-sidebar h3 { flex-basis: 100% !important; padding: 0 !important; margin: 4px 0 !important; }
  .admin-sidebar a { flex: 1 1 160px !important; border-left: none !important; border-radius: var(--radius-sm) !important; background: var(--color-white); }
  .admin-content { padding: 22px 16px 0 !important; width: 100% !important; max-width: none !important; min-width: 0 !important; }
  .admin-page { padding-top: 18px !important; }
}

@media (max-width: 768px) {
  .nav-toggle { display: inline-flex; }
  .nav-inner { flex-wrap: wrap; gap: 10px; }
  .nav-links { display: none; flex-direction: column; background: var(--color-primary); padding: 10px; width: 100%; gap: 4px; }
  .nav-links a { padding: 10px 12px; }
  .nav-links.open { display: flex; }
  .grid { flex-direction: column; }
  .content-grid { grid-template-columns: 1fr; }
  .page { padding: 20px 0; }
  .container { padding: 0 16px; }
  .form-row { flex-direction: column; }
  .form-row .form-group { flex: 1 1 auto; }
  .form-actions { flex-direction: column; align-items: stretch; }
  .form-actions .btn { width: 100%; }
  .table { min-width: 560px; }
  .card { padding: 16px; }
  .dashboard-hero { align-items: stretch; flex-direction: column; padding: 22px; }
  .program-panel { padding: 22px; }
  .section-heading { align-items: flex-start; flex-direction: column; }
  .program-grid { grid-template-columns: 1fr; }
  .hero { padding: 36px 24px; }
  .hero h1 { font-size: 2rem; }
}

@media (max-width: 480px) {
  .hero h1 { font-size: 1.7rem; }
  .hero p { font-size: 0.98rem; }
  .btn { width: 100%; }
  .actions { flex-direction: column; align-items: stretch; }
}
</style>

<header class="nav">
    <div class="container nav-inner">
        <a class="brand" href="${pageContext.request.contextPath}/home">HopeConnect</a>
        <button class="nav-toggle" type="button" onclick="toggleNav()">Menu</button>
        <ul class="nav-links" id="navLinks">
            <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/about">About</a></li>
            <li><a href="${pageContext.request.contextPath}/contact">Contact</a></li>
            <c:choose>
                <c:when test="${not empty sessionScope.user && sessionScope.user.role == 'admin'}">
                    <li><a href="${pageContext.request.contextPath}/admin/dashboard">Admin Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/programs">Manage Programs</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/applications">Applications</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/users">Users</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </c:when>
                <c:when test="${not empty sessionScope.user}">
                    <li><a href="${pageContext.request.contextPath}/dashboard">Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/available-programs">Available Programs</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageContext.request.contextPath}/login">Login</a></li>
                    <li><a href="${pageContext.request.contextPath}/register">Register</a></li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</header>

<script>
function toggleNav() {
    var links = document.getElementById('navLinks');
    if (links) links.classList.toggle('open');
}
</script>
