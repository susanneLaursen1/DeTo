package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private TextView Warning;
    private int counter = 5;
    TextView textView;
    String text = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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




        Name = (EditText)findViewById(R.id.editTextTextPassword);
        Password = (EditText)findViewById(R.id.editTextTextPassword2);
        Login = (Button)findViewById(R.id.loginBT);
        Info = (TextView)findViewById(R.id.textViewInfo);
        Warning = (TextView)findViewById(R.id.textWarning);

        Info.setText("Number of attemts remaning: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              validate(Name.getText().toString(),Password.getText().toString());
            }
        });
    }
    private void validate(String UserName, String UserPassword){
        if((UserName.equals("Deto")) && (UserPassword.equals("2020"))){
            Intent i = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(i);
        } else{
           counter--;
            Info.setText("Number of attemts remaning:" + String.valueOf(counter));

            if(counter == 0){
                Login.setEnabled(false);
                Warning.setText("You have no more attempts left. \n Please try to log in from a computer");
            }

        }
     }


}