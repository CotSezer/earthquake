package com.sezer.earthquake.model;

import java.util.ArrayList;

public class Geometry{
    public String type;
    public ArrayList<Double> coordinates;

    public String getType() {
        return type;
    }

    public ArrayList<Double> getCoordinates() {
        return coordinates;
    }
}