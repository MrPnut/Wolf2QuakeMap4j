package com.ncsoftworks.wmc.bean;

import com.ncsoftworks.wmc.cli.options.ConverterOptions;
import com.ncsoftworks.wmc.util.Constants;
import com.ncsoftworks.wmc.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class GameFiles {
    private final File gameMapsFile;
    private final File mapHeadFile;
    private final File textureMappingPropertiesFile;

    public File getGameMapsFile() {
        return gameMapsFile;
    }

    public File getMapHeadFile() {
        return mapHeadFile;
    }

    public File getTextureMappingPropertiesFile() {
        return textureMappingPropertiesFile;
    }

    public GameFiles(File gameMapsFile, File mapHeadFile, File textureMappingPropertiesFile) {
        this.gameMapsFile = gameMapsFile;
        this.mapHeadFile = mapHeadFile;
        this.textureMappingPropertiesFile = textureMappingPropertiesFile;
    }

    public static GameFiles fromConverterOptions(ConverterOptions converterOptions) {
        File gameMapsFile = locateFile(Constants.GAME_MAP_FILE,
                                       converterOptions.getGameMapsLocation(),
                                       converterOptions.getDirectory());
        File mapHeadFile = locateFile(Constants.MAP_HEADER_FILE,
                                      converterOptions.getMapHeadLocation(),
                                      converterOptions.getDirectory());

        File textureMappingPropertiesFile = locateFile(Constants.TEXTURE_MAPPING_PROPERTIES_FILE,
                                                       converterOptions.getTextureMappingLocation(),
                                                       converterOptions.getDirectory());

        return new GameFiles(gameMapsFile, mapHeadFile, textureMappingPropertiesFile);
    }

    private static File locateFile(String fileName, String fileLocation, String directory) {

        File file = null;

        if (StringUtils.isNotBlank(fileLocation)) {
            file = new File(fileLocation);
        }

        if (file != null && file.exists()) {
            return file;
        }

        if (StringUtils.isNotBlank(directory)) {
            directory = FileUtils.normalizeDirectoryName(directory);

            file = new File(String.format("%s/%s", directory, fileName));
        }

        if (file != null && file.exists()) {
            return file;
        }

        return new File(fileName);
    }

}
