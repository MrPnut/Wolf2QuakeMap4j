package com.ncsoftworks.wmc.cli;

import com.ncsoftworks.wmc.bean.GameFiles;
import com.ncsoftworks.wmc.cli.options.CommonsCliOptionsAdapter;
import com.ncsoftworks.wmc.cli.options.ConverterOptions;
import com.ncsoftworks.wmc.cli.options.OptionsFactory;
import com.ncsoftworks.wmc.util.Constants;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String ... args) throws ParseException {
        Options options = OptionsFactory.createOptions();

        CommandLineParser parser = new GnuParser();
        CommandLine commandLine = parser.parse(options, args);

        if (commandLine.hasOption(Constants.OPTION_HELP)) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("wmc4j", OptionsFactory.createOptions());
            return;
        }

        ConverterOptions converterOptions = CommonsCliOptionsAdapter.createConverterOptions(commandLine);
        GameFiles files = GameFiles.fromConverterOptions(converterOptions);

        try {
            new CommandLineRunner(converterOptions, files).start();
        } catch(IllegalArgumentException e) {
            log.error("Caught exception", e);
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("wmc4j", OptionsFactory.createOptions());
        } catch(Exception e) {
            log.error("Caught exception", e);
        }
    }
}
