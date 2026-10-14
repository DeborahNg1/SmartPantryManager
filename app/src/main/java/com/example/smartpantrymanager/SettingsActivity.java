package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREF_NAME = "PantryPrefs";
    private static final String KEY_NOTIFICATIONS = "notifications";
    private static final String KEY_ALERTS = "alerts";
    private static final String KEY_DARK_MODE = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchCompat switchNotifications = findViewById(R.id.switchNotifications);
        SwitchCompat switchAlerts = findViewById(R.id.switchAlerts);
        SwitchCompat switchDarkMode = findViewById(R.id.switchDarkMode);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        switchNotifications.setChecked(prefs.getBoolean(KEY_NOTIFICATIONS, true));
        switchAlerts.setChecked(prefs.getBoolean(KEY_ALERTS, true));
        switchDarkMode.setChecked(prefs.getBoolean(KEY_DARK_MODE, false));

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply();
            String msg = isChecked ? "Pantry reminders enabled" : "Reminders disabled";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        switchAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_ALERTS, isChecked).apply();
            String msg = isChecked ? "Expiring-soon alerts enabled" : "Alerts disabled";
            Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply();
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        BottomNavigationView nav = findViewById(R.id.bottomNavigation);
        if (nav != null) {
            nav.setSelectedItemId(R.id.nav_settings);
            nav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_pantry) {
                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_suggestions) {
                    startActivity(new Intent(SettingsActivity.this, SuggestedRecipesActivity.class));
                    finish();
                    return true;
                } else if (itemId == R.id.nav_settings) {
                    return true;
                }
                return false;
            });
        }
    }
}