package com.sezer.earthquake.model;

public class Feature{
    public String type;
    public Properties properties;
    public Geometry geometry;
    public String id;

    public String getType() {
        return type;
    }

    public Properties getProperties() {
        return properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getId() {
        return id;
    }
}

