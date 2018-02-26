package jsorgensen.com.control4weatherapp.Views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import jsorgensen.com.control4weatherapp.R;


public class CityForecastViewHolder extends RecyclerView.ViewHolder {

    public CityForecastViewHolder(View itemView){
        super(itemView);

        layout = (ConstraintLayout) itemView;
        temperatureTextView = itemView.findViewById(R.id.forecastHeadlineTempTextView);
        cityTextView = itemView.findViewById(R.id.forecastHeadlineCityTextView);
        iconImageView = itemView.findViewById(R.id.forecastHeadlineIconImageView);
    }

    public ConstraintLayout layout;
    private TextView temperatureTextView;
    private TextView cityTextView;
    public ImageView iconImageView;

    public void setTemperatureText(String temp){
        temperatureTextView.setText(temp);
    }

    public void setCityText(String city){
        cityTextView.setText(city);
    }
}
