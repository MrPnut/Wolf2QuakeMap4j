package com.ncsoftworks.wmc.bean;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Wolfesnstein 3D map header data used to load the maps themselves
 */

public class WolfensteinMapHeader {
    private int rlewTag;
    private int[] headerOffsets;

    public WolfensteinMapHeader(int rlewTag, int[] offsets) {
        this.rlewTag = rlewTag;
        this.headerOffsets = offsets;
    }

    public int getRlewTag() {
        return rlewTag;
    }

    public int[] getHeaderOffsets() {
        return headerOffsets;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("RLEtag\n");
        stringBuilder.append("======\n");
        stringBuilder.append(String.format("%x\n\n", this.getRlewTag()));
        stringBuilder.append("Offsets\n");
        stringBuilder.append("=======\n");

        for (Integer offset : this.getHeaderOffsets()) {
            stringBuilder.append(Integer.toHexString(offset)).append(" ");
        }

        return stringBuilder.toString();
    }

}


