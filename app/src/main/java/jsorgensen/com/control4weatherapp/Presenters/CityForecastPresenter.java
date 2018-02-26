package jsorgensen.com.control4weatherapp.Presenters;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
    private boolean isEditingCities = false;

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
        HashSet<Integer> cityIds = extractPersistedCityIds();

        if(cityIds == null){
            setPresets();
        }else{
            if(hasLastRequestTimeElapsed()){
                requestForecasts(joinToString(cityIds));
            }else{
                try{
                    extractForecasts(cityIds);
                }catch (Exception e){}
            }
        }
    }

    private HashSet<Integer> extractPersistedCityIds(){
        String idsString = app.sharedPreferences().getCityIds();
        if(idsString == null)
            return null;

        String[] idsArray = idsString.split(",");

        HashSet<Integer> idsSet = new HashSet<>();
        for(String idString: idsArray){
            int id = Integer.parseInt(idString);
            idsSet.add(id);
        }

        return idsSet;
    }

    private boolean hasLastRequestTimeElapsed(){
        Long latestRequestLong = app.sharedPreferences().latestRequest();

        Long currentTime = new Date().getTime();

        Long deltaMinutes = TimeUnit.MILLISECONDS.toMinutes(currentTime - latestRequestLong);

        return deltaMinutes >= 10L;
    }

    private void setPresets(){
        selectedCities.clear();

        ArrayList<java.lang.Integer> cityIds = new ArrayList<>();
        cityIds.add(Constants.SALT_LAKE_ID);
        cityIds.add(Constants.SAN_FRANCISCO_ID);
        cityIds.add(Constants.NEW_YORK_ID);

        app.sharedPreferences().setCityIds(joinToString(cityIds));

        requestForecasts(joinToString(cityIds));
    }

    private void extractForecasts(HashSet<Integer> cityIds){
        forecasts.clear();

        for(int id: cityIds) {
            DetailedForecast forecast = extractForecast(id);

            if (forecast == null){
                continue;
            }

            forecasts.add(forecast);
        }

        adapter.notifyDataSetChanged();
    }

    private DetailedForecast extractForecast(int id){
        String forecastJsonString = app.sharedPreferences().getDetailedForecast(id);
        try{
            JSONObject forecastJson = new JSONObject(forecastJsonString);
            DetailedForecast forecast = new DetailedForecast(forecastJson);

            return forecast;
        }catch (Exception e){}

        return null;
    }

    private void requestForecasts(ArrayList<DetailedForecast> forecasts){
        ArrayList<java.lang.Integer> cityIds = new ArrayList<>();
        for(DetailedForecast forecast: forecasts){
            cityIds.add(forecast.id);
        }

        requestForecasts(joinToString(cityIds));
    }

    private void requestForecasts(String cityIds){
        if(cityIds.equals(""))
            return;

        clockLatestRequest();

        weatherService.requestGetWeatherByCityIds(this, cityIds);
    }

    private void requestForecast(String cityName){
        clockLatestRequest();

        weatherService.requestGetWeatherByName(this, cityName);
    }

    public void receiveWeatherForecasts(ArrayList<DetailedForecast> forecasts){
        if(forecasts == null)
            return;

        persistForecasts(forecasts);
        clockLatestRequest();

        HashSet<Integer> cityIds = extractPersistedCityIds();
        extractForecasts(cityIds);

        adapter.notifyDataSetChanged();
    }

    public void requestError(){
        clockLatestRequest();
    }

    private void clockLatestRequest(){
        app.sharedPreferences().setLatestRequest(new Date().getTime());
    }

    private void persistForecasts(ArrayList<DetailedForecast> forecasts){
        HashSet<Integer> ids = extractPersistedCityIds();
        for(DetailedForecast forecast: forecasts){
            ids.add(forecast.id);
        }

        app.sharedPreferences().setCityIds(joinToString(ids));

        for(DetailedForecast forecast: forecasts){
            app.sharedPreferences().persistForecast(forecast);
        }
    }

    private String joinToString(ArrayList<java.lang.Integer> cityIds){
        StringBuffer result = new StringBuffer();
        for(java.lang.Integer id: cityIds){
            result.append(id + ",");
        }
        if(result.length() > 0)
            return result.toString().substring(0, result.toString().length()-1);
        else
            return "";
    }

    private String joinToString(HashSet<Integer> cityIds){
        StringBuffer result = new StringBuffer();
        for(Integer id: cityIds){
            result.append(id + ",");
        }
        String idsString = result.toString().substring(0, result.toString().length()-1);

        return idsString;
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

    public void addCity(){
        ConstraintLayout layout = (ConstraintLayout)LayoutInflater.from(activity).inflate(R.layout.add_city, null);
        final EditText cityNameView = layout.findViewById(R.id.addCityNameEditText);

        new AlertDialog.Builder(activity).setTitle("Add City")
                .setView(layout)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String cityName = cityNameView.getText().toString();
                        requestForecast(cityName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public void editCity(){
        isEditingCities = !isEditingCities;

        adapter.notifyDataSetChanged();
    }

    private void deleteCity(DetailedForecast forecast){
        deleteCityId(forecast.id);

        app.sharedPreferences().deleteForecast(forecast);

        forecasts.remove(forecast);

        adapter.notifyDataSetChanged();
    }

    private void deleteCityId(int id){
        HashSet<Integer> cityIds = extractPersistedCityIds();
        cityIds.remove(id);
        app.sharedPreferences().setCityIds(joinToString(cityIds));
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
        if(forecast == null)
            return;

        holder.setTemperatureText(forecast.main.temp+"°F");
        holder.setCityText(forecast.toString());
        if(isEditingCities){
            int resID = activity.getResources().getIdentifier("ic_delete", "drawable", activity.getPackageName());
            holder.iconImageView.setImageResource(resID);
            holder.iconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCity(forecast);
                }
            });
        }else{
            String iconURL = Constants.WEATHER_ICON_URL.replace("<WEATHER_ICON>", forecast.weather.descriptions.get(0).icon);
            Picasso.with(activity).load(iconURL).into(holder.iconImageView);
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDetailedForecast(forecast);
            }
        });
        holder.layout.setClickable(enableClick);
    }
}
