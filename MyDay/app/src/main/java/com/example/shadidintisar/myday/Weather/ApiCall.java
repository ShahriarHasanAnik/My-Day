package com.example.shadidintisar.myday.Weather;

import android.os.AsyncTask;

import com.example.shadidintisar.weather_forcast.Model.Clouds;
import com.example.shadidintisar.weather_forcast.Model.Coord;
import com.example.shadidintisar.weather_forcast.Model.Main;
import com.example.shadidintisar.weather_forcast.Model.OpenWeatherMap;
import com.example.shadidintisar.weather_forcast.Model.Sys;
import com.example.shadidintisar.weather_forcast.Model.Weather;
import com.example.shadidintisar.weather_forcast.Model.Wind;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ApiCall {
    public static class GetWeather extends AsyncTask<String, Void, OpenWeatherMap>{

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

                    JSONObject weatherJSON = mainJson.getJSONObject("weather");
                    int id = weatherJSON.getInt("id");
                    String main = weatherJSON.getString("main");
                    String desc = weatherJSON.getString("description");
                    String icon = weatherJSON.getString("icon");


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


                    JSONObject cloudJSON = mainJson.getJSONObject("cloud");
                    int all = cloudJSON.getInt("all");



                    Clouds cloud=new Clouds(all);


                    JSONObject sysJSON = mainJson.getJSONObject("sys");
                    int type = sysJSON.getInt("type");
                    int id2 = sysJSON.getInt("id2");
                    double message = sysJSON.getDouble("message");
                    String country = sysJSON.getString("country");
                    double sunrise = sysJSON.getDouble("sunrise");
                    double sunset = sysJSON.getDouble("sunset");



                    Sys sys = new Sys(type,id2,message,country,sunrise,sunset);

                    String base= mainJSON.getString("base");
                    int visibility=mainJSON.getInt("visibility");
                    String name=mainJSON.getString("name");

                    OpenWeatherMap openWeatherMap=new OpenWeatherMap(coord,weather,mn,wind,cloud,sys,name);

                    return openWeatherMap;

                }
            }catch (IOException ignored) {
            } catch (JSONException ignored) {
            }

            return null;
        }
    }
}
