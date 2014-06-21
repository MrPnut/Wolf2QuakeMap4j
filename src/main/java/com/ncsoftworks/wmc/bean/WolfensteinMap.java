package com.ncsoftworks.wmc.bean;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Immutable representation of a Wolfenstein map
 */

public class WolfensteinMap {
    private final List<Integer> planeStart;
    private final List<Integer> planeLength;

    private final Map<Integer, Map<Integer, Integer>> tileData;

    private final int width;
    private final int height;

    private final String name;

    WolfensteinMap(List<Integer> planeStart, List<Integer> planeLength, Map<Integer, Map<Integer, Integer>> tileData,
                   int width, int height, String name) {

        this.planeStart = planeStart;
        this.planeLength = planeLength;
        this.tileData = tileData;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public List<Integer> getPlaneStart() {
        return planeStart;
    }

    public List<Integer> getPlaneLength() {
        return planeLength;
    }

    public Map<Integer, Map<Integer, Integer>> getTileData() { return Collections.unmodifiableMap(tileData); }

    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Integer i : planeStart) {
            sb.append(i + " ");
        }

        sb.append("\n");

        for (Integer j : planeLength) {
            sb.append(j + " ");
        }

        sb.append("\n");

        sb.append(this.width + "\n");
        sb.append(this.height + "\n");
        sb.append(this.name);

        return sb.toString();
    }

    public static class Builder {
        private List<Integer> planeStart = new ArrayList<Integer>(3);
        private List<Integer> planeLength = new ArrayList<Integer>(3);
        private Map<Integer, Map<Integer, Integer>> tileData = new HashMap<Integer, Map<Integer, Integer>>();

        private int width;
        private int height;

        private String name;

        public Builder() {}

        public Builder(WolfensteinMap wolfensteinMap) {
            this.planeStart = new ArrayList<Integer>(wolfensteinMap.getPlaneStart());
            this.planeLength = new ArrayList<Integer>(wolfensteinMap.getPlaneLength());
            this.tileData
                    = wolfensteinMap.getTileData() == null
                        ? null : new HashMap<Integer, Map<Integer, Integer>>(wolfensteinMap.getTileData());
            this.width = wolfensteinMap.getWidth();
            this.height = wolfensteinMap.getHeight();
            this.name = wolfensteinMap.getName();
        }

        public Builder addPlaneStart(int planeStart) {
            this.planeStart.add(planeStart);
            return this;
        }

        public Builder addPlaneLength(int planeLength) {
            this.planeLength.add(planeLength);
            return this;
        }

        public Builder putTileData(int key, Map<Integer, Integer> tileData) {
            this.tileData.put(key, Collections.unmodifiableMap(tileData));
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public WolfensteinMap build() {
            return new WolfensteinMap(Collections.unmodifiableList(planeStart),
                                      Collections.unmodifiableList(planeLength),
                                      Collections.unmodifiableMap(tileData),
                                      width,
                                      height,
                                      name);
        }
    }
}
