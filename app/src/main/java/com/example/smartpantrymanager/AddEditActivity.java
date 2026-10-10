package com.example.smartpantrymanager;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    private EditText etName, etQty, etUnit, etExpiry;
    private Button btnSave;
    private DatabaseHelper dbHelper;
    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        etName = findViewById(R.id.etName);
        etQty = findViewById(R.id.etQty);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        btnSave = findViewById(R.id.btnSave);
        dbHelper = new DatabaseHelper(this);

        if (getIntent().hasExtra("ID")) {
            itemId = getIntent().getIntExtra("ID", -1);
            etName.setText(getIntent().getStringExtra("NAME"));
            etQty.setText(String.valueOf(getIntent().getDoubleExtra("QTY", 0)));
            etUnit.setText(getIntent().getStringExtra("UNIT"));
            etExpiry.setText(getIntent().getStringExtra("EXPIRY"));
        }

        btnSave.setOnClickListener(v -> savePantryItem());
    }

    private void savePantryItem() {
        String name = etName.getText().toString().trim();
        String qtyStr = etQty.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Ingredient name is required");
            return;
        }
        if (TextUtils.isEmpty(qtyStr)) {
            etQty.setError("Quantity is required");
            return;
        }

        double qty;
        try {
            qty = Double.parseDouble(qtyStr);
            if (qty <= 0) {
                etQty.setError("Quantity must be greater than zero");
                return;
            }
        } catch (NumberFormatException e) {
            etQty.setError("Enter a valid number");
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