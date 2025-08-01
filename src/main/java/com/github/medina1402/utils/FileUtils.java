package com.github.medina1402.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void WriteToFile(String fullPath, String content) throws IOException {
        File file = new File(fullPath);
        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new IOException("No se pudo crear la carpeta: " + parentDir.getAbsolutePath());
            }
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    public static void CreateFolder(String fullPath) throws IOException {
        File file = new File(fullPath);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new IOException("No se pudo crear la carpeta: " + fullPath);
            }
        }
    }
}
