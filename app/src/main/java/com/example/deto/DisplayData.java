package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.HashSet;


public class DisplayData extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    //Declaring an Spinner
    private Spinner spinner;

    //An ArrayList for Spinner Items
    private ArrayList<String> students;

    //JSON Array
    private JSONArray result;

    //TextViews to display details
    private TextView textViewName;

    private TextView textViewSurname;
    private TextView textViewDate;
    private TextView textViewNitritvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);
        students = new ArrayList<String>();


        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.spinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener
        spinner.setOnItemSelectedListener(this);

        //Initializing TextViews
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewSurname = (TextView) findViewById(R.id.textViewSurname);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewNitritvalue = (TextView) findViewById(R.id.textViewNitritvalue);

        //This method will fetch the data from the URL
        getData();
        }

    private void getData(){
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getStudents(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }}
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getStudents(JSONArray j) {

        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                students.add(json.getString(Config.KEY_Name));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        //Setting
        // adapter to show the items in the spinner

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, students));
    }
    //

    //Method to get student name of a particular position
    private String getName(int position){
        String name="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            name = json.getString(Config.KEY_Name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return name;
    }

    //Method to get student name of a particular position
    private String getSurname(int position){
        String surname="";
        try {
            //Getting object of given index
            JSONObject json = result.getJSONObject(position);

            //Fetching name from that object
            surname = json.getString(Config.KEY_Surname);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the name
        return surname;
    }
    //Doing the same with this method as we did with getName()
    private String getDate(int position){
        String date="";
        try {
            JSONObject json = result.getJSONObject(position);
            date = json.getString(Config.KEY_Date);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return date;
    }

    //Doing the same with this method as we did with getName()
    private String getNitritvalue(int position){
        String nitritvalue="";
        try {
            JSONObject json = result.getJSONObject(position);
            nitritvalue = json.getString(Config.KEY_Nitritvalue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return nitritvalue;
    }


    //this method will execute when we pic an item from the spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Setting the values to textviews for a selected item
        textViewName.setText(getName(position));
        textViewSurname.setText(getSurname(position));
        textViewDate.setText(getDate(position));
        textViewNitritvalue.setText(getNitritvalue(position));
    }

    //When no item is selected this method would execute
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textViewName.setText("");
        textViewSurname.setText("");
        textViewDate.setText("");
        textViewNitritvalue.setText("");
    }
}
