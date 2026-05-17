# 🏥 SmartOP — Hospital Queue Management System

A full-stack web application that digitizes hospital 
OPD queue management for government hospitals.

## 🚀 Features
- Patient registration and appointment booking
- Live queue tracking with real-time updates
- Alert system when turn is near
- Doctor dashboard to manage queue
- Admin panel with reports and analytics

## 🛠️ Tech Stack
- **Backend:** Java, Spring Boot, REST API
- **Database:** MySQL
- **Frontend:** HTML, CSS, JavaScript

## 👥 3 User Portals
1. **Patient** — Book slots, track live queue
2. **Doctor** — Manage daily patient queue  
3. **Admin** — Control doctors, departments, reports

## ▶️ How To Run
1. Clone the repository
2. Setup MySQL database named `hospital`
3. Update `application.properties` with your MySQL password
4. Run: `mvnw.cmd spring-boot:run`
5. Open: `http://localhost:8080/index.html`

## 🔐 Default Login
| Portal | ID | Password |
|--------|-----|----------|
| Admin | ADMIN001 | admin123 |
| Doctor | DR001 | doc123 |
| Patient | Register with mobile | — |

## 📸 Project Structure
- Landing page with 3 portal login buttons
- Patient books appointment and tracks live queue
- Doctor manages queue — call next, mark done
- Admin controls doctors, departments and reports
