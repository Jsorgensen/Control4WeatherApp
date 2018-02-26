package jsorgensen.com.control4weatherapp.Utilities;


public class Constants {

    public static final String API_KEY = "&APPID=da65fafb6cb9242168b7724fb5ab75e7";
    public static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/group?id=<CITY_IDS>&units=imperial" + API_KEY;
    public static final String FORECAST_BY_NAME_URL = "http://api.openweathermap.org/data/2.5/weather?q=<CITY_NAME>&units=imperial" + API_KEY;
    public static final String WEATHER_ICON_URL = "http://openweathermap.org/img/w/<WEATHER_ICON>.png";

    public static final int SALT_LAKE_ID = 5780993;
    public static final int SAN_FRANCISCO_ID = 5391959;
    public static final int NEW_YORK_ID = 5128581;

    public static final String EXTRA_FORECAST = "extraForecast";
}
