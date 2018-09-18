package com.example.admin.trainspotting;

import android.content.Intent;
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
import com.example.admin.trainspotting.Classes.Train;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {

    FragmentManager manager = getSupportFragmentManager();
    ItemFragment fragment = ItemFragment.newInstance();
    RequestQueue queue;
    String _url;

    Button bDepartureStation;
    Button bDestinationStation;
    ListView trainListView;

    private Boolean buttonPressed;

    //Valituttujen asemien koodit
    String mDepartureStation;
    String mDestinationStation;

    public static List<Station> stationList;
    public List<Train> trainList = new ArrayList<>();

    TrainListAdapter trainAdapter;

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

        /* TÄNNE UI MÄÄRITYKSET*/
        bDepartureStation = findViewById(R.id.departureStationSelected);
        bDestinationStation = findViewById(R.id.destinationStationSelected);

        trainListView = findViewById(R.id.trainListView);

        trainAdapter = new TrainListAdapter(this, R.layout.station_list_item, trainList);
        trainListView.setAdapter(trainAdapter);

        trainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String trainNumber = ((TextView) view.findViewById(R.id.trainType)).getText().toString();
                String trainType = ((TextView) view.findViewById(R.id.trainCategory)).getText().toString();
                String departureStation = ((TextView) view.findViewById(R.id.departureStationInfo)).getText().toString();
                String destinationStation = ((TextView) view.findViewById(R.id.arrivalStationInfo)).getText().toString();

                Intent intent = new Intent(getApplicationContext(), SingleTrainActivity.class);
                intent.putExtra("TRAIN_NUMBER", trainNumber);
                intent.putExtra("TRAIN_TYPE", trainType);
                intent.putExtra("DEPARTURE_STATION", departureStation);
                intent.putExtra("DESTINATION_STATION", destinationStation);
                startActivity(intent);
            }
        });

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

    public void getTrains(View view) {
        boolean include_nonstopping = false;
        int limit = 20;
        String url = _url + "live-trains/station/" + mDepartureStation + '/' + mDestinationStation + "?include_nonstopping=" + String.valueOf(include_nonstopping) + "&limit=" + limit;

        trainAdapter.setStations(mDepartureStation, mDestinationStation);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Log.i("Response: ", response.toString());

                Gson gson = new GsonBuilder().create();
                Type listType = new TypeToken<List<Train>>(){}.getType();
                trainList.clear();
                trainList.addAll((ArrayList<Train>) gson.fromJson(response.toString(), listType));


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        trainAdapter.notifyDataSetChanged();
                    }
                });
                Log.i("LIST: ", trainList.toString());

            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        HTTPTrain.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }

}
