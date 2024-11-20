package com.mobileapp.mobilelaba2.ui.contacts;

public class Contact {
    private final String name;
    private final String surname;
    private final String number;
    private final String address;

    public Contact(String name, String surname, String number, String address) {
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }
}
