package com.ncsoftworks.wmc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class QuakeBrush {
    private List<Plane> planes;
    private String texture;
    private int rotAngle;
    private float xScale;
    private float yScale;

    public QuakeBrush() {
        this.planes = new ArrayList<Plane>(6);
    }

    public void addPlane(Plane plane) {
        planes.add(plane);
    }

    public List<Plane> getPlanes() {
        return planes;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }

}

