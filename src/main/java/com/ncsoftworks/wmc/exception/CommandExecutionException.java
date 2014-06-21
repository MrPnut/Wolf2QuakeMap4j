package com.ncsoftworks.wmc.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Exception wrapper thrown for java.lang.reflect failures or command body exceptions
 */
public class CommandExecutionException extends Exception {
    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable t) {
        super(message, t);
    }
}
