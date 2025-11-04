package com.sam.citycyclerentals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CityCycleRentals.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_USERS = "users";
    private static final String TABLE_BIKES = "bikes";
    private static final String TABLE_RENTALS = "rentals";

    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD = "password";

    private static final String COLUMN_BIKE_ID = "id";
    private static final String COLUMN_BIKE_NAME = "bike_name";
    private static final String COLUMN_BIKE_TYPE = "bike_type";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_PRICE = "bike_price"; // Added price
    private static final String COLUMN_IMAGE = "bike_image"; // Added image
    private static final String COLUMN_AVAILABILITY = "availability";


    private static final String COLUMN_RENTAL_ID = "id";
    private static final String COLUMN_USER_ID_FK = "user_id";
    private static final String COLUMN_BIKE_ID_FK = "bike_id";
    private static final String COLUMN_START_DATE = "start_date";
    private static final String COLUMN_END_DATE = "end_date";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_PAYMENT_METHOD = "payment_method";
    private static final String COLUMN_TOTAL_PRICE = "total_price";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        SQLiteDatabase db = this.getWritableDatabase();

        String dbPath = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
        Log.d("DB_PATH", "Database Path: " + dbPath);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("DB_CHECK", "Database is being created...");

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT UNIQUE, " +
                COLUMN_PHONE + " TEXT, " +
                COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        Log.d("DB_CHECK", "Users table created successfully.");

        String CREATE_BIKES_TABLE = "CREATE TABLE " + TABLE_BIKES + " ("
                + COLUMN_BIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_BIKE_NAME + " TEXT, "
                + COLUMN_BIKE_TYPE + " TEXT, "
                + COLUMN_CITY + " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_IMAGE + " INTEGER, "
                + COLUMN_AVAILABILITY + " INTEGER)";
        db.execSQL(CREATE_BIKES_TABLE);
        Log.d("DB_CHECK", "Bikes table created successfully.");

        insertBikeData(db);

        String createRentalsTable = "CREATE TABLE rentals (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER, " +
                "bike_id INTEGER, " +
                "start_date TEXT, " +
                "end_date TEXT, " +
                "status TEXT, " +
                "payment_method TEXT, " +
                "total_price REAL, " +
                "FOREIGN KEY(user_id) REFERENCES users(id), " +
                "FOREIGN KEY(bike_id) REFERENCES bikes(id))";
        db.execSQL(createRentalsTable);

    }

    private void insertBikeData(SQLiteDatabase db) {
        Log.d("DB_CHECK", "Inserting bike data...");
        ContentValues values = new ContentValues();

        String[][] bikes = {

                {"Mountain Bike 1", "Mountain", "Colombo", "50", "1", String.valueOf(R.drawable.bike_image1)},
                {"Gear Bike 1", "Gear", "Colombo", "40", "1", String.valueOf(R.drawable.bike_image2)},
                {"Scooter 1", "Scooter", "Colombo", "30", "1", String.valueOf(R.drawable.bike_image3)},
                {"Mountain Bike 2", "Mountain", "Colombo", "55", "1", String.valueOf(R.drawable.bike_image4)},
                {"Gear Bike 2", "Gear", "Colombo", "45", "1", String.valueOf(R.drawable.bike_image5)},

                {"Mountain Bike 3", "Mountain", "Nuwara Eliya", "60", "1", String.valueOf(R.drawable.bike_image1)},
                {"Gear Bike 3", "Gear", "Nuwara Eliya", "50", "1", String.valueOf(R.drawable.bike_image2)},
                {"Scooter 2", "Scooter", "Nuwara Eliya", "35", "1", String.valueOf(R.drawable.bike_image6)},
                {"Mountain Bike 4", "Mountain", "Nuwara Eliya", "65", "1", String.valueOf(R.drawable.bike_image4)},
                {"Gear Bike 4", "Gear", "Nuwara Eliya", "55", "1", String.valueOf(R.drawable.bike_image5)},

                {"Mountain Bike 5", "Mountain", "Kandy", "50", "1", String.valueOf(R.drawable.bike_image1)},
                {"Gear Bike 5", "Gear", "Kandy", "45", "1", String.valueOf(R.drawable.bike_image2)},
                {"Scooter 3", "Scooter", "Kandy", "40", "1", String.valueOf(R.drawable.bike_image3)},
                {"Mountain Bike 6", "Mountain", "Kandy", "60", "1", String.valueOf(R.drawable.bike_image4)},
                {"Gear Bike 6", "Gear", "Kandy", "50", "1", String.valueOf(R.drawable.bike_image5)},

                {"Mountain Bike 7", "Mountain", "Sigiriya", "55", "1", String.valueOf(R.drawable.bike_image1)},
                {"Gear Bike 7", "Gear", "Sigiriya", "45", "1", String.valueOf(R.drawable.bike_image2)},
                {"Scooter 4", "Scooter", "Sigiriya", "40", "1", String.valueOf(R.drawable.bike_image3)},
                {"Mountain Bike 8", "Mountain", "Sigiriya", "60", "1", String.valueOf(R.drawable.bike_image4)},
                {"Gear Bike 8", "Gear", "Sigiriya", "50", "1", String.valueOf(R.drawable.bike_image5)},

                {"Mountain Bike 9", "Mountain", "Ella", "50", "1", String.valueOf(R.drawable.bike_image1)},
                {"Gear Bike 9", "Gear", "Ella", "45", "1", String.valueOf(R.drawable.bike_image2)},
                {"Scooter 5", "Scooter", "Ella", "40", "1", String.valueOf(R.drawable.bike_image3)},
                {"Mountain Bike 10", "Mountain", "Ella", "55", "1", String.valueOf(R.drawable.bike_image4)},
                {"Gear Bike 10", "Gear", "Ella", "50", "1", String.valueOf(R.drawable.bike_image5)},

                {"Mountain Bike 11", "Mountain", "Galle", "60", "1", String.valueOf(R.drawable.bike_image1)},
                {"Gear Bike 11", "Gear", "Galle", "50", "1", String.valueOf(R.drawable.bike_image2)},
                {"Scooter 6", "Scooter", "Galle", "40", "1", String.valueOf(R.drawable.bike_image3)},
                {"Mountain Bike 12", "Mountain", "Galle", "65", "1", String.valueOf(R.drawable.bike_image4)},
                {"Gear Bike 12", "Gear", "Galle", "55", "1", String.valueOf(R.drawable.bike_image5)}
        };

        for (String[] bike : bikes) {
            values.put(COLUMN_BIKE_NAME, bike[0]);
            values.put(COLUMN_BIKE_TYPE, bike[1]);
            values.put(COLUMN_CITY, bike[2]);
            values.put(COLUMN_PRICE, Double.parseDouble(bike[3]));
            values.put(COLUMN_IMAGE, R.drawable.bike_image1);
            values.put(COLUMN_AVAILABILITY, Integer.parseInt(bike[4]));
            db.insert(TABLE_BIKES, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIKES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RENTALS);
        onCreate(db);
    }

    public boolean isValidUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});
        boolean userExists = cursor.getCount() > 0;
        cursor.close();
        return userExists;
    }

    public boolean registerUser(String name, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.close();
            return false;
        }
        cursor.close();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FULL_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PHONE, phone);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public String getUsername(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String username = "User";

        Cursor cursor = db.rawQuery("SELECT full_name FROM users WHERE email=?", new String[]{email});

        if (cursor.moveToFirst()) {
            username = cursor.getString(0);
        }
        cursor.close();
        return username;
    }

    public boolean updateUserDetails(String loggedInUserEmail, String fullName, String phone, String payment) {
        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("full_name", fullName);
        contentValues.put("phone", phone);
        contentValues.put("payment", payment);

        String whereClause = "email = ?";
        String[] whereArgs = new String[]{loggedInUserEmail};

        int rowsAffected = db.update("users", contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }

    public Cursor getUserDetails(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM users WHERE email = ?";

        return db.rawQuery(query, new String[]{email});
    }

    public Cursor getBikesByCity(String city, String bikeType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BIKES + " WHERE city = ?";

        if (bikeType != null && !bikeType.equals("All")) {
            query += " AND bike_type = ?";
            return db.rawQuery(query, new String[]{city, bikeType});
        } else {
            return db.rawQuery(query, new String[]{city});
        }
    }
    public boolean insertRental(int userId, int bikeId, String startDate, String endDate, String status, String paymentMethod, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("bike_id", bikeId);
        values.put("start_date", startDate);
        values.put("end_date", endDate);
        values.put("status", status);
        values.put("payment_method", paymentMethod);
        values.put("total_price", totalPrice);

        long result = db.insert("rentals", null, values);

        if (result == -1) {
            Log.e("DB_ERROR", "Failed to insert rental. UserID: " + userId + ", BikeID: " + bikeId);
            return false;
        } else {
            Log.d("DB_SUCCESS", "Rental inserted successfully! Rental ID: " + result);
            return true;
        }
    }


    public List<Rental> getRentalsByStatus(int userId, String status) {
        List<Rental> rentalList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_RENTALS + " WHERE " + COLUMN_USER_ID_FK + "=? AND " + COLUMN_STATUS + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId), status});

        if (cursor.moveToFirst()) {
            do {
                Rental rental = new Rental(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getDouble(7)
                );
                rentalList.add(rental);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return rentalList;
    }
    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        String query = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        return userId;
    }
    public boolean updateRentalStatus(int rentalId, String newStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);

        int rowsUpdated = db.update("rentals", values, "id = ?", new String[]{String.valueOf(rentalId)});
        db.close();

        return rowsUpdated > 0;
    }

}


