package com.example.labamobile2.ui.contacts;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.labamobile2.R;

public class ContactsHolder extends RecyclerView.ViewHolder {
    TextView contactNameSurnameView;
    TextView numberView;

    public ContactsHolder(@NonNull View itemView) {
        super(itemView);
        contactNameSurnameView = itemView.findViewById(R.id.contactNameSurname);
        numberView = itemView.findViewById(R.id.contactNumber);
    }
}