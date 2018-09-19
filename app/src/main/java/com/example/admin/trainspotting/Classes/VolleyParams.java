package com.example.admin.trainspotting.Classes;

public class VolleyParams {
    private String url;
    private String requestType;

    public VolleyParams(String url, String requestType) {
        setUrl(url);
        setRequestType(requestType);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }


}
