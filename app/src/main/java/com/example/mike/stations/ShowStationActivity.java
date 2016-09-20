package com.example.mike.stations;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by prolomov on 19.09.2016.
 */
public class ShowStationActivity extends AppCompatActivity {
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

        TextView tvStation = (TextView) findViewById(R.id.stationTitle);
        TextView tvCity = (TextView) findViewById(R.id.city);
        TextView tvRegion = (TextView) findViewById(R.id.region);
        TextView tvCountry = (TextView) findViewById(R.id.country);
        Button btnSelectStation = (Button) findViewById(R.id.btnSelectStation);

        final StationsApplication stApp = (StationsApplication) getApplicationContext();

        tvStation.setText(stApp.getCurrentStationTitle());
        tvCity.setText(stApp.getCurrentStationCityForForm());
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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

    public void setToolbarBackIcon() {
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
