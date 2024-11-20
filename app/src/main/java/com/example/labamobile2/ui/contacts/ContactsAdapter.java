package com.mobileapp.mobilelaba2.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.mobilelaba2.R;

import java.io.IOException;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {

    Context context;
    List<Contact> contacts;

    public ContactsAdapter(Context context, List<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsHolder(LayoutInflater.from(context).inflate(R.layout.contact_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsHolder holder, int position) {
        final Contact contact = contacts.get(position);
        holder.numberView.setText(contact == null ? "" : contact.getNumber());
        holder.contactNameSurnameView.setText(contact == null ? "" : contact.getName() + " " + contact.getSurname());

        holder.itemView.setOnClickListener(v -> {
            if (contact != null && contact.getAddress() != null && !contact.getAddress().isEmpty()) {
                openMapForContact(contact);
            } else {
                Toast.makeText(context, "Адреса не вказана для цього контакту", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMapForContact(Contact contact) {
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addresses = geocoder.getFromLocationName(contact.getAddress(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Не вдалося знайти координати для цієї адреси", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Помилка при пошуку координат", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
