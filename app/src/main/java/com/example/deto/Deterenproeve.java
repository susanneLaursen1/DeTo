package com.example.deto;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Deterenproeve extends AppCompatActivity {
    private EditText editTextInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_worker);
        editTextInput = findViewById(R.id.edit_text_input);
    }
    public void startService(View v) {
        String input = editTextInput.getText().toString();
        Intent serviceIntent = new Intent(this, ServiceForeground.class);
        serviceIntent.putExtra("inputExtra", input);
        ContextCompat.startForegroundService(this, serviceIntent);
    }
    public void stopService(View v) {
        Intent serviceIntent = new Intent(this, ServiceForeground.class);
        stopService(serviceIntent);

    }
}

