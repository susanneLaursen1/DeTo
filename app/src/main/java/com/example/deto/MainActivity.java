package com.example.deto;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText Name, Password;
    private Button Login, Unbind;
    private TextView Info, Warning;
    private int counter = 5;
    private ServiceConnection serviceConnection;
    private MyBoundService boundService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        creatServiceConnection();
    }

    private void setupUI() {
        Name = (EditText)findViewById(R.id.editTextTextPassword);
        Password = (EditText)findViewById(R.id.editTextTextPassword2);
        Login = (Button)findViewById(R.id.loginBT);
        Unbind = (Button)findViewById(R.id.btnUnBindService);

        Info = (TextView)findViewById(R.id.textViewInfo);
        Warning = (TextView)findViewById(R.id.textWarning);
        Info.setText("Number of attemts remaning: 5");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        Unbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService();
            }
        });
    }
    private void validate(String UserName, String UserPassword){
        if((UserName.equals("Deto")) && (UserPassword.equals("2020"))){
            bindService();

            Intent i = new Intent(MainActivity.this,SecondActivity.class);
            startActivity(i);
        } else{
           counter--;
            Info.setText("Number of attemts remaning:" + counter);

            if(counter == 0){
                Login.setEnabled(false);
                Warning.setText("You have no more attempts left. \n Please try to log in from a computer");
            }
        }
     }
    //BoundService
    private void creatServiceConnection(){
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                Log.d(TAG, "onServiceConnected: ");
                boundService = ((MyBoundService.BoundServiceBinder)iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: ");

            }
        };
    }
    private void bindService(){
        Intent boundServiceIntent = new Intent(this, MyBoundService.class);
        bindService(boundServiceIntent,serviceConnection, BIND_AUTO_CREATE);
    }

    private void unbindService(){
        if(serviceConnection!=null) {
            unbindService(serviceConnection);
        }
    }
}