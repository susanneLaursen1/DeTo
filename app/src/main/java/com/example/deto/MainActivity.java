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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

            Intent j = new Intent(MainActivity.this,SyncMainActivity.class);
            startActivity(j);
        } else{
           counter--;
            Info.setText("Number of attemts remaning:" + counter);

            if(counter == 0){
                Login.setEnabled(false);
                Warning.setText("You have no more attempts left. \n Please try to log in from a computer");
            }

        }
     }


}