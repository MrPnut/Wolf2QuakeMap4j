package com.ncsoftworks.wmc.loader;

import com.ncsoftworks.wmc.bean.WolfensteinMap;
import com.ncsoftworks.wmc.bean.WolfensteinMapHeader;
import com.ncsoftworks.wmc.exception.MapLoaderException;
import com.ncsoftworks.wmc.util.CompressionUtils;
import com.ncsoftworks.wmc.util.LittleEndianRandomAccessFile;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.Normalizer;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * A map loader for Wolfenstein 3D map files
 */

public class WolfensteinMapLoader {

    private static final int NUMBER_OF_PLANES = 2;
    private static final int SIZE_OF_MAP = (64 * 64) * 2;

    private final File gameMapsFile;

    private final WolfensteinMapHeader headerData;
    private final List<WolfensteinMap> mapInfo;

    public WolfensteinMapLoader(File mapheadFile, File gameMapsFile) throws MapLoaderException {
        verifyParameters(mapheadFile, gameMapsFile);

        this.gameMapsFile = gameMapsFile;
        this.headerData = loadMapHeaders(mapheadFile);
        this.mapInfo = loadMapData();
    }

    private void verifyParameters(File mapheadFile, File gameMapsFile) throws MapLoaderException {
        if (mapheadFile == null) {
            throw new MapLoaderException(new IllegalArgumentException("Map header cannot be null"));
        }

        if (gameMapsFile == null) {
            throw new MapLoaderException((new IllegalArgumentException("Gamemaps file cannot be null")));
        }
    }

    private static WolfensteinMapHeader loadMapHeaders(File mapHeaderFile) throws MapLoaderException {
        try {
            LittleEndianRandomAccessFile in = new LittleEndianRandomAccessFile(mapHeaderFile, "r");
            int RLEWtag = in.readUnsignedShort();

            int[] headerOffsets = new int[100];
            for (int i = 0; i < headerOffsets.length; i++) {
                headerOffsets[i] = in.readInt();
            }

            in.close();

            return new WolfensteinMapHeader(RLEWtag, headerOffsets);

        } catch (FileNotFoundException e) {
            throw new MapLoaderException("Caught exception while loading map headers", e);
        } catch (IOException e) {
            throw new MapLoaderException("Caught exception while loading map headers", e);
        }
    }

    private List<WolfensteinMap> loadMapData() throws MapLoaderException {
        List<WolfensteinMap> maps = new ArrayList<WolfensteinMap>();

        try {
            LittleEndianRandomAccessFile in = new LittleEndianRandomAccessFile(gameMapsFile, "r");

            int[] headerOffsets = headerData.getHeaderOffsets();

            for (int i = 0; i < 60; i++) {
                if (headerOffsets[i] <= 0) {
                    continue;
                }

                in.seek(headerOffsets[i]);

                WolfensteinMap.Builder builder = new WolfensteinMap.Builder();

                for (int j = 0; j < 3; j++) {
                    builder.addPlaneStart(in.readInt());
                }

                for (int k = 0; k < 3; k++) {
                    builder.addPlaneLength(in.readUnsignedShort());
                }

                builder.setWidth(in.readUnsignedShort())
                       .setHeight(in.readUnsignedShort());

                byte[] mapName = new byte[16];
                in.read(mapName, 0, mapName.length);

                builder.setName(StringUtils.trim(new String(mapName, "US-ASCII").replaceAll("\\P{Print}", "")));

                maps.add(builder.build());
            }

            in.close();
        } catch (FileNotFoundException e) {
            throw new MapLoaderException("Caught exception while loading map data", e);
        } catch (IOException e1) {
            throw new MapLoaderException("Caught exception while loading map data", e1);
        }

        return maps;
    }

    public WolfensteinMap loadMap(WolfensteinMap map) throws MapLoaderException {

        try {
            LittleEndianRandomAccessFile in = new LittleEndianRandomAccessFile(gameMapsFile, "r");

            int[][] planeValues = new int[2][];

            for (int plane = 0; plane < NUMBER_OF_PLANES; plane++) {
                int pos = map.getPlaneStart().get(plane);
                int compressed = map.getPlaneLength().get(plane);

                in.seek(pos);

                int expandedLength = in.readUnsignedShort();
                byte[] buf = new byte[compressed];

                in.read(buf, 0, compressed);

                int[] expandedBuf = CompressionUtils.carmackExpand(buf, expandedLength);
                expandedBuf = CompressionUtils.rleExpand(expandedBuf, 1, SIZE_OF_MAP, headerData.getRlewTag());

                planeValues[plane] = expandedBuf;
            }

            WolfensteinMap.Builder builder = new WolfensteinMap.Builder(map);
            buildTileInfo(planeValues[0], builder);

            in.close();

            return builder.build();

        } catch(IOException e) {
            throw new MapLoaderException(String.format("Caught exception trying to load map: %s", map.getName()));
        }
    }

    private WolfensteinMap.Builder  buildTileInfo(int[] planeData, WolfensteinMap.Builder builder) {
        for (int i = 0; i < 64; i++) {
            Map<Integer, Integer> tileInfoRow = new HashMap<Integer, Integer>();

            for (int j = 0; j < 64; j++) {
                tileInfoRow.put(j, planeData[(i * 64) + j]);
            }

            builder.putTileData(i, tileInfoRow);
        }

        return builder;
    }

    public WolfensteinMapHeader getHeaderData() {
        return this.headerData;
    }

    public List<WolfensteinMap> getMapInfo() {
        return Collections.unmodifiableList(this.mapInfo);
    }
}







