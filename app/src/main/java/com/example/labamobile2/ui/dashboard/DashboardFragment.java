package com.example.labamobile2.ui.dashboard;

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

import com.example.labamobile2.R;
import com.example.labamobile2.ui.dashboard.database.Car;
import com.example.labamobile2.ui.dashboard.database.CarsAdapter;
import com.example.labamobile2.ui.dashboard.database.DBManager;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewCars);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        TextView textViewAverageVolume = view.findViewById(R.id.averageEngineVolume);
        Button buttonFilterRedWagons = view.findViewById(R.id.buttonRedWagons);
        Button buttonAverageVolume = view.findViewById(R.id.buttonAverageVolume);
        Button buttonShowAll = view.findViewById(R.id.buttonShowAll);

        try (DBManager dbManager = new DBManager(requireContext())) {

            // Очищення бази даних і додавання початкових даних
            dbManager.deleteAllCars();
            dbManager.addCar(new Car("Toyota", "універсал", "червоний", 2.0, 25000));
            dbManager.addCar(new Car("Ford", "седан", "синій", 1.6, 20000));
            dbManager.addCar(new Car("Volkswagen", "універсал", "червоний", 1.8, 23000));
            dbManager.addCar(new Car("BMW", "кросовер", "чорний", 3.0, 45000));
            dbManager.addCar(new Car("Honda", "хетчбек", "білий", 1.5, 18000));

            // Відображення всіх автомобілів
            List<Car> cars = dbManager.getAllCars();
            CarsAdapter carsAdapter = new CarsAdapter(requireContext(), cars);
            recyclerView.setAdapter(carsAdapter);

            // Кнопка для відображення червоних універсалів
            buttonFilterRedWagons.setOnClickListener(v -> {
                List<Car> redWagons = dbManager.getRedStationWagons();
                CarsAdapter filteredAdapter = new CarsAdapter(requireContext(), redWagons);
                recyclerView.setAdapter(filteredAdapter);
            });

            // Кнопка для відображення середнього об'єму двигуна
            buttonAverageVolume.setOnClickListener(v -> {
                double averageVolume = dbManager.getAverageEngineVolume();
                textViewAverageVolume.setText(String.format(Locale.getDefault(), "Середній об'єм: %.2f л", averageVolume));
            });

            // Кнопка для відображення всіх автомобілів
            buttonShowAll.setOnClickListener(v -> {
                List<Car> allCars = dbManager.getAllCars();
                CarsAdapter allCarsAdapter = new CarsAdapter(requireContext(), allCars);
                recyclerView.setAdapter(allCarsAdapter);
            });
        }

        return view;
    }
}
