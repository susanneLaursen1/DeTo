package com.example.deto;
import android.app.Notification;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.deto.ForegroundWorker.CHANNEL_ID;

public class ServiceForeground extends Service {
    private final IBinder iBinder = new LocalBinder();
    public class LocalBinder extends Binder {
        ServiceForeground getservice (){
            return ServiceForeground.this;
        }
    }
    int sleepTime; //sleeptime in milliseconds
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        Intent notificationIntent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();
        sleepTime = intent.getIntExtra("EKSTRA_KEY_SLEEPTIME", 3000);
        return START_STICKY;

        //Tidsfaktor Hver time eller lign.
    }
    @Override
    public void onDestroy() {

        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return iBinder;
    }

    public void RetrieveData() {
        String url = Config.DATA_URL;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(Config. KEY_Name);
                String surname = jo.getString(Config. KEY_Surname);
                String date = jo.getString(Config.KEY_Date);
                String nitritvalue = jo.getString(Config.KEY_Nitritvalue);

                final HashMap<String, String> employees = new HashMap<>();
                employees.put(Config.KEY_Name,  name);
                employees.put(Config.KEY_Surname, surname);
                employees.put(Config.KEY_Date, date);
                employees.put(Config.KEY_Nitritvalue, nitritvalue);

                list.add(employees);


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

