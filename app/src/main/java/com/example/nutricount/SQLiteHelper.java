package com.example.nutricount;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "nutricount.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_ACCOUNTS = "accounts";

    private static final String CREATE_TABLE_ACCOUNTS = "CREATE TABLE " + TABLE_ACCOUNTS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)";

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
        onCreate(sqLiteDatabase);
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE username=? OR email=?",
                new String[] { username, username }
        );

        return cursor.getCount() > 0;
    }

    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_ACCOUNTS + " WHERE username=? OR email=? AND password=?",
                new String[] { username, username, password }
        );

        return cursor.getCount() > 0;
    }

    public void createAccount(
            SQLiteDatabase db, String username, String email, String password,
            String firstName, String lastName, String gender,
            double height, double weight, double goal, List<String> allergy
    ) {
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("email", email);
        values.put("password", password);

        values.put("firstName", firstName);
        values.put("lastName", lastName);
        values.put("gender", gender);

        values.put("height", height);
        values.put("weight", weight);
        values.put("goal", goal);
        values.put("allergy", String.join(",", allergy));

        db.insert(TABLE_ACCOUNTS, null, values);
    }

    public void populateInitialAccounts() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Account> accounts = new ArrayList<>();

        accounts.add(new Account(
                0, "eru.2001", "ocampo.jerud.2001@gmail.com", "eru.2001",
                "Jerud", "Ocampo", "Male",
                169, 60.3, 69, new ArrayList<>()
        ));
        accounts.add(new Account(
                1, "michei025", "floresjake021600@gmail.com", "michei025",
                "Jake", "Flores", "Male",
                176, 102, 80, new ArrayList<>()
        ));

        for (Account a: accounts) {
            createAccount(
                    db, a.getUsername(), a.getEmail(), a.getPassword(),
                    a.getFirstName(), a.getLastName(), a.getGender(),
                    a.getHeight(), a.getWeight(), a.getGoal(), a.getAllergy()
            );
        }
    }
}
