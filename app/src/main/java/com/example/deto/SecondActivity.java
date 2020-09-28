package com.example.deto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override //sætter menuen til at åbne den rigtige fane af alarm, graf eller vedligehold
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item2:
                Intent i = new Intent(SecondActivity.this, Alarm.class);
            startActivity(i);
            return true;
            case R.id.item3:
                Intent ii = new Intent(SecondActivity.this, GrafiskOversigt.class);
                startActivity(ii);
                return true;
            case R.id.item4:
                Intent iii = new Intent(SecondActivity.this, KalibreringOgVedligehold.class);
                startActivity(iii);
                return true;
            default:return super.onOptionsItemSelected(item);
        }

    }
}