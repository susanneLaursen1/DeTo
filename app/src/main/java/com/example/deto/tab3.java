package com.example.deto;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class tab3 extends Fragment {
    private int[] ids = {R.raw.vid1, R.raw.vid2};
    private int index = 1;
    private VideoView myVideo;
    private TextView myText;

    public static tab3 newInstance(String param1, String param2) {
        tab3 fragment = new tab3();
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
        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        myText = view.findViewById(R.id.textViewVideo);
        String KaliberingBeskrivelse = new String("1. Der skal på forhånd laves 3 opløsninger med hhv. 1, 10 og 100 ppm nitrit. \n" +
                "2. Sensoren skal afmonteres fra toilet, skylles med demineraliseret vand og proben skal forsigtigt tørres. \n" +
                "3 Derefter sættes sensoren ned i 1ppm opløsningen, og efter 60s aflæses målingen. \n" +
                "\t3.1 Proben skal endnu engang skylles med demineraliseret vand og tørres. \n" +
                "4. Derefter sættes sensoren ned i 10ppm opløsningen, og efter 60s aflæses målingen. \n" +
                "\t4.1 Proben skal endnu engang skylles med demineraliseret vand og tørres. \n" +
                "5. Derefter sættes sensoren ned i 100ppm opløsningen, og efter 60s aflæses målingen. \n" +
                "\t5.1 Proben skal for sidste gang skylles med demineraliseret vand. \n" +
                "6. De aflæste målinger skrives nu ind i det vedhæftede Excel. \n" +
                "7. Den ligning som nu ses på skærmen indskrives på Raspberrien. ");
        myText.setText(KaliberingBeskrivelse);

        myVideo  = view.findViewById(R.id.video_view);
        myVideo.requestFocus();
        myVideo.setVideoURI(getPath(index));
        index++;
        final MediaController mediaController = new MediaController(getActivity());
        myVideo.setMediaController(mediaController);

        myVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaController.setAnchorView(myVideo);
                myVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myVideo.start();
                    }
                });
            }
        });

        myVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (index == ids.length) index = 1;
                index++;
                myVideo.setVideoURI(getPath(index));
            }
        });

        return view;
    }

    private Uri getPath(int id) {
        return Uri.parse("android.resource://" + getActivity().getPackageName() + "/raw/vid" + id);

    }
}