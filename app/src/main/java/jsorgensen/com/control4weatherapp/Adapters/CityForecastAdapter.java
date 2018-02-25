package jsorgensen.com.control4weatherapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import jsorgensen.com.control4weatherapp.R;
import jsorgensen.com.control4weatherapp.Views.CityForecastViewHolder;


public class CityForecastAdapter extends RecyclerView.Adapter<CityForecastViewHolder> {

    public CityForecastAdapter(CityForecastPresenter presenter){
        this.presenter = presenter;
    }

    CityForecastPresenter presenter;

    @Override
    public CityForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CityForecastViewHolder view = LayoutInflater.from(presenter.fragment.getActivity()).inflate(R.layout.city_forecast_headline, parent);
        return presenter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(CityForecastViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }
}
