package com.example.admin.trainspotting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.trainspotting.Classes.TimeTableRow;
import com.example.admin.trainspotting.Classes.Train;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TrainListAdapter extends ArrayAdapter<Train> {

    private Context mContext;
    private int resourceLayout;
    private String departingStation;
    private String destinationStation;

    DateFormat dateFormat = new SimpleDateFormat("hh:mm");


    public TrainListAdapter(Context context, int resource, List<Train> trains) {
        super(context, resource, trains);
        this.resourceLayout = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater viewInflater;
            viewInflater = LayoutInflater.from(mContext);
            view = viewInflater.inflate(resourceLayout, null);
        }

        Train train = getItem(position);

        if(train != null) {
            TextView textType = view.findViewById(R.id.trainType);
            TextView textCategory =  view.findViewById(R.id.trainCategory);
            TextView alertInfoBox = view.findViewById(R.id.alertInfoBox);
            TextView minuteAmount = view.findViewById(R.id.minuteAmountInfo);
            TextView minuteText = view.findViewById(R.id.minuteText);
            TextView departureStation = view.findViewById(R.id.departureStationInfo);
            TextView arrivalStation = view.findViewById(R.id.arrivalStationInfo);
            TextView departureTime = view.findViewById(R.id.departureTimeInfo);
            TextView destinationTime = view.findViewById(R.id.destinationTimeInfo);

            if(textType != null) {
                textType.setText(train.getTrainNumber());
            }

            if(textCategory != null) {
                textCategory.setText(train.getTrainType());
            }

            if(alertInfoBox != null && train.isCancelled() == true) {
                alertInfoBox.setText("PERUTTU");
                alertInfoBox.setVisibility(View.VISIBLE);
            }

            if(minuteAmount != null) {
                minuteAmount.setText("d");
                minuteAmount.setVisibility(View.VISIBLE);
                minuteText.setVisibility(View.VISIBLE);
            }

            if(departureStation != null) {
                departureStation.setText(train.getDepartureStation());
            }

            if(arrivalStation != null) {
                arrivalStation.setText(train.getDestinationStation());
            }

            if(departureTime != null && departingStation != null) {

                TimeTableRow stop = train.getDepartingStation(departingStation);
                Log.i("STATION: ", stop.toString());
                String time = dateFormat.format(stop.getScheduledTime());
                departureTime.setText(' ' + departingStation + ": " +  time);
            }

            if(destinationTime != null && destinationStation != null) {
                TimeTableRow stop = train.getArrivingStation(destinationStation);
                String time = dateFormat.format(stop.getScheduledTime());
                destinationTime.setText(' ' + destinationStation + ": " + time);
            }

        }

        return view;
    }

    public void setStations(String departingStation, String destinationStation) {
        this.departingStation = departingStation;
        this.destinationStation = destinationStation;
    }
}
