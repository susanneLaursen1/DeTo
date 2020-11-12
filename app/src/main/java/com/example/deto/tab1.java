package com.example.deto;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Intent;

public class tab1 extends Fragment {
    //Til foregroundworker
    private EditText editTextInput;

    public static tab1 newInstance(String param1, String param2) {
        tab1 fragment = new tab1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Foreground
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        editTextInput = view.findViewById(R.id.edit_text_input);
        return view;

    }

    public void startService(View view) {

        String input = editTextInput.getText().toString();

        Intent serviceIntent = new Intent(getActivity(), ServiceForeground.class);
        serviceIntent.putExtra("inputExtra", input);

        ContextCompat.startForegroundService(getActivity(), serviceIntent);
    }

    /** Called when the user touches the button */

    public void stopService(View view) {
        // Do something in response to button click
        Intent serviceIntent = new Intent(getActivity(), ServiceForeground.class);
        //stopService(serviceIntent);


    }


}