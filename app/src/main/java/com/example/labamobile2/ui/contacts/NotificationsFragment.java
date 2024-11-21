package com.example.labamobile2.ui.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labamobile2.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentLocation = new LatLng(50.4021, 30.5167); // Поточна локація
    private EditText addressInput;
    private ImageButton searchButton;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        // Ініціалізація UI-компонентів
        addressInput = view.findViewById(R.id.searchField);
        searchButton = view.findViewById(R.id.searchButton);
        recyclerView = view.findViewById(R.id.recyclerViewContact);

        // Налаштування RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Contact> contacts = getContacts(); // Функція для отримання контактів
        ContactsAdapter adapter = new ContactsAdapter(requireContext(), contacts);
        recyclerView.setAdapter(adapter);

        // Ініціалізація карти
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Логіка кнопки пошуку
        searchButton.setOnClickListener(v -> {
            String address = addressInput.getText().toString().trim();
            if (!TextUtils.isEmpty(address)) {
                geocodeAddress(address);
            } else {
                addressInput.setError("Введіть адресу");
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Додавання маркера на поточне місце
        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Поточне місце"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
    }

    private List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();

        String[] phoneProjection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        String selection = ContactsContract.CommonDataKinds.Phone.NUMBER + " LIKE ?"; // Фільтрація номерів
        String[] selectionArgs = {"%" + 7}; // Номер закінчується на `7`

        Cursor cursor = requireContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                phoneProjection,
                selection,
                selectionArgs,
                null
        );

        if (cursor != null) {
            try {
                int displayNameIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneNumberIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

                while (cursor.moveToNext()) {
                    String contactName = cursor.getString(displayNameIndex);
                    String contactNumber = cursor.getString(phoneNumberIndex);

                    contacts.add(new Contact(contactName, "", contactNumber));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

        return contacts;
    }

    private void geocodeAddress(String address) {
        // URL для Google Geocoding API
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + address + "&key=AIzaSyDZG1vyyuvtlZt9GThK6fypPxuUQ-qREpE";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

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
                            LatLng destination = new LatLng(lat, lng);

                            requireActivity().runOnUiThread(() -> drawRoute(destination));
                        } else {
                            requireActivity().runOnUiThread(() -> addressInput.setError("Адреса не знайдена"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void drawRoute(LatLng destination) {
        mMap.clear();

        // Додавання маркера для пункту призначення
        mMap.addMarker(new MarkerOptions().position(destination).title("Місце призначення"));

        // Побудова маршруту
        PolylineOptions options = new PolylineOptions()
                .add(currentLocation)
                .add(destination)
                .width(10)
                .color(getResources().getColor(R.color.teal_200));

        mMap.addPolyline(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destination, 15));
    }
}
