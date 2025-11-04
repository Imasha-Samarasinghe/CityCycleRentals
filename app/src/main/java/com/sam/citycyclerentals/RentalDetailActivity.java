package com.sam.citycyclerentals;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.ArrayList;

public class RentalDetailActivity extends AppCompatActivity{

    private RecyclerView ongoingRentalsRecycler, pastRentalsRecycler;
    private RentalAdapter ongoingAdapter, pastAdapter;
    private DatabaseHelper dbHelper;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_detail);

        ongoingRentalsRecycler = findViewById(R.id.ongoingRentalsRecycler);
        pastRentalsRecycler = findViewById(R.id.pastRentalsRecycler);

        dbHelper = new DatabaseHelper(this);
        userEmail = getIntent().getStringExtra("user_email");

        if (userEmail == null || userEmail.isEmpty()) {
            Toast.makeText(this, "User email not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        refreshRentals();
    }

    private void refreshRentals() {
        int userId = dbHelper.getUserIdByEmail(userEmail);

        if (userId == -1) {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        List<Rental> ongoingRentals = dbHelper.getRentalsByStatus(userId, "Ongoing");
        List<Rental> pastRentals = dbHelper.getRentalsByStatus(userId, "Completed");

        ongoingAdapter = new RentalAdapter(this, ongoingRentals, this);
        pastAdapter = new RentalAdapter(this, pastRentals, this);

        ongoingRentalsRecycler.setLayoutManager(new LinearLayoutManager(this));
        pastRentalsRecycler.setLayoutManager(new LinearLayoutManager(this));

        ongoingRentalsRecycler.setAdapter(ongoingAdapter);
        pastRentalsRecycler.setAdapter(pastAdapter);
    }


}
