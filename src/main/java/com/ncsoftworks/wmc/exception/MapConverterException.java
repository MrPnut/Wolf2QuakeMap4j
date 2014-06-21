package com.ncsoftworks.wmc.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Exception thrown during map convert failure
 */
public class MapConverterException extends Exception {
    public MapConverterException(String msg) {
        super(msg);
    }

    public MapConverterException(String msg, Exception e) {
        super(msg, e);
    }
}
