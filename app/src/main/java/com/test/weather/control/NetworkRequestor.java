package com.test.weather.control;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.weather.R;
import com.test.weather.model.WeatherObject;
import com.test.weather.model.data.FiveDaysWeatherData;
import com.test.weather.model.data.ForecastData;
import com.test.weather.model.data.LocationMapObjectData;
import com.test.weather.util.Constants;
import com.test.weather.view.UpdateUIHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NetworkRequestor {

	private static final String TAG = NetworkRequestor.class.getSimpleName();
	private Context context;
	private static final int CONNECTION_TIMEOUT = 15000;
	private static final int DATARETRIEVAL_TIMEOUT = 60000;
	private UpdateUIHandler uiHandler;
	private RequestQueue queue;
	private static NetworkRequestor networkRequestor;

	private NetworkRequestor(Context context, UpdateUIHandler uiHandler){
		this.context = context;
		this.uiHandler = uiHandler;
		queue = Volley.newRequestQueue(context);
	}

	public static NetworkRequestor getNetworkRequestor(Context context, UpdateUIHandler uiHandler){
		if(networkRequestor == null){
			networkRequestor = new NetworkRequestor(context, uiHandler);
		}

		return networkRequestor;
	}

	public void makeJsonObject(final String apiUrl){
		StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "Response " + response);
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				LocationMapObjectData locationMapObject = gson.fromJson(response, LocationMapObjectData.class);
				if (null == locationMapObject) {
					uiHandler.updateErrorDetails();
				} else {
					uiHandler.updatePresentDayDetails(locationMapObject);
					fiveDaysApiJsonObjectCall(locationMapObject.getName());
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "Error " + error.getMessage());
			}
		});
		queue.add(stringRequest);
	}

	private void fiveDaysApiJsonObjectCall(String city){
		String apiUrl = "http://api.openweathermap.org/data/2.5/forecast?q="+city+ "&APPID="+ Constants.API_KEY+"&units=metric";
		final List<WeatherObject> daysOfTheWeek = new ArrayList<WeatherObject>();
		StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "More days Response: " + response);
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.create();
				ForecastData forecast = gson.fromJson(response, ForecastData.class);
				if (null == forecast) {
					uiHandler.updateErrorDetails();
				} else {
					int[] everyday = new int[]{0,0,0,0,0,0,0};
					List<FiveDaysWeatherData> weatherInfo = forecast.getList();
					if(null != weatherInfo){
						for(int i = 0; i < weatherInfo.size(); i++){
							String time = weatherInfo.get(i).getDateText();
							String shortDay = convertTimeToDay(time);
							String temp = weatherInfo.get(i).getMain().getTemp();
							String tempMin = weatherInfo.get(i).getMain().getTemp_min();

							if(convertTimeToDay(time).equals("Mon") && everyday[0] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[0] = 1;
							}
							if(convertTimeToDay(time).equals("Tue") && everyday[1] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[1] = 1;
							}
							if(convertTimeToDay(time).equals("Wed") && everyday[2] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[2] = 1;
							}
							if(convertTimeToDay(time).equals("Thu") && everyday[3] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[3] = 1;
							}
							if(convertTimeToDay(time).equals("Fri") && everyday[4] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[4] = 1;
							}
							if(convertTimeToDay(time).equals("Sat") && everyday[5] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[5] = 1;
							}
							if(convertTimeToDay(time).equals("Sun") && everyday[6] < 1){
								daysOfTheWeek.add(new WeatherObject(shortDay, R.drawable.small_weather_icon, temp, tempMin));
								everyday[6] = 1;
							}
							uiHandler.updateNextFiveDaysDetails(daysOfTheWeek);
						}
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d(TAG, "Error " + error.getMessage());
			}
		});
		queue.add(stringRequest);
	}

	private String convertTimeToDay(String time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SSSS", Locale.getDefault());
		String days = "";
		try {
			Date date = format.parse(time);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			days = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days;
	}
}