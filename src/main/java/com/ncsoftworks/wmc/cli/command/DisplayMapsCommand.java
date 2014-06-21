package com.ncsoftworks.wmc.cli.command;

import com.ncsoftworks.wmc.bean.GameFiles;
import com.ncsoftworks.wmc.bean.WolfensteinMap;
import com.ncsoftworks.wmc.cli.options.ConverterOptions;
import com.ncsoftworks.wmc.exception.MapLoaderException;
import com.ncsoftworks.wmc.loader.WolfensteinMapLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Command that may be invoked to display all maps
 */

public class DisplayMapsCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(DisplayMapsCommand.class);

    private final GameFiles gameFiles;
    private final ConverterOptions converterOptions;

    public DisplayMapsCommand(GameFiles gameFiles, ConverterOptions converterOptions) {
        log.info("Constructing instance");
        this.gameFiles = gameFiles;
        this.converterOptions = converterOptions;
    }

    @Override
    public void execute(List<String> args) {
        log.info("Executing command: " + this.getClass().getCanonicalName());

        try {
            WolfensteinMapLoader loader = new WolfensteinMapLoader(gameFiles.getMapHeadFile(), gameFiles.getGameMapsFile());
            List<WolfensteinMap> maps = loader.getMapInfo();

            log.info("===================");
            log.info("Displaying map info");
            log.info("===================");

            log.info(String.format("%-15s %5s %5s", "Map Name", "Width", "Height"));

            for (WolfensteinMap map : maps) {
                log.info(String.format("%15s %5s %5s", map.getName(), map.getWidth(), map.getHeight()));
            }

        } catch (MapLoaderException e) {
            e.printStackTrace();
        }
    }
}
