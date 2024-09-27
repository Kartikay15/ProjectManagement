# Project Management System

## Overview
This Project Management System is designed to manage projects, employees, and tasks efficiently. It allows managers to add, view, update, and delete projects, allocate employees to projects, and generate reports on project tasks. The application emphasizes SQL interactions, object-oriented principles, exception handling, and unit testing.

### Key Features
- **Project Management**: Add, view, update, and delete projects.
- **Employee Allocation**: Assign employees to various projects.
- **Task Management**: Add tasks with allocation and deadlines to projects.
- **Report Generation**: Generate reports for tasks and expenses over specific periods.
- **Database Connectivity**: Persistent storage of data in a MySQL relational database.

## Getting Started
Follow the instructions below to set up the project locally on your machine.

### Prerequisites
- **Java Development Kit (JDK)**: Ensure you have JDK 8 or higher installed.
- **MySQL Database**: Install MySQL and set up a database for the application.

### Setting Up the Database
1. **Create a Database**:
    ```sql
    CREATE DATABASE db;
    ```

2. **Create Tables**: Execute the following SQL commands to create the necessary tables:
    ```sql
    CREATE TABLE Employee (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        designation VARCHAR(255),
        gender ENUM('Male', 'Female', 'Other'),
        salary DECIMAL(10, 2),
        project_id INT,
        FOREIGN KEY (project_id) REFERENCES Project(id)
    );

    CREATE TABLE Project (
        id INT AUTO_INCREMENT PRIMARY KEY,
        projectName VARCHAR(255) NOT NULL,
        description TEXT,
        start_date DATE,
        status ENUM('started', 'development', 'build', 'test', 'deployed')
    );

    CREATE TABLE Task (
        task_id INT AUTO_INCREMENT PRIMARY KEY,
        task_name VARCHAR(255) NOT NULL,
        project_id INT,
        employee_id INT,
        status ENUM('Assigned', 'Started', 'Completed'),
        FOREIGN KEY (project_id) REFERENCES Project(id),
        FOREIGN KEY (employee_id) REFERENCES Employee(id)
    );
    ```

### Clone the Repository
Clone this repository to your local machine:
```bash
git clone https://github.com/kartikay15/ProjectManagement.git
cd ProjectManagement
```

### Add MySQL Connector
1. **Download MySQL Connector**: Download the MySQL Connector JAR file from the [MySQL website](https://dev.mysql.com/downloads/connector/j/).

2. **Add Connector to Project**: 
   - Place the downloaded JAR file into the `lib` directory of your project.
   - Update your `pom.xml` file (if using Maven) to include the MySQL connector as a dependency:
   ```xml
   <dependency>
       <groupId>mysql</groupId>
       <artifactId>mysql-connector-java</artifactId>
       <version>8.0.33</version> <!-- Use the latest version -->
   </dependency>
   ```
   - Otherwise just add .jar file as External Library to the project.
  
### Configure Database Connection
1. **Create a Properties File**: Create a file named `db.properties` in the `src/` directory with the following content:

    ```properties
    db.host=localhost
    db.port=3306
    db.name=db
    db.username=root
    db.password=root
    ```

2. **Adjust the values** according to your MySQL setup.

