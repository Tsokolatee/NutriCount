package com.example.nutricount;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Database Variables
    public static final String DB_NAME = "nutricount.db";
    private static final int DB_VERSION = 2;

    // Table Variables
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_CALORIES = "calories";

    // Column Variables
    private static final String ID = "ID";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String GENDER = "gender";
    private static final String BIRTHDAY = "birthday";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String GOAL = "goal";
    private static final String ALLERGY = "allergy";

    private static final String FOOD_NAME = "foodName";
    private static final String CATEGORY = "category";
    private static final String DESCRIPTION = "description";

    public String LoggedInEmail = "";

    // TABLE COMMANDS
    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE " + TABLE_ACCOUNTS + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EMAIL + " TEXT," +
            PASSWORD + " TEXT," +
            FIRST_NAME + " TEXT," +
            LAST_NAME + " TEXT," +
            GENDER + " TEXT," +
            BIRTHDAY + " TEXT," +
            HEIGHT + " REAL," +
            WEIGHT + " REAL," +
            GOAL + " REAL," +
            ALLERGY + " TEXT)";

    private static final String CREATE_TABLE_CALORIES = "CREATE TABLE " + TABLE_CALORIES + " (" +
            FOOD_NAME + " TEXT PRIMARY KEY," +
            CATEGORY + " TEXT," +
            DESCRIPTION +" TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNTS);
        sqLiteDatabase.execSQL(CREATE_TABLE_CALORIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CALORIES);
        onCreate(sqLiteDatabase);
    }

    // Class Functions
    public Boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + EMAIL + "=?",
                new String[] { email }
        );

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public Boolean checkCredentials(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + EMAIL + "=? AND " + PASSWORD + "=?",
                new String[] { email, password }
        );

        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    @SuppressLint("Range")
    public Account getAccount(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE " + EMAIL + "=?",
                new String[] { email }
        );

        try {
            Account user = null;

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                List<String> allergies = Arrays.asList(cursor.getString(cursor.getColumnIndex(ALLERGY)).split(","));

                user = new Account(
                        -1,
                        cursor.getString(cursor.getColumnIndex(EMAIL)),
                        cursor.getString(cursor.getColumnIndex(PASSWORD)),
                        cursor.getString(cursor.getColumnIndex(FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(LAST_NAME)),
                        cursor.getString(cursor.getColumnIndex(GENDER)),
                        cursor.getString(cursor.getColumnIndex(BIRTHDAY)),
                        cursor.getDouble(cursor.getColumnIndex(HEIGHT)),
                        cursor.getDouble(cursor.getColumnIndex(WEIGHT)),
                        cursor.getDouble(cursor.getColumnIndex(GOAL)),
                        allergies
                );
            }
            return user;
        } finally {
            cursor.close();
        }
    }

    @SuppressLint("Range")
    public Calories getCalorieItem(String foodName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_CALORIES + " WHERE " + FOOD_NAME + "=?",
                new String[] { foodName }
        );

        try {
            Calories item = null;

            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                 item = new Calories(
                        cursor.getString(cursor.getColumnIndex(FOOD_NAME)),
                        cursor.getString(cursor.getColumnIndex(CATEGORY)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                );
            }
            return item;
        } finally {
            cursor.close();
        }
    }

    // Class Commands
    public void createAccount(
            SQLiteDatabase db, String email, String password,
            String firstName, String lastName, String gender, String birthday,
            double height, double weight, double goal, List<String> allergy
    ) {
        ContentValues values = new ContentValues();

        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        values.put(FIRST_NAME, firstName);
        values.put(LAST_NAME, lastName);
        values.put(GENDER, gender);
        values.put(BIRTHDAY, birthday);
        values.put(HEIGHT, height);
        values.put(WEIGHT, weight);
        values.put(GOAL, goal);
        values.put(ALLERGY, String.join(",", allergy));

        db.insert(TABLE_ACCOUNTS, null, values);
    }

    public void createCalories(SQLiteDatabase db, String foodName, String category, String description) {
        ContentValues values = new ContentValues();

        values.put(FOOD_NAME, foodName);
        values.put(CATEGORY, category);
        values.put(DESCRIPTION, description);

        db.insert(TABLE_CALORIES, null, values);
    }

    public void populateInitialAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Account> accounts = new ArrayList<>();

        accounts.add(new Account(
                0,
                "ocampo.jerud.2001@gmail.com",
                "eru.2001",
                "Jerud",
                "Ocampo",
                "Male",
                "07/29/2001",
                169,
                60.3,
                69,
                new ArrayList<>()
        ));
        accounts.add(new Account(
                1,
                "floresjake021600@gmail.com",
                "michei025",
                "Jake",
                "Flores",
                "Male",
                "02/16/2000",
                176,
                102,
                80,
                new ArrayList<>()
        ));

        for (Account a: accounts) {
            createAccount(
                    db, a.getEmail(), a.getPassword(),
                    a.getFirstName(), a.getLastName(), a.getGender(), a.getBirthday(),
                    a.getHeight(), a.getWeight(), a.getGoal(), a.getAllergy()
            );
        }
    }

    public void populateCalories() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Calories> calories = new ArrayList<>();

        calories.add(new Calories(
                "Pasta",
                "Go Food",
                "288 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Cereal",
                "Go Food",
                "389 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Bread",
                "Go Food",
                "266 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Milk",
                "Grow Food",
                "60 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Chicken",
                "Grow Food",
                "165 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Pork",
                "Grow Food",
                "518 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Cheese",
                "Grow Food",
                "350 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Sausage",
                "Grow Food",
                "244 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Egg",
                "Grow Food",
                "143 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Carrot",
                "Glow Food",
                "41 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Kiwi",
                "Glow Food",
                "61 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Papaya",
                "Glow Food",
                "32 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Lemon",
                "Glow Food",
                "29 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Cabbage",
                "Glow Food",
                "25 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Banana",
                "Glow Food",
                "89 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Orange",
                "Glow Food",
                "47 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Pear",
                "Glow Food",
                "58 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Apple",
                "Glow Food",
                "52 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Ampalaya",
                "Glow Food",
                "34 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Kalabasa",
                "Glow Food",
                "26 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Cucumber",
                "Glow Food",
                "15 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Corn",
                "Glow Food",
                "86 calories in 100 grams"
        ));
        calories.add(new Calories(
                "Eggplant",
                "Glow Food",
                "25 calories in 100 grams"
        ));

        for (Calories c: calories) {
            createCalories(db, c.getFoodName(), c.getCategory(), c.getDescription());
        }
    }
}
