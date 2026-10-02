package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvTitle = findViewById(R.id.tvRecipeTitle);
        TextView tvIngredients = findViewById(R.id.tvIngredientsList);
        TextView tvInstructions = findViewById(R.id.tvInstructionsText);

        String name = getIntent().getStringExtra("NAME");
        String rawIngredients = getIntent().getStringExtra("INGREDIENTS");
        String instructions = getIntent().getStringExtra("INSTRUCTIONS");

        tvTitle.setText(name);
        tvInstructions.setText(instructions);

        StringBuilder formattedIngredients = new StringBuilder();
        if (rawIngredients != null) {
            String[] items = rawIngredients.split(";");
            for (String item : items) {
                String[] parts = item.split(":");
                if (parts.length >= 3) {
                    formattedIngredients.append("• ").append(parts[0]).append(" - ").append(parts[1]).append(" ").append(parts[2]).append("\n");
                }
            }
        }
        tvIngredients.setText(formattedIngredients.toString());
    }
}