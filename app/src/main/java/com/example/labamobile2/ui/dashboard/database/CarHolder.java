package com.example.labamobile2.ui.dashboard.database;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.mobilelaba2.R;

public class StudentCoursesHolder extends RecyclerView.ViewHolder {
    TextView pibView;
    TextView gradesView;
    TextView addressView;
    TextView idView;
    TextView nameView;


    public StudentCoursesHolder(@NonNull View itemView) {
        super(itemView);
        pibView = itemView.findViewById(R.id.PIB);
        nameView = itemView.findViewById(R.id.name);
        gradesView = itemView.findViewById(R.id.grade);
        addressView = itemView.findViewById(R.id.address);
        idView = itemView.findViewById(R.id.StudentId);
    }
}
