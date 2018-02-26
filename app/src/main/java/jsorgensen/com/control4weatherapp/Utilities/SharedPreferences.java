package jsorgensen.com.control4weatherapp.Utilities;


import android.content.Context;

import org.json.JSONArray;

import java.util.ArrayList;

import jsorgensen.com.control4weatherapp.Models.DetailedForecast;

public class SharedPreferences {

    public SharedPreferences(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE);
    }

    Context context;

    String PREFERENCES_FILENAME = "preferences";
    android.content.SharedPreferences prefs;

    String SELECTED_CITIES = "selectedCities";
    String LATEST_REQUEST = "latestRequest";
    String CITY_FORECASTS = "cityForecasts";
    String DETAILED_FORECAST = "detailedForecast";

    private String selectedCities;
    private Long latestRequest;
    private String cityForecasts;
    private String detailedForecast;

    public String getSelectedCities(){ return prefs.getString(SELECTED_CITIES, null); }
    public void setSelectedCities(String value){ prefs.edit().putString(SELECTED_CITIES, value).apply(); }

    public Long latestRequest(){ return prefs.getLong(LATEST_REQUEST, -1L); }

    public String getCityForecasts(){
        return prefs.getString(CITY_FORECASTS, null);
    }
    private void setCityForecasts(String cityForecasts){ prefs.edit().putString(CITY_FORECASTS, cityForecasts).apply(); }

    public String getDetailedForecast(int forecastId){return prefs.getString(DETAILED_FORECAST+forecastId, "");}
    public void setDetailedForecast(String detailedForecast, int forecastId){prefs.edit().putString(DETAILED_FORECAST+forecastId, detailedForecast).apply();}

    public void persistForecasts(ArrayList<DetailedForecast> forecasts){
        JSONArray json = new JSONArray();

        for(DetailedForecast forecast: forecasts){
            json.put(forecast.toJSON());
        }

        setCityForecasts(json.toString());
    }
}
