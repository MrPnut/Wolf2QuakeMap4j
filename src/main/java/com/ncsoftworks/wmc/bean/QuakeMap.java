package com.ncsoftworks.wmc.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class QuakeMap {
    public static final String WORLDSPAWN_CLASSNAME = "worldspawn";

    private int sounds;
    private int worldType;

    private String wad;

    private List<QuakeBrush> brushList;

    public QuakeMap(int sounds, int worldType, String wad) {
        this.sounds = sounds;
        this.worldType = worldType;
        this.wad = wad;
        this.brushList = new ArrayList<QuakeBrush>();
    }

    public void addBrush(QuakeBrush brush) {
        brushList.add(brush);
    }

    public int getSounds() {
        return sounds;
    }

    public int getWorldType() {
        return worldType;
    }

    public String getWad() {
        return wad;
    }

    public List<QuakeBrush> getBrushList() {
        return brushList;
    }
}
