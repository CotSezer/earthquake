package com.sezer.earthquake.model;

import java.util.ArrayList;

public class Response {
    public String type;
    public Metadata metadata;
    public ArrayList<Feature> features;
    public ArrayList<Double> bbox;

    public String getType() {
        return type;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
    }

    public ArrayList<Double> getBbox() {
        return bbox;
    }
}
