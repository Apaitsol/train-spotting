package com.example.admin.trainspotting;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.admin.trainspotting.Classes.Station;
import com.example.admin.trainspotting.Classes.Train;
import com.example.admin.trainspotting.Classes.VolleyParams;
import com.example.admin.trainspotting.Interfaces.IVolleyReceiver;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener, IVolleyReceiver {

    FragmentManager manager = getSupportFragmentManager();
    ItemFragment fragment = ItemFragment.newInstance();

    TextView displayDate;
    Button bDepartureStation;
    Button bDestinationStation;
    Button bTrains;
    ListView trainListView;

    private Boolean buttonPressed;

    //Valituttujen asemien koodit
    String mDepartureStation;
    String mDestinationStation;

    public static List<Station> stationList = new ArrayList<>();
    public List<Train> trainList = new ArrayList<>();

    TrainListAdapter trainAdapter;

    private Calendar calendar;
    private String sDate;
    private SimpleDateFormat httpDate;
    private SimpleDateFormat showDate;

    VolleyParams params;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);

        gson = new GsonBuilder().create();

        /* TÄNNE UI MÄÄRITYKSET*/
        displayDate = findViewById(R.id.displayDate);
        bDepartureStation = findViewById(R.id.departureStationSelected);
        bDestinationStation = findViewById(R.id.destinationStationSelected);
        bTrains = findViewById(R.id.trainsButton);
        bTrains.setEnabled(false);

        trainListView = findViewById(R.id.trainListView);

        trainAdapter = new TrainListAdapter(this, R.layout.station_list_item, trainList);
        trainListView.setAdapter(trainAdapter);

        trainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Pass the selected train in the intent
                Intent intent = new Intent(getApplicationContext(), SingleTrainActivity.class);
                intent.putExtra("TRAIN", trainList.get(position));
                intent.putExtra("STATIONS", (Serializable) stationList);
                startActivity(intent);
            }
        });

        params = new VolleyParams("https://rata.digitraffic.fi/api/v1/metadata/stations", IVolleyReceiver.STATIONS);
        VolleyTask volleyService = new VolleyTask(this, this);
        volleyService.execute(params);
    }

    @Override
    protected void onStart() {
        super.onStart();

        calendar = Calendar.getInstance();
        httpDate = new SimpleDateFormat("yyyy-MM-dd");
        showDate = new SimpleDateFormat("dd.MM.yyyy");

        displayDate.setText(showDate.format(calendar.getTime()));
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
                    trainAdapter.setDestinationStation(mStation.getStationName(), mStation.getStationShortCode());

                } else {
                    bDepartureStation.setText(mStationDisplay);
                    mDepartureStation = mStation.getStationShortCode();
                    trainAdapter.setDepartingStation(mStation.getStationName(), mStation.getStationShortCode());
                }

                if(mDestinationStation != null && mDepartureStation != null) {
                    bTrains.setEnabled(true);
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

    public void findTrains(View view) {
        if(mDepartureStation != null && mDestinationStation != null) {
            Log.i("ASD", "ASD");
            params.setUrl("https://rata.digitraffic.fi/api/v1/live-trains/station/" + mDepartureStation + "/" + mDestinationStation + "?include_nonstopping=false&limit=20");
            params.setRequestType(IVolleyReceiver.TRAINS);

            VolleyTask getTrain = new VolleyTask(this, this);
            getTrain.execute(params);

        }
    }

    @Override
    public void notifySuccess(String requestType, JSONArray response) {
        Type listType;

        switch(requestType)
        {
            case IVolleyReceiver.STATIONS:
                listType = new TypeToken<ArrayList<Station>>(){}.getType();
                stationList = gson.fromJson(response.toString(), listType);

                Iterator<Station> i = stationList.iterator();
                while(i.hasNext()) {
                    Station s = i.next();
                    if(!s.isPassengerTraffic()) {
                        i.remove();
                    }
                }
                break;

            case IVolleyReceiver.TRAINS:
                listType = new TypeToken<List<Train>>(){}.getType();
                trainAdapter.clear();
                trainList.addAll((ArrayList<Train>) gson.fromJson(response.toString(), listType));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        trainAdapter.notifyDataSetChanged();
                    }
                });
                break;
        }
    }

    @Override
    public void notifySuccessObject(String requestType, JSONObject response) {

    }

    @Override
    public void notifyError(String requestType, VolleyError error) {
        Log.i("ERROR", error.getMessage());
        trainAdapter.clear();
        Toast.makeText(this, "Junia ei löytynyt", Toast.LENGTH_LONG).show();
    }

}
