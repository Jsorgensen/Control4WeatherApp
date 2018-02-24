package jsorgensen.com.control4weatherapp.Utilities;


import android.content.Context;

public class SharedPreferences {

    public SharedPreferences(Context context){
        this.context = context;
    }

    Context context;

    String PREFERENCES_FILENAME = "preferences";
    android.content.SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE);

    String CITY_FORECASTS = "cityForecasts";
    String DETAILED_FORECAST = "detailedForecast";

    private String cityForecasts;
    private String detailedForecast;

    public String getCityForecasts(){
        return prefs.getString(CITY_FORECASTS, "");
    }
    public void setCityForecasts(String cityForecasts){
        prefs.edit().putString(CITY_FORECASTS, cityForecasts).apply();
    }

    public String getDetailedForecast(int forecastId){
        return prefs.getString(DETAILED_FORECAST+forecastId, "");
    }
    public void setDetailedForecast(String detailedForecast, int forecastId){
        prefs.edit().putString(DETAILED_FORECAST+forecastId, detailedForecast).apply();
    }
}
