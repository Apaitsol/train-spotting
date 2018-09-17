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
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.view.View;

public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    FragmentManager manager = getSupportFragmentManager();
    ItemFragment fragment = ItemFragment.newInstance();

    RequestQueue queue;
    String _url;

    private ArrayList<Station> stationList;

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
    public void onListFragmentInteraction() { }

    public void selectDepartureStation(View view) {
        fragment.show(manager, "Stationlist");
    }

    public void selectDestinationStation(View view) {
        fragment.show(manager, "Stationlist");
    }

    public void getStations() {
        String url = _url + "metadata/stations";
        Log.i("PERSE", url);

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
        Log.i("PERSE", url);

    }

    public void getTrainInformation(String TrainNumber ) {
        String url = _url + "train-tracking/latest/" + TrainNumber;
        Log.i("PERSE", url);
    }

    public void getStationComposition(String departureStation, String arrivalStation) {
        String url = _url + "live-trains/station" + departureStation + '/' + arrivalStation;
        Log.i("PERSE", url);

    }
}
