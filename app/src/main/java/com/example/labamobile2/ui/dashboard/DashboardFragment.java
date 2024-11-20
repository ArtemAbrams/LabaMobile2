package com.mobileapp.mobilelaba2.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobileapp.mobilelaba2.R;
import com.mobileapp.mobilelaba2.ui.dashboard.database.DBManager;
import com.mobileapp.mobilelaba2.ui.dashboard.database.StudentCourses;
import com.mobileapp.mobilelaba2.ui.dashboard.database.StudentsCoursesAdapter;
import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        try (DBManager dbManager = new DBManager(requireContext())) {

            // clear db from previous initial upload
            dbManager.deleteAllStudents();

            // init upload
            dbManager.addStudent(new StudentCourses("Басаряба І.Ю.", "РПМЗ", "65", "72", "проспект Академіка Глушкова 4д"));
            dbManager.addStudent(new StudentCourses("Яловенко В.В.", "ІТ", "45", "65", "Володимирська 33"));
            dbManager.addStudent(new StudentCourses("Пивко В.М.", "ТПР", "24", "65", "Володимирська 40"));
            dbManager.addStudent(new StudentCourses("Прилипко Д.С.", "ІС", "70", "70", "проспект Академіка Глушкова 4д"));
            dbManager.addStudent(new StudentCourses("Малишевська К.В.", "ІМ", "50", "55", "проспект Академіка Глушкова 4д"));

            List<StudentCourses> studentCourses = dbManager.getAllStudents();
            StudentsCoursesAdapter studentsCoursesAdapter = new StudentsCoursesAdapter(requireContext(), studentCourses);
            recyclerView.setAdapter(studentsCoursesAdapter);

            Button buttonGrade = view.findViewById(R.id.buttonGrade);
            Button buttonAvarage = view.findViewById(R.id.button2);
            Button buttonShowAll = view.findViewById(R.id.button3);
            TextView textViewPercentage = view.findViewById(R.id.studentsPercentage);

            buttonGrade.setOnClickListener(v -> {
                RecyclerView recyclerView1 = view.findViewById(R.id.recyclerViewStudents);
                recyclerView1.setLayoutManager(new LinearLayoutManager(requireContext()));
                List<StudentCourses> studentsGradeMore60 = dbManager.getStudentsWithAverageAbove60();
                StudentsCoursesAdapter studentsMore60Adapter = new StudentsCoursesAdapter(requireContext(), studentsGradeMore60);
                recyclerView1.setAdapter(studentsMore60Adapter);
            });

            buttonShowAll.setOnClickListener(v -> {
                RecyclerView recyclerView12 = view.findViewById(R.id.recyclerViewStudents);
                recyclerView12.setLayoutManager(new LinearLayoutManager(requireContext()));
                List<StudentCourses> studentsAll = dbManager.getAllStudents();
                StudentsCoursesAdapter studentsAllAdapter = new StudentsCoursesAdapter(requireContext(), studentsAll);
                recyclerView12.setAdapter(studentsAllAdapter);
            });

            buttonAvarage.setOnClickListener(v -> {
                double averageSelectedPercentage = dbManager.getAverageSelectedPercentage();
                textViewPercentage.setText(String.format(Locale.getDefault(), "%.2f", averageSelectedPercentage));
            });

            return view;
        }
    }
}