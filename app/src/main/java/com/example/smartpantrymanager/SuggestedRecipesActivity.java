package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvEmpty;
    private DatabaseHelper dbHelper;
    private List<Recipe> matchedRecipes;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerView = findViewById(R.id.recyclerViewSuggestions);
        tvEmpty = findViewById(R.id.tvEmptySuggestions);
        dbHelper = new DatabaseHelper(this);
        matchedRecipes = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        BottomNavigationView nav = findViewById(R.id.bottomNavigation);
        if (nav != null) {
            nav.setSelectedItemId(R.id.nav_suggestions);
            nav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_pantry) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_suggestions) {
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    finish();
                    return true;
                }
                return false;
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMatchedRecipes();
    }

    private void loadMatchedRecipes() {
        List<String> userPantry = dbHelper.getPantryItemNames();
        List<Recipe> allRecipes = getSampleRecipes();

        matchedRecipes.clear();
        for (Recipe recipe : allRecipes) {
            boolean hasAllIngredients = true;

            for (String requiredIng : recipe.getRequiredIngredients()) {
                String reqLower = requiredIng.toLowerCase().trim();
                boolean found = false;

                for (String pantryItem : userPantry) {
                    if (pantryItem.contains(reqLower) || reqLower.contains(pantryItem)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    hasAllIngredients = false;
                    break; // Missing at least one ingredient, fail strict match
                }
            }

            if (hasAllIngredients && !recipe.getRequiredIngredients().isEmpty()) {
                matchedRecipes.add(recipe);
            }
        }

        if (matchedRecipes.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
            tvEmpty.setText("No recipes match your pantry yet - add more ingredients!");
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recipeAdapter = new RecipeAdapter(this, matchedRecipes);
            recyclerView.setAdapter(recipeAdapter);
        }
    }

    private List<Recipe> getSampleRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe(
                "Boiled Egg",
                Arrays.asList("egg"),
                "1. Place eggs in a saucepan and cover with cold water by 1 inch.\n2. Bring water to a rapid boil over high heat.\n3. Once boiling, remove from heat and cover pan.\n4. Let sit for 9-12 minutes depending on desired firmness.\n5. Transfer eggs to an ice bath for 5 minutes before peeling."
        ));

        recipes.add(new Recipe(
                "Creamy Chicken Pasta",
                Arrays.asList("chicken", "pasta", "cream"),
                "1. Boil pasta in salted water until al dente, then drain.\n2. Season chicken breast and cook in a skillet with olive oil until golden brown and fully cooked.\n3. Pour heavy cream into the skillet and let simmer until slightly thickened.\n4. Toss cooked pasta into the creamy sauce, combine well, and serve hot."
        ));

        recipes.add(new Recipe(
                "Potato Salad",
                Arrays.asList("potatoes", "mayo", "eggs"),
                "1. Boil diced potatoes until fork-tender, then drain and let cool.\n2. Hard-boil the eggs, peel, and chop them into pieces.\n3. Combine potatoes, eggs, and mayonnaise in a large bowl.\n4. Season with salt and pepper to taste, mix gently, and chill before serving."
        ));

        recipes.add(new Recipe(
                "Beef Burger",
                Arrays.asList("beef patty", "burger bun", "cheese"),
                "1. Grill or pan-fry the beef patty over medium-high heat until cooked through.\n2. Place a slice of cheese on top of the patty during the last minute of cooking to melt.\n3. Toast the burger bun halves lightly.\n4. Assemble the burger with your patty, cheese, and favorite toppings."
        ));

        recipes.add(new Recipe(
                "Scrambled Eggs",
                Arrays.asList("eggs", "butter", "milk"),
                "1. Crack eggs into a bowl, add a splash of milk, and whisk until blended.\n2. Melt butter in a non-stick skillet over medium-low heat.\n3. Pour in the egg mixture and let it set slightly.\n4. Gently stir with a spatula continuously until soft curds form, then remove from heat."
        ));

        recipes.add(new Recipe(
                "Tomato Soup",
                Arrays.asList("tomatoes", "garlic", "onion"),
                "1. Chop tomatoes, onions, and garlic.\n2. Sauté onions and garlic in a pot with olive oil until fragrant and translucent.\n3. Add chopped tomatoes and simmer until soft.\n4. Blend the mixture until smooth, season with salt and pepper, and serve warm."
        ));

        recipes.add(new Recipe(
                "Vegetable Stir Fry",
                Arrays.asList("rice", "mixed vegetables", "soy sauce"),
                "1. Cook rice according to package instructions.\n2. Heat oil in a wok or large skillet over high heat.\n3. Stir-fry mixed vegetables until tender-crisp.\n4. Drizzle soy sauce over the vegetables, toss to coat, and serve over hot cooked rice."
        ));

        recipes.add(new Recipe(
                "Tuna Sandwich",
                Arrays.asList("tuna", "bread", "mayo"),
                "1. Drain canned tuna and place it in a mixing bowl.\n2. Mix in mayonnaise, salt, and pepper until well combined.\n3. Spread the tuna mixture evenly onto slices of bread.\n4. Top with the second slice of bread, cut in half, and enjoy."
        ));

        recipes.add(new Recipe(
                "Pancakes",
                Arrays.asList("flour", "milk", "eggs", "sugar"),
                "1. Whisk flour, sugar, milk, and eggs together in a bowl until smooth.\n2. Heat a lightly oiled griddle or frying pan over medium heat.\n3. Pour batter onto the griddle, using about 1/4 cup for each pancake.\n4. Cook until bubbles form on top, flip, and cook until golden brown."
        ));

        recipes.add(new Recipe(
                "Omelette",
                Arrays.asList("eggs", "cheese", "onion", "tomato"),
                "1. Whisk eggs in a bowl with a pinch of salt.\n2. Sauté diced onions and tomatoes in a buttered skillet.\n3. Pour beaten eggs over the vegetables and let cook until edges start to set.\n4. Sprinkle cheese on one half, fold the omelette over, and cook until cheese melts."
        ));

        recipes.add(new Recipe(
                "Grilled Cheese",
                Arrays.asList("bread", "cheese", "butter"),
                "1. Butter one side of each slice of bread.\n2. Place cheese between the unbuttered sides.\n3. Cook in a skillet over medium heat until golden brown on both sides and cheese is melted."
        ));

        recipes.add(new Recipe(
                "Garlic Butter Steak",
                Arrays.asList("steak", "garlic", "butter"),
                "1. Season steak with salt and pepper.\n2. Sear in a hot skillet with oil until desired doneness.\n3. Add butter and minced garlic during the last minute, spooning the melted garlic butter over the steak before resting."
        ));

        return recipes;
    }
}