package com.example.deto;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class tab1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String ServerURL = "https://deto-system.000webhostapp.com/InsertData/add_data.php";
    public EditText name, surname, date, nitritvalue;
    private String TempName, TempSurname, TempDate, TempNitrit ;
    private Button insertdata;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
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
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        name = view.findViewById(R.id.editTextname);
        surname = view.findViewById(R.id.editTextsurname);
        date = view.findViewById(R.id.editTextdate);
        nitritvalue = view.findViewById(R.id.editTextnitrit);
        insertdata = view.findViewById(R.id.buttoninsertdata);

        insertdata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                GetData();
                InsertData(TempName, TempSurname, TempDate, TempNitrit);
            }
        });
        return view;

    }
    public void GetData(){
        TempName = name.getText().toString();
        TempSurname = surname.getText().toString();
        TempDate = date.getText().toString();
        TempNitrit = nitritvalue.getText().toString();
        if (TempName.equals("") || TempSurname.equals("") || TempDate.equals("") || TempNitrit.equals("")) {
            Toast.makeText(getActivity(), "Please Enter Detail", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(getActivity(), "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name, surname, date, nitritvalue);
    }

}