package com.example.labamobile2.ui.dashboard.database;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labamobile2.R;

public class CarHolder extends RecyclerView.ViewHolder {
    TextView brandView;
    TextView bodyTypeView;
    TextView colorView;
    TextView engineVolumeView;
    TextView priceView;
    TextView idView;

    public CarHolder(@NonNull View itemView) {
        super(itemView);
        brandView = itemView.findViewById(R.id.brand); // Бренд автомобіля
        bodyTypeView = itemView.findViewById(R.id.bodyType); // Тип кузова
        colorView = itemView.findViewById(R.id.color); // Колір
        engineVolumeView = itemView.findViewById(R.id.engineVolume); // Об'єм двигуна
        priceView = itemView.findViewById(R.id.price); // Ціна
        idView = itemView.findViewById(R.id.carId); // ID автомобіля
    }
}
