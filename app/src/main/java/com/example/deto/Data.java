package com.example.deto;

import android.widget.TextView;

import java.util.List;

public class Data {
    String name;
    String surname;
    String date;
    double nitrite;
    TextView textView;
    String text = "";

    public Data(int i, String string, String cursorString) {
        textView = (TextView) findViewById(R.id.textView);
        DatabaseHelper db = new DatabaseHelper(this);
        db.addData(new Data("name1", "1111"));
        db.addData(new Data("name2", "2222"));
        db.addData(new Data("name3", "3333"));
        db.addData(new Data("name4", "4444"));

        List<Data> data = db.getAllData();

        for(Data d : data){
            String log = " NAME: " + d.getName() + ", SURNAME:"+ d.getSurname() + "DATE:" + d.getDate() + "NITRITVALUE:" + d.getNitrite() + "\n";
            text = text +log;
        }
        textView.setText(text);
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



