package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private int counter = 5;
    DatabaseHelper  myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        Name = (EditText)findViewById(R.id.editTextTextPassword);
        Password = (EditText)findViewById(R.id.editTextTextPassword2);
        Login = (Button)findViewById(R.id.loginBT);

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
            if(counter == 0){
                Login.setEnabled(false);
            }

        }
     }


}