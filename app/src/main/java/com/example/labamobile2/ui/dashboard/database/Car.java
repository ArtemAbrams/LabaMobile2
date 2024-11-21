package com.example.labamobile2.ui.dashboard.database;

public class Car {
    private int id; // Унікальний ідентифікатор
    private String brand; // Бренд автомобіля
    private String bodyType; // Тип кузова (седан, універсал тощо)
    private String color; // Колір
    private double engineVolume; // Об'єм двигуна
    private double price; // Ціна

    // Конструктор за замовчуванням
    public Car() {
    }

    // Конструктор з параметрами
    public Car(String brand, String bodyType, String color, double engineVolume, double price) {
        this.brand = brand;
        this.bodyType = bodyType;
        this.color = color;
        this.engineVolume = engineVolume;
        this.price = price;
    }

    // Геттери та сеттери для всіх полів
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getEngineVolume() {
        return engineVolume;
    }

    public void setEngineVolume(double engineVolume) {
        this.engineVolume = engineVolume;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Метод для повернення об'єму двигуна у зручному форматі
    public String getFormattedEngineVolume() {
        return String.format("%.1f л", this.engineVolume);
    }
}
