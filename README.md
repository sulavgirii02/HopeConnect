# HopeConnect

HopeConnect is a welfare management web application built with Java Servlets, JSP, MySQL, and Jakarta EE. This project implements a complete role-based access control system for managing welfare aid programs and user applications.

## Technology Stack

- **Backend**: Java Servlets (Jakarta EE 6.0), JSP (Jakarta)
- **Database**: MySQL 5.7+ (XAMPP)
- **Build Tool**: Maven 3.6+
- **Server**: Tomcat 11 (Apache)
- **Frontend**: Pure CSS (Flexbox), JSTL/EL, Google Fonts (Nunito, Playfair Display)
- **Security**: BCrypt password hashing, Session-based auth, Role-based filters
- **Database Access**: PreparedStatements (JDBC)

## Prerequisites

Before setting up HopeConnect, ensure you have:
- Java JDK 11+ (tested with Java 23)
- Maven 3.6+ installed and in PATH
- XAMPP with MySQL service running
- Apache Tomcat 11 (or compatible)

## Setup Instructions

### 1. Database Setup (XAMPP + MySQL)

1. **Start XAMPP**:
   - Open XAMPP Control Panel and start **Apache** and **MySQL** services

2. **Create Database**:
   - Open phpMyAdmin: `http://localhost/phpmyadmin`
   - Create a new database named `hopeconnect_db`

3. **Import Schema**:
   - In phpMyAdmin, select `hopeconnect_db`
   - Go to **Import** tab and upload the SQL schema file
   - Alternatively, run the schema SQL from the command line:
     ```sql
     mysql -u root < D:\HopeConnect\db.sql
     ```

4. **Verify Database Credentials** in `src/main/java/com/hopeconnect/util/DBConnection.java`:
   ```java
   public static Connection getConnection() {
       String url = "jdbc:mysql://localhost:3306/hopeconnect_db";
       String user = "root";
       String password = ""; // Update if different
   }
   ```

### 2. Build WAR File

1. **Navigate to Project Root**:
   ```bash
   cd D:\HopeConnect
   ```

2. **Build with Maven**:
   ```bash
   mvn clean package -DskipTests
   ```
   
   This generates `target/HopeConnect.war`

3. **Troubleshooting**:
   - If Maven is not found, add Maven `bin` directory to system PATH
   - If you see Jakarta compilation errors, ensure `pom.xml` has Jakarta dependencies

### 3. Deploy to Tomcat

1. **Copy WAR to Tomcat**:
   ```bash
   copy target\HopeConnect.war "C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps\"
   ```

2. **Start Tomcat**:
   - Open Tomcat command line or use startup script
   - Windows: `catalina.bat run` or double-click `startup.bat`
   - Linux/Mac: `./catalina.sh run`

3. **Access Application**:
   - Navigate to: `http://localhost:8080/HopeConnect/home`

### 4. First-Time Admin Setup

1. **Access Login Page**: `http://localhost:8080/HopeConnect/login`

2. **Create Admin Account**:
   - Register with your details (must create user first)
   - Note: Admin accounts are assigned by database role (`role='admin'`)

3. **Set Admin Role** (via MySQL):
   ```sql
   UPDATE users SET role='admin' WHERE email='your_admin@email.com';
   ```

4. **Login as Admin**:
   - Go to `http://localhost:8080/HopeConnect/login`
   - Sign in with admin credentials
   - Access admin dashboard at `http://localhost:8080/HopeConnect/admin/dashboard`

## Project Structure

```
D:\HopeConnect\
├── pom.xml                    # Maven configuration (Jakarta dependencies)
├── db.sql                     # Database schema (17 tables)
├── README.md                  # This file
│
├── src/main/java/com/hopeconnect/
│   ├── controller/           # Servlets (Auth, User, Admin flows)
│   │   ├── LoginServlet.java
│   │   ├── RegisterServlet.java
│   │   ├── AdminDashboardServlet.java
│   │   ├── AdminProgramServlet.java
│   │   ├── AdminApplicationServlet.java
│   │   ├── AdminUserServlet.java
│   │   └── ... (12 total servlets)
│   │
│   ├── dao/                  # Data Access Objects (JDBC PreparedStatements)
│   │   ├── UserDAO.java
│   │   ├── AidProgramDAO.java
│   │   ├── ApplicationDAO.java
│   │   ├── NotificationDAO.java
│   │   └── AuditLogDAO.java
│   │
│   ├── model/               # POJOs (Plain Old Java Objects)
│   │   ├── User.java
│   │   ├── AidProgram.java
│   │   ├── Application.java
│   │   ├── Notification.java
│   │   └── AuditLog.java
│   │
│   ├── service/             # Business Logic Layer
│   │   ├── UserService.java
│   │   ├── AidProgramService.java
│   │   ├── ApplicationService.java
│   │   └── AuditLogService.java
│   │
│   └── util/                # Utilities
│       ├── DBConnection.java      (Connection pooling)
│       ├── PasswordUtil.java      (BCrypt hashing)
│       ├── ValidationUtil.java    (Email/Phone/Age validation)
│       └── AuthFilter.java        (Role-based access control)
│
└── src/main/webapp/
    ├── WEB-INF/web.xml               # Deployment descriptor (Jakarta EE 6.0)
    ├── views/
    │   ├── partials/
    │   │   ├── header.jsp            # Navigation bar + global CSS
    │   │   └── footer.jsp
    │   ├── home.jsp
    │   ├── about.jsp
    │   ├── contact.jsp
    │   ├── login.jsp
    │   ├── register.jsp
    │   ├── userDashboard.jsp
    │   ├── programListing.jsp
    │   ├── applyForm.jsp
    │   ├── applicationHistory.jsp
    │   ├── notifications.jsp
    │   ├── wishlist.jsp
    │   └── admin/
    │       ├── adminDashboard.jsp    (with sidebar layout)
    │       ├── adminPrograms.jsp
    │       ├── adminApplications.jsp
    │       └── adminUsers.jsp
    └── error/
        ├── 404.jsp
        └── 500.jsp
```

