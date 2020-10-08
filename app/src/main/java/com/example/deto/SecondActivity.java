package com.example.deto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;


public class SecondActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewpager;
    private TabItem tab1, tab2, tab3;
    public PageAdapter pagerAdapter;
    EditText name, surname, date, nitritvalue;
    Button insertdata;
    ProgressDialog mProgressDialog;
    //private static final String INSERTDATA_URL = "https://deto-system.000webhostapp.com/InsertData/add_data.php";
    String ServerURL = "https://deto-system.000webhostapp.com/InsertData/add_data.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        name = (EditText) findViewById(R.id.editTextname);
        surname = (EditText) findViewById(R.id.editTextsurname);
        date = (EditText) findViewById(R.id.editTextdate);
        nitritvalue = (EditText) findViewById(R.id.editTextnitrit);
        insertdata = (Button) findViewById(R.id.buttoninsertdata);


        insertdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString().trim().toLowerCase();
                String Surname = surname.getText().toString().trim().toLowerCase();
                String Date = date.getText().toString().trim().toLowerCase();
                String Nitritvalue = nitritvalue.getText().toString().trim().toLowerCase();

                if (Name.equals("")||Surname.equals("")||Date.equals("")||Nitritvalue.equals("")){
                    Toast.makeText(SecondActivity.this, "Please Enter Detail", Toast.LENGTH_SHORT).show();
                }
                InsertData();

            }
        });

    }

    private void InsertData() {

        String Name = name.getText().toString().trim().toLowerCase();
        String Surname = surname.getText().toString().trim().toLowerCase();
        String Date = date.getText().toString().trim().toLowerCase();
        String Nitritvalue = nitritvalue.getText().toString().trim().toUpperCase();

        if (Name.equals("") || Surname.equals("") || Date.equals("") || Nitritvalue.equals("")) {
            Toast.makeText(SecondActivity.this, "Please Enter Detail", Toast.LENGTH_SHORT).show();
        } else {


            register(Name, Surname, Date, Nitritvalue);
        }
    }

    private void register(String Name, String Surname, String Date, String Nitritvalue) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClasses ruc = new RegisterUserClasses();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressDialog = new ProgressDialog(SecondActivity.this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setProgress(0);
                mProgressDialog.setProgressNumberFormat(null);
                mProgressDialog.setProgressPercentFormat(null);
                mProgressDialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SecondActivity.this, SecondActivity.class);
                startActivity(intent);
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("Name", params[0]);
                data.put("Surname", params[1]);
                data.put("Date", params[2]);
                data.put("Nitritvalue", params[3]);


                String result = ruc.sendPostRequest(ServerURL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(Name, Surname, Date, Nitritvalue);
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

