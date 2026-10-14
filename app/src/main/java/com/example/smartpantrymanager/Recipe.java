package com.example.smartpantrymanager;

import java.util.List;

public class Recipe {
    private String title;
    private List<String> requiredIngredients;
    private String instructions;

    public Recipe(String title, List<String> requiredIngredients) {
        this.title = title;
        this.requiredIngredients = requiredIngredients;
        this.instructions = "1. Prepare all required ingredients.\n2. Combine and cook according to standard recipe directions.\n3. Serve warm and enjoy!";
    }

    public Recipe(String title, List<String> requiredIngredients, String instructions) {
        this.title = title;
        this.requiredIngredients = requiredIngredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getRequiredIngredients() {
        return requiredIngredients;
    }

    public String getInstructions() {
        return instructions;
    }
}