package com.ncsoftworks.wmc.cli.command;

import com.ncsoftworks.wmc.exception.CommandExecutionException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Time: 10:42 PM
 */

public interface Command {
    public void execute(List<String> args) throws CommandExecutionException;
}
