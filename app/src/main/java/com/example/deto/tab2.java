
package com.example.deto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static com.example.deto.MyBoundService.SERVICE_TASK_RESULT_COMPLETE;
import static com.example.deto.MyBoundService.EKSTRA_KEY_BROADCAST_NAME_RESULT;

public class tab2 extends Fragment {
    private static final String TAG = "Tab2";
    public static final String EXTRA_NAME_SURNAME = "EXTRA_Name_Surname";
    private Spinner spinner;
    private  String SelectedName;
    ListView listViewPatientData;
    TextView textViewPatientName;

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
        listViewPatientData = view.findViewById(R.id.listViewPatientData);
        textViewPatientName = view.findViewById(R.id.textviewPatientName);
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
                    SelectName(nameresult);
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
             if(parent.getItemAtPosition(position).equals("Select a Name")) { //Do nothing
             }
             else{
                 String str = parent.getItemAtPosition(position).toString();
                 Toast.makeText(getActivity(), "Selected " + str, Toast.LENGTH_LONG).show();
                 SelectedName = str.split(" ")[0];
                 textViewPatientName.setText(str);
                 getPatientData();
             }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getActivity(), "Please Enter Data Value", Toast.LENGTH_LONG).show();

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
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String date = jo.getString(Config.KEY_Date_p);
                String nitritvalue = jo.getString(Config.KEY_Nitritvalue_p);

                final HashMap<String, String> patientData = new HashMap<>();
                patientData.put(Config.KEY_Date_p, date);
                patientData.put(Config.KEY_Nitritvalue_p, nitritvalue);
                list.add(patientData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<HashMap<String, String>>  itemsAdapter =
                new ArrayAdapter<HashMap<String, String>> (getContext(), android.R.layout.simple_list_item_1, (list));
        listViewPatientData.setAdapter(itemsAdapter);

    }

}
