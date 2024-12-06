# Green Shadow Crop Monitoring System

## Overview
The **Green Shadow Crop Monitoring System** is a comprehensive web-based application for managing and monitoring agricultural operations. The system supports features like crop management, field tracking, equipment and vehicle allocation, staff assignments, and monitoring logs, all with robust security and role-based access control.

## Features
- **User Management:** Role-based access control (MANAGER, ADMINISTRATIVE, SCIENTIST).
- **Field Management:** Manage fields with geolocation and images.
- **Crop Management:** Add, update, and view crop details including scientific and common names, categories, and seasons.
- **Staff Management:** Assign staff to fields, vehicles, and equipment.
- **Equipment and Vehicle Management:** Manage and allocate resources to staff and fields.
- **Monitoring Logs:** Record and retrieve monitoring activities.
- **JWT Authentication:** Token-based authentication for secure access.
- **CORS Enabled:** Supports secure communication with front-end applications.
- **Blacklist Tokens:** Securely log out users by invalidating tokens.

## API Documentation
https://documenter.getpostman.com/view/36176457/2sAYBbeUgh

## Front-END Repo
https://github.com/glen654/Green-shadow-crop-monitoring-system.git

## Technologies Used
### Backend
- **Java 17**
- **Spring Boot**
    - Spring Security
    - Spring Data JPA
    - Spring Web
- **MySQL**: Database
- **JJWT**: JSON Web Token for authentication
- **Lombok**: Reduce boilerplate code
- **Maven**: Build tool

### Frontend
- **HTML**, **CSS**, **JavaScript** (Vanilla JS/jQuery)

### Tools
- **Postman**: API testing
- **Git**: Version control
- **IntelliJ IDEA**: IDE
- **MySQL Workbench**: Database management

## Setup and Installation
### Prerequisites
- Java 17+
- Maven
- MySQL
- Node.js (optional for front-end builds)

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-repo/green-shadow-crop-monitoring.git
   cd green-shadow-crop-monitoring

## API Endpoints

### Authentication

`POST` /api/v1/auth/login: Authenticate and obtain a token.

`POST` /api/v1/auth/refresh: Refresh the token.

`POST` /api/v1/auth/logout: Blacklist a token.

### Crop Management

`GET` /api/v1/crop: Get all crops.

`POST` /api/v1/crop: Add a new crop.

`PUT` /api/v1/crop/{id}: Update a crop.

`DELETE` /api/v1/crop/{id}: Delete a crop.

### Field Management

`GET` /api/v1/field: Get all fields.

`POST` /api/v1/field: Add a new field.

`PUT `/api/v1/field/{id}: Update a field.

`DELETE` /api/v1/field/{id}: Delete a field.

### Equipment Management

`GET` /api/v1/equipment: Get all equipment.

`POST` /api/v1/equipment: Add new equipment.

`PUT` /api/v1/equipment/{id}: Update equipment details.

`DELETE` /api/v1/equipment/{id}: Delete equipment.

### Monitoring Logs

`GET` /api/v1/monitoring-log: Get all monitoring logs.

`POST` /api/v1/monitoring-log: Create a monitoring log.

`PUT` /api/v1/monitoring-log/{id}: Update a monitoring log.

`DELETE `/api/v1/monitoring-log/{id}: Delete a monitoring log.

### Vehicle Management

`GET` /api/v1/vehicle: Get all vehicles.

`POST` /api/v1/vehicle: Add a new vehicle.

`PUT` /api/v1/vehicle/{id}: Update vehicle details.

`DELETE` /api/v1/vehicle/{id}: Delete a vehicle.

### Staff Management

`GET` /api/v1/staff: Get all staff members.

`POST` /api/v1/staff: Add a new staff member.

`PUT `/api/v1/staff/{id}: Update staff details.

`DELETE` /api/v1/staff/{id}: Delete a staff member.


## License

This project is licensed [License.txt](License.txt) under the MIT License.