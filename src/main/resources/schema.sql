-- Spring will execute the SQL statements in schema.sql against your configured database before the app starts serving requests.

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS spring_bank;

-- Use the database
USE spring_bank;

-- Define the savings table with simplified fields
CREATE TABLE IF NOT EXISTS savings (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for each account, auto-incremented
    first_name VARCHAR(100) NOT NULL,   -- First name of the account holder
    last_name VARCHAR(100) NOT NULL,    -- Last name of the account holder
    email VARCHAR(100) NOT NULL UNIQUE, -- Email of the account holder (unique constraint)
    savings_amount DECIMAL(19, 2) DEFAULT 0.00  -- Current savings amount with 2 decimal places
    );