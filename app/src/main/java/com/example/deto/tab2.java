package com.example.deto;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SimpleAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText txtvalue;
    Button btnfetch;
    ListView listview;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        txtvalue = view.findViewById(R.id.editText);
        btnfetch = view.findViewById(R.id.buttonfetch);
        listview = view.findViewById(R.id.listView);



        btnfetch.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                RetrieveData();
            }
        });
        return view;

    }
    private void RetrieveData() {

        String value = txtvalue.getText().toString().trim();

        if (value.equals("")) {
            Toast.makeText(getActivity(), "Please Enter Data Value", Toast.LENGTH_LONG).show();
            return;
        }

        String url = Config.DATA_URL + txtvalue.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
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

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                //String name = jo.getString(Config.KEY_Name);
                String surname = jo.getString(Config. KEY_Surname);
                String date = jo.getString(Config.KEY_Date);
                String nitritvalue = jo.getString(Config.KEY_Nitritvalue);



                final HashMap<String, String> employees = new HashMap<>();
                //employees.put(Config.KEY_Name,  name);
                employees.put(Config.KEY_Surname, surname);
                employees.put(Config.KEY_Date, date);
                employees.put(Config.KEY_Nitritvalue, nitritvalue);

                list.add(employees);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list, R.layout.activity_mylist,
                new String[]{Config.KEY_Surname, Config.KEY_Date, Config.KEY_Nitritvalue},
                new int[]{ R.id.editTextsurname, R.id.editTextdate, R.id.editTextnitrit});

        listview.setAdapter(adapter);
    }
}


