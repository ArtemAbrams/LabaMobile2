package com.example.labamobile2.ui.dashboard.database;

public class Constants {
    public static final String DB_NAME = "cars_db.db"; // Назва бази даних
    public static final int DB_VERSION = 1; // Версія бази даних
    public static final String TABLE_NAME = "cars"; // Назва таблиці

    // Колонки таблиці
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_BRAND = "brand";
    public static final String COLUMN_NAME_BODY_TYPE = "body_type"; // Тип кузова
    public static final String COLUMN_NAME_COLOR = "color";
    public static final String COLUMN_NAME_ENGINE_VOLUME = "engine_volume"; // Об'єм двигуна
    public static final String COLUMN_NAME_PRICE = "price";

    // SQL-запит для створення таблиці
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_NAME_BRAND + " TEXT," +
                    COLUMN_NAME_BODY_TYPE + " TEXT," +
                    COLUMN_NAME_COLOR + " TEXT," +
                    COLUMN_NAME_ENGINE_VOLUME + " REAL," +
                    COLUMN_NAME_PRICE + " REAL)";

    // SQL-запит для видалення таблиці
    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
