package com.example.admin.trainspotting;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SearchActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    FragmentManager manager = getSupportFragmentManager();
    ItemFragment fragment = ItemFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    public void onListFragmentInteraction() {

    }

    public void selectDepartureStation(View view) {

        fragment.show(manager, "Stationlist");

    }

    public void selectDestinationStation(View view) {
        fragment.show(manager, "Stationlist");
    }
}
