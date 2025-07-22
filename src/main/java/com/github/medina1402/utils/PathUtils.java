package com.github.medina1402.utils;

import java.io.File;

public class PathUtils {
    public static String FormatDirectoryPath(String path) {
        if (path == null || path.isBlank()) return "";
        String separator = File.separator;
        if (!path.endsWith(separator)) {
            return path + separator;
        }
        return path;
    }
}
