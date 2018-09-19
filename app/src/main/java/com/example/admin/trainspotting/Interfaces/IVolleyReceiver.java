package com.example.admin.trainspotting.Interfaces;

import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IVolleyReceiver {
    void notifySuccess(String requestType, JSONArray response);
    void notifySuccessObject(String requestType, JSONObject response);
    void notifyError(String requestType, VolleyError error);
    String STATIONS = "ARRAY_STATIONS";
    String TRAINS = "ARRAY_TRAINS";
}
