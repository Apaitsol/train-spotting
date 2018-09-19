package com.example.admin.trainspotting;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.trainspotting.Classes.Station;
import com.example.admin.trainspotting.Classes.Train;

import java.util.Iterator;
import java.util.List;

public class SingleTrainActivity extends AppCompatActivity {

    private static final String TRAIN_NUMBER ="TRAIN_NUMBER";
    private static final String TRAIN_TYPE ="TRAIN_TYPE";
    private static final String DEPARTURE_STATION ="DEPARTURE_STATION";
    private static final String DESTINATION_STATION ="DESTINATION_STATION";

    TextView trainNumberTextView;
    TextView trainTypeTextView;
    TextView departureStationTextView;
    TextView destinationStationTextView;

    ListView timeTable;

    TimeTableAdapter ttAdapter;

    Train mTrain;
    List<Station> mStations;
    String mDepartureStation;
    String mDestinationStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_train);

        // GET DATA FROM INTENT
        Intent intent = getIntent();
        mTrain = (Train) intent.getSerializableExtra("TRAIN");
        mStations = (List<Station>) intent.getSerializableExtra("STATIONS");

        // REFERENCE UI ELEMENTS
        trainNumberTextView = findViewById(R.id.trainNumber);
        trainTypeTextView = findViewById(R.id.trainType);
        departureStationTextView = findViewById(R.id.departureStation);
        destinationStationTextView = findViewById(R.id.destinationStation);
        timeTable = findViewById(R.id.timeTableList);

        ttAdapter = new TimeTableAdapter(this, R.layout.timetablerow_single_item, mTrain.getTimeTableRows(), mStations);
        timeTable.setAdapter(ttAdapter);

        // SET TEXTS

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mDepartureStation = findStationName(mStations, mTrain.getDepartureStation());
                mDestinationStation = findStationName(mStations, mTrain.getDestinationStation());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        departureStationTextView.setText(mDepartureStation);
                        destinationStationTextView.setText(mDestinationStation);
                    }
                });
            }
        });


        trainNumberTextView.setText(mTrain.getTrainNumber());
        trainTypeTextView.setText(mTrain.getTrainType());

    }

    String findStationName(List<Station> stationList, String shortCode) {
        Iterator<Station> i = stationList.iterator();
        while(i.hasNext()) {
            Station s = i.next();
            if(s.getStationShortCode().equals(shortCode)) {
                return s.getStationName();
            }
        }
        return null;
    }
}
