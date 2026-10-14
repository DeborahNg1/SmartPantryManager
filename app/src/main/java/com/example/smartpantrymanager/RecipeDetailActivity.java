package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvInstructions = findViewById(R.id.tvDetailInstructions);

        String name = getIntent().getStringExtra("title");
        if (name == null) {
            name = getIntent().getStringExtra("NAME");
        }

        String rawIngredients = getIntent().getStringExtra("ingredients");
        if (rawIngredients == null) {
            rawIngredients = getIntent().getStringExtra("INGREDIENTS");
        }

        String instructions = getIntent().getStringExtra("instructions");
        if (instructions == null) {
            instructions = getIntent().getStringExtra("INSTRUCTIONS");
        }

        if (tvTitle != null && name != null) {
            tvTitle.setText(name);
        }

        if (tvInstructions != null && instructions != null) {
            tvInstructions.setText(instructions);
        } else if (tvInstructions != null) {
            tvInstructions.setText("Follow standard preparation steps for this recipe.");
        }

        if (tvIngredients != null && rawIngredients != null) {
            tvIngredients.setText(rawIngredients);
        }
    }
}