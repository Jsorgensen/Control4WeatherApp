package jsorgensen.com.control4weatherapp.Presenters;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import jsorgensen.com.control4weatherapp.Adapters.CityForecastAdapter;
import jsorgensen.com.control4weatherapp.Controllers.App;
import jsorgensen.com.control4weatherapp.Controllers.DetailedForecastActivity;
import jsorgensen.com.control4weatherapp.Models.City;
import jsorgensen.com.control4weatherapp.Models.DetailedForecast;
import jsorgensen.com.control4weatherapp.R;
import jsorgensen.com.control4weatherapp.Services.WeatherService;
import jsorgensen.com.control4weatherapp.Utilities.Constants;
import jsorgensen.com.control4weatherapp.Views.CityForecastViewHolder;
import jsorgensen.com.control4weatherapp.Views.CityForecastsFragment;

public class CityForecastPresenter implements Presenter {

    public CityForecastsFragment fragment;
    private Activity activity;
    private App app;
    private WeatherService weatherService;
    private ArrayList<DetailedForecast> forecasts = new ArrayList<>();
    private SparseArray<City> selectedCities = new SparseArray<>();

    private CityForecastAdapter adapter;
    public int getCount(){ return forecasts.size(); }
    private boolean enableClick = true;

    public CityForecastPresenter(CityForecastsFragment fragment){
        this.fragment = fragment;
        activity = fragment.getActivity();
    }

    @Override
    public void start() {
        app = (App)activity.getApplication();
        weatherService = WeatherService.getInstance();

        setUpAdapters();
    }

    @Override
    public Bundle bundle() {
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("forecasts", forecasts);

        return bundle;
    }

    @Override
    public void unBundle(Bundle bundle) {
        if(bundle == null)
            return;

        ArrayList<DetailedForecast> toForecasts = bundle.getParcelableArrayList("forecasts");
        if(toForecasts != null){
            forecasts.addAll(toForecasts);
        }
    }

    public void requestData(){
        String cityForecasts = app.sharedPreferences().getCityForecasts();

        if(cityForecasts == null){
            setPresets();
        }else{
            try{
                JSONArray jsonArray = new JSONArray(cityForecasts);
                extractForecasts(jsonArray);
            }catch (Exception e){}
        }
    }

    private void setPresets(){
        selectedCities.clear();

        ArrayList<Integer> cityIds = new ArrayList<>();
        cityIds.add(Constants.SALT_LAKE_ID);
        cityIds.add(Constants.SAN_FRANCISCO_ID);
        cityIds.add(Constants.NEW_YORK_ID);

        requestForecasts(joinToString(cityIds));
    }

    private void extractForecasts(JSONArray json){
        try{
            forecasts.clear();

            for(int i=0; i<json.length(); i++){
                JSONObject jsonObject = json.getJSONObject(i);
                DetailedForecast forecast = new DetailedForecast(jsonObject);
                forecasts.add(forecast);
            }

            adapter.notifyDataSetChanged();
        }catch (Exception e){}
    }

    private void requestForecasts(ArrayList<DetailedForecast> forecasts){
        ArrayList<Integer> cityIds = new ArrayList<>();
        for(DetailedForecast forecast: forecasts){
            cityIds.add(forecast.id);
        }

        requestForecasts(joinToString(cityIds));
    }

    private void requestForecasts(String cityIds){
        weatherService.requestGetWeatherByCityIds(this, cityIds);
    }

    public void receiveWeatherForecasts(ArrayList<DetailedForecast> forecasts){
        this.forecasts.clear();
        this.forecasts.addAll(forecasts);

        app.sharedPreferences().persistForecasts(forecasts);

        adapter.notifyDataSetChanged();
    }

    private String joinToString(ArrayList<Integer> cityIds){
        StringBuffer result = new StringBuffer();
        for(Integer id: cityIds){
            result.append(id + ",");
        }
        String idsString = result.toString().substring(0, result.toString().length()-1);

        return idsString;
    }

    private Boolean checkLatestRequest(){
        Long latestRequestLong = app.sharedPreferences().latestRequest();

        Date currentDate = new Date();

        Long deltaTime = currentDate.getTime() - latestRequestLong;
        Long deltaMinutes = TimeUnit.MILLISECONDS.toMinutes(deltaTime);

        boolean hasEnoughTimeElapsed = deltaMinutes >= 10;
        return hasEnoughTimeElapsed;
    }

    private void setUpAdapters(){
        adapter = new CityForecastAdapter(this);
        ((RecyclerView) activity.findViewById(R.id.citiesRecyclerView)).setAdapter(adapter);
        ((RecyclerView) activity.findViewById(R.id.citiesRecyclerView)).setLayoutManager(new LinearLayoutManager(activity));
    }

    private void navigateToDetailedForecast(DetailedForecast forecast){
        disableInput(true);

        Intent forecastIntent = new Intent(activity, DetailedForecastActivity.class);
        forecastIntent.putExtra(Constants.EXTRA_FORECAST, forecast);
        activity.startActivity(forecastIntent);
    }

    public void resume(){
        disableInput(false);
    }

    private void disableInput(boolean disable){
        if(disable){
            fragment.getView().findViewById(R.id.citiesProgressBar).setVisibility(View.VISIBLE);
        }else{
            fragment.getView().findViewById(R.id.citiesProgressBar).setVisibility(View.GONE);
        }

        enableClick = !disable;
    }

    public void onBindViewHolder(CityForecastViewHolder holder, int position){
        DetailedForecast forecast = forecasts.get(position);
        bindForecast(holder, forecast);
    }

    private void bindForecast(CityForecastViewHolder holder, final DetailedForecast forecast){
        holder.setTemperatureText(forecast.main.temp+"°F");
        holder.setCityText(forecast.toString());
        String iconURL = Constants.WEATHER_ICON_URL.replace("<WEATHER_ICON>", forecast.weather.descriptions.get(0).icon);
        Picasso.with(activity).load(iconURL).into(holder.iconImageView);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDetailedForecast(forecast);
            }
        });
        holder.layout.setClickable(enableClick);
    }
}
