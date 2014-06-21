package com.ncsoftworks.wmc.writer;

import com.ncsoftworks.wmc.bean.Plane;
import com.ncsoftworks.wmc.bean.Point;
import com.ncsoftworks.wmc.bean.QuakeBrush;
import com.ncsoftworks.wmc.bean.QuakeMap;
import com.ncsoftworks.wmc.exception.MapWriterException;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Writes out a Quake map file object to a text file
 */

public class QuakeMapWriter {

    private static final String EXCEPTION_MSG = "Caught exception writing file: %s";

    public static void writeToFile(QuakeMap quakeMap, File outputFile) throws MapWriterException {
        try {
            FileOutputStream outStream = new FileOutputStream(outputFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outStream);
            BufferedWriter out = new BufferedWriter(outputStreamWriter);

            // Begin header

            out.write("{\n");
            out.write("\"sounds\" \"" + quakeMap.getSounds() + "\"\n");
            out.write("\"classname\" \"" + QuakeMap.WORLDSPAWN_CLASSNAME + "\"\n");
            out.write("\"wad\" \"" + quakeMap.getWad()+ "\"\n");
            out.write("\"worldtype\" \"" + quakeMap.getWorldType()+ "\"\n");

            // End header

            // Begin brushes

            for (QuakeBrush brush : quakeMap.getBrushList()) {
                out.write("{\n");

                for (Plane<Point> plane : brush.getPlanes()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("  ");

                    for (Point<Integer> point : plane.getPoints()) {
                        sb.append("( ");
                        sb.append(point.getX() + " " + point.getY() + " " + point.getZ());
                        sb.append(" ) ");
                    }

                    out.write(sb.toString() + "GROUND1_8 " + "0 " + "0 " + "0 " + "1.0 " + "1.0\n");
                }

                out.write("}\n");
            }

            out.write("}\n");
            out.close();

        } catch (FileNotFoundException e) {
            throw new MapWriterException(String.format(EXCEPTION_MSG, outputFile.getAbsolutePath()), e);
        } catch (IOException e) {
            throw new MapWriterException(String.format(EXCEPTION_MSG, outputFile.getAbsolutePath()), e);
        }
    }
}
