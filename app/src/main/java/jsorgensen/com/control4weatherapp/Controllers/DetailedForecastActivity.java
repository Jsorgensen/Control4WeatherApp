package jsorgensen.com.control4weatherapp.Controllers;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jsorgensen.com.control4weatherapp.R;
import jsorgensen.com.control4weatherapp.Views.DetailedForecastFragment;

public class DetailedForecastActivity extends AppCompatActivity implements DetailedForecastFragment.OnFragmentInteractionListener {

    private DetailedForecastFragment detailedForecastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_forecast);

        detailedForecastFragment = new DetailedForecastFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.flDetailedForecastPanel, detailedForecastFragment)
                .commit();

        detailedForecastFragment.activityState = savedInstanceState;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
