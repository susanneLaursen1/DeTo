package com.example.deto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.deto.MyBoundService.EKSTRA_KEY_BROADCAST_MESSAGE;
import static com.example.deto.MyBoundService.SERVICE_TASK_MESSAGE_COMPLETE;
public class tab1 extends Fragment {
    private static final String TAG = "Tab1";
    ListView listViewNoti;


 public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        listViewNoti = view.findViewById(R.id.listViewNoti);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter mfilter = new IntentFilter();
        mfilter.addAction(SERVICE_TASK_MESSAGE_COMPLETE);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(messageReceiver, mfilter);

    }
    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(messageReceiver);
    }


    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent resultData) {
            if(resultData!=null){
                ArrayList message = resultData.getStringArrayListExtra(EKSTRA_KEY_BROADCAST_MESSAGE);
                if(message!=null){
                        viewData(message);
                }
            }
        }
        private void viewData(ArrayList<String> message) {
            ArrayList<String> messageList = new ArrayList<String>();
            for (String m : message) {
                String Smessage = message.toString();
                messageList.add(m);
            }
            ArrayAdapter<String> itemsAdapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, (messageList));
            listViewNoti.setAdapter(itemsAdapter);

        }
    };

}