package com.example.labamobile2.ui.dashboard.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labamobile2.R;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarHolder> {

    Context context;
    List<Car> cars;

    public CarsAdapter(Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarHolder(LayoutInflater.from(context).inflate(R.layout.car_view, parent, false));
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {
        Car car = cars.get(position);

        holder.brandView.setText(car.getBrand());
        holder.bodyTypeView.setText(car.getBodyType());
        holder.colorView.setText(car.getColor());
        holder.engineVolumeView.setText(String.format("%.1f л", car.getEngineVolume()));
        holder.priceView.setText(String.format("$%.2f", car.getPrice()));
        String id = "ID: " + car.getId();
        holder.idView.setText(id);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }
}
