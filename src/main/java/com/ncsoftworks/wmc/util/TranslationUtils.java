package com.ncsoftworks.wmc.util;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Miscellaneous static utilities for conversion
 */

public class TranslationUtils {

    private static TranslationUtils instance = null;

    private List<Integer> tileToBoxMultipliers = buildTileToBoxMultipliers();

    private TranslationUtils() {}

    public static TranslationUtils getInstance() {
        if (instance == null) {
            instance = new TranslationUtils();
        }

        return instance;
    }

    public List<Integer> getTileToBoxMultipliers() {
        return tileToBoxMultipliers;
    }

    /**
     * The X, Y, and Z multipliers per side in order to build a 3D box from a 2D tile
     *
     * Given a tile (with x and y):
     *
     *      Side # (0-6) -- sn:
     *          For each plane
     *              x = (64 * tile.x) + (64 * multiplierList.get((sn * 6) + (0))
     *              y = (64 * multiplierList.get((sn * 6) + (1))
     *              z = (64 * tile.y + (64 * multiplierList.get(2)))
     *
     * @return
     */

    private static List<Integer> buildTileToBoxMultipliers() {

        List<Integer> multipliers = Arrays.asList(
                0, 1, 1, 1, 1, 1, 1, 0, 1,
                0, 0, 0, 1, 0, 0, 1, 1, 0,
                0, 1, 1, 0, 0, 1, 0, 0, 0,
                1, 1, 0, 1, 0, 0, 1, 0, 1,
                1, 1, 1, 0, 1, 1, 0, 1, 0,
                1, 0, 0, 0, 0, 0, 0, 0, 1
        );

        return Collections.unmodifiableList(multipliers);
    }
}
