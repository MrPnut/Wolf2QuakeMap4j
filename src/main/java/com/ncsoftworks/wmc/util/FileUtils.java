package com.ncsoftworks.wmc.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Nick
 */

public class FileUtils {
    public static String normalizeDirectoryName(String directoryName) {
        directoryName = StringUtils.removeEnd(directoryName, "/");
        directoryName = StringUtils.removeEnd(directoryName, "\\");

        return directoryName;
    }
}
