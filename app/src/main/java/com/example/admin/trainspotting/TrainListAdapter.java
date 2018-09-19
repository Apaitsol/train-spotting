package com.example.admin.trainspotting;

import android.content.Context;
import android.os.Build;
import android.support.v4.os.LocaleListCompat;
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
import java.util.Locale;

public class TrainListAdapter extends ArrayAdapter<Train> {

    private Context mContext;
    private int resourceLayout;
    private String departingStation;
    private String destinationStation;
    private String departingStationCode;
    private String destinationStationCode;

    public void setDepartingStation(String departingStation, String departingStationShort) {
        this.departingStation = departingStation;
        this.departingStationCode = departingStationShort;
        Log.i("TEST", departingStation);
    }

    public void setDestinationStation(String destinationStation, String destinationStationShort) {
        this.destinationStation = destinationStation;
        this.destinationStationCode = destinationStationShort;
        Log.i("TEST2", destinationStation);
    }


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
            TextView departureStationShort = view.findViewById(R.id.departureStationInfo);
            TextView destinationStationShort = view.findViewById(R.id.arrivalStationInfo);
            TextView tDepartureStation = view.findViewById(R.id.departureStation);
            TextView tDestinationStation = view.findViewById(R.id.destinationStation);
            TextView departureTime = view.findViewById(R.id.departureTime);
            TextView destinationTime = view.findViewById(R.id.destinationTime);

            if(textType != null) {
                textType.setText(train.getTrainNumber());
            }

            if(textCategory != null) {
                textCategory.setText(train.getTrainType());
            }

            if(departureStationShort != null) {
                departureStationShort.setText(train.getDepartureStation());
            }

            if(destinationStationShort != null) {
                destinationStationShort.setText(train.getDestinationStation());
            }

            if(tDepartureStation != null && departingStation != null) {

                TimeTableRow stop = train.getDepartingStation(departingStationCode);
                String time = new SimpleDateFormat().getTimeInstance(DateFormat.SHORT).format(stop.getScheduledTime());
                tDepartureStation.setText(departingStation);
                departureTime.setText(time);
            }

            if(tDestinationStation != null && destinationStation != null) {
                TimeTableRow stop = train.getArrivingStation(destinationStationCode);
                String time = new SimpleDateFormat().getTimeInstance(DateFormat.SHORT).format(stop.getScheduledTime());
                tDestinationStation.setText(destinationStation);
                destinationTime.setText(time);

            }

        }

        return view;
    }

}
