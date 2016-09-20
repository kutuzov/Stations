package com.example.mike.stations;

import android.app.Application;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;


/**
 * Created by prolomov on 16.09.2016.
 */
public class StationsApplication extends Application {
    private int day, month, year;
    private String stDate, stFrom, stTo;
    private ArrayList<Response.Stations> stationsList, searchedStationsList,
            stationsListGrouped, searchedStationsListGrouped;
    private boolean currentDirection, currentSearched;
    public boolean DIRECTION_FROM = true;
    public boolean DIRECTION_TO = false;
    private Response response;
    private Response.Stations currentStation;

    public StationsApplication() {

        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        generateDateString();

        stFrom = "";
        stTo = "";

    }

    private void generateDateString() {
        stDate = generateStringWithLeadingZero(day) + "." + generateStringWithLeadingZero(month) + "." + year;
    }

    private String generateStringWithLeadingZero(Integer intValue) {
        String strValue = intValue.toString();
        if (strValue.length() == 1) {
            strValue = "0" + strValue;
        }
        return strValue;
    }

    //We collect stations every time basic view(not search) is accessed
    private void collectStations() {

        //No need to load from JSON every time
        if (response == null) {
            String jsonString = loadJSONFromAsset();
            Gson gson = new Gson();
            response = gson.fromJson(jsonString, Response.class);
        }

        stationsList = new ArrayList<Response.Stations>();
        stationsListGrouped = new ArrayList<Response.Stations>();

        if (currentDirection == DIRECTION_FROM) {
            for (Response.Cities cf : response.citiesFrom) {
                for (Response.Stations st : cf.stations)
                    stationsList.add(st);
            }
        } else {
            for (Response.Cities cf : response.citiesTo) {
                for (Response.Stations st : cf.stations)
                    stationsList.add(st);
            }
        }

        Collections.sort(stationsList, new Comparator() {

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

        String groupByArgument = "";
        int i = 0;
        Response response = new Response();
        for (Iterator<Response.Stations> it = stationsList.iterator(); it.hasNext(); i++) {
            Response.Stations loopStation = it.next();
            String currentArgument = loopStation.countryTitle + ", " + loopStation.cityTitle;
            if (!currentArgument.equals(groupByArgument)) {
                Response.Stations loopStationDivider = response.new Stations();
                loopStationDivider.countryTitle = loopStation.countryTitle;
                loopStationDivider.cityTitle = loopStation.cityTitle;
                loopStationDivider.stationTitle = "";
                loopStationDivider.stationId = 0;
                stationsListGrouped.add(loopStationDivider);
                stationsListGrouped.add(loopStation);
                groupByArgument = currentArgument;
            } else {
                stationsListGrouped.add(loopStation);
            }
        }

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

    public ArrayList getStationsList(boolean searched, String searchQuery) {
        currentSearched = searched;
        if (!searched) collectStations();
        if (searched) {
            return doSearchStations(searchQuery);
        }
        return stationsListGrouped;
    }

    public ArrayList doSearchStations(String searchQuery) {

        searchedStationsList = new ArrayList<Response.Stations>();
        searchedStationsListGrouped = new ArrayList<Response.Stations>();

        for (Response.Stations st : stationsList) {
            if (st.countryTitle.toLowerCase().contains(searchQuery.toLowerCase()) ||
                    st.cityTitle.toLowerCase().contains(searchQuery.toLowerCase()) ||
                    st.stationTitle.toLowerCase().contains(searchQuery.toLowerCase()))
                searchedStationsList.add(st);

        }

        String groupByArgument = "";
        int i = 0;
        Response response = new Response();
        for (Iterator<Response.Stations> it = searchedStationsList.iterator(); it.hasNext(); i++) {
            Response.Stations loopStation = it.next();
            String currentArgument = loopStation.countryTitle + ", " + loopStation.cityTitle;
            if (!currentArgument.equals(groupByArgument)) {
                Response.Stations loopStationDivider = response.new Stations();
                loopStationDivider.countryTitle = loopStation.countryTitle;
                loopStationDivider.cityTitle = loopStation.cityTitle;
                loopStationDivider.stationTitle = "";
                loopStationDivider.stationId = 0;
                searchedStationsListGrouped.add(loopStationDivider);
                searchedStationsListGrouped.add(loopStation);
                groupByArgument = currentArgument;
            } else {
                searchedStationsListGrouped.add(loopStation);
            }
        }

        return searchedStationsListGrouped;
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

    public boolean getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(boolean currentDirection) {
        this.currentDirection = currentDirection;
    }

    private Object getStation(int position) {
        if (currentSearched) {
            return searchedStationsListGrouped.get(position);
        } else {
            return stationsListGrouped.get(position);
        }
    }

    public void setStationCard(int position) {

        if (currentSearched) {
            currentStation = searchedStationsListGrouped.get(position);
        } else {
            currentStation = stationsListGrouped.get(position);
        }

    }

    public void setStation() {
        String title;

        title = currentStation.stationTitle;

        if (currentDirection == DIRECTION_FROM) {
            stFrom = title;
        } else {
            stTo = title;
        }

    }

    public String getStFrom() {
        return stFrom;
    }

    public String getStTo() {
        return stTo;
    }

    public String getCurrentStationTitle() {
        return currentStation.stationTitle;
    }

    //Here we modify getter because we already have word city written on form
    //This is special getter used only for form inflating
    public String getCurrentStationCityForForm() {
        String returnCity = currentStation.cityTitle;
        String textCity = getString(R.string.textCity);
        if (returnCity.toLowerCase().startsWith(textCity)) {
            returnCity = returnCity.substring(6, returnCity.length());
        }
        return returnCity;
    }

    //Standard getter
    public String getCurrentStationCity() {
        return currentStation.cityTitle;
    }

    public String getCurrentStationRegion() {
        return currentStation.regionTitle;
    }

    public String getCurrentStationCountry() {
        return currentStation.countryTitle;
    }

}
