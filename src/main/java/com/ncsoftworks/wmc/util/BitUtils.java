package com.ncsoftworks.wmc.util;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Other bit twiddling utilities that aren't categorized
 */
public class BitUtils {
    // Returns uint16_t from first two elements of a byte array
    public static int readWord(byte[] source) {
        int high = source[0] &0xff;
        int low = source[1] &0xff;

        return high | low << 8;
    }
}
