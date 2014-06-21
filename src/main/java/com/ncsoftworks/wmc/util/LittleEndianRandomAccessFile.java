package com.ncsoftworks.wmc.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class LittleEndianRandomAccessFile {
    private final RandomAccessFile raf;
    private final byte w[];

    public LittleEndianRandomAccessFile(File file, String mode) throws FileNotFoundException {
        this.raf = new RandomAccessFile(file, mode);
        w = new byte[8];
    }

    public final short readShort() throws IOException
    {
        raf.readFully(w, 0, 2);
        return (short)(
                (w[1]&0xff) << 8 |
                        (w[0]&0xff));
    }

    public final int readBigUnsignedShort() throws IOException
    {
        return raf.readUnsignedShort();
    }

    public final short readBigShort() throws IOException
    {
        return raf.readShort();
    }

    public final int readUnsignedShort() throws IOException
    {
        raf.readFully(w, 0, 2);
        return (
                (w[1]&0xff) << 8 |
                        (w[0]&0xff));
    }

    public final char readChar() throws IOException
    {
        raf.readFully(w, 0, 2);
        return (char) (
                (w[1]&0xff) << 8 |
                        (w[0]&0xff));
    }

    public final int readInt() throws IOException
    {
        raf.readFully(w, 0, 4);
        return
                (w[3])      << 24 |
                        (w[2]&0xff) << 16 |
                        (w[1]&0xff) <<  8 |
                        (w[0]&0xff);
    }

    public final long readLong() throws IOException
    {
        raf.readFully(w, 0, 8);
        return
                (long)(w[7])      << 56 |
                        (long)(w[6]&0xff) << 48 |
                        (long)(w[5]&0xff) << 40 |
                        (long)(w[4]&0xff) << 32 |
                        (long)(w[3]&0xff) << 24 |
                        (long)(w[2]&0xff) << 16 |
                        (long)(w[1]&0xff) <<  8 |
                        (long)(w[0]&0xff);
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    public final int read(byte b[], int off, int len) throws IOException {
        return raf.read(b, off, len);
    }

    public final void readFully(byte b[]) throws IOException {
        raf.readFully(b, 0, b.length);
    }

    public final void readFully(byte b[], int off, int len) throws IOException {
        raf.readFully(b, off, len);
    }

    public final int skipBytes(int n) throws IOException {
        return raf.skipBytes(n);
    }

    public final boolean readBoolean() throws IOException {
        return raf.readBoolean();
    }

    public final byte readByte() throws IOException {
        return raf.readByte();
    }

    public final int readUnsignedByte() throws IOException {
        return raf.readUnsignedByte();
    }

    public final void close() throws IOException {
        raf.close();
    }

    public void seek(int pos) throws IOException {
        raf.seek(pos);
    }
}
