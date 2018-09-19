package com.example.admin.trainspotting;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.trainspotting.Classes.Station;
import com.example.admin.trainspotting.Classes.TimeTableRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

public class TimeTableAdapter extends ArrayAdapter<TimeTableRow> {

    Context mContext;
    private int resourceLayout;

    public TimeTableAdapter(Context context, int resource, List<TimeTableRow> timeTable) {
        super(context, resource, timeTable);
        this.resourceLayout = resource;
        this.mContext = context;

        Iterator<TimeTableRow> i = timeTable.iterator();
        while(i.hasNext()) {
            TimeTableRow ttRow = i.next();
            if(!ttRow.isTrainStopping()) {
                i.remove();
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater viewInflater;
            viewInflater = LayoutInflater.from(mContext);
            view = viewInflater.inflate(resourceLayout, null);
        }

        TimeTableRow ttRow = getItem(position);

        if(ttRow != null && ttRow.isTrainStopping() == true) {
            TextView ttType = view.findViewById(R.id.timetableType);
            TextView ttTime = view.findViewById(R.id.timetableTime);
            TextView ttStation = view.findViewById(R.id.timetableStation);


            if(ttType != null) {
                switch(ttRow.getType()) {
                    case "ARRIVAL":
                        ttType.setTextColor(mContext.getColor(R.color.arrivalColor));
                        ttType.setText("Saapuu");
                        break;
                    case "DEPARTURE":
                        ttType.setTextColor(mContext.getColor(R.color.departureColor));
                        ttType.setText("Lähtee");
                        break;
                }
            }

            if(ttTime != null) {
                String time = DateFormat.getTimeInstance(DateFormat.SHORT).format(ttRow.getScheduledTime());
                ttTime.setText(time);
            }

            if(ttStation != null) {
                ttStation.setText(ttRow.getStationShortCode());
            }
        }
        return view;
    }
}
