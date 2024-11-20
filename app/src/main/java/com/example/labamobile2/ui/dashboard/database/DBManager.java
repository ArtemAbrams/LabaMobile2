package com.mobileapp.mobilelaba2.ui.dashboard.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void addStudent(StudentCourses studentCourses){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constants.COLUMN_NAME_PIB, studentCourses.getPib());
        cv.put(Constants.COLUMN_NAME_NAME, studentCourses.getName());
        cv.put(Constants.COLUMN_NAME_GRADE_1, studentCourses.getGrade1());
        cv.put(Constants.COLUMN_NAME_GRADE_2, studentCourses.getGrade2());
        cv.put(Constants.COLUMN_NAME_ADDRESS, studentCourses.getAddress());

        db.insert(Constants.TABLE_NAME, null, cv);
        db.close();
    }

    public void deleteAllStudents() {
        final List<Integer> ids = getAllStudents().stream().map(StudentCourses::getId).collect(Collectors.toList());
        SQLiteDatabase db = this.getWritableDatabase();
        ids.forEach(id -> db.delete(Constants.TABLE_NAME, Constants.COLUMN_NAME_ID + " = ?", new String[]{String.valueOf(id)}));
        db.close();
    }

    public List<StudentCourses> getAllStudents() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<StudentCourses> studentCoursesList = new ArrayList<>();

        String selectAllStudents = "SELECT * FROM " + Constants.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllStudents, null);

        if(cursor.moveToFirst()){
            do {
                StudentCourses studentCourses = new StudentCourses();
                studentCourses.setId(Integer.parseInt(cursor.getString(0)));
                studentCourses.setPib(cursor.getString(1));
                studentCourses.setName(cursor.getString(2));
                studentCourses.setGrade1(cursor.getString(3));
                studentCourses.setGrade2(cursor.getString(4));
                studentCourses.setAddress(cursor.getString(5));
                studentCoursesList.add(studentCourses);
            } while(cursor.moveToNext());
        }
        return studentCoursesList;
    }

    public List<StudentCourses> getStudentsWithAverageAbove60() {
        List<StudentCourses> studentsAbove60 = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME +
                " WHERE (" + Constants.COLUMN_NAME_GRADE_1 + " + " + Constants.COLUMN_NAME_GRADE_2 + ") / 2 > 60";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StudentCourses studentCourses = new StudentCourses();
                studentCourses.setId(Integer.parseInt(cursor.getString(0)));
                studentCourses.setPib(cursor.getString(1));
                studentCourses.setName(cursor.getString(2));
                studentCourses.setGrade1(cursor.getString(3));
                studentCourses.setGrade2(cursor.getString(4));
                studentCourses.setAddress(cursor.getString(5));
                studentsAbove60.add(studentCourses);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return studentsAbove60;
    }

    public double getAverageSelectedPercentage() {
        final double allSize = getAllStudents().size();
        final double above60Avg = getStudentsWithAverageAbove60().size();
        return above60Avg / allSize * 100;
    }
}
