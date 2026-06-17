package com.hackathon.expense.controller;

import com.hackathon.expense.dto.AiAnalysisResult;
import com.hackathon.expense.dto.ExpenseRequest;
import com.hackathon.expense.entity.Expense;
import com.hackathon.expense.repository.ExpenseRepository;
import com.hackathon.expense.service.GeminiExpenseAiService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final GeminiExpenseAiService geminiExpenseAiService;

    public ExpenseController(
            ExpenseRepository expenseRepository,
            GeminiExpenseAiService geminiExpenseAiService) {
        this.expenseRepository = expenseRepository;
        this.geminiExpenseAiService = geminiExpenseAiService;
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Expense createExpense(@RequestBody ExpenseRequest request) {
        AiAnalysisResult analysis = geminiExpenseAiService.analyzeExpense(
                request.getTitle(),
                request.getAmount());

        Expense expense = new Expense();
        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(analysis.getCategory());
        expense.setAiAdvice(analysis.getAiAdvice());

        return expenseRepository.save(expense);
    }
}
