# Learning Management System (Back-end)

### Used Technology -  Spring boot | React | MySQL | Firebase | AWS RDS & EC2 | TailwindCSS

This project is a comprehensive web application built using Spring Boot, React, and various technologies to provide a robust platform for managing student data, assignments, and educational resources. Here's a brief overview of the key features:

## Features
##### 1.  Authentication and Security: Utilizes Spring Boot JWT for secure role-based authentication (Role - Student and Admin).
##### 2. File and Image Storage: Firebase integration for storing assignment files and profile pictures.
##### 3. Scalable Deployment: Backend deployed on AWS EC2, with the database on AWS RDS, ensuring scalability and reliability.
##### 4. Entity Management: CRUD operations for entities like Student, Course, Batch, etc.
##### 5. Course Enrollment: Allows assigning subjects to courses and enrolling students in courses and batches.
##### 6. Email Integration: Facility to send emails integrated within the system.
##### 7. Data Visualization: Utilizes Pie, Bar, and Line charts to visualize assignment, student, and system data.
##### 8. Admin Dashboard: Real-time display of total students, subjects, schools, assignments, and pass rates
##### 9. Monitoring and Metrics: Tracks 200, 400, 404, 500 HTTP request count, error count, CPU, RAM, and database status using Spring Boot Actuator.
##### 10. Exception Handling: Custom exception handling for better error management.
##### 11. Performance Optimization: Implements pagination to improve API performance.
##### 12. Frontend Interaction: Axios for API data fetch, with state management in React using useState, useEffect, and Context.
##### 13. Responsive Design: All interfaces are designed to be responsive for optimal viewing across devices.

## Getting Started
To get started with our platform, follow these simple steps:
### Frontend Setup
##### 1. Clone this repo.`backend-royal-lms-system`.
##### 2. Install dependencies using `npm install`
##### 3. Run the development server using `npm run dev`.

### Backtend Setup
##### 1. Navigate to the `Royal-academy-lms-fontend` repo and clone it
##### 2. Set up your MySQL database and configure the database connection in `application.properties`.
##### 3. Build the Spring Boot application using your favorite IDE or Maven.
##### 4. Run the application, and the backend server will start on the configured port.


### Database diagram
![@localhost](https://github.com/LakshanChinthaka/backend-royal-lms-system/assets/115285758/189ab9f8-103f-4d34-bea0-66b69000d305)

### Home Page
![image](https://github.com/LakshanChinthaka/backend-royal-lms-system/assets/115285758/48670d4d-108d-44c8-b3c2-2c946272593f)

###  Admin Dashboard
![image](https://github.com/LakshanChinthaka/backend-royal-lms-system/assets/115285758/a6ff2a6c-0de4-4ea9-8db3-5d2b49f08e8f)

### Pagination
![image](https://github.com/LakshanChinthaka/backend-royal-lms-system/assets/115285758/3ced5327-19fc-4cdb-aac1-7fc8db1488bf)

### Create batch Method
![image](https://github.com/LakshanChinthaka/backend-royal-lms-system/assets/115285758/6c81f7b2-61c9-466e-aa29-8499983c0f1a)

### A case where spring boot actuator was used
![image](https://github.com/LakshanChinthaka/backend-royal-lms-system/assets/115285758/dda62664-e9e0-48e7-9567-fa84c10ff0e8)

### Custom Exception handling 
![ex](https://github.com/LakshanChinthaka/point-of-sale-system/assets/115285758/042ee44c-8732-4599-ab1b-24650a0c0fa7)

