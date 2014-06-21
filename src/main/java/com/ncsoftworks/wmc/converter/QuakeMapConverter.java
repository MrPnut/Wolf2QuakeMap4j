package com.ncsoftworks.wmc.converter;

import com.ncsoftworks.wmc.bean.*;
import com.ncsoftworks.wmc.exception.MapConverterException;
import com.ncsoftworks.wmc.util.TranslationUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Converts a Wolfenstein 3D map to a Quake map
 */

public class QuakeMapConverter {

    private static List<Integer> multipliers = TranslationUtils.getInstance().getTileToBoxMultipliers();

    private String wadName = "base.wad";

    public QuakeMapConverter() {
        this(null);
    }

    public QuakeMapConverter(String wadName) {
        if (StringUtils.isNotBlank(wadName)) {
            this.wadName = wadName;
        }
    }

    public QuakeMap convert(WolfensteinMap mapFile) throws MapConverterException {

        QuakeMap quakeMap = new QuakeMap(0, 0, wadName);

        doConvert(mapFile, quakeMap);

        return quakeMap;
    }

    private static void doConvert(WolfensteinMap in, QuakeMap out) {

        Map<Integer, Map<Integer, Integer>> tileData = in.getTileData();

        // TODO: More research needs to be done with this.  The map isn't "sealed" when the preprocess logic
        // runs.  It may have something to do with tile number values

//       tileData = preProcess(tileData);

        for(Map.Entry<Integer, Map<Integer, Integer>> xEntry : tileData.entrySet()) {
            for (Map.Entry<Integer, Integer> yEntry : xEntry.getValue().entrySet()) {

                // Removed values from the preProcess operation
                if (yEntry.getValue() == null) {
                    continue;
                }

                if (yEntry.getValue() >= 90 && yEntry.getValue() <= 101) {
                    continue;
                }

                if (yEntry.getValue() >= 107) {
                    continue;
                }

                out.addBrush(convertTileToBrush(xEntry.getKey(), yEntry.getKey()));
            }
        }
    }



    private static Map<Integer, Map<Integer, Integer>> preProcess(Map<Integer, Map<Integer, Integer>> tileData) {
        // Skip tile conversion if they aren't at least two blocks away from a floor (can't see them)
        // Floors are tile values >= 107

        // TODO: Too much bit twiddling going on here

        // Incoming map is immutable, so just copy the GOOD values into the new map

        Map<Integer, Map<Integer, Integer>> processedMap = new HashMap<Integer, Map<Integer, Integer>>();

        for (int i=0; i<64; i++) {

            Map<Integer, Integer> row = new HashMap<Integer, Integer>();

            for (int j=0; j<64; j++) {

                if ( (i > 2) && (tileData.get(i - 2).get(j) >= 107) ) {
                    row.put(j, tileData.get(i).get(j));
                    continue;
                }

                if ( (i < 62) && (tileData.get(i + 2).get(j) >= 107) ) {
                    row.put(j, tileData.get(i).get(j));
                    continue;
                }


                if ( (j > 2) &&
                        (tileData.get(i).get(j - 2) != null) &&
                            (tileData.get(i).get(j - 2) >= 107) ) {
                    row.put(j, tileData.get(i).get(j));
                    continue;
                }

                if ( (j < 62) &&
                        (tileData.get(i).get(j + 2) != null) &&
                            (tileData.get(i).get(j + 2) >= 107)) {
                    row.put(j, tileData.get(i).get(j));
                    continue;
                }


            }

           processedMap.put(i, row);
        }

        return processedMap;
    }

    private static QuakeBrush convertTileToBrush(int x, int y) {
        QuakeBrush brush = new QuakeBrush();

        for (int i=0; i<6; i++) {
            Plane<Point> plane = new Plane<Point>();

            for (int j=0; j<3; j++) {

                int index = (i * 9) + (j * 3);

                // Switched around intentionally
                int xValue = (64 * y + (64 * multipliers.get(index + 2)));
                int yValue = (64 * x) + (64 * multipliers.get(index));

                int zValue = (256 * multipliers.get(index + 1));

                plane.addPoint(new Point<Integer>(xValue, yValue, zValue));
            }

            brush.addPlane(plane);
        }

        return brush;
    }
}
