package com.example.deto;

import android.app.ProgressDialog;

import android.graphics.Color;



import android.os.Bundle;

import android.util.Log;

import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;

import com.android.volley.Response;

import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;

import com.github.mikephil.charting.charts.LineChart;

import com.github.mikephil.charting.data.LineData;

import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;

import org.json.JSONException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class Graph extends AppCompatActivity {

    private ProgressDialog pd;


    ArrayList yAxis;
    ArrayList yValues;
    ArrayList xAxis1;
    Entry values;
    LineChart linechart;


    LineData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        pd = new ProgressDialog(Graph.this);
        pd.setMessage("loading");


        //Log.d("array", Arrays.toString(fullData));
        linechart = (LineChart) findViewById(R.id.linechart);
        load_data_from_server();

    }

    public void load_data_from_server() {
        pd.show();
        String url = "https://deto-system.000webhostapp.com/InsertData/graph.php";
        xAxis1 = new ArrayList();
        yAxis = null;
        yValues = new ArrayList();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {

                    }

                    public void onResponse(String response) {
                        Log.d("string", response);

                        try {

                            JSONArray jsonarray = new JSONArray(response);

                            for (int i = 0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);


                                String score = jsonobject.getString("Date");
                                String name = jsonobject.getString("Nitritvalue");

                                xAxis1.add(name);

                                values = new Entry(Float.valueOf(score), i);
                                yValues.add(values);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }

                        LineDataSet lineDataSet1 = new LineDataSet(yValues, "Goals LaLiga 16/17");
                        lineDataSet1.setColor(Color.rgb(0, 82, 159));

                        yAxis = new ArrayList();
                        yAxis.add(lineDataSet1);
                        String names[] = (String[]) xAxis1.toArray(new String[xAxis1.size()]);
                        data = new LineData(names, yAxis);
                        linechart.setData(data);
                        linechart.setDescription("");
                        linechart.animateXY(2000, 2000);
                        linechart.invalidate();
                        pd.hide();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {

                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                            pd.hide();
                        }
                    }
                }

        );

        Singleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
}