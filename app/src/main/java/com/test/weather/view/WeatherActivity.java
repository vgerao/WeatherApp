package com.test.weather.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.test.weather.R;
import com.test.weather.control.NetworkRequestor;
import com.test.weather.model.WeatherObject;
import com.test.weather.model.data.LocationMapObjectData;
import com.test.weather.util.Constants;
import com.test.weather.view.adapters.HorizontalAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity implements LocationListener, UpdateUIHandler {

    private RecyclerView recyclerView;
    private HorizontalAdapter recyclerViewAdapter;
    private TextView cityCountry;
    private TextView currentDate;
    private TextView circleTitle;
    private TextView windResult;
    private TextView sunsetTextView;
    private TextView sunRiseTextView;
    private TextView humidityResult;
    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    private  ImageButton addLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        cityCountry = (TextView)findViewById(R.id.city_country);
        currentDate = (TextView)findViewById(R.id.current_date);
        circleTitle = (TextView)findViewById(R.id.mark);
        windResult = (TextView)findViewById(R.id.wind_result);
        humidityResult = (TextView)findViewById(R.id.humidity_result);
        sunRiseTextView = (TextView)findViewById(R.id.sunrise_result);
        sunsetTextView = (TextView)findViewById(R.id.sunset_result);

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(WeatherActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
                // make API call with longitude and latitude
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location == null) {
                        makeServerCall(17.3850, 78.4867, null);
                    } else {
                        makeServerCall(location.getLatitude(), location.getLongitude(), null);
                    }
                }
        }

        addLocation = (ImageButton) findViewById(R.id.add_location);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchCityActivity();
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView = (RecyclerView)findViewById(R.id.weather_daily_list);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if(location == null) {
                            //By Default get the Hyderabd location Data
                            makeServerCall(17.3850, 78.4867, null);
                        }else {
                            makeServerCall(location.getLatitude(), location.getLongitude(), null);
                        }
                    }else{
                        //By Default get the Hyderabd location Data
                        makeServerCall(17.3850, 78.4867, null);
                    }
                }
            }else{
                Toast.makeText(WeatherActivity.this, getString(R.string.permission_notice), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void makeServerCall(double latitude, double longitude, String cityName){
        String appUrl = Constants.URL;
        if(cityName != null){
            appUrl =appUrl + "q="+cityName+"&APPID="+ Constants.API_KEY+"&units=metric";
        }else {
            appUrl = appUrl + "lat=" + latitude + "&lon=" + longitude + "&APPID=" + Constants.API_KEY + "&units=metric";
        }

        NetworkRequestor networkRequestor = NetworkRequestor.getNetworkRequestor(this, this);
        networkRequestor.makeJsonObject(appUrl);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showErrorAlertToUser("GPS is disabled in your device. Would you like to enable it?", "Goto Settings Page To Enable GPS", true);
        }
    }

    private void showErrorAlertToUser(String errorMessage, String buttonText, boolean showNegButton) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errorMessage)
                .setCancelable(false)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        if(showNegButton) {
            alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
        }
        AlertDialog alert = alertDialogBuilder.create();
        alert.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        alert.show();
    }

    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    private void showSearchCityActivity() {

        final View dialogView = View.inflate(this, R.layout.location_dialog,null);

        final Dialog dialog = new Dialog(this,R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        final EditText enterLocationET = (EditText)dialog.findViewById(R.id.enter_locationET);
        Button searchButton = (Button)dialog.findViewById(R.id.search_location_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(dialogView, false, dialog, enterLocationET.getText().toString());
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView, true, null, "");
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){
                    revealShow(dialogView, false, dialog, "");
                    return true;
                }
                return false;
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();
    }

    private void revealShow(View dialogView, boolean showActivity, final Dialog dialog, final String newLocText) {

        final View view = dialogView.findViewById(R.id.dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (addLocation.getX() + (addLocation.getWidth()/2));
        int cy = (int) (addLocation.getY())+ addLocation.getHeight() + 56;


        if(showActivity){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(1000);
            revealAnimator.start();

        } else {
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            makeServerCall(0, 0, newLocText);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.setDuration(1000);
            anim.start();
        }

    }

    @Override
    public void updatePresentDayDetails(Object object) {
        if(object instanceof LocationMapObjectData) {
            LocationMapObjectData locationMapObject = (LocationMapObjectData)object;
            String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
            String todayDate = getTodayDateInStringFormat();
            Long tempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
            String weatherTemp = String.valueOf(tempVal) + "°";
            String windSpeed = locationMapObject.getWind().getSpeed();
            String humidityValue = locationMapObject.getMain().getHumudity();

            String sunsetResult = locationMapObject.getSys().getSunset();
            String sunRiseResult = locationMapObject.getSys().getSunrise();

            cityCountry.setText(Html.fromHtml(city));
            currentDate.setText(Html.fromHtml(todayDate));
            circleTitle.setText(Html.fromHtml(weatherTemp).toString());
            windResult.setText(Html.fromHtml(windSpeed) + " km/h");
            humidityResult.setText(Html.fromHtml(humidityValue) + " %");

            sunRiseTextView.setText(Html.fromHtml(sunRiseResult));
            sunsetTextView.setText(Html.fromHtml(sunsetResult));
        }
    }

    @Override
    public void updateNextFiveDaysDetails(List<WeatherObject> daysOfTheWeek) {
        recyclerViewAdapter = new HorizontalAdapter(daysOfTheWeek, WeatherActivity.this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    public void updateErrorDetails() {
        showErrorAlertToUser("Some thing went wrong, Please try again", "Ok", false);
    }
}
