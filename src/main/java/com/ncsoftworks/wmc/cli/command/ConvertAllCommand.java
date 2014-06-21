package com.ncsoftworks.wmc.cli.command;

import com.ncsoftworks.wmc.bean.GameFiles;
import com.ncsoftworks.wmc.bean.QuakeMap;
import com.ncsoftworks.wmc.bean.WolfensteinMap;
import com.ncsoftworks.wmc.cli.options.ConverterOptions;
import com.ncsoftworks.wmc.converter.QuakeMapConverter;
import com.ncsoftworks.wmc.exception.CommandExecutionException;
import com.ncsoftworks.wmc.exception.MapConverterException;
import com.ncsoftworks.wmc.exception.MapLoaderException;
import com.ncsoftworks.wmc.exception.MapWriterException;
import com.ncsoftworks.wmc.loader.WolfensteinMapLoader;
import com.ncsoftworks.wmc.util.FileUtils;
import com.ncsoftworks.wmc.writer.QuakeMapWriter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Command that can be invoked to convert all maps to quake .map text files based on options passed in
 */

public class ConvertAllCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ConvertAllCommand.class);

    private static final String EXCEPTION_GENERAL = "Caught exception while converting map";

    private ConverterOptions converterOptions;
    private GameFiles gameFiles;

    public ConvertAllCommand(GameFiles gameFiles, ConverterOptions converterOptions) {
        log.info("Constructing instance");
        this.converterOptions = converterOptions;
        this.gameFiles = gameFiles;
    }

    @Override
    public void execute(List<String> args) throws CommandExecutionException {
        log.info("Executing command: " + this.getClass().getCanonicalName());

        String directory = FileUtils.normalizeDirectoryName(converterOptions.getOutputDirectory());

        try {
            WolfensteinMapLoader loader = new WolfensteinMapLoader(gameFiles.getMapHeadFile(), gameFiles.getGameMapsFile());
            QuakeMapConverter converter = new QuakeMapConverter(converterOptions.getWadFileName());

            for (WolfensteinMap map : loader.getMapInfo()) {
                map = loader.loadMap(map);
                QuakeMap quakeMap = converter.convert(map);

                File outputFile = new File(String.format("%s/%s.%s", directory, StringUtils.trim(map.getName()), "map"));

                log.info(String.format("Writing output file: %s", outputFile.getAbsolutePath()));
                QuakeMapWriter.writeToFile(quakeMap, outputFile);
            }

        } catch (MapLoaderException e) {
            throw new CommandExecutionException(EXCEPTION_GENERAL, e);
        } catch (MapConverterException e) {
            throw new CommandExecutionException(EXCEPTION_GENERAL, e);
        } catch (MapWriterException e) {
            throw new CommandExecutionException(EXCEPTION_GENERAL, e);
        }
    }
}
