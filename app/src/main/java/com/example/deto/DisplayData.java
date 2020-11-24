package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import static com.example.deto.tab2.EXTRA_NAME_SURNAME;

public class DisplayData extends AppCompatActivity {
    private static final String TAG = "DisplayData";
    private EditText NameSurename;
    private ListView listAllItems;
    private String Name;
    private String Surname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        listAllItems = findViewById(R.id.listViewAllItems);

        NameSurename = findViewById(R.id.PersonName);
        String Navnvalgt = getIntent().getStringExtra(EXTRA_NAME_SURNAME);
        Name = Navnvalgt.split(" ")[0];
        Surname = Navnvalgt.split(" ")[1];

        NameSurename.setText(Navnvalgt);

    }
}