# SmartPantryManager 🍳🥫

## Overview
SmartPantryManager is an offline Android application developed in **Java** for Android Studio[cite: 8, 9]. The application is designed to help users reduce food waste by tracking ingredients they currently have at home (their "pantry") and strictly suggesting recipes they can prepare using *only* those available items—eliminating unnecessary grocery trips[cite: 8, 9].

## Key Features
- **Pantry Management (CRUD):** Add, edit, and delete pantry items with custom quantities, units (grams, pcs, cups, tbsp, etc.), and expiry dates[cite: 8, 9].
- **Dynamic List Views:** Uses a `RecyclerView` with a custom adapter to display real-time database records[cite: 9, 10].
- **Strict-Matching Algorithm:** Automatically evaluates pantry contents against database recipes, ensuring only recipes with 100% available ingredients are suggested[cite: 9].
- **Detailed Recipe Views:** Tap any suggested recipe to view step-by-step cooking instructions and ingredient breakdowns.
- **Settings & Preferences:** Custom options persisted locally using `SharedPreferences`.
- **Bottom Navigation:** Seamless persistent navigation between Pantry, Recipe Suggestions, and Settings.

## Database Architecture Choice: SQLite
- **Choice:** **SQLite via `SQLiteOpenHelper`**[cite: 10]
- **Justification:** SQLite was chosen because it runs locally on-device without requiring internet connectivity or cloud backend configurations, perfectly aligning with an offline-first mobile assistant. It natively supports full CRUD operations, structured relational queries (`Cursor`), and guarantees that user data remains persistently stored between application sessions.

## Project Structure & Requirements Met
- **Language:** Written 100% in Java[cite: 9].
- **Activities:** Contains 5 distinct Activities (`MainActivity`, `AddEditActivity`, `SuggestedRecipesActivity`, `RecipeDetailActivity`, and `SettingsActivity`)[cite: 9, 10].
- **Version Control:** Developed iteratively under Git version control with incremental commit histories[cite: 11].

## Setup and Run Instructions
1. Clone the repository or extract the project source code ZIP[cite: 11, 13].
2. Open **Android Studio** (Koala or newer recommended).
3. Select **File > Open** and choose the `SmartPantryManager` project directory.
4. Allow Gradle to sync dependencies.
5. Connect your physical Android device (with USB Debugging enabled) or start an Android Emulator[cite: 11, 12].
6. Run the build command via terminal or click the **Run 'app'** play button in Android Studio:
   ```powershell
   .\gradlew assembleDebug