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
    private String Username = "Deto";
    private String Userpassword = "2020";
    boolean isValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.editTextTextPassword);
        Password = (EditText)findViewById(R.id.editTextTextPassword2);
        Login = (Button)findViewById(R.id.loginBT);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = Name.getText().toString();
                String inputPassword = Password.getText().toString();

                if(inputName.isEmpty() || inputPassword.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please inter all the details correctly",Toast.LENGTH_SHORT).show();

                }else{
                    isValid = validate(inputName,inputPassword);
                    if(!isValid){

                        counter--;
                        Toast.makeText(MainActivity.this,"Incorrectly credentials entered! ",Toast.LENGTH_SHORT).show();

                        if(counter == 0){
                            Login.setEnabled(false);
                        }else{
                            Toast.makeText(MainActivity.this,"LogIn Successful",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                            startActivity(intent);

                        }

                    }


                }

            }
        });
    }
    private boolean validate(String name, String password){
        if(name.equals(Username) && password.equals(Userpassword)){
            return true;
        }
        return false;
    }

}