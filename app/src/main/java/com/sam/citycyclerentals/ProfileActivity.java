package com.sam.citycyclerentals;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextFullName, editTextPhone, editTextPaymentDetails, editTextEmail;
    private Button buttonSaveChanges, buttonAboutUs, buttonFeedback, buttonLogout;
    private DatabaseHelper databaseHelper;
    private String loggedInUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        loggedInUserEmail = getIntent().getStringExtra("email");

        if (loggedInUserEmail == null || loggedInUserEmail.isEmpty()) {
            Toast.makeText(this, "Error: User email not found!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        databaseHelper = new DatabaseHelper(this);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPaymentDetails = findViewById(R.id.editTextPaymentDetails);
        editTextEmail = findViewById(R.id.editTextEmail);

        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        buttonAboutUs = findViewById(R.id.buttonAboutUs);
        buttonFeedback = findViewById(R.id.buttonFeedback);
        buttonLogout = findViewById(R.id.buttonLogout);

        loggedInUserEmail = getIntent().getStringExtra("email");

        loadUserData();

        buttonSaveChanges.setOnClickListener(v -> {
            String fullName = editTextFullName.getText().toString();
            String phone = editTextPhone.getText().toString();
            String payment = editTextPaymentDetails.getText().toString();

            if (databaseHelper.updateUserDetails(loggedInUserEmail, fullName, phone, payment)) {
                Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });

        buttonAboutUs.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, AboutUsActivity.class)));
        buttonFeedback.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, FeedbackActivity.class)));
        buttonLogout.setOnClickListener(v -> {
            Toast.makeText(ProfileActivity.this, "Logged Out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void loadUserData() {
        Cursor cursor = databaseHelper.getUserDetails(loggedInUserEmail);
        if (cursor.moveToFirst()) {
            editTextFullName.setText(cursor.getString(1));
            editTextEmail.setText(cursor.getString(2));
            editTextPhone.setText(cursor.getString(3));
            editTextPaymentDetails.setText(cursor.getString(4));
            editTextEmail.setEnabled(false);
        }
    }
}
