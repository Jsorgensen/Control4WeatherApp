package jsorgensen.com.control4weatherapp.Presenters;


import android.os.Bundle;

import java.util.ArrayList;

import jsorgensen.com.control4weatherapp.Models.DetailedForecast;

public class CityForecastPresenter implements Presenter {

    ArrayList<DetailedForecast> forecasts = new ArrayList<>();

    @Override
    public void start() {
        setUpAdapters();
    }

    @Override
    public Bundle bundle() {
        Bundle bundle = new Bundle();

        bundle.putParcelableArray("forecasts", forecasts);

        return bundle;
    }

    @Override
    public void unBundle(Bundle bundle) {
        ArrayList<DetailedForecast> toForecasts = bundle.getParcelableArray<DetailedForecast>("forecasts");
    }

    private void setUpAdapters(){
        klljklkj
    }
}
