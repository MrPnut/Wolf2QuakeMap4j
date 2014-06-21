package com.ncsoftworks.wmc.cli;

import com.ncsoftworks.wmc.bean.GameFiles;
import com.ncsoftworks.wmc.cli.command.Command;
import com.ncsoftworks.wmc.cli.command.ConvertAllCommand;
import com.ncsoftworks.wmc.cli.command.DisplayMapsCommand;
import com.ncsoftworks.wmc.cli.options.ConverterOptions;
import com.ncsoftworks.wmc.exception.CommandExecutionException;
import com.ncsoftworks.wmc.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */
public class CommandLineRunner  {

    private static final Logger log = LoggerFactory.getLogger(CommandLineRunner.class);

    private static final String REFLECTION_EXCEPTION_MSG = "Caught exception while invoking method or constructing object";

    private static final Map<String, Class<?>> commandMap;

    private final ConverterOptions converterOptions;
    private final GameFiles gameFiles;

    static {
        Map<String, Class<?>> tempCommandMap = new HashMap<String, Class<?>>();

        tempCommandMap.put(Constants.COMMAND_DISPLAY, DisplayMapsCommand.class);
        tempCommandMap.put(Constants.COMMAND_CONVERT_ALL, ConvertAllCommand.class);

        commandMap = Collections.unmodifiableMap(tempCommandMap);
    }

    public CommandLineRunner(ConverterOptions options, GameFiles gameFiles) throws IllegalArgumentException {

        if (options == null) {
            throw new IllegalArgumentException("ConverterOptions must be initialized and present");
        }

        assertFileExists(gameFiles.getGameMapsFile(), "GameMaps file non existent or unreadable");
        assertFileExists(gameFiles.getMapHeadFile(), "MapHead file non existent or unreadable");

        this.converterOptions = options;
        this.gameFiles = gameFiles;
    }

    public void start() throws CommandExecutionException {
        displayBanner();
        log.info("Game files have been located");

        for (Map.Entry<String, List<String>> entry : converterOptions.getCommands().entrySet()) {
            Command command = loadCommand(entry.getKey().toLowerCase());

            if (command == null) {
                log.error(String.format("Command not found: %s", entry.getKey().toLowerCase()));
                continue;
            }

            command.execute(entry.getValue());
        }
    }

    private Command loadCommand(String commandName) throws CommandExecutionException {
        log.info("Loading command: " + commandName);

        Constructor constructor;

        try {
            Class<?> commandClass = commandMap.get(commandName);

            if (commandClass == null) {
                return null;
            }

            constructor = commandMap.get(commandName).getConstructor(GameFiles.class, ConverterOptions.class);
            log.info(String.format("Constructor found: %s", constructor));

        } catch (NoSuchMethodException e) {
            throw new CommandExecutionException(REFLECTION_EXCEPTION_MSG, e);
        }
        try {
            return (Command) constructor.newInstance(gameFiles, converterOptions);
        } catch (InstantiationException e) {
            throw new CommandExecutionException(REFLECTION_EXCEPTION_MSG, e);
        } catch (IllegalAccessException e) {
            throw new CommandExecutionException(REFLECTION_EXCEPTION_MSG, e);
        } catch (InvocationTargetException e) {
            throw new CommandExecutionException(REFLECTION_EXCEPTION_MSG, e);
        }
    }

    private static void displayBanner() {
        System.out.println(" _    ____  ________   ___ _ ");
        System.out.println("| |  | |  \\/  /  __ \\ /   (_)");
        System.out.println("| |  | | .  . | /  \\// /| |_ ");
        System.out.println("| |/\\| | |\\/| | |   / /_| | |");
        System.out.println("\\  /\\  / |  | | \\__/\\___  | |");
        System.out.println(" \\/  \\/\\_|  |_/\\____/   |_/ |");
        System.out.println("                         _/ |");
        System.out.println("                        |__/ ");

        System.out.println("Wolfenstein 3d -> Quake Map converter");
        System.out.println("2013-2014, Nick [MrPeanut] Cavallo");
        System.out.println("=====================================\n\n");
    }

    private static void assertFileExists(File file, String errorMsg) throws IllegalArgumentException {
        if (file == null) {
            throw new IllegalArgumentException(errorMsg);
        }

        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("%s: (%s)", errorMsg, file.getAbsolutePath()));
        }
    }
}
