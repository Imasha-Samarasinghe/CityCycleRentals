package com.sam.citycyclerentals;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RentalSelectionActivity extends AppCompatActivity {

    private TextView textViewCity;
    private ImageView imageViewCity;
    private Spinner spinnerBikeType;
    private Button buttonFromDate, buttonToDate, buttonSearch;
    private RecyclerView recyclerViewBikes;
    private String  selectedBikeType;
    private int selectedDuration = 1;
    private List<Bike> bikeList;
    private DatabaseHelper databaseHelper;
    private String selectedCity;
    private Bike selectedBike = null;
    private BikeAdapter bikeAdapter;

    private String selectedFromDate = "";
    private String selectedToDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rental_selection);

        databaseHelper = new DatabaseHelper(this);
        bikeList = new ArrayList<>();

        textViewCity = findViewById(R.id.textViewCity);
        imageViewCity = findViewById(R.id.imageViewCity);
        spinnerBikeType = findViewById(R.id.spinnerBikeType);
        buttonFromDate = findViewById(R.id.buttonFromDate);
        buttonToDate = findViewById(R.id.buttonToDate);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerViewBikes = findViewById(R.id.recyclerViewBikes);

        selectedCity = getIntent().getStringExtra("CITY_NAME");
        int cityImage = getIntent().getIntExtra("CITY_IMAGE", R.drawable.city1);

        textViewCity.setText(selectedCity);
        imageViewCity.setImageResource(cityImage);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bike_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBikeType.setAdapter(adapter);

        recyclerViewBikes.setLayoutManager(new LinearLayoutManager(this));
        bikeAdapter = new BikeAdapter(this, bikeList, bike -> {
            selectedBike = bike;
            Log.d("DEBUG_SELECTION", "Bike selected: " + selectedBike.getId());
            validateAndProceed();
        });

        recyclerViewBikes.setAdapter(bikeAdapter);

        buttonFromDate.setOnClickListener(v -> showDatePickerDialog(true));
        buttonToDate.setOnClickListener(v -> showDatePickerDialog(false));

        buttonSearch.setOnClickListener(v -> searchBikes());
    }

    private void showDatePickerDialog(boolean isFromDate) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year1, month1, dayOfMonth);

            String selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;

            if (isFromDate) {
                selectedFromDate = selectedDate;
                buttonFromDate.setText("From: " + selectedDate);
                Log.d("DEBUG_DATE_PICK", "Selected Start Date: " + selectedFromDate);
            } else {
                selectedToDate = selectedDate;
                buttonToDate.setText("To: " + selectedDate);
                Log.d("DEBUG_DATE_PICK", "Selected End Date: " + selectedToDate);
            }

            if (!selectedFromDate.isEmpty() && !selectedToDate.isEmpty()) {
                calculateRentalDuration();
            }

        }, year, month, day);
        datePickerDialog.show();
    }

    private void searchBikes() {
        selectedBikeType = spinnerBikeType.getSelectedItem().toString();
        bikeList.clear();

        Cursor cursor;
        if (selectedBikeType.equals("All")) {
            cursor = databaseHelper.getBikesByCity(selectedCity, null);
        } else {
            cursor = databaseHelper.getBikesByCity(selectedCity, selectedBikeType);
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int bikeId = cursor.getInt(0);
                String bikeName = cursor.getString(1);
                int bikeImage = cursor.getInt(4);
                int availability = cursor.getInt(5);

                bikeList.add(new Bike(bikeId, bikeName, bikeImage, availability));
                Log.d("DEBUG_BIKE", "Loaded Bike: " + bikeId + " - " + bikeName);
            } while (cursor.moveToNext());

            cursor.close();
        }

        bikeAdapter.notifyDataSetChanged();
        Log.d("DEBUG_BIKE", "Search updated: " + bikeList.size() + " bikes found.");
    }

    private void calculateRentalDuration() {
        try {
            if (selectedFromDate.isEmpty() || selectedToDate.isEmpty()) {
                Log.e("DEBUG_DURATION", "Start Date or End Date is empty. Skipping calculation.");
                return;
            }

            String[] fromParts = selectedFromDate.split("/");
            String[] toParts = selectedToDate.split("/");

            Calendar fromCalendar = Calendar.getInstance();
            fromCalendar.set(Integer.parseInt(fromParts[2]), Integer.parseInt(fromParts[1]) - 1, Integer.parseInt(fromParts[0]));

            Calendar toCalendar = Calendar.getInstance();
            toCalendar.set(Integer.parseInt(toParts[2]), Integer.parseInt(toParts[1]) - 1, Integer.parseInt(toParts[0]));

            long differenceMillis = toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis();
            selectedDuration = (int) (differenceMillis / (1000 * 60 * 60 * 24));

            if (selectedDuration < 1) {
                selectedDuration = 1;
            }

            Log.d("DEBUG_DURATION", "Final Rental Duration: " + selectedDuration + " days");

        } catch (Exception e) {
            Log.e("DEBUG_DURATION", "Error calculating duration: " + e.getMessage());
            selectedDuration = 1;
        }
    }


    private void validateAndProceed() {
        if (selectedBike == null) {
            Toast.makeText(this, "Please select a bike.", Toast.LENGTH_SHORT).show();
            Log.e("DEBUG_SELECTION", "No bike selected.");
            return;
        }

        if (selectedFromDate.isEmpty() || selectedToDate.isEmpty()) {
            Toast.makeText(this, "Please select rental dates.", Toast.LENGTH_SHORT).show();
            Log.e("DEBUG_SELECTION", "Dates not selected.");
            return;
        }


        Intent intent = new Intent(RentalSelectionActivity.this, RentActivity.class);
        intent.putExtra("rental_duration", selectedDuration);
        intent.putExtra("bike_id", selectedBike.getId());
        intent.putExtra("start_date", selectedFromDate);
        intent.putExtra("end_date", selectedToDate);

        Log.d("DEBUG_SELECTION", "Intent Extras Set: " +
                "start_date=" + intent.getStringExtra("start_date") + ", " +
                "end_date=" + intent.getStringExtra("end_date"));

        startActivity(intent);
    }

}