## Design System

### Color Palette
```css
--color-primary: #1A6B5A (Forest Green)
--color-blue: #185FA5
--color-amber: #BA7517
--color-red: #E24B4A
--color-gray-dark: #444441
--color-gray-mid: #888780
--color-gray-light: #D3D1C7
```

### Typography
- **Headings**: Playfair Display (serif, 600-700 weight)
- **Body**: Nunito (sans-serif, 400-700 weight)
- **Font Size**: 15px base, responsive scaling

### Layout
- **Container Max Width**: 1200px with 20px horizontal padding
- **Responsive Breakpoint**: 768px (mobile)
- **Sidebar**: 220px (admin pages only)
- **Grid Gap**: 18px

## Key Features

### Authentication & Authorization
- **Registration**: Email, phone, age validation with BCrypt password hashing
- **Login**: Session-based with legacy SHA-256 password auto-upgrade
- **Logout**: Clear session with flash message
- **Role-Based Access**: Admin vs. User via AuthFilter servlet filter

### Admin Panel
- **Dashboard**: Analytics (total users, applications, approval ratio)
- **Program Management**: Create, edit, publish/unpublish programs
- **Application Review**: Approve/reject user applications with audit logging
- **User Management**: Verify/deactivate user accounts

### User Portal
- **Program Browse**: Search and filter by name, category, eligibility
- **Application Submission**: Apply for programs with duplicate check protection
- **Application History**: Track status (pending/approved/rejected)
- **Notifications**: Real-time alerts on application status changes
- **Wishlist**: Save programs for later

### Database
- **17 Tables**: users, aid_programs, applications, notifications, audit_logs, etc.
- **Constraints**: Foreign keys, unique indexes, NOT NULL enforcement
- **Audit Logging**: All admin actions tracked with timestamp and user ID

## Security Features

1. **Password Security**:
   - BCrypt hashing (WORKLOAD=12)
   - Legacy SHA-256 support with auto-upgrade on login
   - Secure password reset capability

2. **SQL Injection Prevention**:
   - All queries use PreparedStatements
   - Parameterized queries for user input

3. **Session Management**:
   - Session-based authentication
   - Logout invalidates session
   - Redirect to login for unauthorized access

4. **Authorization**:
   - AuthFilter checks role for protected paths
   - Admin-only endpoints `/admin/*` restricted
   - User applications blocked for admin role

## Troubleshooting

### Issue: "One or more Filters failed to start"
**Solution**: Ensure `pom.xml` has Jakarta (not javax) dependencies:
```xml
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
    <version>6.0.0</version>
</dependency>
<dependency>
    <groupId>jakarta.servlet.jsp</groupId>
    <artifactId>jakarta.servlet.jsp-api</artifactId>
    <version>3.1.1</version>
</dependency>
<dependency>
    <groupId>jakarta.servlet.jsp.jstl</groupId>
    <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    <version>3.0.0</version>
</dependency>
```

### Issue: "Invalid salt version" on login
**Solution**: Ensure BCrypt is in `pom.xml`:
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```
And `PasswordUtil.java` has fallback for legacy hashes:
```java
if (!isValidBcrypt(password, hash)) {
    return verifyLegacySha256(password, hash); // Auto-upgrade
}
```

### Issue: Database connection fails
**Solution**: Verify MySQL is running:
```bash
mysql -u root -e "SELECT 1;"
```
And update `DBConnection.java` credentials.

## Admin User Workflows

### 1. Create & Publish a Program
1. Login as admin
2. Go to `/admin/programs`
3. Fill in program details (title, category, eligibility, description)
4. Select "Publish Now" in dropdown
5. Click "Create Program"
6. Program appears in user's program listing

### 2. Review & Approve Applications
1. Go to `/admin/applications`
2. View all pending applications
3. Click "Approve" or "Reject" with optional reason
4. User receives notification (status updated + email alert)

### 3. Manage Users
1. Go to `/admin/users`
2. Click "Verify" to approve new registrations
3. Click "Deactivate" to suspend account access

## User Workflows

### 1. Register & Login
1. Click "Register" on public site
2. Fill in: Full Name, Email, Phone, Age, Password
3. Receive confirmation
4. Login with email + password

### 2. Apply for Programs
1. Go to "Explore Programs" (`/programs`)
2. Browse or search by name/category
3. Click "Apply Now" on a program
4. Submit application
5. Check "My Applications" for status

### 3. Track Notifications
1. Go to "Notifications" (`/notifications`)
2. Receive alerts when admin approves/rejects
3. Mark as read to clear

## Notes

- **No Java in JSP**: All JSP pages use JSTL (`<c:if>`, `<c:forEach>`) and EL (`${variable}`)
- **MVC Compliance**: No database code in JSP; all logic in Service/DAO layers
- **CSS-Only Styling**: No Bootstrap; pure CSS with Flexbox + responsive media queries
- **Audit Trail**: All admin actions logged with timestamp, user, and action type
- **Session Flash Messages**: "You have been logged out." displayed on logout

## Testing Checklist

- [ ] Admin login and dashboard access
- [ ] Create and publish a program
- [ ] User registration with age validation
- [ ] User login and program browsing
- [ ] User application submission (duplicate check)
- [ ] Admin application review (approve/reject)
- [ ] User notification on status change
- [ ] Admin wishlist and application history views
- [ ] Logout with flash message
- [ ] Responsive design (768px breakpoint)


