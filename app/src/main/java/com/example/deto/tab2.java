
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static com.example.deto.MyBoundService.SERVICE_TASK_RESULT_COMPLETE;
import static com.example.deto.MyBoundService.EKSTRA_KEY_BROADCAST_RESULT;
import static com.example.deto.MyBoundService.EKSTRA_KEY_BROADCAST_NAME_RESULT;

public class tab2 extends Fragment {
    private static final String TAG = "Tab2";
    public static final String EXTRA_NAME_SURNAME = "EXTRA_Name_Surname";
    private ListView listViewItems;
    TextView viewName, viewSurname;

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
        listViewItems = view.findViewById(R.id.listViewItems);


        viewName = view.findViewById(R.id.e_name);
        viewSurname = view.findViewById(R.id.e_surname);

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
                ArrayList result = broadcatedResult.getStringArrayListExtra(EKSTRA_KEY_BROADCAST_RESULT);
                ArrayList nameresult = broadcatedResult.getStringArrayListExtra(EKSTRA_KEY_BROADCAST_NAME_RESULT);
                if(nameresult!=null){
                    ViewData(nameresult);
                }
            }
        }
    };

    private void ViewData(ArrayList res){
        // Store unique items in result.
        ArrayList result = new ArrayList();
        // Record encountered Strings in HashSet.
        HashSet set = new HashSet();

        // Loop over argument list.
        for (Object item : res) {
            if (!set.contains(item)) {
                result.add(item);
                set.add(item);
            }

            ListAdapter adapter = new SimpleAdapter(
                    getActivity(), result, R.layout.activity_mylist,
                    new String[]{Config.KEY_Name, Config.KEY_Surname},
                    new int[]{R.id.e_name,R.id.e_surname});
            listViewItems.setAdapter(adapter);
            listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String Navnvalgt  =  listViewItems.getItemAtPosition(position).toString();
                    Toast.makeText(getActivity()," Navnet" + Navnvalgt,Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

}
