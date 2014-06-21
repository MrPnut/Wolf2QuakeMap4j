package com.ncsoftworks.wmc.cli.options;

import com.ncsoftworks.wmc.util.Constants;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Adapter / Converter for Apache Commons CLI to application specific options
 */

public class CommonsCliOptionsAdapter {
    public static ConverterOptions createConverterOptions(CommandLine commandLine) {
        ConverterOptions.Builder builder = new ConverterOptions.Builder();

        builder.setDirectory(commandLine.getOptionValue(Constants.OPTION_DIRECTORY))
               .setGameMapsLocation(commandLine.getOptionValue(Constants.OPTION_GAMEMAPS))
               .setMapHeadLocation(commandLine.getOptionValue(Constants.OPTION_MAPHEAD))
               .setWadFileName(commandLine.getOptionValue(Constants.WAD_FILE_NAME))
               .setOutputDirectory(commandLine.getOptionValue(Constants.OPTION_OUTPUT_DIRECTORY));


        Map<String, List<String>> commands = new HashMap<String, List<String>>();

        for (Option option : commandLine.getOptions()) {
            String argName = option.getLongOpt().toLowerCase();
            if ((!argName.equals(Constants.OPTION_DIRECTORY))
                && !argName.equals(Constants.OPTION_GAMEMAPS)
                && !argName.equals(Constants.OPTION_MAPHEAD)
                    && !argName.equals(Constants.WAD_FILE_NAME)
                    && !argName.equals(Constants.OPTION_OUTPUT_DIRECTORY)) {

                commands.put(argName, option.getValues() == null
                        ? new ArrayList<String>() : Arrays.asList(option.getValues()));
            }
        }

        return builder.setCommands(commands).build();
    }
}
