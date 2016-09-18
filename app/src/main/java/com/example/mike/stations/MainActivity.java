package com.example.mike.stations;


import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public int theme;
    public Toolbar toolbar;
    public String fromStation, toStation;
    public Date date;
    public StationsApplication stApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        theme = R.style.MyMaterialTheme;
        setTheme(theme);


        //TODO Add detailed view activity
        //TODO Add choice - look option
        //TODO Add group by
        //TODO send choice back to MainActivity

        //TODO Add "To" activity
        //TODO Add settings screen and inflate menu


        setContentView(R.layout.activity_main);
        setupToolbar();
//        setToolbarBackIcon();

        TextView tvFromStation = (TextView) findViewById(R.id.tvFromStation);
        Button btnFromStation = (Button) findViewById(R.id.btnFromStation);

        TextView tvToStation = (TextView) findViewById(R.id.tvToStation);
        Button btnToStation = (Button) findViewById(R.id.btnToStation);

        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        Button btnDate = (Button) findViewById(R.id.btnDate);

        StationsApplication stApp = (StationsApplication)getApplicationContext();

        tvDate.setText(stApp.getStDate());

        btnFromStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View relativeLayoutView = findViewById(R.id.rl_main);

               Snackbar
                        .make(relativeLayoutView, R.string.loadHint, Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                            }
                        })
                        .setDuration(Snackbar.LENGTH_LONG)
                        .show();
                StationsApplication stApp = (StationsApplication)getApplicationContext();
                stApp.setCurrentDirection(stApp.DIRECTION_TO);
                startSelectFromStationAsync();
            }
        });

        btnToStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectToStation();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectDate();
            }
        });

    }

    public void startSelectFromStation() {
        Intent intent = new Intent(this, SelectFromStationActivity.class);
        startActivity(intent);
    }

    public void startSelectFromStationAsync() {

        class StartFromAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                StationsApplication stApp = (StationsApplication)getApplicationContext();
                stApp.getStationsFromList(false, "");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                startSelectFromStation();
            }
        }

        new StartFromAsyncTask().execute();

    }

    public void startSelectToStation() {

        Intent intent = new Intent(this, SelectFromStationActivity.class);
        startActivity(intent);
    }

    public void startSelectDate() {
        Intent intent = new Intent(this, DatePickerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }


    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }






}
