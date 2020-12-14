
package com.example.deto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.PointsGraphSeries;
import com.jjoe64.graphview.series.Series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.deto.MyBoundService.EKSTRA_KEY_BROADCAST_NAME_RESULT;
import static com.example.deto.MyBoundService.SERVICE_TASK_RESULT_COMPLETE;

public class tab2 extends Fragment {
    private static final String TAG = "Tab2";
    public static final String EXTRA_NAME_SURNAME = "EXTRA_Name_Surname";
    private Spinner spinner;
    private  String SelectedName;
    TextView textViewPatientName;
    GraphView graph;
    PointsGraphSeries<DataPoint> series;
    private JSONArray result;

    //TextViews to display details
    private TextView textViewCourse;
    private TextView textViewSession;



    SimpleDateFormat sdfToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat sdfToString = new SimpleDateFormat("dd-MM HH:mm");

    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        textViewPatientName = view.findViewById(R.id.textviewPatientName);
        graph = view.findViewById(R.id.graph);
        // first series is a line



        //Initializing TextViews

        textViewCourse =view.findViewById(R.id.textViewCourse);
        textViewSession = view.findViewById(R.id.textViewSession);
        return view;

    }

    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction(SERVICE_TASK_RESULT_COMPLETE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(backgroundServiceResulstReceiver, filter);
    }
    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(backgroundServiceResulstReceiver);
    }

    //Broadcast
    private BroadcastReceiver backgroundServiceResulstReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent broadcatedResult) {
            if(broadcatedResult!=null){
                ArrayList nameresult = broadcatedResult.getStringArrayListExtra(EKSTRA_KEY_BROADCAST_NAME_RESULT);
                if(nameresult!=null){
                    ArrayList result = new ArrayList();
                    // Loop over argument list.
                    for (Object item : nameresult) {
                        if (!result.contains(item)) {
                            result.add(item);
                        }
                        SelectName(result);
                    }
                }

            }
        }
    };

    public void SelectName(ArrayList res){
        ArrayAdapter<ArrayList> arrayadapter = new ArrayAdapter<ArrayList>(
                getActivity(),
                android.R.layout.simple_spinner_item, res);
        arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayadapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

             if(parent.getItemAtPosition(position).equals("Vælg en borger")) { //Do nothing
             }
             else{
                 String str = parent.getItemAtPosition(position).toString();
                 Toast.makeText(getActivity(), "Selected " + str, Toast.LENGTH_LONG).show();
                 SelectedName = str.split(" ")[0];
                 textViewPatientName.setText(str);
                 //textViewCourse.setText(getNitrit(position));
                // textViewSession.setText(getDate(position));
                // getDate(position);
                // getNitrit(position);


                 getPatientData();

             }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please Enter Data Value", Toast.LENGTH_LONG).show();
                textViewCourse.setText("");
                textViewSession.setText("");


            }
        });
    }


    private void getPatientData() {
        String url = Config.DATA_URL_PatientData + SelectedName;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showPatientData(response);

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void showPatientData(String response) {
        ArrayList<Date> x_axis=new ArrayList<Date>();
        ArrayList<Double> y_axis=new ArrayList<Double>();
        ArrayList<String> datelist=new ArrayList<String>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String date = jo.getString(Config.KEY_Date_p);
                datelist.add(date);
                StringBuilder builders = new StringBuilder();
                for (String datee : datelist) {
                    builders.append(datee + "\n");
                }
                textViewCourse.setLines(10);
                textViewCourse.setText(builders.toString());
                textViewCourse.setMovementMethod(new ScrollingMovementMethod());

                Date x_date_D = null;
                try {
                    x_date_D = sdfToDate.parse(date);
                    x_axis.add(x_date_D);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String nitritvalue = jo.getString(Config.KEY_Nitritvalue_p);
                Double y_nitritValueD = Double.valueOf(nitritvalue);
                y_axis.add(y_nitritValueD);

                StringBuilder builder = new StringBuilder();
                for (Double details : y_axis) {
                    builder.append(details + "\n");
                }
                textViewSession.setLines(10);
                textViewSession.setText(builder.toString());
                textViewSession.setMovementMethod(new ScrollingMovementMethod());


                for(int k=0; k <x_axis.size();k++)
                {
                    series= new PointsGraphSeries<>(new DataPoint[]{
                            new DataPoint(x_axis.get(k), y_axis.get(k))
                    });



                    graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

                    GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
                    // set manual X bounds

                    graph.addSeries(series);

                  //  gridLabel.setHorizontalLabelsAngle(90);
                    gridLabel.setHorizontalAxisTitle("Dato");
                    gridLabel.setVerticalAxisTitle("Nitritværdi (ppm)");



                    series.setOnDataPointTapListener(new OnDataPointTapListener()
                    {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            Date d = new java.sql.Date((long) dataPoint.getX());
                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM HH:mm");
                            String formatted = format1.format(d.getTime());
                            Toast.makeText(getContext(), "X:" + formatted +"\nY:"+ dataPoint.getY()+ "ppm", Toast.LENGTH_SHORT).show();
                        }
                    });


                  //    textViewCourse.setText(x_axis);

                  //  textViewSession.setText((CharSequence) y_axis);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}