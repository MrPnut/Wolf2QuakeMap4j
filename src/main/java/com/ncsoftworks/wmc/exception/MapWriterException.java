package com.ncsoftworks.wmc.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Wrapper exception for map writers
 */

public class MapWriterException extends Exception {
    public MapWriterException(String message) {
        super(message);
    }

    public MapWriterException(String message, Throwable t) {
        super(message, t);
    }
}
