package com.example.mike.stations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prolomov on 16.09.2016.
 */
public class DatePickerActivity extends AppCompatActivity {
    public int theme;
    public Toolbar toolbar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = R.style.MyMaterialTheme;
        setTheme(theme);

        setContentView(R.layout.date_picker);
        setupToolbar();
        setToolbarBackIcon();


        final DatePicker dpDate = (DatePicker) findViewById(R.id.dpDate);
        Button btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

        final StationsApplication stApp = (StationsApplication)getApplicationContext();

        dpDate.init(stApp.getYear(), stApp.getMonth()-1, stApp.getDay(), null);

        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stApp.setDay(dpDate.getDayOfMonth());
                stApp.setMonth(dpDate.getMonth() + 1);
                stApp.setYear(dpDate.getYear());
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
