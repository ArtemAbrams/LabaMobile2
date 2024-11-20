package com.example.labamobile2.ui.dashboard.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.mobilelaba2.R;

import java.util.List;

public class StudentsCoursesAdapter extends RecyclerView.Adapter<StudentCoursesHolder> {

    Context context;
    List<StudentCourses> studentCourses;

    public StudentsCoursesAdapter(Context context, List<StudentCourses> studentCourses) {
        this.context = context;
        this.studentCourses = studentCourses;
    }

    @NonNull
    @Override
    public StudentCoursesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentCoursesHolder(LayoutInflater.from(context).inflate(R.layout.student_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCoursesHolder holder, int position) {
        holder.pibView.setText(studentCourses.get(position).getPib());
        holder.nameView.setText(studentCourses.get(position).getName());
        holder.gradesView.setText((studentCourses.get(position).getStudentAVGGrade()));
        holder.addressView.setText(studentCourses.get(position).getAddress());
        String id = "    " + studentCourses.get(position).getId();
        holder.idView.setText(id);
    }

    @Override
    public int getItemCount() {
        return studentCourses.size();
    }
}
