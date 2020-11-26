package com.example.deto;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class tab3 extends Fragment {
    private int[] ids = {R.raw.vid1, R.raw.vid2};
    private int index = 1;
    private VideoView myVideo1;
   public static tab3 newInstance(String param1, String param2) {
        tab3 fragment = new tab3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isVisible();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);
       /* myVideo1  = view.findViewById(R.id.video_view);
        myVideo1.requestFocus();
        myVideo1.setVideoURI(getPath(index));
        index++;
        final MediaController mediaController = new MediaController(getActivity());
        myVideo1.setMediaController(mediaController);

        if(isVisible()){
            myVideo1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaController.setAnchorView(myVideo1);
                    myVideo1.start();
                }
            });

            myVideo1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (index == ids.length) index = 1;
                    index++;
                    myVideo1.setVideoURI(getPath(index));
                }
            });
        }else{
            //Should do nothing
        }
         */

        return view;
    }

    private Uri getPath(int id) {
        return Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/vid" + id);
    }


}
