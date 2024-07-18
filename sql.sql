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