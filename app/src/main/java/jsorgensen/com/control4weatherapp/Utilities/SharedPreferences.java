package jsorgensen.com.control4weatherapp.Utilities;


import android.content.Context;

import org.json.JSONObject;

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
    String CITY_IDS = "cityIds";
    String DETAILED_FORECAST = "detailedForecast";

    private String selectedCities;
    private Long latestRequest;
    private String cityForecasts;
    private String detailedForecast;

    public String getSelectedCities(){ return prefs.getString(SELECTED_CITIES, null); }
    public void setSelectedCities(String value){ prefs.edit().putString(SELECTED_CITIES, value).apply(); }

    public Long latestRequest(){ return prefs.getLong(LATEST_REQUEST, -1L); }
    public void setLatestRequest(Long value){ prefs.edit().putLong(LATEST_REQUEST, value).apply(); }

    public String getCityIds(){ return prefs.getString(CITY_IDS, null); }
    public void setCityIds(String cityIds){ prefs.edit().putString(CITY_IDS, cityIds).apply(); }

    public String getDetailedForecast(int forecastId){return prefs.getString(DETAILED_FORECAST+forecastId, "");}
    private void setDetailedForecast(String detailedForecast, int forecastId){prefs.edit().putString(DETAILED_FORECAST+forecastId, detailedForecast).apply();}

    public void persistForecast(DetailedForecast forecast){
        JSONObject json = forecast.toJSON();

        setDetailedForecast(json.toString(), forecast.id);
    }

    public void deleteForecast(DetailedForecast forecast){
        setDetailedForecast(null, forecast.id);
    }
}
