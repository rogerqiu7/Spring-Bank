package com.example.spring_bank;

import com.example.spring_bank.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository interface — it handles communication with the database.
// It extends JpaRepository, which gives us basic CRUD methods like save(), findById(), findAll(), deleteById(), etc.
// Spring Data JPA automatically generates the implementation behind the scenes — you don’t need to write SQL.
@Repository
public interface SavingsRepository extends JpaRepository<Savings, Integer> {

    // This method will automatically generate a query like:
    // SELECT * FROM savings WHERE email = ?
    // Just by naming the method "findByEmail", Spring knows what to do.
    Optional<Savings> findByEmail(String email);
}