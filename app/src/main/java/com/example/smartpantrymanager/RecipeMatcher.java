package com.example.smartpantrymanager;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeMatcher {

    public static class Recipe {
        public int id;
        public String name;
        public String ingredients;
        public String instructions;

        public Recipe(int id, String name, String ingredients, String instructions) {
            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.instructions = instructions;
        }
    }

    public static List<Recipe> getStrictMatches(DatabaseHelper db) {
        List<Recipe> matches = new ArrayList<>();
        Map<String, Double> pantryMap = new HashMap<>();

        Cursor pCursor = db.getAllPantryItems();
        if (pCursor != null && pCursor.moveToFirst()) {
            do {
                String name = pCursor.getString(pCursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_NAME)).toLowerCase().trim();
                double qty = pCursor.getDouble(pCursor.getColumnIndexOrThrow(DatabaseHelper.COL_PANTRY_QTY));

                if (name.endsWith("s") && !name.endsWith("ss")) {
                    name = name.substring(0, name.length() - 1);
                }
                pantryMap.put(name, pantryMap.getOrDefault(name, 0.0) + qty);
            } while (pCursor.moveToNext());
            pCursor.close();
        }

        Cursor rCursor = db.getAllRecipes();
        if (rCursor != null && rCursor.moveToFirst()) {
            do {
                int id = rCursor.getInt(rCursor.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE_ID));
                String name = rCursor.getString(rCursor.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE_NAME));
                String ingredientsStr = rCursor.getString(rCursor.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE_INGREDIENTS));
                String instructions = rCursor.getString(rCursor.getColumnIndexOrThrow(DatabaseHelper.COL_RECIPE_INSTRUCTIONS));

                String[] reqArray = ingredientsStr.split(";");
                boolean fullyMatches = true;

                for (String req : reqArray) {
                    String[] parts = req.split(":");
                    if (parts.length < 2) continue;

                    String reqName = parts[0].toLowerCase().trim();
                    if (reqName.endsWith("s") && !reqName.endsWith("ss")) {
                        reqName = reqName.substring(0, reqName.length() - 1);
                    }
                    double reqQty = Double.parseDouble(parts[1]);

                    if (!pantryMap.containsKey(reqName) || pantryMap.get(reqName) < reqQty) {
                        fullyMatches = false;
                        break;
                    }
                }

                if (fullyMatches) {
                    matches.add(new Recipe(id, name, ingredientsStr, instructions));
                }
            } while (rCursor.moveToNext());
            rCursor.close();
        }

        return matches;
    }
}