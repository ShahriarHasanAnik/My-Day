package com.example.shadidintisar.myday.Weather;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shadidintisar.weather_forcast.Model.Clouds;
import com.example.shadidintisar.weather_forcast.Model.Coord;
import com.example.shadidintisar.weather_forcast.Model.Main;
import com.example.shadidintisar.weather_forcast.Model.OpenWeatherMap;
import com.example.shadidintisar.weather_forcast.Model.Sys;
import com.example.shadidintisar.weather_forcast.Model.Weather;
import com.example.shadidintisar.weather_forcast.Model.Wind;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class weatherPage extends AppCompatActivity {

    TextView txtCity, txtLastUpdate, txtDescription, txtHumidity, txtTime, txtCelsius;
    ImageView imageView;

    Button getLoc;

    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLastUpdate = (TextView) findViewById(R.id.txtLastUpdate);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        //txtTime = (TextView) findViewById(R.id.txtTime);
        txtCelsius = (TextView) findViewById(R.id.txtCelsius);
        imageView = (ImageView) findViewById(R.id.imageView);

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);


        getLoc = findViewById(R.id.getLocationButton);
        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(weatherPage.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(weatherPage.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.getLastLocation().addOnSuccessListener(weatherPage.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            System.out.println(location.getLatitude()+">>>>>>>>>>>"+location.getLongitude());

                            weatherPage.GetWeather getWeather = new weatherPage.GetWeather(location.getLatitude(),location.getLongitude());
                            getWeather.execute();
                        }
                    }
                });

            }
        });




    }
    private void requestPermission(){
    ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);

}



    private class GetWeather extends AsyncTask<String, Void, OpenWeatherMap> {

        double lat,lon;

        public GetWeather(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        protected OpenWeatherMap doInBackground(String... strings) {

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(lat)+"&lon="+String.valueOf(lon)+"&appid=084c48f490a2c383dd36c1f1fd3fd960")
                    .get()
                    .build();

            try {
                Response response = client.newCall(request).execute();

                int statusCode = response.code();
                final String jsonData = response.body().string();

                System.out.println(jsonData);

                if (statusCode == 200) {
                    JSONObject mainJson = new JSONObject(jsonData);

                    JSONObject coordJSON = mainJson.getJSONObject("coord");
                    double lat = coordJSON.getDouble("lat");
                    double lon = coordJSON.getDouble("lon");

                    Coord coord = new Coord(lat,lon);

                    JSONArray weatherJSONArray = mainJson.getJSONArray("weather");
                    JSONObject weatherJSON = weatherJSONArray.getJSONObject(0);
                    int id = weatherJSON.getInt("id");
                    String main = weatherJSON.getString("main");
                    String desc = weatherJSON.getString("description");
                    String icon = weatherJSON.getString("icon");
                    System.out.println(desc);


                    Weather weather = new Weather(id,main,desc,icon);

                    JSONObject mainJSON = mainJson.getJSONObject("main");
                    double temp = mainJSON.getDouble("temp");
                    double pressure = mainJSON.getDouble("pressure");
                    int humidity = mainJSON.getInt("humidity");
                    double temp_min = mainJSON.getDouble("temp_min");
                    double temp_max = mainJSON.getDouble("temp_max");

                    Main mn = new Main(temp,pressure,humidity,temp_min,temp_max);


                    JSONObject windJSON = mainJson.getJSONObject("wind");
                    double speed = windJSON.getDouble("speed");
                    int deg = windJSON.getInt("deg");



                    Wind wind=new Wind(speed,deg);


                    JSONObject cloudJSON = mainJson.getJSONObject("clouds");
                    int all = cloudJSON.getInt("all");

                    Clouds cloud=new Clouds(all);


                    JSONObject sysJSON = mainJson.getJSONObject("sys");
                    int type = sysJSON.getInt("type");
                    int id2 = sysJSON.getInt("id");
                    double message = sysJSON.getDouble("message");
                    String country = sysJSON.getString("country");
                    double sunrise = sysJSON.getDouble("sunrise");
                    double sunset = sysJSON.getDouble("sunset");

                    Sys sys = new Sys(type,id2,message,country,sunrise,sunset);
/*
                    int visibility=mainJSON.getInt("visibility");
                    System.out.println(visibility);
                    String name=mainJSON.getString("name");

*/

                    OpenWeatherMap openWeatherMap=new OpenWeatherMap(coord,weather,mn,wind,cloud,sys,"tongi");


                    return openWeatherMap;

                }
            }catch (IOException ignored) {
            } catch (JSONException ignored) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(OpenWeatherMap openWeatherMap) {
           // System.out.println();

            txtCity.setText(openWeatherMap.getName()+","+openWeatherMap.getSys().getCountry());
            txtDescription.setText(openWeatherMap.getWeather().getDescriptionn());
            txtHumidity.setText(String.valueOf(openWeatherMap.getMain().getHumidity()+"%"));
            txtCelsius.setText(String.format("%.2f °C",openWeatherMap.getMain().getTemp()-273));
            Picasso.with(weatherPage.this).load("http://openweathermap.org/img/w/"+openWeatherMap.getWeather().getIcon()+".png")
                    .into(imageView);

        }
    }
}
