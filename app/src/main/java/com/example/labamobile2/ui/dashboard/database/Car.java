package com.example.labamobile2.ui.dashboard.database;

public class StudentCourses {
    private int id;
    private String pib;
    private String name;
    private String grade1;
    private String grade2;
    private String address;

    public String getStudentAVGGrade() {
        final double grade1 = Double.parseDouble(this.getGrade1());
        final double grade2 = Double.parseDouble(this.getGrade2());
        return String.valueOf((grade1 + grade2) / 2);
    }

    public StudentCourses() {
    }

    public StudentCourses(String pib, String name, String grade1, String grade2, String address) {
        this.pib = pib;
        this.name = name;
        this.grade1 = grade1;
        this.grade2 = grade2;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade1() {
        return grade1;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
