package com.example.deto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Deto.Data";
    public static final String TABLE_NAME = "Deto_table";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "SURNAME";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "NITRITVALUE";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " TEXT, " + COL_2 + " TEXT, " + COL_3 + " DATETIME, " + COL_4 + " DOUBLE" + " ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    void addData(Data data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, data.getName());
        values.put(COL_2, data.getSurname());
        values.put(COL_3, data.getDate());
        values.put(COL_4, data.getNitrite());

        db.insert(DATABASE_NAME, null, values);
        db.close();

    }

    Data getData(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DATABASE_NAME, new String[]{COL_1, COL_2, COL_3, COL_4}, COL_1 + "=?", new String[]{String.valueOf(name)},
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Data data = new Data(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return data;
    }
    public List<Data> getAllData(){
        String selectQuery = "SELECT *FROM" + DATABASE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery((selectQuery,null);

        if(cursor.moveToFirst()){
            do {
                Data data = new Data();
                data.setName(Integer.parseInt(cursor.getString(0);
            }
        }

    }
}

