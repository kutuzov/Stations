package com.example.mike.stations;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by prolomov on 16.09.2016.
 */
public class SelectStationActivity extends AppCompatActivity {
    public int theme;
    public Toolbar toolbar;
    public ArrayList stationsList;
    public boolean searched;
    public String searchQuery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searched = false;
        searchQuery = "";
        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery = intent.getStringExtra(SearchManager.QUERY);
            searched = true;
        }

        theme = R.style.MyMaterialTheme;
        setTheme(theme);

        setContentView(R.layout.activity_grid);
        setupToolbar();
        setToolbarBackIcon();

        StationsApplication stApp = (StationsApplication)getApplicationContext();
        stationsList = stApp.getStationsList(searched, searchQuery);

        CardOverviewAdapter adapter;
        RecyclerView rvGrid = (RecyclerView) findViewById(R.id.rvGrid);
        adapter = new CardOverviewAdapter(this, getCards());
        rvGrid.setAdapter(adapter);
        rvGrid.setLayoutManager(new LinearLayoutManager(this));

    }

    private ArrayList<Response.Stations> getCards() {
        return stationsList;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) SelectStationActivity.this.getSystemService(Context.SEARCH_SERVICE);

       SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SelectStationActivity.this.getComponentName()));
            }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
