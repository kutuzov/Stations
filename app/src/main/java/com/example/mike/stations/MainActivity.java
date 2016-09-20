package com.example.mike.stations;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends AppCompatActivity {

    public int theme;
    public Toolbar toolbar;
    public Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        theme = R.style.MyMaterialTheme;
        setTheme(theme);

        setContentView(R.layout.activity_main);
        setupToolbar();

        TextView tvFromStation = (TextView) findViewById(R.id.tvFromStation);
        Button btnFromStation = (Button) findViewById(R.id.btnFromStation);

        TextView tvToStation = (TextView) findViewById(R.id.tvToStation);
        Button btnToStation = (Button) findViewById(R.id.btnToStation);

        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        Button btnDate = (Button) findViewById(R.id.btnDate);

        StationsApplication stApp = (StationsApplication) getApplicationContext();

        tvDate.setText(stApp.getStDate());

        if (stApp.getStFrom() != "") {
            tvFromStation.setText(stApp.getStFrom());
        }
        if (stApp.getStTo() != "") {
            tvToStation.setText(stApp.getStTo());
        }

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
                StationsApplication stApp = (StationsApplication) getApplicationContext();
                stApp.setCurrentDirection(stApp.DIRECTION_FROM);
                startSelectStationAsync();
            }
        });

        btnToStation.setOnClickListener(new View.OnClickListener() {
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

                StationsApplication stApp = (StationsApplication) getApplicationContext();
                stApp.setCurrentDirection(stApp.DIRECTION_TO);
                startSelectStationAsync();


            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSelectDate();
            }
        });

    }


    //We load data async for user-friendly interface
    public void startSelectStationAsync() {

        class StartFromAsyncTask extends AsyncTask {
            @Override
            protected Object doInBackground(Object[] objects) {
                StationsApplication stApp = (StationsApplication) getApplicationContext();
                stApp.getStationsList(false, "");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                startSelectStation();
            }
        }

        new StartFromAsyncTask().execute();

    }

    public void startSelectStation() {
        Intent intent = new Intent(this, SelectStationActivity.class);
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
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            String htmlBr = "<br/>";
            String htmlB = "<b>";
            AlertDialog.Builder appInfo = new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
            appInfo.setTitle((R.string.textAboutTitle));
            String htmlText = getString(R.string.textAbout1) + htmlBr + getString(R.string.textAbout2)
                    + htmlBr + htmlBr + htmlB + getString(R.string.textAbout3);
            appInfo.setMessage(Html.fromHtml(htmlText));

            appInfo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            appInfo.show();
            return true;
        }
        if (id == R.id.action_exit) {
            this.finishAffinity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


}
