package com.ncsoftworks.wmc.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Exception thrown if failure occurs during map loading
 */
public class MapLoaderException extends Exception{
    public MapLoaderException(String message) {
        super(message);
    }

    public MapLoaderException(String message, Exception e) {
        super(message, e);
    }

    public MapLoaderException(Throwable t) { super(t); }
}
