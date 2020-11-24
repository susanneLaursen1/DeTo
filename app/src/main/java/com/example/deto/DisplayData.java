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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;

import static com.example.deto.MyBoundService.EKSTRA_KEY_BROADCAST_RESULT;
import static com.example.deto.MyBoundService.SERVICE_TASK_RESULT_COMPLETE;
import static com.example.deto.tab2.EXTRA_NAME_SURNAME;

public class DisplayData extends AppCompatActivity {
    private static final String TAG = "DisplayData";
    private EditText NameSurename;
    private ListView listAllItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        listAllItems = findViewById(R.id.listViewAllItems);

        NameSurename = findViewById(R.id.PersonName);
        String Navnvalgt = getIntent().getStringExtra(EXTRA_NAME_SURNAME);

        String Name = Navnvalgt.substring(0);
        NameSurename.setText(Name);
        Log.d(TAG, "onCreate: " + Name);

    }


    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SERVICE_TASK_RESULT_COMPLETE);
        LocalBroadcastManager.getInstance(this).registerReceiver(backgroundServiceResulstReceiver, filter);
    }
    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(backgroundServiceResulstReceiver);
    }
    //Broadcast
    private BroadcastReceiver backgroundServiceResulstReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent broadcatedResult) {
            if(broadcatedResult!=null){
                ArrayList result = broadcatedResult.getStringArrayListExtra(EKSTRA_KEY_BROADCAST_RESULT);
                if(result!=null){
                    ViewData(result);
                }
            }
        }

        private void ViewData(ArrayList result) {
            ListAdapter adapter = new SimpleAdapter(
                    DisplayData.this, result, R.layout.activity_mylist,
                    new String[]{Config.KEY_Name, Config.KEY_Surname,Config.KEY_Date,Config.KEY_Nitritvalue},
                    new int[]{R.id.e_name,R.id.e_surname,R.id.e_date,R.id.e_nitritvalue});
            listAllItems.setAdapter(adapter);
        }
    };
}