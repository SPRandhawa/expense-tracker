package com.example.expense_tracker;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
public class HomeController {

    private final ExpenseRepository repo;

    public HomeController(ExpenseRepository repo) {
        this.repo = repo;
    }

    // 🏠 Dashboard
    @GetMapping("/")
    public String home(Model model) {
        List<Expense> expenses = repo.findAll();

        BigDecimal total = expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("expenses", expenses);
        model.addAttribute("total", total);

        return "index";
    }

    // ➕ Add Expense
    @PostMapping("/add")
    public String addExpense(@RequestParam String title,
                             @RequestParam BigDecimal amount,
                             @RequestParam String category,
                             @RequestParam LocalDate date) {

        Expense e = new Expense();
        e.setTitle(title);
        e.setAmount(amount);
        e.setCategory(category);
        e.setDate(date);

        repo.save(e);

        return "redirect:/";
    }

    // ❌ Delete
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }

    // ✏️ Edit page
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Expense e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        model.addAttribute("expense", e);
        return "edit";
    }

    // 🔄 Update
    @PostMapping("/update/{id}")
    public String updateExpense(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam BigDecimal amount,
                                @RequestParam String category,
                                @RequestParam LocalDate date) {

        Expense e = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        e.setTitle(title);
        e.setAmount(amount);
        e.setCategory(category);
        e.setDate(date);

        repo.save(e);

        return "redirect:/";
    }
}