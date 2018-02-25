package jsorgensen.com.control4weatherapp.Presenters;


import android.os.Bundle;

public interface Presenter {

    void start();
    Bundle bundle();
    void unBundle(Bundle bundle);

}
