package com.example.labamobile2.ui.contacts;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.labamobile2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng deviceLocation = new LatLng(50.4021, 30.5167); // Початкові координати (наприклад, Київ)
    private EditText searchField;
    private ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Ініціалізація карти
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Ініціалізація поля введення і кнопки пошуку
        searchField = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(v -> searchLocation(searchField.getText().toString().trim()));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Додавання маркера на поточне місце розташування
        mMap.addMarker(new MarkerOptions().position(deviceLocation).title("Поточне місце"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deviceLocation, 12));
    }

    private void searchLocation(String locationName) {
        if (locationName.isEmpty()) {
            searchField.setError("Введіть назву місця");
            return;
        }

        // URL для геокодування назви місця
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + locationName + "&key=AIzaSyBMBZLOkeFApS4NDdCOZwGkajhlf3IEnUA";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        assert response.body() != null;
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray results = jsonResponse.getJSONArray("results");

                        if (results.length() > 0) {
                            JSONObject location = results.getJSONObject(0)
                                    .getJSONObject("geometry")
                                    .getJSONObject("location");

                            double lat = location.getDouble("lat");
                            double lng = location.getDouble("lng");
                            LatLng searchedLocation = new LatLng(lat, lng);

                            runOnUiThread(() -> showSearchedLocation(searchedLocation, locationName));
                        } else {
                            runOnUiThread(() -> searchField.setError("Місце не знайдено"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void showSearchedLocation(LatLng location, String locationName) {
        // Додавання маркера на знайдене місце
        mMap.clear(); // Очищення попередніх маркерів
        mMap.addMarker(new MarkerOptions().position(location).title(locationName));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }
}
