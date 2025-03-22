package com.example.spring_bank;

import com.example.spring_bank.Savings;
import com.example.spring_bank.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service class contains the core business logic of the app.
// It acts as the middle layer between the Controller (handling HTTP) and Repository (handling database).
@Service
public class SavingsService {

    // This is the repository that talks to the database
    private final SavingsRepository repository;  // Repository for database operations

    // constructor uses dependency injection to automatically pass in the repository instance
    @Autowired
    public SavingsService(SavingsRepository repository) {
        this.repository = repository;
    }

    // Method to get all savings accounts from the database
    public List<Savings> getAllAccounts() {
        return repository.findAll();  // Built-in JPA method that returns all rows
    }

    // Get a single account by its ID
    public Optional<Savings> getAccountById(Integer id) {
        return repository.findById(id);  // JPA provides this method automatically
    }

    // Get an account by its email address
    public Optional<Savings> getAccountByEmail(String email) {
        return repository.findByEmail(email);  // Custom method defined in our repository
    }

    // Create a new account and save it to the database
    public Savings createAccount(Savings savings) {
        return repository.save(savings);
    }

    // Update an existing account by its ID
    public Optional<Savings> updateAccount(Integer id, Savings savings) {
        return repository.findById(id) // First check if the account exists
                .map(existingAccount -> {
                    // If it does, update the fields
                    existingAccount.setFirstName(savings.getFirstName());
                    existingAccount.setLastName(savings.getLastName());
                    existingAccount.setEmail(savings.getEmail());
                    existingAccount.setSavingsAmount(savings.getSavingsAmount());
                    // Save the updated account
                    return repository.save(existingAccount);
                });
    }

    // Delete an account by its ID
    public void deleteAccount(Integer id) {
        repository.deleteById(id);  // Deletes the row with the given ID
    }

    // Check if an account exists in the database by ID
    public boolean accountExists(Integer id) {
        return repository.existsById(id);  // Returns true if account exists, false otherwise
    }
}