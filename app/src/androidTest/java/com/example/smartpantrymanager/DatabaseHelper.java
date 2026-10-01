package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pantry_manager.db";
    private static final int DATABASE_VERSION = 1;

    // Pantry Table
    public static final String TABLE_PANTRY = "pantry";
    public static final String COL_PANTRY_ID = "id";
    public static final String COL_PANTRY_NAME = "name";
    public static final String COL_PANTRY_QTY = "quantity";
    public static final String COL_PANTRY_UNIT = "unit";
    public static final String COL_PANTRY_EXPIRY = "expiry_date";

    // Recipes Table
    public static final String TABLE_RECIPES = "recipes";
    public static final String COL_RECIPE_ID = "id";
    public static final String COL_RECIPE_NAME = "name";
    public static final String COL_RECIPE_INGREDIENTS = "ingredients"; // Format: name:qty:unit;
    public static final String COL_RECIPE_INSTRUCTIONS = "instructions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Pantry Table
        String createPantry = "CREATE TABLE " + TABLE_PANTRY + " (" +
                COL_PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PANTRY_NAME + " TEXT, " +
                COL_PANTRY_QTY + " REAL, " +
                COL_PANTRY_UNIT + " TEXT, " +
                COL_PANTRY_EXPIRY + " TEXT)";

        // Create Recipes Table
        String createRecipes = "CREATE TABLE " + TABLE_RECIPES + " (" +
                COL_RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_RECIPE_NAME + " TEXT, " +
                COL_RECIPE_INGREDIENTS + " TEXT, " +
                COL_RECIPE_INSTRUCTIONS + " TEXT)";

        db.execSQL(createPantry);
        db.execSQL(createRecipes);

        // Pre-load Recipes on First Run
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    // Seed Pre-loaded Recipes
    private void seedRecipes(SQLiteDatabase db) {
        String[][] recipes = {
                {"Scrambled Eggs", "egg:2:pcs;butter:1:tbsp", "Whisk eggs. Melt butter in pan and scramble over medium heat."},
                {"Rice and Beans", "rice:1:cup;beans:1:can", "Cook rice. Heat beans. Mix together and serve."},
                {"Pancakes", "flour:1:cup;milk:1:cup;egg:1:pcs;butter:2:tbsp", "Whisk batter. Pour onto hot greased pan and flip when bubbly."},
                {"Tomato Soup", "tomato:4:pcs;water:2:cups;butter:1:tbsp", "Boil tomatoes, blend, and simmer with butter."},
                {"Fruit Salad", "apple:1:pcs;banana:1:pcs;orange:1:pcs", "Chop all fruits and toss in a bowl."},
                {"Boiled Eggs", "egg:2:pcs;water:4:cups", "Boil eggs in water for 8 minutes."},
                {"French Toast", "bread:2:slices;egg:1:pcs;milk:0.25:cup", "Whisk egg and milk. Dip bread and fry until golden."},
                {"Oatmeal", "oats:1:cup;water:2:cups", "Boil oats in water for 5 minutes stirring constantly."},
                {"Creamy Chicken Pasta", "pasta:200:g;chicken:250:g;cream:1:cup;garlic:2:cloves", "Boil pasta. Sauté chicken and garlic, then stir in cream and pasta."},
                {"Potato Salad", "potato:3:pcs;mayo:3:tbsp;egg:2:pcs", "Boil potatoes and eggs. Dice, mix with mayo, and season."},
                {"Strawberry Smoothie", "strawberry:1:cup;milk:1:cup;yogurt:0.5:cup", "Blend strawberries, milk, and yogurt until smooth."},
                {"Beef Burger", "ground beef:200:g;bun:1:pcs;lettuce:1:leaf;tomato:1:slice", "Form beef into patty, grill, and assemble burger with toppings."},
                {"Ice Cream", "cream:2:cups;milk:1:cup;sugar:0.5:cup", "Whisk ingredients together, freeze, and churn until smooth."},
                {"Trifle", "cake:200:g;custard:1:cup;jelly:1:cup;fruit:1:cup", "Layer sponge cake, jelly, custard, and fruit in a glass container."},
                {"Lasagna", "pasta:200:g;ground beef:300:g;cheese:100:g;tomato sauce:1:can", "Layer pasta sheets, cooked beef sauce, and cheese, then bake until golden."}
        };

        for (String[] r : recipes) {
            ContentValues cv = new ContentValues();
            cv.put(COL_RECIPE_NAME, r[0]);
            cv.put(COL_RECIPE_INGREDIENTS, r[1]);
            cv.put(COL_RECIPE_INSTRUCTIONS, r[2]);
            db.insert(TABLE_RECIPES, null, cv);
        }
    }

    // CRUD Helper Methods
    public boolean addPantryItem(String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PANTRY_NAME, name.trim().toLowerCase());
        cv.put(COL_PANTRY_QTY, qty);
        cv.put(COL_PANTRY_UNIT, unit.trim().toLowerCase());
        cv.put(COL_PANTRY_EXPIRY, expiry);
        return db.insert(TABLE_PANTRY, null, cv) != -1;
    }

    public boolean updatePantryItem(int id, String name, double qty, String unit, String expiry) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PANTRY_NAME, name.trim().toLowerCase());
        cv.put(COL_PANTRY_QTY, qty);
        cv.put(COL_PANTRY_UNIT, unit.trim().toLowerCase());
        cv.put(COL_PANTRY_EXPIRY, expiry);
        return db.update(TABLE_PANTRY, cv, COL_PANTRY_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PANTRY, COL_PANTRY_ID + "=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Cursor getAllPantryItems() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_PANTRY, null);
    }

    public Cursor getAllRecipes() {
        return this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_RECIPES, null);
    }
}