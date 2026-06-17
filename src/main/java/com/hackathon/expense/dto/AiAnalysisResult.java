package com.hackathon.expense.dto;

public class AiAnalysisResult {

    private final String category;
    private final String aiAdvice;

    public AiAnalysisResult(String category, String aiAdvice) {
        this.category = category;
        this.aiAdvice = aiAdvice;
    }

    public String getCategory() {
        return category;
    }

    public String getAiAdvice() {
        return aiAdvice;
    }
}
