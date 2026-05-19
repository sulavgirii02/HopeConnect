# HopeConnect

HopeConnect is a Java/Jakarta EE welfare management web application for publishing aid programs, accepting user applications, reviewing applications from an admin panel, and notifying users about status changes.

The project currently uses servlet controllers, JSP views, JDBC DAOs, a small service layer, MySQL schema scripts, and a Maven WAR build.

## Technology Stack

- Backend: Java 11+, Jakarta Servlet 6.0, JSP 3.1
- Frontend: JSP, JSTL, EL, CSS, Google Fonts
- Database: MySQL with JDBC PreparedStatements
- Build: Maven WAR project
- Server: Apache Tomcat 11 or another Jakarta Servlet 6 compatible container
- Security: BCrypt password hashing, session authentication, role-based `AuthFilter`

## Current Project Structure

```text
.
|-- pom.xml
|-- README.md
|-- db.sql
|-- db_backup.sql
|-- backup.sh
|-- DEPLOYMENT_GUIDE.md
|-- HERO_ENHANCEMENTS.md
|-- HERO_ENHANCEMENTS.txt
|-- VISUAL_GUIDE.md
|-- guidelines_extracted.txt
|-- test.txt
|-- ts
|-- src/
|   |-- main/
|       |-- java/
|       |   |-- com/hopeconnect/
|       |       |-- controller/
|       |       |   |-- AboutServlet.java
|       |       |   |-- AdminApplicationServlet.java
|       |       |   |-- AdminDashboardServlet.java
|       |       |   |-- AdminMessagesServlet.java
|       |       |   |-- AdminProgramServlet.java
|       |       |   |-- AdminUserServlet.java
|       |       |   |-- ApplicationHistoryServlet.java
|       |       |   |-- ApplicationServlet.java
|       |       |   |-- ApplicationSuccessServlet.java
|       |       |   |-- ContactServlet.java
|       |       |   |-- HomeServlet.java
|       |       |   |-- LoginServlet.java
|       |       |   |-- LogoutServlet.java
|       |       |   |-- NotificationServlet.java
|       |       |   |-- ProfileServlet.java
|       |       |   |-- ProgramListingServlet.java
|       |       |   |-- RegisterServlet.java
|       |       |   |-- UserDashboardServlet.java
|       |       |   |-- WishlistServlet.java
|       |       |-- dao/
|       |       |-- filter/
|       |       |-- model/
|       |       |-- service/
|       |       |-- util/
|       |-- webapp/
|           |-- 403.jsp
|           |-- index.jsp
|           |-- WEB-INF/web.xml
|           |-- css/
|           |   |-- layout.css
|           |   |-- theme.css
|           |-- error/
|           |   |-- 404.jsp
|           |   |-- 500.jsp
|           |-- views/
|               |-- about.jsp
|               |-- applicationHistory.jsp
|               |-- applicationSuccess.jsp
|               |-- applyForm.jsp
|               |-- contact.jsp
|               |-- dashboard.jsp
|               |-- home.jsp
|               |-- login.jsp
|               |-- notifications.jsp
|               |-- profile.jsp
|               |-- programListing.jsp
|               |-- register.jsp
|               |-- userDashboard.jsp
|               |-- wishlist.jsp
|               |-- admin/
|               |-- partials/
|-- target/
```

## Main Modules

- `controller`: servlet endpoints for public pages, user workflows, and admin workflows.
- `dao`: JDBC data access classes for users, programs, applications, notifications, wishlists, audit logs, dashboard stats, and contact messages.
- `service`: business logic wrappers around DAO operations.
- `model`: POJOs used by controllers, services, DAOs, and JSPs.
- `filter`: `AuthFilter` protects authenticated and admin-only routes.
- `util`: database connection, schema migration, startup seeding, password hashing, validation, and flash messages.
- `webapp/views`: JSP pages rendered by servlet controllers.
- `webapp/css`: shared layout and theme styles.

## Routes

Public routes:

- `/home`
- `/about`
- `/contact`
- `/login`
- `/logout`
- `/register`
- `/programs`
- `/application-success`

Authenticated user routes:

