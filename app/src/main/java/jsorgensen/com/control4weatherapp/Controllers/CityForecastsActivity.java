package jsorgensen.com.control4weatherapp.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;

import jsorgensen.com.control4weatherapp.R;

public class CityForecastsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_forecasts);

        AndroidNetworking.initialize(getApplicationContext());


    }
}
