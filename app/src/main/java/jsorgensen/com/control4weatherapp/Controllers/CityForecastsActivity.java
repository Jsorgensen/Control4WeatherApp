package jsorgensen.com.control4weatherapp.Controllers;

import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;

import jsorgensen.com.control4weatherapp.R;
import jsorgensen.com.control4weatherapp.Views.CityForecastsFragment;

public class CityForecastsActivity extends AppCompatActivity implements CityForecastsFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_forecasts);

        AndroidNetworking.initialize(getApplicationContext());

        cityForecastsFragment = new CityForecastsFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.flPanel, cityForecastsFragment)
                .commit();
    }

    CityForecastsFragment cityForecastsFragment;

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
