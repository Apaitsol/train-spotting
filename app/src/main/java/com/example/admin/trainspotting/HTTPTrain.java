package com.example.admin.trainspotting;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class HTTPTrain {
    private static HTTPTrain mInstance;
    private String _url;
    private RequestQueue mQueue;
    private static Context mContext;

    private HTTPTrain(Context context) {
        mContext = context;
        mQueue = getRequestQueue();
    }

    public static synchronized HTTPTrain getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new HTTPTrain(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
