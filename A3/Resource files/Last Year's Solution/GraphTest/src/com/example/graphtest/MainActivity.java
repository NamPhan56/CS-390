package com.example.graphtest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     // init example series data
        GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
            new GraphViewData(1, 2.0d)
            , new GraphViewData(2, 1.5d)
            , new GraphViewData(3, 2.5d)
            , new GraphViewData(4, 1.0d)
        });
         
        GraphView graphView = new LineGraphView(
            this // context
            , "GraphViewDemo" // heading
        );
        graphView.addSeries(exampleSeries); // data
         
        LinearLayout layout = (LinearLayout) findViewById(R.id.graph1);
        layout.addView(graphView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
