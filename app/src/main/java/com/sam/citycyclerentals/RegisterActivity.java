package com.sam.citycyclerentals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone, editTextPassword;
    private Button buttonRegister;
    private TextView textViewLogin;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d("REGISTER_ACTIVITY", "RegisterActivity started!");

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);

        databaseHelper = new DatabaseHelper(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("REGISTER_BUTTON", "Register button clicked");
                registerUser();
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN_TEXTVIEW", "Login text clicked");
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show());
            return;
        }

        boolean success = databaseHelper.registerUser(name, email, phone, password);

        if (success) {
            runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show());
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            runOnUiThread(() -> Toast.makeText(RegisterActivity.this, "Email already exists! Try another one.", Toast.LENGTH_SHORT).show());
        }
    }
}
