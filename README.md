# HR Management System

## Overview
A comprehensive Human Resource Management System (HRMS) built with Spring Boot, providing REST APIs for managing employees, departments, jobs, and organizational entities. The system features secure JWT authentication, role-based access control, and multi-language support.

## Quick Links
- **API Documentation**: [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- **Base URL**: `http://localhost:8080/api/v1`
- **GitHub**: [MuhamedSaad112](https://github.com/MuhamedSaad112)
- **Contact**: [m.saad1122003@gmail.com](mailto:m.saad1122003@gmail.com)
- **LinkedIn**: [muhamedsaad112](https://www.linkedin.com/in/muhamedsaad112/)

## Database Design (Class Diagram)

![HR Management System](https://github.com/user-attachments/assets/5338aa34-9d30-4632-8973-a5fe83c97297)


## Features

### Core Functionality
- Employee lifecycle management
- Department and location tracking
- Job and career history
- Task assignment and tracking
- User authentication with JWT
- Role-based access control
- Multi-language support (English/Arabic)

### Key Features
- Secure JWT-based authentication
- Role-based access control (RBAC)
- Multi-language support (English/Arabic)
- Email notifications
- Redis caching
- Comprehensive auditing
- Swagger/OpenAPI documentation
- Error handling with Zalando Problem (0.27.0)


## Technical Stack

### Core Technologies
- Java 21
- Spring Boot 3.2.1
- MySQL Database
- Redis Cache (Redisson 3.39.0)
- JWT Authentication (0.11.5)
- MapStruct 1.5.5.Final
- Lombok 1.18.30
- SpringDoc OpenAPI 2.3.0

## Entities

### Core Entities
- AbstractAuditingEntity: A base entity class that tracks creation and modification details, such as timestamps and user info.
- Authority: Defines roles and authorities for users, enabling role-based access control (RBAC).
- Country: Represents countries for employee addresses and locations.
- Department: Represents departments within the organization.
- Employee: Contains personal and professional information of employees.
- Job: Defines various job positions within the organization.
- JobHistory: Keeps track of an employee's career progression.
- Location: Represents workplace locations.
- Region: Represents geographical regions where the company operates.
- Task: Tracks employee tasks and assignments.
- User: Defines system users, including their credentials, for JWT-based authentication.

## API Documentation

### Authentication Endpoints
```
POST   /api/v1/auth/login         # User authentication and JWT token generation
```
![{57809F17-384E-4276-8D2E-880CB91C1FEE}](https://github.com/user-attachments/assets/6c1b11c2-0d64-4e7c-b318-75ebe8906447)

### Employee Management
```
GET    /api/v1/employees          # List all employees
POST   /api/v1/employees          # Create new employee
GET    /api/v1/employees/{id}     # Get employee details
PUT    /api/v1/employees/{id}     # Update employee information
DELETE /api/v1/employees/{id}     # Delete employee
```
![{354D1760-F92C-406B-A369-59DCB4E68FA1}](https://github.com/user-attachments/assets/4a5c661e-e779-4250-846c-45220d993904)

### Department Management
```
GET    /api/v1/departments        # List all departments
POST   /api/v1/departments        # Create new department
GET    /api/v1/departments/{id}   # Get department details
PUT    /api/v1/departments/{id}   # Update department
DELETE /api/v1/departments/{id}   # Delete department
```
![{416DF997-3C3E-4FF7-A2F9-0EDEBA0853CF}](https://github.com/user-attachments/assets/056e7962-beb4-4af7-b336-ca9632d24b44)

### Job Management
```
GET    /api/v1/jobs              # List all jobs
POST   /api/v1/jobs              # Create new job
GET    /api/v1/jobs/{id}         # Get job details
PUT    /api/v1/jobs/{id}         # Update job
DELETE /api/v1/jobs/{id}         # Delete job
```
![{DC0D7624-24BD-4389-81A2-B541D0918A17}](https://github.com/user-attachments/assets/692ddc77-e5b7-4f27-a4c0-07f6872a1334)

### Location Management
```
GET    /api/v1/locations         # List all locations
POST   /api/v1/locations         # Create new location
GET    /api/v1/locations/{id}    # Get location details
PUT    /api/v1/locations/{id}    # Update location
DELETE /api/v1/locations/{id}    # Delete location
```
![{5307D06D-6502-4575-9F6A-034E53231584}](https://github.com/user-attachments/assets/b6d721df-7bc9-4c33-98a1-e70206a7fc50)

### Region Management
```
GET    /api/v1/regions          # List all regions
POST   /api/v1/regions          # Create new region
GET    /api/v1/regions/{id}     # Get region details
PUT    /api/v1/regions/{id}     # Update region
DELETE /api/v1/regions/{id}     # Delete region
```
![{F4A31A03-2808-4526-BE3D-D5568466753B}](https://github.com/user-attachments/assets/b4badbe0-951a-47b5-9d21-285d130cf28e)

### Task Management
```
GET    /api/v1/tasks           # List all tasks
POST   /api/v1/tasks           # Create new task
GET    /api/v1/tasks/{id}      # Get task details
PUT    /api/v1/tasks/{id}      # Update task
DELETE /api/v1/tasks/{id}      # Delete task
```
![{27EE0101-F581-477B-8AA7-CB49DB41586B}](https://github.com/user-attachments/assets/a9ab849b-7fe4-4634-87f8-ac10be8c006c)

### Country Management
```
GET    /api/v1/countries       # List all countries
POST   /api/v1/countries       # Create new country
GET    /api/v1/countries/{id}  # Get country details
PUT    /api/v1/countries/{id}  # Update country
DELETE /api/v1/countries/{id}  # Delete country
```
![{84E368BE-68C2-4345-A307-EFB83E21BC54}](https://github.com/user-attachments/assets/dc1d7d76-0fb1-4ba2-99c5-7af70e283ca9)

## Setup Guide

### Prerequisites
- Java 21 or higher
- Maven 3.8+
- MySQL/PostgreSQL
- SMTP server for emails (for email notifications)

### Quick Start
1. Clone repository:
   ```bash
   git clone https://github.com/MuhamedSaad112/HR-Management-System.git
   cd MuhamedSaad112-HR-Management-System
   ```

2. ### Configuration
Application properties template:
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/HRMS-DB
spring.datasource.username=root
spring.datasource.password=yourpassword

# Redis Configuration
spring.redis.host=localhost
spring.redis.port=6379

# Mail Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email
spring.mail.password=your_password

# JWT Configuration
jwt.secret=your_secret_key
jwt.expiration=86400

# Internationalization
spring.messages.basename=i18n/messages
spring.messages.encoding=UTF-8
```

3. Build and run:
   ```bash
   ./mvnw clean install
   ./mvnw spring-boot:run
   ```

4. Access:
   - API: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## Development

### Access Points
- API Base URL: `http://localhost:8080/api/v1`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- API Docs: `http://localhost:8080/v3/api-docs`

### Adding Features
1. Create entity in `entity/`
2. Create DTO in `dto/`
3. Implement mapper in `mapper/`
4. Add repository in `repository/`
5. Create service interface and implementation
6. Add controller endpoints
7. Update security if needed
8. Write tests

### Testing
```bash
./mvnw test
```

## Contributing
1. Fork repository
2. Create a feature branch
3. Commit changes
4. Submit pull request

## License
Copyright 2024 Muhamed Saad

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## 📞 Contact

📝 ***Feel free to contact me. I am always here ...***
[![Linkedin](https://img.shields.io/badge/LinkedIn-Mohamed%20Saad-blue?logo=Linkedin&logoColor=blue&labelColor=black)](https://www.linkedin.com/in/MuhamedSaad112/)
[![Mail](https://img.shields.io/badge/Gmail-m.saad1122003@gmail.com-blue?logo=Gmail&logoColor=blue&labelColor=black)](mailto:m.saad1122003@gmail.com)
<br>

## Author
Mohamed Saad - [GitHub Profile](https://github.com/MuhamedSaad112)
