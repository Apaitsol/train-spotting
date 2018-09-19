package com.example.admin.trainspotting;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.trainspotting.Classes.VolleyParams;
import com.example.admin.trainspotting.Interfaces.IVolleyReceiver;

import org.json.JSONArray;

public class VolleyTask extends AsyncTask<VolleyParams, Void, String> {
    private IVolleyReceiver mResultCallback = null;
    private RequestQueue mQueue;
    private Context mContext;

    public VolleyTask(IVolleyReceiver resultCallback, Context context) {
        mResultCallback = resultCallback;
        mContext = context;
        mQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    private <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    protected String doInBackground(VolleyParams... params) {

        final String url = params[0].getUrl();
        final String requestType = params[0].getRequestType();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                if(mResultCallback != null) {
                    mResultCallback.notifySuccess(requestType, response);
                }
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null) {
                            mResultCallback.notifyError(requestType, error);
                        }
                    }
                });

        addToRequestQueue(jsonArrayRequest);
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

}
