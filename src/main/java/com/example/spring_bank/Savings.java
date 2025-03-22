package com.example.spring_bank;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

// This class represents the structure of a "savings account" record in the database.
// JPA (Java Persistence API) uses the annotations below to map this class to a database table.
@Entity
@Table(name = "savings") // This tells JPA to map this class to a table named "savings"
public class Savings {

    // This is the primary key for the table (i.e., the unique ID for each row)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment the ID when new rows are added
    private Integer id;                // Unique identifier

    // Fields that map to columns in the table
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal savingsAmount;

    // Default constructor
    // JPA needs a no-arg constructor to create entity objects when loading data from the database.
    public Savings() {
        this.savingsAmount = BigDecimal.ZERO; // Default savings amount is 0
    }

    // Constructor called whenever creating a new account (without an ID)
    // Useful for creating new accounts without manually setting the ID (since it's auto-generated).
    public Savings(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.savingsAmount = BigDecimal.ZERO;
    }

    // Full constructor — used if you want to manually set all fields (including ID)
    // useful when you want to create a full Savings object for testing
    public Savings(Integer id, String firstName, String lastName, String email,
                   BigDecimal savingsAmount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.savingsAmount = savingsAmount;
    }

    // Getters and setters allow access and modification of the private fields
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(BigDecimal savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    // This makes it easy to print the contents of a Savings object in the console or logs
    @Override
    public String toString() {
        return "Savings{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", savingsAmount=" + savingsAmount +
                '}';
    }
}