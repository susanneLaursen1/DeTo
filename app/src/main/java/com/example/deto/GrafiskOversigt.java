package com.example.deto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GrafiskOversigt extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafisk_oversigt);

        // Viser graf. Data skal ændres til at vise det korrekte data fra databasen. Nu vises der bare en sinuskurve
        double x,y;
        x=0;
        GraphView graph = (GraphView) findViewById(R.id.graph);
        series1 = new LineGraphSeries<>();

        int numDataPoints=500;
        for(int i =0;i<numDataPoints;i++) {
            x = x + 0.1;
            y = Math.sin(x);
            series1.appendData(new DataPoint(x, y), true, 100);
        }
        graph.addSeries(series1);
    }
}