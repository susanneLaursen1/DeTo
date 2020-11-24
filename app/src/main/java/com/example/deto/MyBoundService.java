package com.example.deto;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.IpSecManager;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import java.util.List;
import java.util.jar.Attributes;

public class MyBoundService extends Service {
    private static final String TAG = "MyBoundService";
    private static final String Channel_Id = "Simplified_coding";
    private static final String Channel_Name = "Simplified Coding";
    private static final String Channel_Desc = "Simplified Coding Notification";

    public static final String SERVICE_TASK_RESULT_COMPLETE = "Service_Task_Result_Complete";
    public static final String EKSTRA_KEY_BROADCAST_RESULT = "EKSTRA_KEY_BROADCAST_RESULT";
    public static final String EKSTRA_KEY_BROADCAST_MESSAGE = "EKSTRA_KEY_BROADCAST_MESSAGE";
    public static final String SERVICE_TASK_MESSAGE_COMPLETE = "Notification_Message_Complete";

    public static final String EKSTRA_KEY_BROADCAST_NAME_RESULT ="EKSTRA_KEY_BROADCAST_NAME_RESULT" ;


    private String Name;
    private String Surname;
    private String Date;
    private Double Nitritvalue;

    String Context;
    private String Title;

    ArrayList<HashMap<String, String>> list;
    ArrayList<HashMap<String, String>> namelist;

    ArrayList<String> contextList;

    public class BoundServiceBinder extends Binder{
        MyBoundService getService(){
            return MyBoundService.this;
        }
    }

    private final BoundServiceBinder binder = new BoundServiceBinder();
    boolean running;


    public MyBoundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        list = null;
        namelist = null;
        contextList = null;
        running = true;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(running){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getData();

                }
            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(Channel_Id,Channel_Name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(Channel_Desc);
            NotificationManager manger = MyBoundService.this.getSystemService(NotificationManager.class);
            manger.createNotificationChannel(channel);
        }

    }
    private void getData() {
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

        RequestQueue requestQueue = Volley.newRequestQueue(MyBoundService.this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        list = new ArrayList<HashMap<String, String>>();
        namelist  = new ArrayList<HashMap<String, String>>();
        contextList = new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String name = jo.getString(Config. KEY_Name);
                String surname = jo.getString(Config. KEY_Surname);
                String date = jo.getString(Config.KEY_Date);
                String nitritvalue = jo.getString(Config.KEY_Nitritvalue);

                final HashMap<String, String> names = new HashMap<>();
                names.put(Config.KEY_Name,  name);
                names.put(Config.KEY_Surname, surname);
                namelist.add(names);

                final HashMap<String, String> employees = new HashMap<>();
                employees.put(Config.KEY_Name,  name);
                employees.put(Config.KEY_Surname, surname);
                employees.put(Config.KEY_Date, date);
                employees.put(Config.KEY_Nitritvalue, nitritvalue);

                list.add(employees);
                sendTaskResultAsBroadcast(list, namelist);

                Nitritvalue = Double.valueOf(nitritvalue);
                if(Nitritvalue >= 15){

                    Name = name;
                    Surname = surname;
                    Date = date;
                    Title = "Borger " + Name + " " +Surname + " har tegn på urinvejsinfektion";
                    Context = "Det blev d." + Date + " detekteret at " + Name + " " +Surname + " har en nitritværdi på "+ Nitritvalue;
                    contextList.add(Context);
                    Log.d(TAG, "showJSON: " + contextList.size());
                    sendMessageAsBroadcast(contextList);
                    displayNotification();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyBoundService.this,Channel_Id)
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle(Title)
                .setContentText(Context)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setOngoing(true);

        NotificationManagerCompat mNotificationMrg = NotificationManagerCompat.from(MyBoundService.this);
        mNotificationMrg.notify(1,mBuilder.build());

    }
    private void sendTaskResultAsBroadcast(ArrayList result, ArrayList nameresult){
        Intent broadcastintent = new Intent();
        broadcastintent.setAction(SERVICE_TASK_RESULT_COMPLETE);
        broadcastintent.putExtra(EKSTRA_KEY_BROADCAST_RESULT, result).putExtra(EKSTRA_KEY_BROADCAST_NAME_RESULT, nameresult);
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastintent);
    }

    private void sendMessageAsBroadcast(ArrayList message){
        Intent mbroadcastintent = new Intent();
        mbroadcastintent.setAction(SERVICE_TASK_MESSAGE_COMPLETE);
        mbroadcastintent.putExtra(EKSTRA_KEY_BROADCAST_MESSAGE, message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(mbroadcastintent);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    @Override
    public void onDestroy() {
        running = false;
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

}
