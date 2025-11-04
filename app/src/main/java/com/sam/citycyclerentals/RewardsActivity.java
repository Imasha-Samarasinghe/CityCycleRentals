package com.sam.citycyclerentals;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RewardsActivity extends AppCompatActivity {

    private TextView reward1, reward2, reward3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        reward1 = findViewById(R.id.reward1);
        reward2 = findViewById(R.id.reward2);
        reward3 = findViewById(R.id.reward3);

        reward1.setText("🎉 10% OFF on Your Next Ride!");
        reward2.setText("🏆 Free Ride After 5 Bookings!");
        reward3.setText("⭐ Earn Points for Every Booking!");
    }
}
