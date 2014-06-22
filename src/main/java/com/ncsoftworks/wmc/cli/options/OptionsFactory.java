package com.ncsoftworks.wmc.cli.options;

import com.ncsoftworks.wmc.util.Constants;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Options you can pass to the command line
 */

public class OptionsFactory {

    public static Options createOptions() {
        Options options = new Options();

        options.addOption(OptionBuilder.withLongOpt(Constants.OPTION_DIRECTORY)
                                       .withDescription("sets the directory to look for wolfenstein files in (defaults to ./)")
                                       .hasArg()
                                       .withArgName("DIRECTORY")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.OPTION_GAMEMAPS)
                                       .withDescription("sets the ABSOLUTE location to the gamemaps file (overrides --directory) (defaults to ./gamemaps.wl6)")
                                       .hasArg()
                                       .withArgName("GAMEMAPS")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.OPTION_MAPHEAD)
                                       .withDescription("sets the ABSOLUTE location of the maphead file (overrides --directory) (defaults to ./maphead.wl6")
                                       .hasArg()
                                       .withArgName("MAPHEAD")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.COMMAND_DISPLAY)
                                       .withDescription("displays the map file information loaded from the gamemaps and maphead files")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.COMMAND_CONVERT_ALL)
                                       .withDescription("convert all maps to the location specified by output-directory")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.OPTION_OUTPUT_DIRECTORY)
                                       .withDescription("the directory that maps will be output to (default to ./)")
                                       .hasArg()
                                       .withArgName("directory")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.COMMAND_HELP)
                                       .withDescription("print this message")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.WAD_FILE_NAME)
                                       .hasArg()
                                       .withArgName("wad")
                                       .withDescription("the file location of a wad file for .map's (defaults to base.wad)")
                                       .create());

        options.addOption(OptionBuilder.withLongOpt(Constants.OPTION_TEXTURE_MAPPING_FILE)
                                       .withDescription("the location to a texture mapping properties file")
                                       .hasArg()
                                       .withArgName("properties")
                                       .create());

        return options;
    }
}
