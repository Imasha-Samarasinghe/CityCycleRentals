package com.sam.citycyclerentals;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private TextView textGreeting;
    private ImageView city1, city2, city3, city4, city5, city6;
    private String loggedInUserEmail;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textGreeting = findViewById(R.id.textGreeting);
        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        city3 = findViewById(R.id.city3);
        city4 = findViewById(R.id.city4);
        city5 = findViewById(R.id.city5);
        city6 = findViewById(R.id.city6);

        databaseHelper = new DatabaseHelper(this);
        loggedInUserEmail = getIntent().getStringExtra("USER_EMAIL");

        if (loggedInUserEmail != null) {
            String username = databaseHelper.getUsername(loggedInUserEmail);
            textGreeting.setText("Hi, " + username + "!");
        } else {
            textGreeting.setText("Hi, User!");
        }

        setCityClickListener(city1, "Colombo", R.drawable.city1);
        setCityClickListener(city2, "Kandy", R.drawable.city2);
        setCityClickListener(city3, "Galle", R.drawable.city3);
        setCityClickListener(city4, "Ella", R.drawable.city4);
        setCityClickListener(city5, "Sigiriya", R.drawable.city5);
        setCityClickListener(city6, "NuwaraEliya", R.drawable.city6);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    return true;
                } else if (id == R.id.nav_my_rides) {
                    startActivity(new Intent(HomeActivity.this, MyRidesActivity.class));
                    return true;
                } else if (id == R.id.nav_rewards) {
                    startActivity(new Intent(HomeActivity.this, RewardsActivity.class));
                    return true;
                } else if (id == R.id.nav_rent) {
                    startActivity(new Intent(HomeActivity.this, RentalDetailActivity.class));
                    return true;
                } else if (id == R.id.nav_profile) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    intent.putExtra("email", loggedInUserEmail);
                    startActivity(intent);

                }
                return false;
            }
        });
    }

    private void setCityClickListener(ImageView cityImage, String cityName, int cityDrawable) {
        cityImage.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RentalSelectionActivity.class);
            intent.putExtra("CITY_NAME", cityName);
            intent.putExtra("CITY_IMAGE", cityDrawable);
            startActivity(intent);
        });
    }
}
