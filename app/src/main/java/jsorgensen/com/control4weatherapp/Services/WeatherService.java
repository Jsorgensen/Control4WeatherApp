package jsorgensen.com.control4weatherapp.Services;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import jsorgensen.com.control4weatherapp.Models.DetailedForecast;
import jsorgensen.com.control4weatherapp.Presenters.CityForecastPresenter;
import jsorgensen.com.control4weatherapp.Utilities.Constants;

public class WeatherService {
    private static final WeatherService ourInstance = new WeatherService();

    public static WeatherService getInstance() {
        return ourInstance;
    }

    private WeatherService() {
    }

    public void requestGetWeatherByCityIds(final CityForecastPresenter presenter, String cityIds){
        String url = Constants.FORECAST_URL.replace("<CITY_IDS>", cityIds);
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            ArrayList<DetailedForecast> forecasts = new ArrayList<>();

                            JSONArray list = response.getJSONArray("list");
                            for(int i=0; i<list.length(); i++){
                                JSONObject jsonObject = list.getJSONObject(i);
                                DetailedForecast forecast = new DetailedForecast(jsonObject);
                                forecasts.add(forecast);
                            }

                            presenter.receiveWeatherForecasts(forecasts);
                        }catch (Exception e){}
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
