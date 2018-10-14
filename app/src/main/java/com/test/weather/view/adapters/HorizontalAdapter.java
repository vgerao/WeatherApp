package com.test.weather.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.weather.R;
import com.test.weather.model.WeatherObject;

import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<WeatherObject> dailyWeather;
    protected Context context;

    public HorizontalAdapter(List<WeatherObject> dailyWeather, Context context) {
        this.dailyWeather = dailyWeather;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerViewHolders viewHolder = null;
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_daily_list, parent, false);
        viewHolder = new RecyclerViewHolders(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        holder.dayOfWeekTv.setText(dailyWeather.get(position).getDayOfWeek());
        holder.weatherIconImgView.setImageResource(dailyWeather.get(position).getWeatherIcon());

        double mTemp = Double.parseDouble(dailyWeather.get(position).getWeatherResult());
        holder.weatherResultTv.setText(String.valueOf(Math.round(mTemp)) + "°");

        holder.weatherResultSmallTv.setText(dailyWeather.get(position).getWeatherResultSmall());
        holder.weatherResultSmallTv.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return dailyWeather.size();
    }
}
