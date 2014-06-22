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

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Command that can be invoked to convert all maps to quake .map text files based on options passed in
 */

public class ConvertAllCommand implements Command {

    private static final Logger log = LoggerFactory.getLogger(ConvertAllCommand.class);

    private static final String EXCEPTION_GENERAL = "Caught exception while converting map";
    private static final String MISSING_TEXTURE_MAPPINGS = "A texture mapping .properties file could not be found, or accessed.  " +
            "If you would like to map Quake textures to tiles, please specify one with command line arguments.";

    private static final String ERROR_PROPERTIES_LOAD = "Caught exception loading properties, ignoring resource";

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
        Properties textureMappings = loadTextureMappings(gameFiles.getTextureMappingPropertiesFile());

        try {
            WolfensteinMapLoader loader = new WolfensteinMapLoader(gameFiles.getMapHeadFile(), gameFiles.getGameMapsFile());
            QuakeMapConverter converter = new QuakeMapConverter(converterOptions.getWadFileName(), textureMappings);

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

    private static Properties loadTextureMappings(File textureMappingsFile) {

        if (textureMappingsFile == null || !textureMappingsFile.exists()) {
            log.warn(MISSING_TEXTURE_MAPPINGS);
            return new Properties();
        }

        log.info(String.format("Loading properties file from file system location: %s", textureMappingsFile.getAbsolutePath()));

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(textureMappingsFile);

            Properties properties = new Properties();
            properties.load(inputStream);

            return properties;
        } catch (FileNotFoundException e) {
            log.warn(ERROR_PROPERTIES_LOAD, e);
            return new Properties();
        } catch (IOException e) {
            log.warn(ERROR_PROPERTIES_LOAD, e);
            return new Properties();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn(ERROR_PROPERTIES_LOAD, e);
                }
            }
        }
    }
}
