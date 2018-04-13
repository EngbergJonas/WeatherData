package com.example.jonasengberg.weatherdata;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class UrlRequest extends AsyncTask <String, String, Weather>
{

    public Weather parseJson(String jsonString)
    {
        try
        {
            JSONObject jsonObj = new JSONObject(jsonString);

            return new Weather(jsonObj.getJSONObject("main").getDouble("temp"),
                        jsonObj.getJSONObject("wind").getDouble("speed"),
                        new Date(((long) jsonObj.getJSONObject("sys").getLong("sunset") + (60l * 60l * 3l) ) * 1000l),
                        new Date(((long) jsonObj.getJSONObject("sys").getLong("sunrise") + (60l * 60l * 3l) ) * 1000l));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Weather doInBackground(String... strings) {

            try
            {
                URL url = new URL(strings[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("GET");
                con.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = reader.readLine();

                return parseJson(response);

            }
            catch(Exception e)
            {
                System.out.println(e);
            }

        return null;

    }
}
