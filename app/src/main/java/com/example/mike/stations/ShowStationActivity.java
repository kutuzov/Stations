package com.example.mike.stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by prolomov on 19.09.2016.
 */
public class ShowStationActivity extends AppCompatActivity{
    public int theme;
    public Toolbar toolbar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = R.style.MyMaterialTheme;
        setTheme(theme);

        setContentView(R.layout.activity_station);
        setupToolbar();
        setToolbarBackIcon();

/*
       // ErrorLayout = (LinearLayout) findViewById(R.id.error);
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        */
/**
         * Removing ugly overscroll effect
         * Setting ScrollView's & ErrorLayout's visibility to gone
         *//*


       sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
//        sv.setVisibility(View.GONE);
//        ErrorLayout.setVisibility(View.GONE);
*/
        TextView tvStation = (TextView)findViewById(R.id.stationTitle);
        TextView tvCity = (TextView)findViewById(R.id.city);
        TextView tvRegion = (TextView)findViewById(R.id.region);
        TextView tvCountry = (TextView)findViewById(R.id.country);
        Button btnSelectStation = (Button)findViewById(R.id.btnSelectStation);

        final StationsApplication stApp = (StationsApplication)getApplicationContext();

        tvStation.setText(stApp.getCurrentStationTitle());
        tvCity.setText(stApp.getCurrentStationCity());
        tvRegion.setText(stApp.getCurrentStationRegion());
        tvCountry.setText(stApp.getCurrentStationCountry());

        btnSelectStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stApp.setStation();
                startMainActivity();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setToolbarBackIcon(){
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
