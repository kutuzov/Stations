package com.example.mike.stations;

import android.app.Application;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by prolomov on 16.09.2016.
 */
public class StationsApplication extends Application {
    private int day,month,year;
    private String stDate;
    private ArrayList<Response.Stations> stationsFromList,searchedStationsFromList;

    public StationsApplication() {

        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        generateDateString();

    }

    private void generateDateString() {
        stDate = generateStringWithLeadingZero(day)+"."+generateStringWithLeadingZero(month)+"."+year;
    }

    private String generateStringWithLeadingZero(Integer intValue) {
        String strValue = intValue.toString();
        if (strValue.length() == 1) {
            strValue = "0"+strValue;
        }
        return strValue;
    }

    private void collectFromStations() {

        String jsonString = loadJSONFromAsset();
        Gson gson = new Gson();
        Response response = gson.fromJson(jsonString, Response.class);

        stationsFromList = new ArrayList<Response.Stations>();

        for (Response.CitiesFrom cf : response.citiesFrom) {
            for (Response.Stations st : cf.stations)
                stationsFromList.add(st);
        }

        Collections.sort(stationsFromList, new Comparator() {

            public int compare(Object o1, Object o2) {

                String x1 = ((Response.Stations) o1).countryTitle;
                String x2 = ((Response.Stations) o2).countryTitle;
                int sComp = x1.compareTo(x2);

                if (sComp != 0) {
                    return sComp;
                } else {
                    x1 = ((Response.Stations) o1).cityTitle;
                    x2 = ((Response.Stations) o2).cityTitle;
                    return x1.compareTo(x2);
                }
            }
        });

    }

    private String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("allStations.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    public ArrayList getStationsFromList(boolean searched, String searchQuery) {
        if (stationsFromList == null) {
            collectFromStations();}
        if (searched) { return doSearchFromStations(searchQuery);}
        return stationsFromList;
    }

    public ArrayList doSearchFromStations(String searchQuery) {

        searchedStationsFromList = new ArrayList<Response.Stations>();

        for (Response.Stations st : stationsFromList) {
            if (st.countryTitle.toLowerCase().contains(searchQuery.toLowerCase()) ||
                    st.cityTitle.toLowerCase().contains(searchQuery.toLowerCase()) ||
                    st.stationTitle.toLowerCase().contains(searchQuery.toLowerCase()))
                searchedStationsFromList.add(st);

        }
        return searchedStationsFromList;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public String getStDate() {
        generateDateString();
        return stDate;
    }
}
