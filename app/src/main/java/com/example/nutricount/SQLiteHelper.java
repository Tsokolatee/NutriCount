package com.example.nutricount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    // Database Variables
    public static final String DB_NAME = "nutricount.db";
    private static final int DB_VERSION = 1;

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
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String GOAL = "goal";
    private static final String ALLERGY = "allergy";

    private static final String FOOD_NAME = "food_name";
    private static final String CATEGORY = "category";
    private static final String DESCRIPTION = "description";

    // TABLE COMMANDS
    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE " + TABLE_ACCOUNTS + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            EMAIL + " TEXT," +
            PASSWORD + " TEXT," +
            FIRST_NAME + " TEXT," +
            LAST_NAME + " TEXT," +
            GENDER + " TEXT," +
            HEIGHT + " REAL," +
            WEIGHT + " REAL," +
            GOAL + " REAL," +
            ALLERGY + " TEXT)";

    private static final String CREATE_TABLE_CALORIES = "CREATE TABLE" + " (" +
            FOOD_NAME + " STRING PRIMARY KEY," +
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
        onCreate(sqLiteDatabase);
    }

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

    public void createAccount(
            SQLiteDatabase db, String email, String password,
            String firstName, String lastName, String gender,
            double height, double weight, double goal, List<String> allergy
    ) {
        ContentValues values = new ContentValues();

        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        values.put(FIRST_NAME, firstName);
        values.put(LAST_NAME, lastName);
        values.put(GENDER, gender);
        values.put(HEIGHT, height);
        values.put(WEIGHT, weight);
        values.put(GOAL, goal);
        values.put(ALLERGY, String.join(",", allergy));

        db.insert(TABLE_ACCOUNTS, null, values);
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
                176,
                102,
                80,
                new ArrayList<>()
        ));

        for (Account a: accounts) {
            createAccount(
                    db, a.getEmail(), a.getPassword(),
                    a.getFirstName(), a.getLastName(), a.getGender(),
                    a.getHeight(), a.getWeight(), a.getGoal(), a.getAllergy()
            );
        }
    }
}
