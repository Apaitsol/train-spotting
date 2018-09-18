package com.example.admin.trainspotting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SingleTrainActivity extends AppCompatActivity {

    private static final String TRAIN_NUMBER ="TRAIN_NUMBER";
    private static final String TRAIN_TYPE ="TRAIN_TYPE";
    private static final String DEPARTURE_STATION ="DEPARTURE_STATION";
    private static final String DESTINATION_STATION ="DESTINATION_STATION";

    TextView trainNumberTextView;
    TextView trainTypeTextView;
    TextView departureStationTextView;
    TextView destinationStationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_train);

        // GET DATA FROM INTENT
        Intent intent = getIntent();
        String trainNumber = intent.getStringExtra(TRAIN_NUMBER);
        String trainType = intent.getStringExtra(TRAIN_TYPE);
        String departureStation = intent.getStringExtra(DEPARTURE_STATION);
        String destinationStation = intent.getStringExtra(DESTINATION_STATION);

        // REFERENCE UI ELEMENTS
        trainNumberTextView = findViewById(R.id.trainNumber);
        trainTypeTextView = findViewById(R.id.trainType);
        departureStationTextView = findViewById(R.id.departureStation);
        destinationStationTextView = findViewById(R.id.destinationStation);

        // SET TEXTS
        trainNumberTextView.setText(trainNumber);
        trainTypeTextView.setText(trainType);
        departureStationTextView.setText(departureStation);
        destinationStationTextView.setText(destinationStation);


    }
}
