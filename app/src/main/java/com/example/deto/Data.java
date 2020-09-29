package com.example.deto;

public class Data {
    String name;
    String surname;
    String date;
    double nitrite;

    public Data(int i, String string, String cursorString) {

    }

    public Data(String name1, String s) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.nitrite = nitrite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getNitrite() {
        return nitrite;
    }

    public void setNitrite(double nitrite) {
        this.nitrite = nitrite;
    }

}



