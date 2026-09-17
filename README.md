# Employee Management System
A simple command-line based **Employee Management System** developed using **Java**

The project is designed to manage employee records, departments, and basic salary information through a simple console interface.
---
## Project Overview
The Employee Management System provides a centralized way to store, search, update, and delete employee records.
The system contains three main functional modules:
1. Employee Management
2. Department Management
3. Salary Management
Data is stored permanently in an SQLite database and accessed through JDBC.
---
## Features
### 1. Employee Management
* Add a new employee
* View all employees
* Search employee by ID
* Search employee by name
* Update employee details
* Delete employee
* View employees by department
### 2. Department Management
* Add a department
* View all departments
* View employees belonging to a department
### 3. Salary Management
* View salary report
* Calculate average salary
* Find highest salary
* Find lowest salary
* Calculate department-wise average salary
### 4. Validation and Error Handling
* Prevent empty required fields
* Prevent negative salary values
* Detect duplicate employee IDs
* Check whether a department exists
* Handle invalid user input
* Handle database errors
---
## Technologies Used
* **Programming Language:** Java 17
* **Database:** SQLite
* **Database Connectivity:** JDBC
* **Version Control:** Git and GitHub
---
## Project Structure
```text
EmployeeManagementSystem/
│
├── src/
│   ├── Employee.java
│   ├── Department.java
│   ├── Database.java
│   ├── EmployeeManager.java
│   ├── DepartmentManager.java
│   └── Main.java
│
├── sql/
│   └── schema.sql
│
├── data/
│   └── employees.db
│
├── README.md
├── statement.md
└── .gitignore
```
---
## Database Structure
The project uses two main tables.
### Department
```text
departments
├── dept_id
├── dept_name
└── location
```
### Employee
```text
employees
├── emp_id
├── name
├── salary
├── post
├── email
├── phone
├── joining_date
└── dept_id
```
The `dept_id` field connects employees with departments.
---
## Requirements
Before running the project, install:
* **JDK 17 or later**
* **SQLite JDBC Driver**
* **BlueJ** (or another Java IDE)
The SQLite JDBC driver is required to connect the Java application with the SQLite database.
---
## Running the Project in Java
1. Install **JDK 17 or later**.
2. Download the **SQLite JDBC driver `.jar`** file.
3. Place the SQLite JDBC driver in the project folder.
4. Open a terminal/command prompt in the project folder.
5. Compile all Java source files:
```bash
javac -cp "sqlite-jdbc.jar" -d out src/*.java
```
6. Run the `Main` class:
```bash
java -cp "out:sqlite-jdbc.jar" Main
```
7. The Employee Management System will start in the terminal.
The SQLite database will be created automatically inside the `data` folder when the application starts.
---
## Main Menu
The application provides the following menu:
```text
========================================
       EMPLOYEE MANAGEMENT SYSTEM
========================================
1. Employee Management
2. Department Management
3. Salary Management
4. Exit
```
---
## Testing
The application can be tested using manual validation test cases such as:
| Test Case                    | Expected Result               |
| ---------------------------- | ----------------------------- |
| Add valid employee           | Employee added successfully   |
| Add duplicate employee ID    | Duplicate ID rejected         |
| Enter negative salary        | Invalid salary rejected       |
| Search existing employee     | Employee details displayed    |
| Search non-existing employee | Employee not found            |
| Update employee              | Employee details updated      |
| Delete employee              | Employee removed              |
| Add department               | Department added successfully |
| View department employees    | Employees displayed           |
| Generate salary report       | Salary statistics displayed   |
---
## Future Enhancements
Possible future improvements include:
* Employee attendance management
* Leave management
* Login and user authentication
* Graphical user interface
* Payroll management
* PDF report generation
* Additional employee analytics
---