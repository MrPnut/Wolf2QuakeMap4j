package com.ncsoftworks.wmc.cli.options;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 * Option abstraction and builder in case I want to do another UI
 */

public class ConverterOptions {
    private final Map<String, List<String>> commands;

    private final String directory;
    private final String gameMapsLocation;
    private final String mapHeadLocation;
    private final String textureMappingLocation;
    private final String wadFileName;
    private final String outputDirectory;

    public ConverterOptions(String directory,
                            String gameMapsLocation,
                            String mapHeadLocation,
                            String textureMappingLocation,
                            String wadFileName,
                            String outputDirectory,
                            Map<String, List<String>> commands) {

        this.directory = directory;
        this.gameMapsLocation = gameMapsLocation;
        this.mapHeadLocation = mapHeadLocation;
        this.textureMappingLocation = textureMappingLocation;
        this.commands = commands;
        this.wadFileName = wadFileName;
        this.outputDirectory = outputDirectory == null ? "./" : outputDirectory;

    }

    public Map<String, List<String>> getCommands() {
        return commands;
    }

    public String getDirectory() {
        return directory;
    }

    public String getGameMapsLocation() {
        return gameMapsLocation;
    }

    public String getMapHeadLocation() {
        return mapHeadLocation;
    }

    public String getTextureMappingLocation() {
        return textureMappingLocation;
    }

    public String getWadFileName() { return wadFileName; }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public static class Builder {
        private Map<String, List<String>> commands;

        private String directory;
        private String gameMapsLocation;
        private String mapHeadLocation;
        private String textureMappingLocation;
        private String wadFileName;
        private String outputDirectory;

        public Builder setCommands(Map<String, List<String>> commands) {
            this.commands = commands;
            return this;
        }

        public Builder setDirectory(String directory) {
            this.directory = directory;
            return this;
        }

        public Builder setGameMapsLocation(String gameMapsLocation) {
            this.gameMapsLocation = gameMapsLocation;
            return this;
        }

        public Builder setMapHeadLocation(String mapHeadLocation) {
            this.mapHeadLocation = mapHeadLocation;
            return this;
        }

        public Builder setWadFileName(String wadFileName) {
            this.wadFileName = wadFileName;
            return this;
        }

        public Builder setTextureMappingLocation(String textureMappingLocation) {
            this.textureMappingLocation = textureMappingLocation;
            return this;
        }

        public Builder setOutputDirectory(String outputDirectory) {
            this.outputDirectory = outputDirectory;
            return this;
        }

        public ConverterOptions build() {
            return new ConverterOptions(directory,
                                        gameMapsLocation,
                                        mapHeadLocation,
                                        textureMappingLocation,
                                        wadFileName,
                                        outputDirectory,
                                        Collections.unmodifiableMap(commands));
        }
    }
}
