package com.example.labamobile2.ui.contacts;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labamobile2.R;

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
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
