package com.example.expense_tracker;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // 🔍 Filter by category
    List<Expense> findByCategory(String category);

    // 📅 Filter by date
    List<Expense> findByDate(LocalDate date);

    // 📊 Filter by date range
    List<Expense> findByDateBetween(LocalDate start, LocalDate end);
}