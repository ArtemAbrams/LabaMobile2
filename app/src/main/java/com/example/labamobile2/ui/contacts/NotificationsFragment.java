package com.mobileapp.mobilelaba2.ui.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.mobilelaba2.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private static final int REQUEST_READ_CONTACTS_PERMISSION = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Якщо дозвіл не наданий, викличе запит на дозвіл
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS_PERMISSION);
        } else {
            // Якщо дозвіл вже наданий, відображення контактів
            List<Contact> ivanContacts = getContactsByName("Ivan");

            RecyclerView recyclerView = view.findViewById(R.id.recyclerViewContact);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            ContactsAdapter contactsAdapter = new ContactsAdapter(requireContext(), ivanContacts);
            recyclerView.setAdapter(contactsAdapter);
        }
    }

    public List<Contact> getContactsByName(String nameToSearch) {
        List<Contact> contacts = new ArrayList<>();

        String[] phoneProjection = {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,  // Додано ID контакту
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        // Оновлення selection для пошуку контакту з конкретним ім'ям
        String selection = "UPPER(" + ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + ") LIKE ?";
        String[] selectionArgs = {nameToSearch.toUpperCase() + "%"};  // Пошук по імені, не враховуючи регістр

        Cursor cursor = requireContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                phoneProjection,
                selection,
                selectionArgs,
                null
        );

        if (cursor != null) {
            try {
                int contactIdIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                int displayNameIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int phoneNumberIndex = cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);

                while (cursor.moveToNext()) {
                    String contactId = cursor.getString(contactIdIndex);  // Отримання ID контакту
                    String contactName = cursor.getString(displayNameIndex);
                    String contactNumber = cursor.getString(phoneNumberIndex);

                    // Отримуємо адресу контакту
                    String contactAddress = getContactAddress(contactId);

                    // Додаємо контакт з адресою
                    contacts.add(new Contact(contactName, " ", contactNumber, contactAddress));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

        return contacts;
    }

    // Метод для отримання адреси контакту за його ID
    private String getContactAddress(String contactId) {
        String address = null;
        String[] addressProjection = {
                ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
        };

        String addressSelection = ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?";
        String[] addressSelectionArgs = {contactId};

        Cursor addressCursor = requireContext().getContentResolver().query(
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
                addressProjection,
                addressSelection,
                addressSelectionArgs,
                null
        );

        if (addressCursor != null) {
            try {
                if (addressCursor.moveToFirst()) {  // Перевіряємо, чи є адреса у контакту
                    int addressIndex = addressCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS);
                    address = addressCursor.getString(addressIndex);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                addressCursor.close();
            }
        }

        return address;
    }
}
