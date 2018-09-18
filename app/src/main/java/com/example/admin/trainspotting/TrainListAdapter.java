package com.example.admin.trainspotting;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.trainspotting.Classes.Train;

import java.util.List;

public class TrainListAdapter extends ArrayAdapter<Train> {

    private Context mContext;
    private int resourceLayout;

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

        if(train ==  null) {
            TextView textType = view.findViewById(R.id.trainType);
            TextView textCategory =  view.findViewById(R.id.trainCategory);

            if(textType != null) {
                textType.setText("asd");
            }

            if(textCategory != null) {
                textCategory.setText("dsa");
            }
        }
        
        return view;
    }
}
