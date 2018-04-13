package com.example.jonasengberg.weatherdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    EditText locationText;
    ListView listView;
    Weather weatherRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        locationText = (EditText)findViewById(R.id.inputText);
    }

    public void onClick(View v)
    {
        try
        {
            weatherRequest = new UrlRequest().execute("http://api.openweathermap.org/data/2.5/weather?q=" + locationText.getText().toString() + "&appid=7df44667e4be542fd638d0d19df66f63&units=metric").get();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        updateListView();
    }

    public void updateListView()
    {
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weatherRequest.getAsArray());
        ListView lv = findViewById(R.id.listView);
        lv.setAdapter(itemsAdapter);
    }
}


