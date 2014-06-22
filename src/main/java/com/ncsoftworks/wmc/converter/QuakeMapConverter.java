package com.ncsoftworks.wmc.converter;

import com.ncsoftworks.wmc.bean.*;
import com.ncsoftworks.wmc.exception.MapConverterException;
import com.ncsoftworks.wmc.util.TranslationUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Converts a Wolfenstein 3D map to a Quake map
 */

public class QuakeMapConverter {

    private static final Logger log = LoggerFactory.getLogger(QuakeMapConverter.class);

    private static List<Integer> multipliers = TranslationUtils.getInstance().getTileToBoxMultipliers();

    private final String wadName;
    private final Properties textureMappings;

    public QuakeMapConverter() {
        this("base.wad", new Properties());
    }

    public QuakeMapConverter(String wadName, Properties textureMappings) {
        this.wadName = wadName;
        this.textureMappings = textureMappings;
    }

    public QuakeMap convert(WolfensteinMap mapFile) throws MapConverterException {
        log.info("Converting map: " + mapFile.getName());

        QuakeMap quakeMap = new QuakeMap(0, 0, wadName);
        doConvert(mapFile, quakeMap);

        return quakeMap;
    }

    private void doConvert(WolfensteinMap in, QuakeMap out) {

        Map<Integer, Map<Integer, Integer>> tileData = in.getTileData();

        // TODO: More research needs to be done with this.  The map isn't "sealed" when the preprocess logic
        // runs.  It may have something to do with tile number values
        // tileData = preProcess(tileData);

        int mapHeight = in.getHeight();
        int mapWidth = in.getWidth();

        Set<Integer> missingMappings = new HashSet<Integer>();

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

                String texture = textureMappings.getProperty(String.valueOf(yEntry.getValue()));
                if (StringUtils.isBlank(texture)) {
                    missingMappings.add(yEntry.getValue());
                    texture = "default";
                }

                out.addBrush(convertTileToBrush(xEntry.getKey(),
                                                yEntry.getKey(),
                                                mapWidth,
                                                mapHeight,
                                                texture));
            }
        }

        if (missingMappings.size() > 0) {
            log.warn(String.format("Undefined texture mappings for tile numbers: %s", missingMappings));
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

    private QuakeBrush convertTileToBrush(int x,
                                          int y,
                                          int mapWidth,
                                          int mapHeight,
                                          String texture) {

        QuakeBrush brush = new QuakeBrush();

        for (int i=0; i<6; i++) {
            Plane<Point> plane = new Plane<Point>();

            for (int j=0; j<3; j++) {

                int index = (i * 9) + (j * 3);

                // Switched around intentionally
                int xValue = (0 - mapHeight * 64 / 2) + (64 * y + (64 * multipliers.get(index + 2)));
                int yValue = (mapWidth * 64 / 2) + (64 * x * -1) + (64 * multipliers.get(index));

                int zValue = (256 * multipliers.get(index + 1));

                plane.addPoint(new Point<Integer>(xValue, yValue, zValue));
            }

            brush.addPlane(plane);
            brush.setTexture(texture);
        }

        return brush;
    }
}
