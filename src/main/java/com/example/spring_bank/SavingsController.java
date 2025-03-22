package com.example.spring_bank;

import com.example.spring_bank.Savings;
import com.example.spring_bank.SavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller handles HTTP requests and exposes endpoints for CRUD operations on savings accounts
@RestController
@RequestMapping("/api/savings")  // Base URL path for all endpoints in this controller
public class SavingsController {

    // This connects the controller to the service layer, which handles the main business logic
    private final SavingsService service;

    // This constructor sets up the service using Spring's dependency injection
    @Autowired
    public SavingsController(SavingsService service) {
        this.service = service;
    }

    // home page welcome screen: http://localhost:8080/api/savings/
    @GetMapping("/")
    public String welcome() {
        return "Welcome to Spring Bank";
    }

    // test page
    @GetMapping("/test")
    public String test() {
        return "Controller is working";
    }

    // GET /api/savings
    // Returns a list of all savings accounts
    @GetMapping("/all")
    public ResponseEntity<List<Savings>> getAllAccounts() {
        return ResponseEntity.ok(service.getAllAccounts());
    }

    // GET /api/savings/{id}
    // Returns a savings account by its ID
    // If found → return 200 OK with the account
    // If not found → return 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<Savings> getAccountById(@PathVariable Integer id) {
        return service.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/savings/email/{email}
    // Returns a savings account by email address
    // Same success/failure pattern as above
    @GetMapping("/email/{email}")
    public ResponseEntity<Savings> getAccountByEmail(@PathVariable String email) {
        return service.getAccountByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/savings
    // Creates a new savings account with the data sent in the request body (JSON to photo class)
    // Returns 201 Created status with the new account
    @PostMapping
    public ResponseEntity<Savings> createAccount(@RequestBody Savings savings) {
        Savings created = service.createAccount(savings);
        return new ResponseEntity<>(created, HttpStatus.CREATED);  // Return 201 Created status
    }

    // PUT /api/savings/{id}
    // Updates an existing account by ID using data from the request body
    // If found and updated → return 200 OK with updated account
    // If not found → return 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<Savings> updateAccount(@PathVariable Integer id, @RequestBody Savings savings) {
        return service.updateAccount(id, savings)
                .map(ResponseEntity::ok)  // Return 200 OK with updated account
                .orElse(ResponseEntity.notFound().build());  // Return 404 if account doesn't exist
    }

    // DELETE /api/savings/{id}
    // Deletes the account by ID
    // If deleted → return 204 No Content
    // If not found → return 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Integer id) {
        if (service.accountExists(id)) {
            service.deleteAccount(id);
            return ResponseEntity.noContent().build();  // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // Return 404 Not Found
        }
    }
}