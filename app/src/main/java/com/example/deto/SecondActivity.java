package com.example.deto;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SecondActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TabItem tab1, tab2, tab3;
    public PageAdapter pagerAdapter;

    private String ServerURL = "https://deto-system.000webhostapp.com/InsertData/add_data.php";
    private EditText name, surname, date, nitritvalue;
    private String TempName, TempSurname, TempDate, TempNitrit ;
    private Button insertdata;
    EditText txtvalue;
    Button btnfetch;
    ListView listview;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        name = (EditText) findViewById(R.id.editTextname);
        surname = (EditText) findViewById(R.id.editTextsurname);
        date = (EditText) findViewById(R.id.editTextdate);
        nitritvalue = (EditText) findViewById(R.id.editTextnitrit);
        insertdata = (Button) findViewById(R.id.buttoninsertdata);
        txtvalue = (EditText)findViewById(R.id.editText);
        btnfetch = (Button)findViewById(R.id.buttonfetch);
        listview = (ListView)findViewById(R.id.listView);

        setContentView(R.layout.activity_second);
        txtvalue = (EditText)findViewById(R.id.editText);
        btnfetch = (Button)findViewById(R.id.buttonfetch);
        listview = (ListView)findViewById(R.id.listView);
        btnfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrieveData();

            }
        });

        insertdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                GetData();

                InsertData(TempName, TempSurname, TempDate, TempNitrit);

            }
        });
    }
    private void RetrieveData() {

        String value = txtvalue.getText().toString().trim();

        if (value.equals("")) {
            Toast.makeText(this, "Please Enter Data Value", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(SecondActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String NAME = jo.getString(Config.KEY_Name);
                String  SURNAME= jo.getString(Config.KEY_Surname);
                String DATE = jo.getString(Config.KEY_Date);
                String NITRITVALUE = jo.getString(Config.KEY_Nitritvalue);



                final HashMap<String, String> employees = new HashMap<>();
                employees.put(Config.KEY_Name,  "Name = "+NAME);
                employees.put(Config.KEY_Surname, SURNAME);
                employees.put(Config.KEY_Date, DATE);
                employees.put(Config.KEY_Nitritvalue, NITRITVALUE);

                list.add(employees);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                SecondActivity.this, list, R.layout.activity_mylist,
                new String[]{Config.KEY_Name, Config.KEY_Surname, Config.KEY_Date, Config.KEY_Nitritvalue},
                new int[]{R.id.title, R.id.date, R.id.data, R.id.tvid});

        listview.setAdapter(adapter);

    }

    public void GetData(){
        TempName = name.getText().toString();
        TempSurname = surname.getText().toString();
        TempDate = date.getText().toString();
        TempNitrit = nitritvalue.getText().toString();
        if (TempName.equals("") || TempSurname.equals("") || TempDate.equals("") || TempNitrit.equals("")) {
            Toast.makeText(SecondActivity.this, "Please Enter Detail", Toast.LENGTH_SHORT).show();
        }
    }

    public void InsertData(final String name, final String surname, final String date, final String nitritvalue){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = name ;
                String SurnameHolder = surname ;
                String DateHolder = date;
                String NitritHolder = nitritvalue;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("Name", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("Surname", SurnameHolder));
                nameValuePairs.add(new BasicNameValuePair("Date", DateHolder));
                nameValuePairs.add(new BasicNameValuePair("Nitritvalue", NitritHolder));

                try {

                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }
            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(SecondActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name, surname, date, nitritvalue);
    }



            @Override //sætter menuen til at åbne den rigtige fane af alarm, graf eller vedligehold
            public boolean onOptionsItemSelected (@NonNull MenuItem item){
                switch (item.getItemId()) {

                    case R.id.item2:
                        Intent i = new Intent(SecondActivity.this, Alarm.class);
                        startActivity(i);
                        return true;
                    case R.id.item3:
                        Intent ii = new Intent(SecondActivity.this, GrafiskOversigt.class);
                        startActivity(ii);
                        return true;
                    case R.id.item4:
                        Intent iii = new Intent(SecondActivity.this, KalibreringOgVedligehold.class);
                        startActivity(iii);
                        return true;
                    default:
                        return super.onOptionsItemSelected(item);
                }

            }
        }

