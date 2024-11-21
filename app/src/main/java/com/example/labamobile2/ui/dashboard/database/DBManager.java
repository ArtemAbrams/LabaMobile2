package com.example.labamobile2.ui.dashboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public DBManager(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Constants.DROP_TABLE);
        onCreate(db);
    }

    // Метод для додавання автомобіля
    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_NAME_BRAND, car.getBrand());
        cv.put(Constants.COLUMN_NAME_BODY_TYPE, car.getBodyType());
        cv.put(Constants.COLUMN_NAME_COLOR, car.getColor());
        cv.put(Constants.COLUMN_NAME_ENGINE_VOLUME, car.getEngineVolume());
        cv.put(Constants.COLUMN_NAME_PRICE, car.getPrice());

        db.insert(Constants.TABLE_NAME, null, cv);
        db.close();
    }

    // Метод для видалення всіх автомобілів
    public void deleteAllCars() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, null, null);
        db.close();
    }

    // Метод для отримання списку всіх автомобілів
    public List<Car> getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Car> carList = new ArrayList<>();

        String selectAllCars = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllCars, null);

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_ID)));
                car.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_BRAND)));
                car.setBodyType(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_BODY_TYPE)));
                car.setColor(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_COLOR)));
                car.setEngineVolume(cursor.getDouble(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_ENGINE_VOLUME)));
                car.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_PRICE)));
                carList.add(car);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return carList;
    }

    // Метод для отримання червоних автомобілів з типом кузова "універсал"
    public List<Car> getRedStationWagons() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Car> redStationWagons = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME +
                " WHERE " + Constants.COLUMN_NAME_COLOR + " = 'червоний'" +
                " AND " + Constants.COLUMN_NAME_BODY_TYPE + " = 'універсал'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Car car = new Car();
                car.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_ID)));
                car.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_BRAND)));
                car.setBodyType(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_BODY_TYPE)));
                car.setColor(cursor.getString(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_COLOR)));
                car.setEngineVolume(cursor.getDouble(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_ENGINE_VOLUME)));
                car.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_PRICE)));
                redStationWagons.add(car);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return redStationWagons;
    }

    // Метод для обчислення середнього об'єму двигуна
    public double getAverageEngineVolume() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalVolume = 0;
        int count = 0;

        String selectQuery = "SELECT " + Constants.COLUMN_NAME_ENGINE_VOLUME + " FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                totalVolume += cursor.getDouble(cursor.getColumnIndexOrThrow(Constants.COLUMN_NAME_ENGINE_VOLUME));
                count++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        return count > 0 ? totalVolume / count : 0;
    }
}