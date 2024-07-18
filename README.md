# Document Management System

## Overview

This project is a Document Management System built using Core Java, MySQL, and JDBC. The application provides a menu-based console interface for managing documents, clients, and cases in a law firm.

## Features

- **Document Management**
  - Upload new documents
  - View document details
  - Update document information
  - Delete documents

- **Client Management**
  - Add new clients
  - View client details
  - Update client information
  - Delete clients

- **Case Management**
  - Create new cases
  - View case details
  - Update case information
  - Close cases

## Prerequisites

- Java Development Kit (JDK) 8 or above (i have used version 22)
- MySQL database server
- MySQL Workbench or another MySQL client (optional, for database management)

## Setup Instructions

### Step 1: Clone the Repository

```sh
git clone https://github.com/Vishnu250206/Document-Management-System.git
cd Document-Management-System
```

### Step 2: Set Up the Database

1. Open MySQL Workbench or another MySQL client.
2. Create a new database and tables using the following SQL script:

```sql
CREATE DATABASE DocumentManagement;

USE DocumentManagement;

CREATE TABLE Document (
    document_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    description TEXT,
    file_path VARCHAR(255),
    upload_date DATE
);

CREATE TABLE Client (
    client_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    contact_number VARCHAR(15),
    email VARCHAR(255),
    address TEXT
);

CREATE TABLE CaseTable (
    case_id INT PRIMARY KEY AUTO_INCREMENT,
    client_id INT,
    case_number VARCHAR(255),
    description TEXT,
    status VARCHAR(50),
    open_date DATE,
    close_date DATE,
    FOREIGN KEY (client_id) REFERENCES Client(client_id)
);
```

### Step 3: Configure the Database Connection

1. Create a `config` directory in the root of the project.
2. Inside the `config` directory, create a file named `db.properties`.
3. Add the following content to `db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/DocumentManagement
db.user= (ID)
db.password= (PASSWORD)
```

Replace `ID` and `PASSWORD` with your actual MySQL id and password.

### Step 4: Compile and Run the Application

1. Open Command Prompt or Terminal.
2. Navigate to the project directory.
3. Compile the Java files:

```sh
javac -cp ".;lib/mysql-connector-java-8.0.26.jar" src/MainMenu.java src/DocumentManager.java src/ClientManager.java src/CaseManager.java src/db/DBConnection.java
```

4. Run the application:

```sh
java -cp ".;lib/mysql-connector-java-8.0.26.jar;src" MainMenu
```

## Usage

1. **Manage Documents**:
   - Choose option 1 from the main menu to manage documents.
   - Follow the prompts to upload, view, update, or delete documents.

2. **Manage Clients**:
   - Choose option 2 from the main menu to manage clients.
   - Follow the prompts to add, view, update, or delete clients.

3. **Manage Cases**:
   - Choose option 3 from the main menu to manage cases.
   - Follow the prompts to create, view, update, or close cases.

## Troubleshooting

- Ensure that the MySQL server is running and accessible.
- Verify that the `db.properties` file contains the correct database connection information.
- Check for any SQL exceptions or errors in the console output for further debugging.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.