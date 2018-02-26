package jsorgensen.com.control4weatherapp.Presenters;


import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import jsorgensen.com.control4weatherapp.Models.DetailedForecast;
import jsorgensen.com.control4weatherapp.R;
import jsorgensen.com.control4weatherapp.Utilities.Constants;
import jsorgensen.com.control4weatherapp.Views.DetailedForecastFragment;

public class DetailedForecastPresenter implements Presenter {

    public DetailedForecastPresenter(DetailedForecastFragment fragment){
        this.fragment = fragment;
        this.activity = fragment.getActivity();
    }

    private DetailedForecastFragment fragment;
    private Activity activity;
    private DetailedForecast forecast;

    @Override
    public void start() {

    }

    @Override
    public Bundle bundle() {
        Bundle bundle = new Bundle();

        bundle.putParcelable(Constants.EXTRA_FORECAST, forecast);

        return bundle;
    }

    @Override
    public void unBundle(Bundle bundle) {
        if(bundle == null){
            forecast = activity.getIntent().getParcelableExtra(Constants.EXTRA_FORECAST);
        }else{
            forecast = bundle.getParcelable(Constants.EXTRA_FORECAST);
        }

        updateForecastView();
    }

    private void updateForecastView(){
        if(forecast == null)
            return;

        ((TextView)fragment.getView().findViewById(R.id.detailedForecastTempTextView)).setText(forecast.main.temp + "°F");
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastMinTextView)).setText("Min\n" + forecast.main.tempMin + "°F");
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastMaxTextView)).setText("Max\n" + forecast.main.tempMax + "°F");
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastCityTextView)).setText(forecast.cityName);
        String timeStamp = new SimpleDateFormat("DDD MMM dd HH:mm aa").format(forecast.timeStamp);
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastTimeTextView)).setText(timeStamp);
        String iconURL = Constants.WEATHER_ICON_URL.replace("<WEATHER_ICON>", forecast.weather.descriptions.get(0).icon);
        Picasso.with(activity).load(iconURL).into(((ImageView)fragment.getView().findViewById(R.id.detailedForecastIconImageView)));
        ((TextView)fragment.getView().findViewById(R.id.detaildForecastDescriptionTextView)).setText(forecast.weather.descriptions.get(0).description);
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastPressureTextView)).setText("Pressure: " + forecast.main.pressure);
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastHumidityTextView)).setText("Humidity: " + forecast.main.humidity);
        ((TextView)fragment.getView().findViewById(R.id.detailedForecastWindSpeedTextView)).setText("Wind Speed: " + forecast.wind.speed);
        if(forecast.clouds != null)
            ((TextView)fragment.getView().findViewById(R.id.detaildForecastCloudsTextView)).setText("Cloud Coverage: " + forecast.clouds.coverage);
        if(forecast.rain != null)
            ((TextView)fragment.getView().findViewById(R.id.detaildForecastRainTextView)).setText("Inches of Rain Fall for last 3 hours: " + forecast.rain.last3HoursRainFall);
        if(forecast.snow != null)
            ((TextView)fragment.getView().findViewById(R.id.detaildForecastSnowTextView)).setText("Inches of Snow Fall for last 3 hours: " + forecast.snow.last3HoursSnowFall);
    }
}
