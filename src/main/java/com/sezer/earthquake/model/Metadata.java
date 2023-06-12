package com.sezer.earthquake.model;

public class Metadata{
    public long generated;
    public String url;
    public String title;
    public int status;
    public String api;
    public int count;

    public long getGenerated() {
        return generated;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getApi() {
        return api;
    }

    public int getCount() {
        return count;
    }
}
