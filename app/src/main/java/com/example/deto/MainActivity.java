package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter = 5;
    private TextView Warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.editTextTextPassword);
        Password = (EditText)findViewById(R.id.editTextTextPassword2);
        Login = (Button)findViewById(R.id.loginBT);
        Info = (TextView)findViewById(R.id.textViewInfo);
        Warning = (TextView)findViewById(R.id.textWarning);

        Info.setText("Number of attempts remaining: 5");

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
           Info.setText("Number of attempts remaining: " + String.valueOf(counter));
            if(counter == 0){
                Login.setEnabled(false);
                Warning.setText("Try connecting through a computer. You may need to renew your login.");
               // Toast t = Toast.makeText(MainActivity.this, "Try connecting through a computer. You may need to renew your login.",
                 //       Toast.LENGTH_LONG);
                //t.setGravity(0,0,0);
                //t.show();
            }

        }
     }


}