- `/dashboard`
- `/user-dashboard`
- `/profile`
- `/apply`
- `/my-applications`
- `/wishlist`
- `/notifications`

Admin routes:

- `/admin/dashboard`
- `/admin/programs`
- `/admin/applications`
- `/admin/users`
- `/admin/messages`

`src/main/webapp/WEB-INF/web.xml` sets `home` as the welcome file, maps `/programs` and `/wishlist`, configures `AuthFilter`, and declares custom 404/500 pages. Most other servlet routes use `@WebServlet` annotations.

## Database

The application is configured for:

```text
Database: hopeconnect_db
URL:      jdbc:mysql://localhost:3306/hopeconnect_db?useSSL=false&serverTimezone=UTC
User:     root
Password: 12345
```

Update these values in `src/main/java/com/hopeconnect/util/DBConnection.java` if your local MySQL credentials differ.

`db.sql` currently creates these tables:

- `users`
- `user_profiles`
- `aid_programs`
- `applications`
- `notifications`
- `audit_logs`
- `wishlists`
- `application_documents`
- `contact_messages`
- `login_logs`

`StartupSeeder` runs on application startup. It calls `SchemaMigrator.migrate()` and creates these default accounts if they do not already exist:

```text
Admin: admin@hopeconnect.local / Admin@123
User:  user@hopeconnect.local  / User@123
```

## Setup

1. Install Java JDK 11 or newer.
2. Install Maven 3.6 or newer.
3. Install and start MySQL.
4. Create/import the database:

```bash
mysql -u root -p < db.sql
```

5. Confirm the database credentials in `DBConnection.java`.
6. Build the WAR:

```bash
mvn clean package -DskipTests
```

7. Deploy the generated WAR:

```text
target/HopeConnect.war
```

Copy it to Tomcat's `webapps` directory, start Tomcat, then open:

```text
http://localhost:8080/HopeConnect/home
```

## Features

- User registration and login
- BCrypt password hashing
- Automatic default admin/demo user seeding
- Role-based route protection
- Public program browsing
- User application submission
- Application history tracking
- Notifications for application updates
- Wishlist support
- User profile management
- Contact form and admin message review
- Admin dashboard metrics
- Admin program management
- Admin application approval/rejection with audit logging
- Admin user verification, status, and role management

## Admin Workflow

1. Log in with the seeded admin account or another admin user.
2. Open `/admin/dashboard`.
3. Manage aid programs at `/admin/programs`.
4. Review submitted applications at `/admin/applications`.
5. Manage users at `/admin/users`.
6. Review contact messages at `/admin/messages`.

## User Workflow

1. Register or log in with the seeded demo account.
2. Browse programs at `/programs`.
3. Apply through `/apply?programId=<id>`.
4. Track applications at `/my-applications`.
5. Read updates at `/notifications`.
6. Save programs at `/wishlist`.

## Design Assets and Styling

The active CSS files are:

- `src/main/webapp/css/theme.css`
- `src/main/webapp/css/layout.css`

The project also includes design/reference notes:

- `VISUAL_GUIDE.md`
- `HERO_ENHANCEMENTS.md`
- `HERO_ENHANCEMENTS.txt`
- `guidelines_extracted.txt`

## Backup Notes

`backup.sh` creates timestamped SQL dumps under `./backups`, but it currently uses `DB_NAME="hopeconnect"`. The application and `db.sql` use `hopeconnect_db`, so update `backup.sh` before relying on it:

```bash
DB_NAME="hopeconnect_db"
```

Then run:

```bash
chmod +x backup.sh
./backup.sh
```

On Windows, you can also use `mysqldump` directly:

```bash
mysqldump -u root -p hopeconnect_db > db_backup.sql
```

## Useful Commands

Build the project:

```bash
mvn clean package -DskipTests
```

Compile only:

```bash
mvn compile
```

Check the current Git changes:

```bash
git status --short
```

## Notes

- This is a WAR-based web application, not a standalone Spring Boot app.
- JSP pages should stay view-focused; controller, service, and DAO classes handle application logic and database work.
- `DBConnection` uses `DriverManager`; connection pooling is not implemented.
- The repository currently contains generated/build output under `target/` and IDE metadata under `.idea/`.
- This project is for CS5054NT coursework.
