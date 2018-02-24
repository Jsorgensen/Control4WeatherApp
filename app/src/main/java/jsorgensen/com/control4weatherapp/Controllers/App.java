package jsorgensen.com.control4weatherapp.Controllers;

import android.app.Application;

import jsorgensen.com.control4weatherapp.Utilities.SharedPreferences;

public class App extends Application {

    @Override
    public void onCreate() {
        sharedPreferences = new SharedPreferences(getApplicationContext());

        super.onCreate();
    }

    SharedPreferences sharedPreferences;
}
