package com.example.admin.trainspotting;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.admin.trainspotting.Classes.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    FragmentManager manager = getSupportFragmentManager();
    ItemFragment fragment = ItemFragment.newInstance();
    RequestQueue queue;
    String _url;

    Button bDepartureStation;
    Button bDestinationStation;
    private Boolean buttonPressed;
    String mDepartureStation;
    String mDestinationStation;

    public static List<Station> stationList;

    private Calendar calendar;
    private String sDate;
    private SimpleDateFormat httpDate;
    private SimpleDateFormat displayDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _url = "https://rata.digitraffic.fi/api/v1/";
        queue = HTTPTrain.getInstance(this.getApplicationContext()).getRequestQueue();
        setContentView(R.layout.activity_search);
        bDepartureStation = findViewById(R.id.departureStationSelected);
        bDestinationStation = findViewById(R.id.destinationStationSelected);
        getStations();
    }

    @Override
    protected void onStart() {
        super.onStart();

        calendar = Calendar.getInstance();
        httpDate = new SimpleDateFormat("yyyy-MM-dd");
        displayDate = new SimpleDateFormat("dd.MM.yyyy");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onListFragmentInteraction(Station station) {
        // Log.i("A", station.getStationShortCode());
        final Station mStation = station;
        final String mStationDisplay = station.getStationName().toString();


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(buttonPressed) {
                    bDestinationStation.setText(mStationDisplay);
                    mDestinationStation = mStation.getStationShortCode();

                } else {
                    bDepartureStation.setText(mStationDisplay);
                    mDepartureStation = mStation.getStationShortCode();
                }
                fragment.dismiss();
            }
        });

    }

    public void selectDepartureStation(View view) {

        fragment.show(manager, "Stationlist");
        buttonPressed = false;
    }

    public void selectDestinationStation(View view) {
        fragment.show(manager, "Stationlist");
        buttonPressed = true;
    }

    public void getStations() {
        String url = _url + "metadata/stations";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response: ", response.toString());
                stationList = new ArrayList<>();
                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<ArrayList<Station>>(){}.getType();
                stationList = gson.fromJson(response.toString(), listType);
                Log.i("LIST: ", stationList.toString());
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        HTTPTrain.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

    public void getTrainLocation(String TrainNumber) {
        String url = _url + "train-locations/latest/" + TrainNumber;

    }

    public void getTrainInformation(String TrainNumber ) {
        String url = _url + "train-tracking/latest/" + TrainNumber;
    }

    public void getStationComposition(View view) {
        String url = _url + "live-trains/station/" + mDepartureStation + '/' + mDestinationStation;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                Log.i("Response: ", response.toString());
                stationList = new ArrayList<>();
                Gson gson = new GsonBuilder().create();
                // Type listType = new TypeToken<ArrayList<Station>>(){}.getType();
                // stationList = gson.fromJson(response.toString(), listType);
                Log.i("LIST: ", stationList.toString());
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        HTTPTrain.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

}
