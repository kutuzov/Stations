package com.example.mike.stations;

import java.util.List;

/**
 * Created by prolomov on 15.09.2016.
 */
public class Response {
    public List<Cities> citiesFrom;
    public List<Cities> citiesTo;

//    public ArrayList<Cities> citiesFormList = new ArrayList<Cities>();

    public class Cities {
        public String countryTitle;
        public Point point;
        public String districtTitle;
        public int cityId;
        public String cityTitle;
        public String regionTitle;
        public List<Stations> stations;
    }

    public class Point {
        public float longitude;
        float latitude;
    }

    public class Stations {
        public String countryTitle;
        public Point point;
        public String districtTitle;
        public int cityId;
        public String cityTitle;
        public String regionTitle;
        public int stationId;
        public String stationTitle;

    }
}

