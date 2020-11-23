package com.example.deto;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class tab2 extends Fragment{

    Button btnvisgraf;
    Button btnvisgraf1;
    private Spinner spinner;

    //An ArrayList for Spinner Items
    public ArrayList<String> students;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static tab2 newInstance(String param1, String param2) {
        tab2 fragment = new tab2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        // Inflate the layout for this fragment
        btnvisgraf = view.findViewById(R.id.VisGraf);
        btnvisgraf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),DisplayData.class);
                startActivity(i);
            }
        });
        btnvisgraf1 = view.findViewById(R.id.VisGraf1);
        btnvisgraf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Graph.class);
                startActivity(i);
            }
        });

        return view;

    }

    }