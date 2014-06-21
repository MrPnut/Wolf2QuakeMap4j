package com.ncsoftworks.wmc.bean;

import com.ncsoftworks.wmc.cli.options.ConverterOptions;
import com.ncsoftworks.wmc.util.Constants;
import com.ncsoftworks.wmc.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Exception thrown during map convert failure
 */

public class GameFiles {
    private File gameMapsFile;
    private File mapHeadFile;

    public File getGameMapsFile() {
        return gameMapsFile;
    }

    public File getMapHeadFile() {
        return mapHeadFile;
    }

    public GameFiles(File gameMapsFile, File mapHeadFile) {
        this.gameMapsFile = gameMapsFile;
        this.mapHeadFile = mapHeadFile;
    }

    public static GameFiles fromConverterOptions(ConverterOptions converterOptions) {
        File gameMapsFile = locateFile(Constants.GAME_MAP_FILE,
                                       converterOptions.getGameMapsLocation(),
                                       converterOptions.getDirectory());
        File mapHeadFile = locateFile(Constants.MAP_HEADER_FILE,
                                      converterOptions.getMapHeadLocation(),
                                      converterOptions.getDirectory());

        return new GameFiles(gameMapsFile, mapHeadFile);
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
