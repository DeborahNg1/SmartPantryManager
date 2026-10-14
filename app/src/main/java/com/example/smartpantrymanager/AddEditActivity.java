package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText etName, etQty, etExpiry;
    private Spinner spinnerUnit;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etName = findViewById(R.id.etName);
        etQty = findViewById(R.id.etQty);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        etExpiry = findViewById(R.id.etExpiry);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        String[] units = {"pcs", "g", "kg", "ml", "l", "tbsp", "tsp", "cup", "cups", "can", "slice"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnit.setAdapter(adapter);

        if (getIntent().hasExtra("ID")) {
            itemId = getIntent().getIntExtra("ID", -1);
            etName.setText(getIntent().getStringExtra("NAME"));
            etQty.setText(String.valueOf(getIntent().getDoubleExtra("QTY", 0)));
            etExpiry.setText(getIntent().getStringExtra("EXPIRY"));

            // Set spinner selection to match the saved unit
            String savedUnit = getIntent().getStringExtra("UNIT");
            if (savedUnit != null) {
                for (int i = 0; i < units.length; i++) {
                    if (units[i].equalsIgnoreCase(savedUnit.trim())) {
                        spinnerUnit.setSelection(i);
                        break;
                    }
                }
            }
        }

        btnSave.setOnClickListener(v -> savePantryItem());
    }

    private void savePantryItem() {
        String name = etName.getText().toString().trim();
        String qtyStr = etQty.getText().toString().trim();
        String unit = spinnerUnit.getSelectedItem() != null ? spinnerUnit.getSelectedItem().toString() : "";
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Ingredient name is required");
            etName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(qtyStr)) {
            etQty.setError("Quantity is required");
            etQty.requestFocus();
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
            if (qty <= 0) {
                etQty.setError("Quantity must be greater than zero");
                etQty.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            etQty.setError("Enter a valid number");
            etQty.requestFocus();
            return;
        }

        boolean success;
        if (itemId == -1) {
            success = dbHelper.addPantryItem(name, qty, unit, expiry);
        } else {
            success = dbHelper.updatePantryItem(itemId, name, qty, unit, expiry);
        }

        if (success) {
            Toast.makeText(this, "Item saved successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving item.", Toast.LENGTH_SHORT).show();
        }
    }
}