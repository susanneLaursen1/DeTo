package com.example.deto;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

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

public class MyService extends Service {
    private final IBinder iBinder = new LocalBinder();
    private List<HashMap<String, String>> list;


    public class LocalBinder extends Binder {
        MyService getservice (){
            return MyService.this;
        }
    }
    public MyService() {
    }

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
        list = new ArrayList<HashMap<String, String>>();
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
    public List<HashMap<String, String>> getList() {
       return list;
    }
}

