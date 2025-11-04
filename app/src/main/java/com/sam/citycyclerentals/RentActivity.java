package com.sam.citycyclerentals;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

public class RentActivity extends AppCompatActivity {

    private ImageView bikeImage;
    private TextView bikeName, bikePrice, totalPrice;
    private EditText fullName, phoneNumber, email, cardNumber, expiryDate, cvv;
    private RadioGroup paymentMethods;
    private RadioButton cashOption, onlinePaymentOption;
    private Button confirmRentButton, cancelButton, payButton;
    private LinearLayout paymentDetailsLayout;

    private double bikePriceValue, totalRentalPrice;
    private String selectedFromDate, selectedToDate;
    private int selectedBikeId, rentalDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        bikeImage = findViewById(R.id.bikeImage);
        bikeName = findViewById(R.id.bikeName);
        bikePrice = findViewById(R.id.bikePrice);
        totalPrice = findViewById(R.id.totalPrice);
        fullName = findViewById(R.id.fullName);
        phoneNumber = findViewById(R.id.phoneNumber);
        email = findViewById(R.id.email);
        paymentMethods = findViewById(R.id.paymentMethods);
        cashOption = findViewById(R.id.cashOption);
        onlinePaymentOption = findViewById(R.id.onlinePaymentOption);
        confirmRentButton = findViewById(R.id.confirmRentButton);
        cancelButton = findViewById(R.id.cancelButton);
        payButton = findViewById(R.id.payButton);
        paymentDetailsLayout = findViewById(R.id.paymentDetailsLayout);

        cardNumber = findViewById(R.id.cardNumber);
        expiryDate = findViewById(R.id.expiryDate);
        cvv = findViewById(R.id.cvv);

        Intent intent = getIntent();
        selectedBikeId = intent.getIntExtra("bike_id", -1);
        selectedFromDate = intent.getStringExtra("start_date");
        selectedToDate = intent.getStringExtra("end_date");
        rentalDuration = intent.getIntExtra("rental_duration", 1);
        String bikeNameString = intent.getStringExtra("bike_name");
        bikePriceValue = intent.getDoubleExtra("bike_price", 0);
        int bikeImageResource = intent.getIntExtra("bike_image", 0);

        Log.d("DEBUG_RENT", "Bike ID: " + selectedBikeId);
        Log.d("DEBUG_RENT", "Start Date: " + selectedFromDate);
        Log.d("DEBUG_RENT", "End Date: " + selectedToDate);
        Log.d("DEBUG_RENT", "Rental Duration: " + rentalDuration);

        bikeName.setText(bikeNameString);
        bikePrice.setText("Price: $" + bikePriceValue);
        bikeImage.setImageResource(bikeImageResource);

        totalRentalPrice = rentalDuration * bikePriceValue;
        totalPrice.setText("Total: $" + totalRentalPrice);

        paymentMethods.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.onlinePaymentOption) {
                paymentDetailsLayout.setVisibility(View.VISIBLE);
                payButton.setVisibility(View.VISIBLE);
            } else {
                paymentDetailsLayout.setVisibility(View.GONE);
                payButton.setVisibility(View.GONE);
            }
        });

        confirmRentButton.setOnClickListener(v -> confirmRental());

        cancelButton.setOnClickListener(v -> finish());

        payButton.setOnClickListener(v -> {
            if (validatePaymentDetails()) {
                Toast.makeText(RentActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                confirmRental();
            } else {
                Toast.makeText(RentActivity.this, "Please fill all payment details correctly!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void confirmRental() {
        if (!validateInputs()) {
            Toast.makeText(this, "Please fill all fields correctly!", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        String userEmail = email.getText().toString().trim();
        int userId = dbHelper.getUserIdByEmail(userEmail);

        if (userId == -1) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        String paymentMethod = cashOption.isChecked() ? "Cash" : "Online";
        Log.d("DEBUG_RENT", "User ID: " + userId);
        Log.d("DEBUG_RENT", "Bike ID: " + selectedBikeId);
        Log.d("DEBUG_RENT", "Start Date: " + selectedFromDate);
        Log.d("DEBUG_RENT", "End Date: " + selectedToDate);
        Log.d("DEBUG_RENT", "Total Price: $" + totalRentalPrice);

        if (selectedBikeId == -1 || selectedFromDate == null || selectedToDate == null) {
            Toast.makeText(this, "Error retrieving rental details!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = dbHelper.insertRental(userId, selectedBikeId, selectedFromDate, selectedToDate, "Ongoing", paymentMethod, totalRentalPrice);

        if (success) {
            Toast.makeText(this, "Rental Confirmed!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RentActivity.this, RentalDetailActivity.class);
            intent.putExtra("user_email", userEmail);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Failed to confirm rental.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs() {
        String name = fullName.getText().toString().trim();
        String phone = phoneNumber.getText().toString().trim();
        String emailInput = email.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || emailInput.isEmpty()) {
            return false;
        }
        if (!isValidEmail(emailInput)) {
            email.setError("Please enter a valid email address");
            return false;
        }
        if (!isValidPhone(phone)) {
            phoneNumber.setError("Please enter a valid phone number");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}");
    }

    private boolean isValidPhone(String phone) {
        return phone.length() >= 10;
    }

    private boolean validatePaymentDetails() {
        String cardNumberInput = cardNumber.getText().toString().trim();
        String expiryDateInput = expiryDate.getText().toString().trim();
        String cvvInput = cvv.getText().toString().trim();
        return !cardNumberInput.isEmpty() && !expiryDateInput.isEmpty() && !cvvInput.isEmpty();
    }
}
