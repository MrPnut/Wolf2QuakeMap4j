package com.ncsoftworks.wmc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class Plane<T extends Point> {
    private List<T> points = new ArrayList<T>(3);

    public void addPoint(T point) {
        points.add(point);
    }

    public List<T> getPoints() {
        return points;
    }
}
