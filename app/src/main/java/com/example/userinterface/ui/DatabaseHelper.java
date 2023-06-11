package com.example.userinterface.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static Context context;
    private static final String DATABASE_NAME = "postBank_db";
    //table users
    private static final String DATABASE_TABLE = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_CODE = "code"; // new column
    private static final String COLUMN_TYPE = "user_type";

    //table jobs
    private static final String DATABASE_JOBS = "jobs";
    private static final String JOB_ID = "id";
    private static final String JOB_TITLE = "title";
    private static final String JOB_REQUIREMENT = "requirement";
    private static final String JOB_DESCRIPTION = "description";
    private static final String JOB_NO_VACANCIES = "positions";
    private static final String JOB_POST_DATE = "post_date"; // new column
    private static final String JOB_DEADLINE_DATE = "deadline_date";





    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    //create table jobs
    private static String Create_table_jobs = "Create Table " + DATABASE_JOBS +
            " ( " + JOB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            JOB_TITLE + " TEXT, " +
            JOB_REQUIREMENT + " TEXT, " +
            JOB_DESCRIPTION + " TEXT, " +
            JOB_NO_VACANCIES + " INTEGER, " +
            JOB_POST_DATE + " TEXT, " +
            JOB_DEADLINE_DATE + " TEXT );";





    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DATABASE_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_CODE + " INTEGER, " +
                COLUMN_TYPE + " TEXT );"; // add new column to the table

        db.execSQL(query);
        db.execSQL(Create_table_jobs);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_JOBS);
        onCreate(db);

    }

    //insert registered data into database
    public boolean registerUsers(String name, String email, String number, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_CODE, "0");
        cv.put(COLUMN_TYPE, "user");// add new column to ContentValues


        long result = db.insert(DATABASE_TABLE,null, cv);

        if (result == -1)
            return false;
        else
            return true;



    }


    //check if user name exists

    public  boolean checkUsername(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //check if user and password exists
    public  boolean checkUserPass(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + COLUMN_EMAIL + "=?" +
                " and " +  COLUMN_PASSWORD + " =?", new String[]{email,password});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //update code function
    public void update_interns(String user_email, int code){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CODE,code);

        long result = db.update(DATABASE_TABLE,cv,COLUMN_EMAIL + "=?",new String[]{user_email});

    }

    //select if email and code exists
    public boolean checkCode(String email, String code) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_EMAIL };
        String selection = COLUMN_EMAIL + " = ? and " + COLUMN_CODE + " = ?";
        String[] selectionArgs = { email, code };
        Cursor cursor = db.query(DATABASE_TABLE, columns, selection, selectionArgs, null, null, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }
    //check the code if zero or greater to know if verified
    public Boolean isVerified(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + COLUMN_EMAIL + "=?" +
                " and " +  COLUMN_PASSWORD + " =?" + " and " + COLUMN_CODE + " >0 ", new String[]{email,password});

        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }

    //check if user type is admin or user

    public Boolean isAdmin(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE " + COLUMN_EMAIL + "=?" +
                " and " +  COLUMN_PASSWORD + " =?" + " and " + COLUMN_TYPE + " = 'admin' ", new String[]{email,password});

        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }

    //ADMIN SIDE DATABASE
    //INSERT JOB DETAILS INTO DATABASE

    public  Boolean insertJobs(String title,String requirement,String description, String positions, String post_date,String deadline_date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(JOB_TITLE,title);
        cv.put(JOB_REQUIREMENT,requirement);
        cv.put(JOB_DESCRIPTION,description);
        cv.put(JOB_NO_VACANCIES,positions);
        cv.put(JOB_POST_DATE,post_date);
        cv.put(JOB_DEADLINE_DATE,deadline_date);


        long result = db.insert(DATABASE_JOBS,null,cv);

        if (result == -1){
            return false;
        }else {
            return true;
        }



    }

}
