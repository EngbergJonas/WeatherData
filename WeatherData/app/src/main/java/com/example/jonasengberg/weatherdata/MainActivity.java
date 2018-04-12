package com.example.jonasengberg.weatherdata;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText input;
    String result = "";
    String strUrl = "";
    String answer = "";
    ArrayList<String> jsondata = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        input = (EditText)findViewById(R.id.inputText);
    }

    public void onClick(View v)
    {
        answer = input.getText().toString();
        strUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + answer + "&appid=7df44667e4be542fd638d0d19df66f63&units=metric";
        new getWeather().execute();
    }

    public void onShow(View v)
    {
        try
        {
            JSONObject jsObj = new JSONObject(result).getJSONObject("main");
            String temp = jsObj.getString("temp");

            jsObj = new JSONObject(result).getJSONObject("wind");
            String wind = jsObj.getString("speed");

            jsObj = new JSONObject(result).getJSONObject("sys");
            String sunrise = jsObj.getString("sunrise");
            String sunset = jsObj.getString("sunset");

            long riseTime = Long.parseLong(sunrise) * 1000L;
            long setTime = Long.parseLong(sunset) * 1000L;

            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            String riseDate = format.format((riseTime));
            String setDate = format.format((setTime));

            jsondata.add("The weather in: " + answer +"\n\nThe temperature is: " + temp.toString() + "\nThe windspeed is: " + wind.toString() +" m/s"+ "\nThe sunrise is at: " + riseDate.toString() + "\nThe sundown is at: " + setDate.toString());

            }catch(Exception e){
                e.printStackTrace();
            }

            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jsondata);
            ListView lv = findViewById(R.id.listView);
            lv.setAdapter(itemsAdapter);
    }

    public class getWeather extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params)
        {
            try
            {
                URL url = new URL(strUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                con.setRequestMethod("GET");
                con.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String response = reader.readLine();

                result = response;

            }
            catch(Exception e)
            {
                System.out.println(e);
            }
            return null;
        }
    }
}
