package com.ncsoftworks.wmc.util;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Bit twiddling Wolf3D compression routines converted to Java
 */
public class CompressionUtils {
    private static final int NEAR = 0xa7;
    private static final int FAR = 0xa8;

    public static int[] carmackExpand(byte[] source, int length) {
        // We want uint16_t's. But a java short can't hold them.  Keep the number of them the same, though.
        int[] output = new int[length /2];
        int inptr = 0;
        int outptr = 0;

        length /=2;

        while(length > 0) {
            int ch = BitUtils.readWord(new byte[] { source[inptr], source[inptr +1] } );
            int chhigh = ch >> 8;
            inptr +=2;

            if (chhigh == NEAR) {
                int count = ch &0xff;

                if (count == 0) {
                    ch |= source[inptr++];
                    output[outptr++] = ch;
                    length--;
                }

                else {
                    int offset = source[inptr++] &0xff;
                    int copyptr = outptr - offset;
                    length -= count;

                    if (length <0 ) {
                        return output;
                    }

                    for (; count > 0; count--) {
                        output[outptr++] = output[copyptr++] &0xffff;
                    }
                }
            }

            else if (chhigh == FAR) {
                int count = ch &0xff;

                if (count == 0) {
                    ch |= source[inptr++];
                    output[outptr++] = ch;
                    length--;
                }

                else {
                    int offset = BitUtils.readWord(new byte[] { source[inptr], source[inptr+1] } );
                    inptr += 2;
                    length -= count;

                    if (length < 0)
                        return output;

                    for (; count > 0; count--) {
                        output[outptr++] = output[offset++];
                    }
                }
            }

            else {
                output[outptr++] = ch;
                length--;
            }
        }

        return output;
    }

    public static int[] rleExpand(int[] source, int beginningIndex, int length, int rlewtag) {

        int si = beginningIndex;
        int di = 0;

        int[] output = new int[length];

        do {
            int value = source[si++];

            if (value != rlewtag) {
                output[di++] = value;
            }

            else {
                int count = source[si++];
                int expandedVal = source[si++];

                for (int i=1; i<=count; i++) {
                    output[di++] = expandedVal;
                }
            }
        }

        while(di < length / 2);

        return output;
    }
}
